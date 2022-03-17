package fr.rowlaxx.convertutils.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.IterableArray;
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
	public <T> Collection<T> toCollection(Iterable<?> iterable, Class<? extends Collection> destination){		
		Collection<T> list;
		
		if (destination == List.class)
			list = new ArrayList<T>();
		if (destination == Set.class)
			list = new HashSet<T>();
		
		list = ReflectionUtils.tryInstanciate(destination);
		for (Object e : iterable)
			list.add( (T) getConverter().convert(e, destination.getGenericParameter(0)) );
		
		return list;
	}

}
