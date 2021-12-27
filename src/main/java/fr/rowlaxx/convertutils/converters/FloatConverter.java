package fr.rowlaxx.convertutils.converters;

import java.math.BigDecimal;
import java.math.BigInteger;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class FloatConverter extends SimpleConverter<Float> {

	public FloatConverter() {
		super(Float.class);
	}
	
	
	@Convert
	public float toFloat(String string) {
		return Integer.parseInt(string);
	}
	
	@Convert
	public float toFloat(byte s) {
		return (float)s;
	}
	
	@Convert
	public float toFloat(short s) {
		return (float)s;
	}
	
	@Convert
	public float toFloat(long s) {
		return (float)s;
	}
	
	@Convert
	public float toFloat(int s) {
		return (float)s;
	}
	
	@Convert
	public float toFloat(double s) {
		return (float)s;
	}
	
	@Convert
	public float toFloat(boolean s) {
		return (float)(s ? 1 : 0);
	}
	
	@Convert
	public float toFloat(BigInteger b) {
		return b.floatValue();
	}
	
	@Convert
	public float toFloat(BigDecimal b) {
		return b.floatValue();
	}

}
