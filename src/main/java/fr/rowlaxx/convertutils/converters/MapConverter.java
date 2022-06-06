package fr.rowlaxx.convertutils.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.InnerSimpleConverter;
import fr.rowlaxx.convertutils.MapKeyType;
import fr.rowlaxx.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class MapConverter extends InnerSimpleConverter<Map> {

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
	
	@SuppressWarnings("unchecked")
	@ConvertMethod
	public <K, V> Map<K, V> toMap(Iterable<?> iterable, MapKeyType destination) throws NoSuchFieldException {
		Map<K, V> map;
		if (destination.getRawType() == Map.class)
			map = new HashMap();
		else
			map = (Map<K, V>) ReflectionUtils.tryInstanciate(destination.getRawType());
				
		final Type keyType = destination.getActualTypeArgument(0);
		final Type valueType = destination.getActualTypeArgument(1);
		final Field keyField = ReflectionUtils.getField(destination.getKey(), mainConverter().convert(keyType, Class.class));

		Object rawKey;
		K key;
		V value;
		for (Object o : iterable) {
			value = mainConverter().convert(o, valueType);
			rawKey = ReflectionUtils.tryGet(keyField, value);
			key = mainConverter().convert(rawKey, keyType);
			map.put(key, value);
		}
		
		return map;
	}
}
