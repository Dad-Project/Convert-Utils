package fr.rowlaxx.convertutils;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.rowlaxx.utils.generic.destination.Destination;

public class Converter {

	final HashMap<Class<?>, List<SimpleConverter<?>>> converters = new HashMap<>();
	
	Converter(){}
	
	public <T, E extends T> E convert(Object object, Class<T> destination) {
		return convert(object, Destination.from(destination));
	}
	
	public <T, E extends T> E convert(Object object, Destination<T> destination) {
		if (object == null)
			return null;
		Objects.requireNonNull(destination, "destination may not be null.");
		
		Class<?> clazz = destination.getDestinationClass();
		do {
			try {
				return processOne(object, destination, converters.get(clazz));
			}catch(ConverterException e) {}
			
			for (Class<?> _interface : clazz.getInterfaces())
				try {
					return processOne(object, destination, converters.get(_interface));
				}catch(ConverterException e) {}
			
		}while((clazz = clazz.getSuperclass()) != null);
		
		throw new ConverterException("Unable to convert " + object.getClass() + " to " + destination);
	}
	
	@SuppressWarnings("unchecked")
	private static final <T, E extends T> E processOne(Object object, Destination<T> destination, List<SimpleConverter<?>> list) {
		if (list == null)
			throw new ConverterException("List is null.");
		
		for (SimpleConverter<?> converter : list) {
			if (!converter.canReturnInnerType() && !destination.is(converter.getConvertClass()))
				continue;
			if (converter.canReturnInnerType() && !converter.getConvertClass().isAssignableFrom(destination.getDestinationClass()))
				continue;
			
			try {
				return (E) ((SimpleConverter<T>)converter).convert(object, destination);
			}catch(ConverterException e) {
				continue;
			}
		}
		throw new ConverterException("Unable to convert " + object.getClass() + " to " + destination + " using this list.");
		
	}
	
	
}
