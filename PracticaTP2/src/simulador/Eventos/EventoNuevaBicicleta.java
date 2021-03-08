package simulador.Eventos;

import java.util.List;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Bicicleta;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo
{
	
	/**
	 * Instancia un evento nueva bicicleta.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id de la bicicleta
	 * @param velocidadMax de la bicicleta
	 * @param itinerario (lista de cruces)
	 */
	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMax, String[] itinerario) 
	{
		super(tiempo, id, velocidadMax, itinerario);
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		List<CruceGenerico<?>> iti = Evento.parseaListaCruces(this.getItinerario() , mapa);
		if (iti == null || iti.size() < 2) throw new ErrorDeSimulacion("El itinerario de la bicicleta tiene menos de 2 cruces.");
		else mapa.addVehiculo(this.getId(), new Bicicleta(this.getId(), this.getVelocidadMax(), iti));
	}
	
	public String toString()
	{
		return "Nueva bicicleta " + this.getId();
	}
}
