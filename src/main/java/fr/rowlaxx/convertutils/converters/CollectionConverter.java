package fr.rowlaxx.convertutils.converters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.InnerConverter;
import fr.rowlaxx.utils.IterableArray;
import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class CollectionConverter extends InnerConverter<Collection> {

	public CollectionConverter() {
		super(Collection.class);
	}
	
	@ConvertMethod
	public <T> Collection<T> toCollection(T[] array, Class<? extends Collection> destination){
		return toCollection(new IterableArray<T>(array), destination);
	}
	
	@SuppressWarnings("unchecked")
	@ConvertMethod
	public <T> Collection<T> toCollection(Iterable<T> iterable, Class<? extends Collection> destination){		
		Collection<T> collection;
		if (destination == List.class)
			collection = new ArrayList<T>();
		else if (destination == Set.class)
			collection = new HashSet<T>();
		else 
			collection = ReflectionUtils.tryInstanciate(destination);
		
		for (T e : iterable)
			collection.add(e);
		
		return collection;
	}
	
	@ConvertMethod
	public <T> Collection<T> toCollection(T[] array, ParameterizedClass clazz){
		return toCollection(new IterableArray<T>(array), clazz);
	}
	
	@SuppressWarnings("unchecked")
	@ConvertMethod
	public <T> Collection<T> toCollection(Iterable<?> iterable, ParameterizedClass clazz){
		Collection<T> collection;
		
		if (clazz.getRawType() == List.class)
			collection = new ArrayList<T>();
		else if (clazz.getRawType() == Set.class)
			collection = new HashSet<T>();
		else
			collection = (Collection<T>) ReflectionUtils.tryInstanciate(clazz.getRawType());
		
		Type type = clazz.getActualTypeArgument(0);
		for (Object p : iterable)
			collection.add( (T) mainConverter().convert(p, type) );
		
		return collection;
	}

}
