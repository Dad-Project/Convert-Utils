package fr.rowlaxx.convertutils.converters;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.StrictSimpleConverter;
import fr.rowlaxx.utils.ParameterizedClass;

@SuppressWarnings("rawtypes")
public class ClassConverter extends StrictSimpleConverter<Class> {

	public ClassConverter() {
		super(Class.class);
	}
	
	@ConvertMethod
	public Class<?> toClass(String string) throws ClassNotFoundException {
		return Class.forName(string);
	}
	
	@ConvertMethod
	public Class<?> toClass(ParameterizedClass destination){
		return destination.getRawType();
	}
}
