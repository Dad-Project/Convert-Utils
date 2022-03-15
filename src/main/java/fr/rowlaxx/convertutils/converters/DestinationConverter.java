package fr.rowlaxx.convertutils.converters;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.generic.clazz.GenericClass;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = false)
public class DestinationConverter extends SimpleConverter<GenericClass>{

	public DestinationConverter() {
		super(GenericClass.class);
	}
	
	@Convert
	public GenericClass<?> toDestination(String string) throws ClassNotFoundException{
		return GenericClass.parse(string);
	}
	
	@Convert
	public GenericClass<?> toDestination(Class<?> clazz){
		return GenericClass.from(clazz);
	}

}
