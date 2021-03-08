package simulador.Objetos.Vehiculos;

import java.util.List;

import simulador.Execpciones.ErrorDeEvento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Ini.IniSection;
import simulador.Objetos.Cruces.CruceGenerico;


public class Bicicleta extends Vehiculo {

	/**
	 * Instancia una bicicleta.
	 *
	 * @param id de la bicicleta
	 * @param velocidadMax de la bicicleta
	 * @param itinerario de la bicicleta
	 * @throws ErrorDeEvento
	 */
	public Bicicleta(String id, int velocidadMax, List<CruceGenerico<?>> itinerario) throws ErrorDeSimulacion
	{
		super(id, velocidadMax, itinerario);
	}

	@Override
	public void setTiempoAveria(int duracionAveria) 
	{
		if (this.VelocidadAct >= this.velocidadMax / 2) super.setTiempoAveria(duracionAveria);
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		super.completaDetallesSeccion(is);
		is.setValue("type", "bike");
	}
}
