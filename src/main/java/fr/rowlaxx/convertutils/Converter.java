package fr.rowlaxx.convertutils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.rowlaxx.utils.ParameterizedClass;

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
		return convert(object, (Type)destination);
	}
	
	public <T, E extends T> E convert(Object object, Type destination) {
		Objects.requireNonNull(destination, "destination may not be null.");
		
		Class<?> temp;
		if (destination instanceof Class)
			temp = (Class<?>)destination;
		else if (destination instanceof ParameterizedClass)
			temp = ((ParameterizedClass) destination).getRawType();
		else
			throw new IllegalArgumentException("Unknow type : " + destination.getClass());
		
		if (object == null)
			return null;
		
		Class<?>[] interfaces;
		E converted;
		
		while (temp != Object.class) {
			interfaces = temp.getInterfaces();
			
			if ( (converted = processOne(object, destination, converters.get(temp))) != null)
				return converted;
			
			for(Class<?> _interface : interfaces)
				if ((converted = processOne(object, destination, converters.get(_interface))) != null)
					return converted;
			
			temp = temp.getSuperclass();
		}
		
		throw new ConverterException("Unable to convert " + object.getClass() + " to " + destination + " using this converter.");
	}
	
	@SuppressWarnings("unchecked")
	private static final <T, E extends T> E processOne(Object object, Type destination, List<SimpleConverter<?>> list) {
		if (list == null)
			return null;
		
		for (SimpleConverter<?> converter : list)
			try {
				return (E) ((SimpleConverter<T>)converter).convert(object, destination);
			}catch(ConverterException e) {
				continue;
			}
		
		return null;
	}
}