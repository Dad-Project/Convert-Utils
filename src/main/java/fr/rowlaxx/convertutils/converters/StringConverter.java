package fr.rowlaxx.convertutils.converters;

import java.util.Locale;
import java.util.Objects;

import fr.rowlaxx.convertutils.Convert;
import fr.rowlaxx.convertutils.Return;
import fr.rowlaxx.convertutils.SimpleConverter;

@Return(canReturnInnerType = false)
public class StringConverter extends SimpleConverter<String> {

	//Constructeurs
	public StringConverter() {
		super(String.class);
	}
	
	//variables
	private int decimal = 8;
	private Locale locale = Locale.US;
	
	@Convert
	public String toString(Object object) {
		return Objects.toString(object);
	}
	
	@Convert
	public String toString(Class<?> clazz) {
		return clazz.getName();
	}
	
	@Convert
	public String toString(double d) {
		return String.format(locale, "%."+decimal+"f", d);
	}
	
	@Convert
	public String toString(float f) {
		return String.format(locale, "%."+decimal+"f", f);
	}
		
	//Setters
	public void setDecimal(int decimal) {
		if (decimal < 0)
			throw new IllegalArgumentException("decimal must be positiv.");
		this.decimal = decimal;
	}
	
	public void setLocale(Locale locale) {
		this.locale = Objects.requireNonNull(locale, "locale may not be null.");
	}
	
}
