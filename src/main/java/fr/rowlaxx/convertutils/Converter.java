package fr.rowlaxx.convertutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Converter {

	//Variables
	private final HashMap<Class<?>, List<SimpleConverter<?>>> converters = new HashMap<>();
	
	//Constructeurs
	Converter(){}
	
	//Methodes
	void addSimpleConverter(SimpleConverter<?> sc) {
		Objects.requireNonNull(sc, "sc may not be null.");
		
		if (sc.hasConverterParent())
			throw new ConverterException("This simple converter already has a parent converter.");
		
		List<SimpleConverter<?>> list;
		Class<?> destination = sc.getDestinationClass();
		
		if ( (list = converters.get(destination)) == null )
			converters.put(destination, list = new ArrayList<>());
		
		for(SimpleConverter<?> converter : list)
			if (converter == sc)
				return;
		
		sc.setConverter(this);
		list.add(0, sc);
	}
	
	//Convert
	public <T, E extends T> E convert(Object object, Class<T> destination) {
		Objects.requireNonNull(destination, "destination may not be null.");

		if (object == null)
			return null;
		
		Class<?> temp = destination;
		Class<?>[] interfaces;
		E converted;
		
		while (temp != Object.class) {
			interfaces = temp.getInterfaces();
			
			converted = processOne(object, destination, converters.get(temp));
			if (converted != null)
				return converted;
			
			for(Class<?> _interface : interfaces)
				if ((converted = processOne(object, destination, converters.get(_interface))) != null)
					return converted;
			
			temp = temp.getSuperclass();
		}
		
		throw new ConverterException("Unable to convert " + object.getClass() + " to " + destination + " using this converter.");
	}
	
	@SuppressWarnings("unchecked")
	private static final <T, E extends T> E processOne(Object object, Class<T> destination, List<SimpleConverter<?>> list) {
		for (SimpleConverter<?> converter : list) {
			if (!converter.canReturnInnerType() && destination != converter.getDestinationClass())
				continue;
			if (converter.canReturnInnerType() && !converter.getDestinationClass().isAssignableFrom(destination))
				continue;
			
			try {
				return (E) ((SimpleConverter<T>)converter).convert(object, destination);
			}catch(ConverterException e) {
				continue;
			}
		}
		
		return null;
	}
}