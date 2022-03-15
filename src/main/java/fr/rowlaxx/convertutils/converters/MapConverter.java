package fr.rowlaxx.convertutils.converters;

import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.generic.ReflectionUtils;
import fr.rowlaxx.utils.generic.clazz.GenericClass;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = true)
public class MapConverter extends SimpleConverter<Map> {

	public MapConverter() {
		super(Map.class);
	}

	@SuppressWarnings("unchecked")
	@Convert
	public <K, V> Map<K, V> toMap(Map<K, V> another, GenericClass<? extends Map> destination) {
		if (destination.getDestinationClass() == Map.class)
			return new HashMap<K, V>(another);
		return ReflectionUtils.tryInstanciate(destination.getDestinationClass(), another);
	}
}
