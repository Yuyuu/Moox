/**
 * 
 */
package dcll.groupe1.moox.generator;

/**
 * Code generator exception.
 * 
 * @author SERIN Kévin
 * 
 */
public class GeneratorException extends Exception {
	private static final long serialVersionUID = 1L;

	public GeneratorException() {
		super();
	}

	/**
	 * @param message
	 */
	public GeneratorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public GeneratorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public GeneratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		// super(message, cause, enableSuppression, writableStackTrace);
	}

}
