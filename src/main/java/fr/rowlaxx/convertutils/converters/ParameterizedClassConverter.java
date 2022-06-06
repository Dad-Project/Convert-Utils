package fr.rowlaxx.convertutils.converters;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.StrictSimpleConverter;
import fr.rowlaxx.utils.ParameterizedClass;

public class ParameterizedClassConverter extends StrictSimpleConverter<ParameterizedClass> {

	//Constructeurs
	public ParameterizedClassConverter() {
		super(ParameterizedClass.class);
	}
	
	//Methodes
	@ConvertMethod
	public ParameterizedClass toParameterizedClass(String str) throws ClassNotFoundException {
		return ParameterizedClass.from(str);
	}
}