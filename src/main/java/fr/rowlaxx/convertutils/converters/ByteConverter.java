package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class ByteConverter extends SimpleConverter<Byte> {

	public ByteConverter() {
		super(Byte.class);
	}
	
	@Convert
	public byte toByte(String string) {
		return Byte.parseByte(string);
	}
	
	@Convert
	public byte toByte(short s) {
		return (byte)s;
	}
	
	@Convert
	public byte toByte(int s) {
		return (byte)s;
	}
	
	@Convert
	public byte toByte(long s) {
		return (byte)s;
	}
	
	@Convert
	public byte toByte(float s) {
		return (byte)s;
	}
	
	@Convert
	public byte toByte(double s) {
		return (byte)s;
	}
	
	@Convert
	public byte toByte(boolean s) {
		return (byte)(s ? 1 : 0);
	}
	
	@Convert
	public byte toByte(BigDecimal b) {
		return b.byteValueExact();
	}
	
	@Convert
	public byte toByte(BigInteger b) {
		return b.byteValueExact();
	}
}