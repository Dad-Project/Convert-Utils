package fr.rowlaxx.convertutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

public abstract class SimpleConverter<T> {

	//Variables
	private Converter converter;//Will be assigned when adding to a converter
	private final Class<T> destination;

	//Constructeurs
	protected SimpleConverter(Class<T> destination) {
		this.destination = Objects.requireNonNull(destination, "destination may not be null.");
		
		for (Method method : ReflectionUtils.getAllMethods(getClass(), ConvertMethod.class))
			initMethod(method);
	}
	
	//Verify
	protected abstract void initMethod(Method method);
	
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
	
	@SuppressWarnings("unchecked")
	protected final <E extends T> E tryInvoke(Method method, Object... params){
		try {
			return (E)method.invoke(this, params);
		} catch (InvocationTargetException e) {
			throw new ConverterException(e.getTargetException().getMessage());
		} catch (IllegalAccessException e) {
			throw new ConverterException("Unable to acces the method " + method);//Should not be thrown
		}
	}

	//Convert
	final <E extends T> E convert(Object object) {
		return convert(object, destination);
	}

	final <E extends T> E convert(Object object, Type destination) {		
		if (destination == null)
			destination = this.destination;
		else {
			@SuppressWarnings("unchecked")
			final Class<E> temp = (Class<E>)((destination instanceof Class) ? destination : ((ParameterizedClass)destination).getRawType());
			if (canReturnInnerType() && !this.destination.isAssignableFrom(temp))
				throw new ConverterException(destination + " do not inherit " + this.destination);
			else if (this.destination != temp)
				throw new ConverterException("the destination is not " + this.destination);
		}
		
		return proccess(object, destination);
	}
	
	protected abstract <E extends T> E proccess(Object object, Type destination);
	
	//Setters
	final void setMainConverter(Converter converter) {
		if (this.converter != null)
			throw new IllegalStateException("This SimpleConverter already has a Converter.");
		this.converter = converter;
	}

	//Getters
	public abstract boolean canReturnInnerType();
	
	public final Converter mainConverter() {
		return converter;
	}
	
	public final Class<T> destinationClass(){
		return this.destination; 
	}
}
