package simulador.Execpciones;

@SuppressWarnings("serial")
public class ErrorDeEvento extends Exception 
{
	/**
	 * Instancia un nuevo error de evento.
	 *
	 * @param mensaje del error 
	 */
	public ErrorDeEvento(String mensaje)
	{
		super(mensaje);
	}
}
