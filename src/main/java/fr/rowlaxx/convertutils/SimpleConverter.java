package fr.rowlaxx.convertutils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.utils.ReflectionUtils;

public class SimpleConverter<T> extends AbstractConverter<T> {

	//Variables
	private Map<String, Method> methods;
	
	//Constructeurs
	protected SimpleConverter(Class<T> destination) {
		super(destination);
	}

	@Override
	protected final void initMethod(Method method) {
		if (methods == null)
			methods = new HashMap<>();
		
		final Class<?>[] parameters = method.getParameterTypes();
		if (parameters.length != 1)
			throw new ConverterException("Only 1 parameter is requiered for Method " + method);
		if (ReflectionUtils.toWrapper(method.getReturnType()) != destinationClass())
			throw new ConverterException("The return type for the Method " + method + " is bad.");
		
		final String parameter = ReflectionUtils.toWrapper(parameters[0]).getName();
		methods.put(parameter, method);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected final T proccess(Object object, Type destination) {
		Class<?> clazz = object.getClass();
		Method method;
		while (clazz != null) {
			if ( (method = methods.get(clazz.getName())) != null)
				return tryInvoke(method, object);
			
			try {
				return proccessInterfaces(clazz, object);
			}catch(UnsupportedOperationException e) {
				clazz = clazz.getSuperclass();
			}
		}
		
		throw new ConverterException("Cannot convert " + object.getClass() + " to " + destination + " with this converter.");
	}
	
	private final <E extends T> E proccessInterfaces(Class<?> _interface, Object object) {
		Method method;
		final Class<?>[] in = _interface.getInterfaces();
		for (int i = 0 ; i < in.length ; i++)
			if ( (method = methods.get(in[i].getName())) != null )
				return tryInvoke(method, object);
		
		for (int i = 0 ; i < in.length ; i++)
			try {
				return proccessInterfaces(in[i], object);
			}catch(UnsupportedOperationException e) {
				continue;
			}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean canReturnInnerType() {
		return false;
	}
}