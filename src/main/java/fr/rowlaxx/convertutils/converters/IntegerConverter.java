package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class IntegerConverter extends SimpleConverter<Integer> {

	public IntegerConverter() {
		super(Integer.class);
	}
	
	@Convert
	public int toInteger(String string) {
		return Integer.parseInt(string);
	}
	
	@Convert
	public int toInteger(byte s) {
		return (int)s;
	}
	
	@Convert
	public int toInteger(short s) {
		return (int)s;
	}
	
	@Convert
	public int toInteger(long s) {
		return (int)s;
	}
	
	@Convert
	public int toInteger(float s) {
		return (int)s;
	}
	
	@Convert
	public int toInteger(double s) {
		return (int)s;
	}
	
	@Convert
	public int toInteger(boolean s) {
		return (int)(s ? 1 : 0);
	}
	
	@Convert
	public int toInteger(BigInteger b) {
		return b.intValueExact();
	}
	
	@Convert
	public int toInteger(BigDecimal b) {
		return b.intValueExact();
	}
}