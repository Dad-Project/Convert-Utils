package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.StrictSimpleConverter;

public class ByteConverter extends StrictSimpleConverter<Byte> {

	public ByteConverter() {
		super(Byte.class);
	}
	
	@ConvertMethod
	public byte toByte(String string) {
		return Byte.parseByte(string);
	}
	
	@ConvertMethod
	public byte toByte(short s) {
		return (byte)s;
	}
	
	@ConvertMethod
	public byte toByte(int s) {
		return (byte)s;
	}
	
	@ConvertMethod
	public byte toByte(long s) {
		return (byte)s;
	}
	
	@ConvertMethod
	public byte toByte(float s) {
		return (byte)s;
	}
	
	@ConvertMethod
	public byte toByte(double s) {
		return (byte)s;
	}
	
	@ConvertMethod
	public byte toByte(boolean s) {
		return (byte)(s ? 1 : 0);
	}
	
	@ConvertMethod
	public byte toByte(BigDecimal b) {
		return b.byteValueExact();
	}
	
	@ConvertMethod
	public byte toByte(BigInteger b) {
		return b.byteValueExact();
	}
}