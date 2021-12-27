package fr.rowlaxx.convertutils.converters;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.generic.destination.Destination;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = false)
public class ClassConverter extends SimpleConverter<Class> {

	public ClassConverter() {
		super(Class.class);
	}
	
	@Convert
	public Class<?> toClass(String string) throws ClassNotFoundException {
		return Class.forName(string);
	}
	
	@Convert
	public Class<?> toClass(Destination<?> destination){
		return destination.getDestinationClass();
	}
}
