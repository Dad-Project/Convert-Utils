package fr.rowlaxx.convertutils.converters;

import java.lang.reflect.Field;
import java.util.Objects;

import fr.rowlaxx.convertutils.ConvertMethod;
import fr.rowlaxx.convertutils.InnerConverter;
import fr.rowlaxx.convertutils.annotations.EnumMatcher;
import fr.rowlaxx.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class EnumConverter extends InnerConverter<Enum>{
	
	//Constructeurs
	public EnumConverter() {
		super(Enum.class);
	}
	
	@ConvertMethod
	public <T extends Enum<T>> T toEnum(String string, Class<T> destination) {
		if (string.isEmpty())
			return null;
		
		//On regarde pour l'annotation ValueMatcher
		EnumMatcher enumMatcher;	
		String[] possibleNames;
		for (Field field : destination.getDeclaredFields()) {
			if (!field.isEnumConstant())
				continue;
			
			enumMatcher = field.getDeclaredAnnotation(EnumMatcher.class);
			if (enumMatcher == null)
				continue;
			
			possibleNames = enumMatcher.possibleMatchs();
			if (possibleNames.length == 0)
				possibleNames = new String[] {field.getName()};
			
			for (String possibleName : possibleNames) {
				if (enumMatcher.caseSensitiv() && !Objects.equals(possibleName, string))
					continue;
				if (!enumMatcher.caseSensitiv() && !possibleName.equalsIgnoreCase(string))
					continue;	
				return ReflectionUtils.tryGet(field, null);
			}
		}
		
		//On regarde pour la methode toString
		final T[] values = destination.getEnumConstants();
		for (T value : values)
			if (Objects.equals(value.toString(), string))
				return value;
		
		//On regarde pour la méthode name
		for (T value : values)
			if (Objects.equals(string, ((Enum<?>)value).name()))
				return value;
		
		throw new IllegalArgumentException("String \"" + string + "\" cannot be converted to " + destination);
	}
}
