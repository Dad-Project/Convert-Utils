package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class FloatConverter extends SimpleConverter<Float> {

	public FloatConverter() {
		super(Float.class);
	}
	
	
	@ConvertMethod
	public float toFloat(String string) {
		return Float.parseFloat(string);
	}
	
	@ConvertMethod
	public float toFloat(byte s) {
		return (float)s;
	}
	
	@ConvertMethod
	public float toFloat(short s) {
		return (float)s;
	}
	
	@ConvertMethod
	public float toFloat(long s) {
		return (float)s;
	}
	
	@ConvertMethod
	public float toFloat(int s) {
		return (float)s;
	}
	
	@ConvertMethod
	public float toFloat(double s) {
		return (float)s;
	}
	
	@ConvertMethod
	public float toFloat(boolean s) {
		return (float)(s ? 1 : 0);
	}
	
	@ConvertMethod
	public float toFloat(BigInteger b) {
		return b.floatValue();
	}
	
	@ConvertMethod
	public float toFloat(BigDecimal b) {
		return b.floatValue();
	}

}
