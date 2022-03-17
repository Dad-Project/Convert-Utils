package fr.rowlaxx.convertutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Objects;

class ConvertMethodWrapper {

	//Variables
	private final Method method;
	private final boolean acceptInnerType;
	private final int priority;
	
	//Constructeurs
	ConvertMethodWrapper(Method method){
		Objects.requireNonNull(method, "method may not be null.");
		final ConvertMethod convert = method.getAnnotation(ConvertMethod.class);
		if (convert == null)
			throw new ConverterException("The desired method must have the Convert. annotation.");
		
		this.method = method;
		this.acceptInnerType = convert.acceptInnerType();
		this.priority = convert.priority();
	}

	//Delegate method
	public void setAccessible(boolean flag) {
		method.setAccessible(flag);
	}

	public Class<?> getDeclaringClass() {
		return method.getDeclaringClass();
	}

	public String getName() {
		return method.getName();
	}

	public int getModifiers() {
		return method.getModifiers();
	}

	public TypeVariable<Method>[] getTypeParameters() {
		return method.getTypeParameters();
	}

	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	public Type getGenericReturnType() {
		return method.getGenericReturnType();
	}

	public final boolean trySetAccessible() {
		return method.trySetAccessible();
	}

	public Class<?>[] getParameterTypes() {
		return method.getParameterTypes();
	}

	public int getParameterCount() {
		return method.getParameterCount();
	}

	public Type[] getGenericParameterTypes() {
		return method.getGenericParameterTypes();
	}

	public Class<?>[] getExceptionTypes() {
		return method.getExceptionTypes();
	}

	public Type[] getGenericExceptionTypes() {
		return method.getGenericExceptionTypes();
	}

	@Override
	public String toString() {
		return this.method.toString();
	}

	public Parameter[] getParameters() {
		return method.getParameters();
	}

	public String toGenericString() {
		return method.toGenericString();
	}

	@Deprecated
	public boolean isAccessible() {
		return method.isAccessible();
	}

	public Object invoke(Object obj, Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(obj, args);
	}

	public final boolean canAccess(Object obj) {
		return method.canAccess(obj);
	}

	public boolean isBridge() {
		return method.isBridge();
	}

	public boolean isVarArgs() {
		return method.isVarArgs();
	}

	public boolean isSynthetic() {
		return method.isSynthetic();
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return method.isAnnotationPresent(annotationClass);
	}

	public boolean isDefault() {
		return method.isDefault();
	}

	public Annotation[] getAnnotations() {
		return method.getAnnotations();
	}

	public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
		return method.getDeclaredAnnotation(annotationClass);
	}

	public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
		return method.getAnnotationsByType(annotationClass);
	}

	public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
		return method.getDeclaredAnnotationsByType(annotationClass);
	}

	public Object getDefaultValue() {
		return method.getDefaultValue();
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return method.getAnnotation(annotationClass);
	}

	public Annotation[] getDeclaredAnnotations() {
		return method.getDeclaredAnnotations();
	}

	public AnnotatedType getAnnotatedReceiverType() {
		return method.getAnnotatedReceiverType();
	}

	public Annotation[][] getParameterAnnotations() {
		return method.getParameterAnnotations();
	}

	public AnnotatedType getAnnotatedReturnType() {
		return method.getAnnotatedReturnType();
	}

	public AnnotatedType[] getAnnotatedParameterTypes() {
		return method.getAnnotatedParameterTypes();
	}

	public AnnotatedType[] getAnnotatedExceptionTypes() {
		return method.getAnnotatedExceptionTypes();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (acceptInnerType ? 1231 : 1237);
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + priority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertMethodWrapper other = (ConvertMethodWrapper) obj;
		if (acceptInnerType != other.acceptInnerType)
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (priority != other.priority)
			return false;
		return true;
	}

	//Getter
	public int getPriority() {
		return priority;
	}
	
	public boolean doAcceptInnerType() {
		return acceptInnerType;
	}
}
