package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class IntegerConverter extends SimpleConverter<Integer> {

	public IntegerConverter() {
		super(Integer.class);
	}
	
	@ConvertMethod
	public int toInteger(String string) {
		return Integer.parseInt(string);
	}
	
	@ConvertMethod
	public int toInteger(byte s) {
		return (int)s;
	}
	
	@ConvertMethod
	public int toInteger(short s) {
		return (int)s;
	}
	
	@ConvertMethod
	public int toInteger(long s) {
		return (int)s;
	}
	
	@ConvertMethod
	public int toInteger(float s) {
		return (int)s;
	}
	
	@ConvertMethod
	public int toInteger(double s) {
		return (int)s;
	}
	
	@ConvertMethod
	public int toInteger(boolean s) {
		return (int)(s ? 1 : 0);
	}
	
	@ConvertMethod
	public int toInteger(BigInteger b) {
		return b.intValueExact();
	}
	
	@ConvertMethod
	public int toInteger(BigDecimal b) {
		return b.intValueExact();
	}
}