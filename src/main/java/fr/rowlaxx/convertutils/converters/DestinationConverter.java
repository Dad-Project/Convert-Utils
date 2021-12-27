package fr.rowlaxx.convertutils.converters;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;
import fr.rowlaxx.utils.generic.destination.Destination;

@SuppressWarnings("rawtypes")
@Return(canReturnInnerType = false)
public class DestinationConverter extends SimpleConverter<Destination>{

	public DestinationConverter() {
		super(Destination.class);
	}
	
	@Convert
	public Destination<?> toDestination(String string) throws ClassNotFoundException{
		return Destination.parse(string);
	}
	
	@Convert
	public Destination<?> toDestination(Class<?> clazz){
		return Destination.from(clazz);
	}

}
