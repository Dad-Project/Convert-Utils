package fr.rowlaxx.convertutils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

public class Converter {

	//Variables
	private final Map<String, SimpleConverter<?>> simpleConverters = new HashMap<>();
	private final Map<String, InnerConverter<?>> innerConverters = new HashMap<>();
	
	//Constructeurs
	Converter(){}
	
	//Methodes
	void putSimpleConverter(AbstractConverter<?> ac) {
		ac.setMainConverter(this);
		final String destination = ac.destinationClass().getName();
		
		if (ac instanceof SimpleConverter)
			this.simpleConverters.put(destination, (SimpleConverter<?>)ac);
		else
			this.innerConverters.put(destination, (InnerConverter<?>)ac);
	}
	
	//Convert
	public final <T, E extends T> E convert(Object object, Class<T> destination) {
		return convert(object, (Type)destination);
	}
	
	@SuppressWarnings("unchecked")
	public final <T> T convert(Object object, Type destination) {
		Objects.requireNonNull(destination, "destination may not be null.");

		if (object == null)
			return null;
				
		Class<?> temp = null;
		if (destination instanceof Class) {
			temp = (Class<?>)destination;
			if (temp.isPrimitive()) {
				temp = ReflectionUtils.toWrapper(temp);
				destination = temp;
			}
			
			if (object.getClass() == temp)
				return (T)object;
		}
		else if (destination instanceof ParameterizedClass)
			temp = ((ParameterizedClass)destination).getRawType();
		else
			throw new ConverterException("Bad type : " + destination.getClass());
		
		
		
		
		final SimpleConverter<?> sc = simpleConverters.get(temp.getName());
		if (sc != null)
			return (T)sc.convert(object, destination);
		
		InnerConverter<?> ic;
		while (temp != null) {
			if ( (ic = innerConverters.get(temp.getName())) != null)
				return (T)ic.convert(object, destination);
			
			try {
				return proccessInterfaces(temp, object, destination);
			}catch(UnsupportedOperationException e) {
				temp = temp.getSuperclass();
			}
		}
		
		throw new ConverterException("No abstract converter found for converting " + object.getClass() + " to " + destination);
	}
	
	@SuppressWarnings("unchecked")
	private final <T> T proccessInterfaces(Class<?> _interface, Object object, Type destination) {
		InnerConverter<?> ic;
		final Class<?>[] in = _interface.getInterfaces();
		for (int i = 0 ; i < in.length ; i++)
			if ( (ic = innerConverters.get(in[i].getName())) != null )
				return (T)ic.convert(object, destination);
		
		for (int i = 0 ; i < in.length ; i++)
			try {
				return proccessInterfaces(in[i], object, destination);
			}catch(UnsupportedOperationException e) {
				continue;
			}
		
		throw new UnsupportedOperationException();
	}
}