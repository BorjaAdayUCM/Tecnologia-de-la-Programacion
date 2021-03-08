package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Ini.IniSection;

public abstract class ConstructorEventos 
{
	protected String etiqueta;
	protected String[] claves;
	protected String[] valoresPorDefecto;
	
	/**
	 * Instancia un constructor eventos.
	 */
	ConstructorEventos()
	{
		this.etiqueta = null;
		this.claves = null;
		this.valoresPorDefecto = null;
	}
	
	/**
	 * Coge el string asociado a la clave y lo devuelve si es valido.
	 *
	 * @param seccion del evento
	 * @param clave de la que extrae el identificador
	 * @return id extraido de la clave dada
	 * @throws IllegalArgumentException id no valido
	 */
	protected static String identificadorValido(IniSection seccion, String clave) throws IllegalArgumentException
	{
		String s = seccion.getValue(clave);
		if (!esIdentificadorValido(s)) throw new IllegalArgumentException("El valor " + s + " no es un ID valido.");
		else return s;
	}
	
	/**
	 * Comprueba si el identificador es valido.
	 *
	 * @param id de objetoSimulacion
	 * @return true, si es valido
	 */
	private static boolean esIdentificadorValido(String id)
	{
		return id != null && id.matches("[a-z0-9_]+");
	}
	
	/**
	 * Coge el int asociado a la clave si existe, sino le asigna uno por defecto.
	 *
	 * @param seccion del evento
	 * @param clave de la que extrae el int
	 * @param valorPorDefecto para la clave
	 * @return el valor del int 
	 */
	protected static int parseaInt(IniSection seccion, String clave, int valorPorDefecto)
	{
		String v = seccion.getValue(clave);
		return (v != null) ? Integer.parseInt(v) : valorPorDefecto;
	}
	
	/**
	 * Coge el int asociado a la clave si existe y no es negativo, sino le asigna uno por defecto.
	 *
	 * @param seccion del evento
	 * @param clave de la que extrea el int
	 * @param valorPorDefecto para la clave
	 * @return el int extraido de la de la clave dada
	 * @throws IllegalArgumentException valor del int negativo
	 */
	protected static int parseaIntNoNegativo(IniSection seccion, String clave, int valorPorDefecto) throws IllegalArgumentException
	{
		int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		if (i < 0) throw new IllegalArgumentException("El valor " + i + " para " + clave + " no puede ser negativo.");
		else return i;
	}
	
	/**
	 * Coge el double asociado a la clave y lo devuelve.
	 *
	 * @param seccion del evento
	 * @param clave de la que extrae el double
	 * @return el double extraido de la clave dada
	 * @throws IllegalArgumentException valor inexistente para la clave dada
	 */
	protected static double parseaDouble(IniSection seccion, String clave) throws IllegalArgumentException
	{
		String v = seccion.getValue(clave);
		if (v == null) throw new IllegalArgumentException("Valor inexistente para la clave: " + clave + ".");
		else return Double.parseDouble(v);
	}
	
	/**
	 * Coge el long asociado a la clave y lo devuelve.
	 *
	 * @param seccion del evento
	 * @param clave de la que extrae el long
	 * @return el long extraido de la clave dada
	 * @throws IllegalArgumentException valor inexistente para la clave dada
	 */
	protected static long parseaLong(IniSection seccion, String clave) throws IllegalArgumentException
	{
		String v = seccion.getValue(clave);
		if (v == null) throw new IllegalArgumentException("Valor inexistente para la clave: " + clave + ".");
		else return Long.parseLong(v);
	}
	
	/**
	 * Coge el string asociado a la clave y parsea realizando un split(",").
	 *
	 * @param seccion del evento
	 * @param clave de la que extrae el string
	 * @return el array de Strings obtenido al hacer el split(",")
	 * @throws IllegalArgumentException valor inexistente para la clave dada
	 */
	protected static String[] parseaList(IniSection seccion, String clave) throws IllegalArgumentException
	{
		String v = seccion.getValue(clave);
		if (v == null) throw new IllegalArgumentException("Valor inexistente para la clave: " + clave + ".");
		else return v.split(",");
	}
	
	/**
	 * Lee el evento y comprueba si se corresponde consigo mismo, en caso afirmartivo devuelve un evento de su tipo.
	 *
	 * @param section del evento
	 * @return el evento al que se refiere la seccion.
	 */
	public abstract Evento parser(IniSection section);

	public String template(){
		String plantilla = "[" + this.etiqueta + "]" + "\n";
		for (int i = 0; i < this.claves.length - 1; i++)
		{
			plantilla += this.claves[i] + " = " + this.valoresPorDefecto[i] + "\n";
		}
		plantilla += this.claves[this.claves.length - 1] + " = " + this.valoresPorDefecto[this.claves.length - 1] ;
		return plantilla;
	}

	
}
