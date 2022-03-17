package fr.rowlaxx.convertutils;

import fr.rowlaxx.convertutils.converters.BooleanConverter;
import fr.rowlaxx.convertutils.converters.ByteConverter;
import fr.rowlaxx.convertutils.converters.ClassConverter;
import fr.rowlaxx.convertutils.converters.CollectionConverter;
import fr.rowlaxx.convertutils.converters.DoubleConverter;
import fr.rowlaxx.convertutils.converters.EnumConverter;
import fr.rowlaxx.convertutils.converters.FloatConverter;
import fr.rowlaxx.convertutils.converters.IntegerConverter;
import fr.rowlaxx.convertutils.converters.LongConverter;
import fr.rowlaxx.convertutils.converters.MapConverter;
import fr.rowlaxx.convertutils.converters.ShortConverter;
import fr.rowlaxx.convertutils.converters.StringConverter;

public class ConverterFactory {

	//Builders
	public static ConverterFactory newInstance() {
		return new ConverterFactory();
	}
	
	public static ConverterFactory newDefaultInstance() {
		final ConverterFactory factory = new ConverterFactory();
		
		factory	.addSimpleConverter(new BooleanConverter())
				.addSimpleConverter(new ByteConverter())
				.addSimpleConverter(new ClassConverter())
				.addSimpleConverter(new CollectionConverter())
				.addSimpleConverter(new DoubleConverter())
				.addSimpleConverter(new EnumConverter())
				.addSimpleConverter(new FloatConverter())
				.addSimpleConverter(new IntegerConverter())
				.addSimpleConverter(new LongConverter())
				.addSimpleConverter(new MapConverter())
				.addSimpleConverter(new ShortConverter())
				.addSimpleConverter(new StringConverter());
				
		return factory;
	}
	
	//Variables
	private Converter converter;
	
	//Constructeurs
	private ConverterFactory() {
		this.converter = new Converter();
	}
	
	//Methodes
	public Converter build() {
		try {
			return this.converter;
		}finally {
			this.converter = null;
		}
	}
	
	public ConverterFactory addSimpleConverter(SimpleConverter<?> simpleConverter) {
		converter.addSimpleConverter(simpleConverter);
		return this;
	}
}
