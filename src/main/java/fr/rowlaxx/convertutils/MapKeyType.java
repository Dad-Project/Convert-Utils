package fr.rowlaxx.convertutils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import fr.rowlaxx.utils.GenericUtils;
import fr.rowlaxx.utils.ParameterizedClass;

public class MapKeyType extends ParameterizedClass {

	public static final MapKeyType from(Method method, Class<?> clazz) {
		final MapKey mapKey = method.getAnnotation(MapKey.class);
		if (mapKey == null)
			throw new IllegalArgumentException("This method do not have the MapKey annotation.");
		
		final ParameterizedClass type = (ParameterizedClass) GenericUtils.resolveReturnType(method, clazz);
		return new MapKeyType(type.getRawType(), type.getActualTypeArguments(), mapKey.fieldName());
	}
	
	public static final MapKeyType from(Method method) {
		return from(method, method.getDeclaringClass());
	}
	
	private String key;
	
	private MapKeyType(Class<?> raw, Type[] args, String key) {
		super(raw, args);
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}