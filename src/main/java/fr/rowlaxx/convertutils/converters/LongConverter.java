package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.StrictSimpleConverter;

public class LongConverter extends StrictSimpleConverter<Long> {

	public LongConverter() {
		super(Long.class);
	}
	
	
	@ConvertMethod
	public long toLong(String string) {
		return Long.parseLong(string);
	}
	
	@ConvertMethod
	public long toLong(byte s) {
		return (long)s;
	}
	
	@ConvertMethod
	public long toLong(short s) {
		return (long)s;
	}
	
	@ConvertMethod
	public long toLong(int s) {
		return (long)s;
	}
	
	@ConvertMethod
	public long toLong(float s) {
		return (long)s;
	}
	
	@ConvertMethod
	public long toLong(double s) {
		return (long)s;
	}
	
	@ConvertMethod
	public long toLong(boolean s) {
		return (long)(s ? 1 : 0);
	}
	
	@ConvertMethod
	public long toLong(BigInteger b) {
		return b.longValueExact();
	}
	
	@ConvertMethod
	public long toLong(BigDecimal b) {
		return b.longValueExact();
	}
}