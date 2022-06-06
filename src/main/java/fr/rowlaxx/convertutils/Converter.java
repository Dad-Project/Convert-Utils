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
	public final <T> T convert(Object object, Class<T> destination) {
		return convert(object, (Type)destination);
	}
	
	public final <T> T convert(Object object, Type destination) {
		Objects.requireNonNull(destination, "destination may not be null.");

		if (object == null)
			return null;
				
		Class<?> temp = destination instanceof Class ? (Class<?>)destination : ((ParameterizedClass)destination).getRawType();
		temp = ReflectionUtils.toWrapper(temp);
		
		final SimpleConverter<?> sc = simpleConverters.get(temp.getName());
		if (sc != null)
			return sc.convert(object, destination);
		
		InnerConverter<?> ic;
		while (temp != null) {
			if ( (ic = innerConverters.get(temp.getName())) != null)
				return ic.convert(object, destination);
			
			for (Class<?> _interface : temp.getInterfaces())
				if ( (ic = innerConverters.get(_interface.getName())) != null )
					return ic.convert(object, destination);
			
			temp = temp.getSuperclass();
		}
		
		throw new ConverterException("No abstract converter found for converting " + object.getClass() + " to " + destination);
	}
}