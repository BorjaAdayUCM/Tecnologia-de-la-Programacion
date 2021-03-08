package simulador.Objetos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Ini.IniSection;

public abstract class ObjetoSimulacion 
{
	protected String id;
	
	/**
	 * Instancia un objeto de la simulacion.
	 *
	 * @param id del objeto
	 */
	public ObjetoSimulacion(String id)
	{
		this.id = id;
	}
	
	public String getId() 
	{
		return id;
	}

	/**
	 * Genera el reporte de un tiempo dado.
	 *
	 * @param tiempo en el que se genera el reporte 
	 * @return El reporte
	 */
	public String generaInforme(int tiempo)
	{
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo + 1);
		this.completaDetallesSeccion(is);
		return is.toString();
	}
	
	@Override
	public String toString() 
	{
		return this.id;
	}
	
	/**
	 * Completa los detalles de seccion del objeto sobre el que se ejecuta.
	 *
	 * @param is 
	 */
	protected abstract void completaDetallesSeccion(IniSection is);

	protected abstract String getNombreSeccion();

	/**
	 * Hace avanzar el estado del objeto sobre el que se ejecuta.
	 *
	 * @throws ErrorDeSimulacion 
	 */
	public abstract void avanza() throws ErrorDeSimulacion;
	
}
