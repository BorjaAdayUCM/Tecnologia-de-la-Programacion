package simulador.Execpciones;


public class IniError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia un nuevo ini error.
	 *
	 * @param mensaje del error de ini
	 */
	public IniError(String mensaje) {
		super(mensaje);
	}
}
