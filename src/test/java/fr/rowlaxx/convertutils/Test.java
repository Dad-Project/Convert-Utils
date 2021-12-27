package fr.rowlaxx.convertutils;

public class Test {

	public static void main(String[] args) {
		Converter myConverter = ConverterFactory.newDefaultInstance().build();
		
		int clazz = myConverter.convert("987", Integer.class);
		System.out.println(clazz);
	}

}
