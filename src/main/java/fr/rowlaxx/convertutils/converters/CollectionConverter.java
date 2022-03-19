package fr.rowlaxx.convertutils.converters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.IterableArray;
import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = true)
public class CollectionConverter extends SimpleConverter<Collection> {

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
		if (destination == Set.class)
			collection = new HashSet<T>();
		
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
		if (clazz.getRawType() == Set.class)
			collection = new HashSet<T>();
		
		Type type = clazz.getActualTypeArgument(0);
		collection = (Collection<T>) ReflectionUtils.tryInstanciate(clazz.getRawType());
		if (collection == null)
			if (clazz.getRawType() == List.class)
				collection = new ArrayList<>();
			else if (clazz.getRawType() == Set.class)
				collection = new HashSet<>();
			else 
				throw new IllegalStateException("Unknow type " + clazz.getRawType());
		
		for (Object p : iterable)
			collection.add( (T) getConverter().convert(p, type) );
		
		return collection;
	}

}
