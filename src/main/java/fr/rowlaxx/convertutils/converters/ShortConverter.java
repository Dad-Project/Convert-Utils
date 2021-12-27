package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class ShortConverter extends SimpleConverter<Short> {

	public ShortConverter() {
		super(Short.class);
	}
	
	@Convert
	public short toShort(String string) {
		return Short.parseShort(string);
	}
	
	@Convert
	public short toShort(byte s) {
		return (short)s;
	}
	
	@Convert
	public short toShort(int s) {
		return (short)s;
	}
	
	@Convert
	public short toShort(long s) {
		return (short)s;
	}
	
	@Convert
	public short toShort(float s) {
		return (short)s;
	}
	
	@Convert
	public short toShort(double s) {
		return (short)s;
	}
	
	@Convert
	public short toShort(boolean s) {
		return (short)(s ? 1 : 0);
	}

	@Convert
	public short toShort(BigInteger b) {
		return b.shortValueExact();
	}
	
	@Convert
	public short toShort(BigDecimal b) {
		return b.shortValueExact();
	}
}
