package fr.rowlaxx.convertutils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

public class InnerSimpleConverter<T> extends SimpleConverter<T> {

	//Variables
	private final Map<String, Map<String, Method>> methods = new HashMap<>();
	
	//Constructeurs
	protected InnerSimpleConverter(Class<T> destination) {
		super(destination);
	}

	@Override
	protected final void initMethod(Method method) {
		final Class<?>[] parameters = method.getParameterTypes();
		if (parameters.length != 2)
			throw new ConverterException("2 parameters is requiered for Method " + method);
		if (Type.class.isAssignableFrom(parameters[1]))
			throw new ConverterException("The second parameter must be a Type.");
		if (!destinationClass().isAssignableFrom(ReflectionUtils.toWrapper(method.getReturnType())))
			throw new ConverterException("The return type for the Method " + method + " is not a " + destinationClass());
		
		final String returnType = ReflectionUtils.toWrapper(method.getReturnType()).getName();
		final String parameter = ReflectionUtils.toWrapper(parameters[0]).getName();
		
		Map<String, Method> m = methods.get(returnType);
		if (m == null) {
			m = new HashMap<>();
			methods.put(returnType, m);
		}
		m.put(parameter, method);
	}

	@Override
	protected final <E extends T> E proccess(Object object, Type destination) {
		Class<?> dest = null;
		if (destination instanceof Class)
			dest = (Class<?>)destination;
		else if (destination instanceof ParameterizedClass)
			dest = (Class<?>)((ParameterizedClass)destination).getRawType();
		else
			throw new ConverterException("Unknow type : " + destination);
		
		Map<String, Method> m;
		Class<?> temp;
		Method method;
		
		while (destinationClass().isAssignableFrom(dest)) {
			m = methods.get(dest.getName());
			
			if (m != null) {
				temp = object.getClass();
				while (temp != null) {
					if ( (method = m.get(temp.getName())) != null)
						return tryInvoke(method, object, destination);
					
					for (Class<?> _interface : temp.getInterfaces() )
						if ( (method = m.get(_interface.getName())) != null)
							return tryInvoke(method, object, destination);
					
					temp = temp.getSuperclass();
				}
			}
			
			dest = dest.getSuperclass();
		}
		
		throw new ConverterException("Cannot convert " + object.getClass() + " to " + destination + " with this converter.");
	}

	@Override
	public final boolean canReturnInnerType() {
		return true;
	}
}
