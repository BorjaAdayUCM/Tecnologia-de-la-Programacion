package simulador.Eventos;

import java.util.List;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Vehiculo;

public class EventoNuevoVehiculo extends Evento 
{
	private String id;
	private Integer velocidadMax;
	private String[] itinerario;
	
	/**
	 * Instancia un evento nuevo vehiculo.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id del vehiculo
	 * @param velocidadMax del vehiculo
	 * @param itinerario (lista de cruces)
	 */
	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMax, String[] itinerario)
	{
		super(tiempo);
		this.id = id;
		this.velocidadMax = velocidadMax;
		this.itinerario = itinerario;
	}
	
	public String getId() 
	{
		return id;
	}

	public Integer getVelocidadMax() 
	{
		return velocidadMax;
	}

	public String[] getItinerario() 
	{
		return itinerario;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<CruceGenerico<?>> iti = Evento.parseaListaCruces(this.itinerario, mapa);
		if (iti == null || iti.size() < 2) throw new ErrorDeSimulacion("El itinerario del vehículo tiene menos de 2 cruces.");
		else mapa.addVehiculo(this.id, new Vehiculo(this.id, this.velocidadMax, iti));
	}
	
	public String toString()
	{
		return "Nuevo vehiculo " + this.getId();
	}
}
