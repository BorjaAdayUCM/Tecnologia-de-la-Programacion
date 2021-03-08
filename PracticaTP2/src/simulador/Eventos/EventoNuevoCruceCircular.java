package simulador.Eventos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceCircular;

public class EventoNuevoCruceCircular extends EventoNuevoCruce 
{
	private int max_time_slice;
	private int min_time_slice;
	
	/**
	 * Instancia un evento nuevo cruce circular.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id del cruce circular
	 * @param min del intervalo
	 * @param max del intervalo
	 */
	public EventoNuevoCruceCircular(int tiempo, String id, int min, int max) 
	{
		super(tiempo, id);
		this.min_time_slice = min;
		this.max_time_slice = max;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		mapa.addCruce(this.getId(), new CruceCircular(this.getId(), this.min_time_slice, this.max_time_slice));
	}
	
	public String toString()
	{
		return "Nuevo cruce circular " + this.getId();
	}
}
