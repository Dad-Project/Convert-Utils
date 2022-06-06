package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.StrictSimpleConverter;

public class ShortConverter extends StrictSimpleConverter<Short> {

	public ShortConverter() {
		super(Short.class);
	}
	
	@ConvertMethod
	public short toShort(String string) {
		return Short.parseShort(string);
	}
	
	@ConvertMethod
	public short toShort(byte s) {
		return (short)s;
	}
	
	@ConvertMethod
	public short toShort(int s) {
		return (short)s;
	}
	
	@ConvertMethod
	public short toShort(long s) {
		return (short)s;
	}
	
	@ConvertMethod
	public short toShort(float s) {
		return (short)s;
	}
	
	@ConvertMethod
	public short toShort(double s) {
		return (short)s;
	}
	
	@ConvertMethod
	public short toShort(boolean s) {
		return (short)(s ? 1 : 0);
	}

	@ConvertMethod
	public short toShort(BigInteger b) {
		return b.shortValueExact();
	}
	
	@ConvertMethod
	public short toShort(BigDecimal b) {
		return b.shortValueExact();
	}
}
