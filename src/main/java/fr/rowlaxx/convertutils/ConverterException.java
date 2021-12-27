package fr.rowlaxx.convertutils;

public class ConverterException extends RuntimeException {
	private static final long serialVersionUID = 5294301128181842151L;

	public ConverterException() {
		super();
	}

	public ConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConverterException(String message) {
		super(message);
	}

	public ConverterException(Throwable cause) {
		super(cause);
	}
}
