package simulador.Eventos;

import java.util.List;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Coche;

public class EventoNuevoCoche extends EventoNuevoVehiculo 
{
	private int resistencia;
	private int duracionMaximaAveria;
	private long seed;
	private double probabilidad;
	
	/**
	 * Instancia un evento nuevo coche.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id del coche
	 * @param velocidadMax del coche
	 * @param itinerario (lista de cruces)
	 * @param resistencia del coche
	 * @param duracionMaximaAveria (tiempo maximo que se averia el coche)
	 * @param seed generadora de numAleatorio
	 * @param probabilidad de averia
	 */
	public EventoNuevoCoche(int tiempo, String id, int velocidadMax, String[] itinerario, int resistencia, int duracionMaximaAveria, long seed, double probabilidad) 
	{
		super(tiempo, id, velocidadMax, itinerario);
		this.resistencia = resistencia;
		this.duracionMaximaAveria = duracionMaximaAveria;
		this.seed = seed;
		this.probabilidad = probabilidad;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion 
	{
		List<CruceGenerico<?>> iti = Evento.parseaListaCruces(this.getItinerario() , mapa);
		if (iti == null || iti.size() < 2) throw new ErrorDeSimulacion("El itinerario del coche tiene menos de 2 cruces.");
		else mapa.addVehiculo(this.getId(), new Coche(this.getId(), this.getVelocidadMax(), this.resistencia, this.probabilidad, this.seed, this.duracionMaximaAveria, iti));
	}
	
	public String toString()
	{
		return "Nuevo coche " + this.getId();
	}
}
