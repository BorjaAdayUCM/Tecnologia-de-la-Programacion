package simulador.Eventos;

import java.util.Iterator;
import java.util.List;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Vehiculos.Vehiculo;
import simulador.Utilidades.Utils;

public class EventoAveriaCoche extends Evento 
{
	private String[] vehiculos;
	private int duracion;
	
	/**
	 * Instancia un evento de averia.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param duracion de la averia
	 * @param vehiculos (lista de vehiculos que averiar)
	 */
	public EventoAveriaCoche(int tiempo, int duracion, String[] vehiculos) 
	{
		super(tiempo);
		this.duracion = duracion;
		this.vehiculos = vehiculos;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<Vehiculo> vehiculos = Evento.parseaListaVehiculo(this.vehiculos, mapa);
		Iterator<Vehiculo> iti = vehiculos.iterator();
		while (iti.hasNext()) iti.next().setTiempoAveria(this.duracion);
	}
	
	public String toString()
	{
		return "Averia vehiculos " + Utils.ListVehicles(this.vehiculos);
	}
}
