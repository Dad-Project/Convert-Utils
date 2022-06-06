package fr.rowlaxx.convertutils.converters;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.StrictSimpleConverter;

public class BooleanConverter extends StrictSimpleConverter<Boolean> {

	public BooleanConverter() {
		super(Boolean.class);
	}
	
	@ConvertMethod
	public boolean toBoolean(String string) {
		return Boolean.parseBoolean(string);
	}
	
	@ConvertMethod
	public boolean toBoolean(byte b) {
		return b == 1;
	}

	@ConvertMethod
	public boolean toBoolean(short b) {
		return b == 1;
	}

	@ConvertMethod
	public boolean toBoolean(long b) {
		return b == 1;
	}
	
	@ConvertMethod
	public boolean toBoolean(char b) {
		return b == 'Y' || b == 'y';
	}

	@ConvertMethod
	public boolean toBoolean(double b) {
		return b == 1;
	}
	
	@ConvertMethod
	public boolean toBoolean(float b) {
		return b == 1;
	}
}