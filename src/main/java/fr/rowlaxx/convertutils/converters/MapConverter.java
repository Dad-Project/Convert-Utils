package fr.rowlaxx.convertutils.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.MapKeyType;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.ParameterizedClass;
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
	
	@SuppressWarnings("unchecked")
	@ConvertMethod
	public <K, V> Map<K, V> toMap(Iterable<?> iterable, MapKeyType destination) throws NoSuchFieldException {
		Map<K, V> map = (Map<K, V>) ReflectionUtils.tryInstanciate(destination.getRawType());
		if (map == null)
			map = new HashMap<>();
		
		final Type keyType = destination.getActualTypeArgument(0);
		final Type valueType = destination.getActualTypeArgument(1);
		final Class<?> valueClass = (Class<?>)(valueType instanceof Class ? valueType : ((ParameterizedClass)valueType).getActualTypeArgument(1));
		final Field keyField = ReflectionUtils.getField(destination.getKey(), valueClass);

		Object rawKey;
		K key;
		V value;
		for (Object o : iterable) {
			value = getConverter().convert(o, valueType);
			rawKey = ReflectionUtils.tryGet(keyField, value);
			key = getConverter().convert(rawKey, keyType);
			map.put(key, value);
		}
		
		return map;
			
	}
}
