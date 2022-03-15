package fr.rowlaxx.convertutils.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.IterableArray;
import fr.rowlaxx.utils.generic.ReflectionUtils;
import fr.rowlaxx.utils.generic.clazz.GenericClass;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = true)
public class CollectionConverter extends SimpleConverter<Collection> {

	public CollectionConverter() {
		super(Collection.class);
	}
	
	@Convert
	public <T> Collection<T> toCollection(T[] array, GenericClass<? extends Collection> destination){
		return toCollection(new IterableArray<T>(array), destination);
	}
	
	@SuppressWarnings("unchecked")
	@Convert
	public <T> Collection<T> toCollection(Iterable<?> iterable, GenericClass<? extends Collection> destination){		
		Collection<T> list;
		
		if (destination.getDestinationClass() == List.class)
			list = new ArrayList<T>();
		if (destination.getDestinationClass() == Set.class)
			list = new HashSet<T>();
		
		list = ReflectionUtils.tryInstanciate(destination.getDestinationClass());
		for (Object e : iterable)
			list.add( (T) getConverter().convert(e, destination.getGenericParameter(0)) );
		
		return list;
	}

}
