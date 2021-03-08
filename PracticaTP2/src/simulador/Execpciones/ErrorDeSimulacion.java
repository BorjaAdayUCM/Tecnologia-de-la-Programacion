package simulador.Execpciones;

@SuppressWarnings("serial")
public class ErrorDeSimulacion extends Exception 
{
	/**
	 * Instancia un nuevo error de simulacion.
	 *
	 * @param mensaje del error de simulacion
	 */
	public ErrorDeSimulacion(String mensaje)
	{
		super(mensaje);
	}
}
