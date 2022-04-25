package fr.rowlaxx.convertutils;

import java.lang.reflect.Field;
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
	
	public static final MapKeyType from(String key, ParameterizedClass clazz) {
		return new MapKeyType(clazz.getRawType(), clazz.getActualTypeArguments(), key);
	}
	
	public static final MapKeyType from(Field field, Class<?> clazz) {
		final MapKey mapKey = field.getAnnotation(MapKey.class);
		if (mapKey == null)
			throw new IllegalArgumentException("This method do not have the MapKey annotation.");
		
		final ParameterizedClass type = (ParameterizedClass) GenericUtils.resolve(field.getGenericType(), clazz);
		return new MapKeyType(type.getRawType(), type.getActualTypeArguments(), mapKey.fieldName());
	}
	
	public static final MapKeyType from(Field field) {
		return from(field, field.getDeclaringClass());
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