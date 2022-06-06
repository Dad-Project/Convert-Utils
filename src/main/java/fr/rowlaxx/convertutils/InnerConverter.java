package fr.rowlaxx.convertutils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.utils.ParameterizedClass;
import fr.rowlaxx.utils.ReflectionUtils;

public class InnerConverter<T> extends AbstractConverter<T> {

	//Variables
	private Map<String, Map<String, Method>> methods;
	
	//Constructeurs
	protected InnerConverter(Class<T> destination) {
		super(destination);
	}

	@Override
	protected final void initMethod(Method method) {
		if (methods == null)
			methods = new HashMap<>();
		
		final Class<?>[] parameters = method.getParameterTypes();
		if (parameters.length != 2)
			throw new ConverterException("2 parameters is requiered for Method " + method);
		
		if (!Type.class.isAssignableFrom(parameters[1]))
			throw new ConverterException("The second parameter in " + method + " must be a Type.");
		if (!destinationClass().isAssignableFrom(ReflectionUtils.toWrapper(method.getReturnType())))
			throw new ConverterException("The return type for the Method " + method + " is not a " + destinationClass());
		
		final String returnType = ReflectionUtils.toWrapper(method.getReturnType()).getName() + "&&" + parameters[1].getName();
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
		while (destinationClass().isAssignableFrom(dest)) {
			if ( (m = methods.get(dest.getName() + "&&" + destination.getClass().getName()) ) != null)
				try{
					return proccess(m, object, destination);
				}catch(UnsupportedOperationException e) {
					continue;
				}
			
			for (Class<?> _interface : dest.getInterfaces())
				if ( (m = methods.get(_interface.getName() + "&&" + destination.getClass().getName() )) != null)
					try{
						return proccess(m, object, destination);
					}catch(UnsupportedOperationException e) {
						continue;
					}
			
			dest = dest.getSuperclass();
		}
		
		throw new ConverterException("Cannot convert " + object.getClass() + " to " + destination + " with this converter.");
	}
	
	private final <E extends T> E proccess(Map<String, Method> map, Object object, Type destination) {
		Class<?> temp = object.getClass();
		Method method;
		while (temp != null) {
			if ( (method = map.get(temp.getName())) != null )
				return tryInvoke(method, object, destination);
			
			try {
				return proccessInterfaces(temp, map, object, destination);
			}catch(UnsupportedOperationException e) {
				temp = temp.getSuperclass();
			}
		}
		
		throw new UnsupportedOperationException();
	}
	
	private final <E extends T> E proccessInterfaces(Class<?> _interface, Map<String, Method> map, Object object, Type destination) {
		Method method;
		final Class<?>[] in = _interface.getInterfaces();
		for (int i = 0 ; i < in.length ; i++)
			if ( (method = map.get(in[i].getName())) != null )
				return tryInvoke(method, object, destination);
		
		for (int i = 0 ; i < in.length ; i++)
			try {
				return proccessInterfaces(in[i], map, object, destination);
			}catch(UnsupportedOperationException e) {
				continue;
			}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean canReturnInnerType() {
		return true;
	}
}
