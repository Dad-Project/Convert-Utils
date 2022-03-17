package fr.rowlaxx.convertutils;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface ConvertMethod {

	public int priority() default 0;
	public boolean acceptInnerType() default true;
	
}
