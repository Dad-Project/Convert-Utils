package fr.rowlaxx.convertutils;

import java.util.ArrayList;
import java.util.List;

import fr.rowlaxx.convertutils.converters.BooleanConverter;
import fr.rowlaxx.convertutils.converters.ByteConverter;
import fr.rowlaxx.convertutils.converters.ClassConverter;
import fr.rowlaxx.convertutils.converters.CollectionConverter;
import fr.rowlaxx.convertutils.converters.DestinationConverter;
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
		factory.addSimpleConverter(new BooleanConverter());
		factory.addSimpleConverter(new ByteConverter());
		factory.addSimpleConverter(new ClassConverter());
		factory.addSimpleConverter(new CollectionConverter());
		factory.addSimpleConverter(new DestinationConverter());
		factory.addSimpleConverter(new DoubleConverter());
		factory.addSimpleConverter(new EnumConverter());
		factory.addSimpleConverter(new FloatConverter());
		factory.addSimpleConverter(new IntegerConverter());
		factory.addSimpleConverter(new LongConverter());
		factory.addSimpleConverter(new MapConverter());
		factory.addSimpleConverter(new ShortConverter());
		factory.addSimpleConverter(new StringConverter());
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
			for (List<SimpleConverter<?>> list : converter.converters.values())
				for (SimpleConverter<?> sc : list)
					sc.converter = this.converter;
			return this.converter;
		}finally {
			this.converter = null;
		}
	}
	
	private List<SimpleConverter<?>> getSimpleConverters(Class<?> clazz){
		if(!converter.converters.containsKey(clazz)) {
			List<SimpleConverter<?>> list = new ArrayList<>();
			converter.converters.put(clazz, list);
			return list;
		}
		return converter.converters.get(clazz);
	}
	
	public ConverterFactory addSimpleConverter(SimpleConverter<?> simpleConverter) {
		getSimpleConverters(simpleConverter.getConvertClass()).add(0, simpleConverter);
		return this;
	}
}
