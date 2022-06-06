package fr.rowlaxx.convertutils;

import java.util.concurrent.TimeUnit;


public class Test {

	public static void main(String[] args) {
		Converter myConverter = ConverterFactory.defaultBuilder().build();
	
		
		System.out.println(myConverter.convert(TimeUnit.DAYS.name(), TimeUnit.class));
	}

}
 