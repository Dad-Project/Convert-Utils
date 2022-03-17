package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class DoubleConverter extends SimpleConverter<Double> {

	public DoubleConverter() {
		super(Double.class);
	}
	
	@ConvertMethod
	public double toDouble(String string) {
		return Integer.parseInt(string);
	}
	
	@ConvertMethod
	public double toDouble(byte s) {
		return (double)s;
	}
	
	@ConvertMethod
	public double toDouble(short s) {
		return (double)s;
	}
	
	@ConvertMethod
	public double toDouble(long s) {
		return (double)s;
	}
	
	@ConvertMethod
	public double toDouble(float s) {
		return (double)s;
	}
	
	@ConvertMethod
	public double toDouble(int s) {
		return (double)s;
	}
	
	@ConvertMethod
	public double toDouble(boolean s) {
		return (double)(s ? 1 : 0);
	}
	
	@ConvertMethod
	public double toDouble(BigInteger b) {
		return b.doubleValue();
	}
	
	@ConvertMethod
	public double toDouble(BigDecimal b) {
		return b.doubleValue();
	}

}
