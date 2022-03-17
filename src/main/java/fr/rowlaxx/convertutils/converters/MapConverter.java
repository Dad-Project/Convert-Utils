package fr.rowlaxx.convertutils.converters;

import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = true)
public class MapConverter extends SimpleConverter<Map> {

	public MapConverter() {
		super(Map.class);
	}

	@SuppressWarnings("unchecked")
	@ConvertMethod
	public <K, V> Map<K, V> toMap(Map<K, V> another, Class<? extends Map> destination) {
		if (destination == Map.class)
			return new HashMap<K, V>(another);
		return ReflectionUtils.tryInstanciate(destination, another);
	}
}
