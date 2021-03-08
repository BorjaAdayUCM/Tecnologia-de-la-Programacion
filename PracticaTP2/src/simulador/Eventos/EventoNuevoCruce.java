package simulador.Eventos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.Cruce;

public class EventoNuevoCruce extends Evento
{
	private String id;
	
	/**
	 * Instancia un evento nuevo cruce.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id del cruce
	 */
	public EventoNuevoCruce(int tiempo, String id) 
	{
		super(tiempo);
		this.id = id;
	}
	
	public String getId() 
	{
		return id;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		mapa.addCruce(this.id, new Cruce(this.id));
	}
	
	public String toString()
	{
		return "Nuevo cruce " + this.getId();
	}
}