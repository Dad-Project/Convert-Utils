package fr.rowlaxx.convertutils.converters;

import java.util.Locale;
import java.util.Objects;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.SimpleConverter;

public class StringConverter extends SimpleConverter<String> {

	//Constructeurs
	public StringConverter() {
		super(String.class);
	}
	
	//variables
	private String format = "%.8f";
	private Locale locale = Locale.US;
	
	@ConvertMethod
	public String toString(Object object) {
		return Objects.toString(object);
	}
	
	@ConvertMethod
	public String toString(Class<?> clazz) {
		return clazz.getName();
	}
	
	@ConvertMethod
	public String toString(double d) {
		return String.format(locale, format, d);
	}
	
	@ConvertMethod
	public String toString(float f) {
		return String.format(locale, format, f);
	}
	
	@ConvertMethod
	public String toString(byte b) {
		return String.valueOf(b);
	}
	
	@ConvertMethod
	public String toString(boolean b) {
		return String.valueOf(b);
	}
	
	@ConvertMethod
	public String toString(short s) {
		return String.valueOf(s);
	}
	
	@ConvertMethod
	public String toString(char c) {
		return String.valueOf(c);
	}
	
	@ConvertMethod
	public String toString(int i) {
		return String.valueOf(i);
	}
	
	@ConvertMethod
	public String toString(long l) {
		return String.valueOf(l);
	}
		
	//Setters
	public void setDecimal(int decimal) {
		if (decimal < 0)
			throw new IllegalArgumentException("decimal must be positiv.");
		this.format = "%."+decimal+"f";
	}
	
	public StringConverter setLocale(Locale locale) {
		this.locale = Objects.requireNonNull(locale, "locale may not be null.");
		return this;
	}
	
}
