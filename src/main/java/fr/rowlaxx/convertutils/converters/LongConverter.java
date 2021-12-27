package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class LongConverter extends SimpleConverter<Long> {

	public LongConverter() {
		super(Long.class);
	}
	
	
	@Convert
	public long toLong(String string) {
		return Integer.parseInt(string);
	}
	
	@Convert
	public long toLong(byte s) {
		return (long)s;
	}
	
	@Convert
	public long toLong(short s) {
		return (long)s;
	}
	
	@Convert
	public long toLong(int s) {
		return (long)s;
	}
	
	@Convert
	public long toLong(float s) {
		return (long)s;
	}
	
	@Convert
	public long toLong(double s) {
		return (long)s;
	}
	
	@Convert
	public long toLong(boolean s) {
		return (long)(s ? 1 : 0);
	}
	
	@Convert
	public long toLong(BigInteger b) {
		return b.longValueExact();
	}
	
	@Convert
	public long toLong(BigDecimal b) {
		return b.longValueExact();
	}
}