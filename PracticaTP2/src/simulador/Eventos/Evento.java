package simulador.Eventos;

import java.util.ArrayList;
import java.util.List;

import simulador.Execpciones.ErrorDeEvento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Vehiculo;

public abstract class Evento 
{
	protected Integer tiempo;

	/**
	 * Instancia un nuevo evento.
	 *
	 * @param tiempo en el que se ejecuta el evento
	 */
	public Evento(Integer tiempo) 
	{
		this.tiempo = tiempo;
	}

	public Integer getTiempo() 
	{
		return this.tiempo;
	}
	
	/**
	 * Convierte un array de strings con ids de vehiculos en una lista de vehiculos.
	 *
	 * @param vehiculos (lista de ids de vehiculos)
	 * @param mapa de objetos simulados
	 * @return lista de vehiculos
	 * @throws ErrorDeSimulacion no se ha encontrado algún vehiculo de la lista de ids.
	 */
	public static List<Vehiculo> parseaListaVehiculo(String[] vehiculos, MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<Vehiculo> list = new ArrayList<Vehiculo>();
		for (int i = 0; i < vehiculos.length; i++)
		{
			list.add(mapa.getVehiculo(vehiculos[i]));
		}
		return list;
	}
	
	/**
	 * Convierte un array de strings con ids de cruces en una lista de cruces.
	 *
	 * @param itinerario (lista de ids de cruces)
	 * @param mapa de objetos simulados
	 * @return lista de cruces
	 * @throws ErrorDeSimulacion no se ha encontrado algún cruce de la lista de ids.
	 */
	public static List<CruceGenerico<?>> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<CruceGenerico<?>> cruces = new ArrayList<CruceGenerico<?>>();
		for (int i = 0; i < itinerario.length; i++)
		{
			cruces.add(mapa.getCruce(itinerario[i]));
		}
		return cruces;
	}
	
	/**
	 * Realiza la acción que pide el evento.
	 *
	 * @param mapa de objetos simulados
	 * @throws ErrorDeSimulacion no se encuentra algun cruce
	 * @throws ErrorDeEvento ya existe el objeto que se quiere añadir o el itinerario contiene menos de 2 cruces
	 */
	public abstract void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion;
}
