package fr.rowlaxx.convertutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import fr.rowlaxx.utils.generic.ReflectionUtils;
import fr.rowlaxx.utils.generic.destination.Destination;

public abstract class SimpleConverter<T> {

	//Variables
	Converter converter;
	private final Class<T> convertClass;
	private final boolean canReturnInnerType;
	private final HashMap<Integer, HashMap<Class<?>, ConvertMethod>> methods = new HashMap<>();

	//Constructeurs
	protected SimpleConverter(Class<T> convertClass) {
		final Return r = getClass().getAnnotation(Return.class);
		if (r == null)
			throw new ConverterException("The simple converter class " + getClass() + " must implement the annotation Return.");

		this.canReturnInnerType = r.canReturnInnerType();
		this.convertClass = convertClass;
		
		HashMap<Class<?>, ConvertMethod> map;
		ConvertMethod convertMethod;
		for (Method method : ReflectionUtils.getAllMethods(getClass(), Convert.class)) {
			if (method.getParameterCount() > 2)
				throw new ConverterException("A Convert method must have a maximum of 2 parameters.");
			if (method.getParameterCount() == 0)
				throw new ConverterException("A Convert method must have at least 1 parameter.");

			if (method.getParameterCount() == 2) {
				if (!canReturnInnerType)
					throw new ConverterException("A Convert method of a SimpleConverter that return no inner type must have 1 parameter.");
				if (method.getParameterTypes()[1] != Destination.class)
					throw new ConverterException("The second parameter must be a Destination.");
			}

			convertMethod = new ConvertMethod(method);
			if (methods.containsKey(-convertMethod.getPriority()))
				map = methods.get(-convertMethod.getPriority());
			else {
				map = new HashMap<>();
				methods.put(-convertMethod.getPriority(), map);
			}

			if(map.containsKey(convertMethod.getFirstParameterClass())) {
				ConvertMethod another = map.get(convertMethod.getFirstParameterClass());
				throw new ConverterException("The methods " + convertMethod.getName() + " and " + another.getName() + " have the same priority and parameters.");
			}

			map.put(convertMethod.getFirstParameterClass(), convertMethod);
		}

	}

	@SuppressWarnings("unchecked")
	public final <E extends T> E convert(Object object) {
		return (E) convert(object, null);
	}

	public final <E extends T> E convert(Object object, Destination<E> destination) {
		if (object == null)
			return null;

		if (!canReturnInnerType && destination != null)
			if (destination.getDestinationClass() != this.convertClass)
				throw new ConverterException("This instance cannot return child destinations.");

		E e;
		Class<?> clazz;
		for (HashMap<Class<?>, ConvertMethod> map : this.methods.values()) {
			clazz = object.getClass();
			do {
				e = processOne(object, destination, map.get(clazz));
				if (e != null)
					return e;

				for (Class<?> _interface : clazz.getInterfaces()) {
					e = processOne(object, destination, map.get(_interface));
					if (e != null)
						return e;
				}
			}while((clazz = clazz.getSuperclass()) != null);
		}

		throw new ConverterException("Unable to convert " + object.getClass() + " to " + destination);
	}

	@SuppressWarnings("unchecked")
	private final <E extends T> E processOne(Object object, Destination<E> destination, ConvertMethod method) {
		if (method == null)
			return null;
		
		try {
			if (method.getParameterCount() == 2)
				return (E) method.invoke(this, object, destination);
			else
				return (E) method.invoke(this, object);
		} catch (InvocationTargetException e) {
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	//Getters
	public boolean canReturnInnerType() {
		return canReturnInnerType;
	}
	
	public Converter getConverter() {
		return converter;
	}
	
	public Class<T> getConvertClass(){
		return this.convertClass;
	}
}
