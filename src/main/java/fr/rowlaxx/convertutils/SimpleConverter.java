package fr.rowlaxx.convertutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

public abstract class SimpleConverter<T> {

	//Variables
	private Converter converter;//Will be assigned when adding to a converter
	
	private final Class<T> destination;
	private final boolean canReturnInnerType;
	private final TreeMap<Integer, HashMap<Class<?>, List<ConvertMethodWrapper>>> methods;

	//Constructeurs
	protected SimpleConverter(Class<T> destination) {
		Objects.requireNonNull(destination, "destination may not be null.");
		
		final Return r = getClass().getAnnotation(Return.class);
		if (r == null)
			throw new ConverterException("The simple converter class " + getClass() + " must implement the annotation Return.");

		this.canReturnInnerType = r.canReturnInnerType();
		this.destination = destination;
		this.methods = new TreeMap<>();
		
		//Vérification
		initConvertMethods();
	}
	
	//Verify
	private void initConvertMethods() {
		List<Method> convertMethods = ReflectionUtils.getAllMethods(getClass(), ConvertMethod.class);
		ConvertMethodWrapper wrapper;
		HashMap<Class<?>, List<ConvertMethodWrapper>> map;
		List<ConvertMethodWrapper> list;
		Class<?> firstParam;
		int priority;
		
		for (Method method : convertMethods) {
			//Vérification
			if (method.getParameterCount() > 2)
				throw new ConverterException("A Convert method must have a maximum of 2 parameters.");
			if (method.getParameterCount() == 0)
				throw new ConverterException("A Convert method must have at least 1 parameter.");

			if (method.getParameterCount() == 2) {
				if (!canReturnInnerType)
					throw new ConverterException("A Convert method of a SimpleConverter that return no inner type must have exactly 1 parameter.");
				if (method.getParameterTypes()[1] != Class.class && !ParameterizedClass.class.isAssignableFrom(method.getParameterTypes()[1]))
					throw new ConverterException("The second parameter must be a Class or a ParameterizedClass.");
			}
			
			//Ajout
			wrapper = new ConvertMethodWrapper(method);
			priority = -wrapper.getPriority();
			firstParam = ReflectionUtils.toWrapper(wrapper.getParameterTypes()[0]);
			
			if ( (map = methods.get(priority)) == null )
				methods.put(priority, map = new HashMap<>());
							
			if ( (list = map.get(firstParam)) == null)
				map.put(firstParam, list = new ArrayList<>());
			
			list.add(wrapper);
		}
	}

	//Convert
	@SuppressWarnings("unchecked")
	public final <E extends T> E convert(Object object) {
		return (E) convert(object, null);
	}

	@SuppressWarnings("unchecked")
	public final <E extends T> E convert(Object object, Type destination) {
		if (object == null)
			return null;
		
		if (destination == null)
			destination = (Class<E>)getDestinationClass();
		
		final Class<?> temp = destination instanceof Class ? (Class<?>)destination : ((ParameterizedClass)destination).getRawType();
		if (canReturnInnerType()) {
			if (!getDestinationClass().isAssignableFrom(temp))
				throw new ConverterException(destination + " do not inherit " + getDestinationClass());
		}
		else {
			if (temp != getDestinationClass())
				throw new ConverterException("the destination is not " + getDestinationClass());
		}
		
		E result;
		Class<?> clazz;
		Class<?>[] interfaces;
		for (HashMap<Class<?>, List<ConvertMethodWrapper>> map : this.methods.values()) {
			clazz = object.getClass();
			
			while(clazz != null) {
				if ( (result = invoke(map.get(clazz), object, destination)) != null)
					return result;
				
				interfaces = clazz.getInterfaces();
				for (Class<?> _interface : interfaces)
					if ( (result = invoke(map.get(_interface), object, destination)) != null)
						return result;
				
				clazz = clazz.getSuperclass();
			}
		}

		throw new ConverterException("Unable to convert " + object.getClass() + " to " + destination);
	}

	@SuppressWarnings("unchecked")
	private final <E extends T> E invoke(List<ConvertMethodWrapper> wrappers, Object object, Type destination) {
		if (wrappers == null)
			return null;
				
		for(ConvertMethodWrapper wrapper : wrappers)
			try {				
				if (wrapper.getParameterCount() == 2)
					return (E) wrapper.invoke(this, object, destination);
				else
					return (E) wrapper.invoke(this, object);
				
			}
			catch(InvocationTargetException e){
				e.getCause().printStackTrace();
				continue;
			}
			catch (IllegalAccessException | IllegalArgumentException e) {
				continue;
			}
		
		return null;
	}
	
	//Setters
	final void setConverter(Converter converter) {
		this.converter = converter;
	}

	//Getters
	public final boolean canReturnInnerType() {
		return canReturnInnerType;
	}
	
	public final Converter getConverter() {
		return converter;
	}
	
	public final boolean hasConverterParent() {
		return converter != null;
	}
	
	public final Class<T> getDestinationClass(){
		return this.destination;
	}
}
