package fr.rowlaxx.convertutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

public abstract class AbstractConverter<T> {

	//Variables
	private Converter converter;//Will be assigned when adding to a converter
	private final Class<T> destination;

	//Constructeurs
	@SuppressWarnings("unchecked")
	protected AbstractConverter(Class<T> destination) {
		this.destination = (Class<T>) ReflectionUtils.toWrapper( Objects.requireNonNull(destination, "destination may not be null.") );
		
		for (Method method : ReflectionUtils.getAllMethods(getClass(), ConvertMethod.class))
			initMethod(method);
	}
	
	//Verify
	protected abstract void initMethod(Method method);
	
	@SuppressWarnings("unchecked")
	protected final <E extends T> E tryInvoke(Method method, Object... params){
		try {
			return (E)method.invoke(this, params);
		} catch (InvocationTargetException e) {
			throw new ConverterException(e.getCause());
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
			if (canReturnInnerType() ) {
				if (!this.destination.isAssignableFrom(temp))
					throw new ConverterException(destination + " do not inherit " + this.destination);
			}
			else if (this.destination != temp)
				throw new ConverterException("the destination is not " + this.destination);
		}
		
		System.out.println(object.getClass() + "   " + object + "   " + destination);
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
