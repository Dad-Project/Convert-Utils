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
import fr.rowlaxx.convertutils.converters.ParameterizedClassConverter;
import fr.rowlaxx.convertutils.converters.ShortConverter;
import fr.rowlaxx.convertutils.converters.StringConverter;

public class ConverterFactory {

	//Builders
	public static ConverterFactory emptyBuilder() {
		return new ConverterFactory();
	}
	
	public static ConverterFactory defaultBuilder() {
		final ConverterFactory factory = new ConverterFactory();
		
		factory	.putSimpleConverter(new BooleanConverter())
				.putSimpleConverter(new ByteConverter())
				.putSimpleConverter(new ClassConverter())
				.putSimpleConverter(new CollectionConverter())
				.putSimpleConverter(new DoubleConverter())
				.putSimpleConverter(new EnumConverter())
				.putSimpleConverter(new FloatConverter())
				.putSimpleConverter(new IntegerConverter())
				.putSimpleConverter(new LongConverter())
				.putSimpleConverter(new MapConverter())
				.putSimpleConverter(new ParameterizedClassConverter())
				.putSimpleConverter(new ShortConverter())
				.putSimpleConverter(new StringConverter());
				
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
	
	public ConverterFactory putSimpleConverter(AbstractConverter<?> simpleConverter) {
		converter.putSimpleConverter(simpleConverter);
		return this;
	}
}
