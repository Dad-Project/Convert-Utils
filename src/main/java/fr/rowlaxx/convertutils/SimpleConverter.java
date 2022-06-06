package fr.rowlaxx.convertutils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.rowlaxx.utils.ReflectionUtils;

public class SimpleConverter<T> extends AbstractConverter<T> {

	//Variables
	private final Map<String, Method> methods = new HashMap<>();
	
	//Constructeurs
	protected SimpleConverter(Class<T> destination) {
		super(destination);
	}

	@Override
	protected final void initMethod(Method method) {
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
			
			for (Class<?> _interface : clazz.getInterfaces() )
				if ( (method = methods.get(_interface.getName())) != null)
					return tryInvoke(method, object);
			
			clazz = clazz.getSuperclass();
		}
		
		throw new ConverterException("Cannot convert " + object.getClass() + " to " + destination + " with this converter.");
	}

	@Override
	public final boolean canReturnInnerType() {
		return false;
	}
}