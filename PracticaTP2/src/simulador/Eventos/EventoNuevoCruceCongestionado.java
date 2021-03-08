package simulador.Eventos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceCongestionado;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce 
{
	/**
	 * Instancia un evento nuevo cruce congestionado.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id del cruce congestionado
	 */
	public EventoNuevoCruceCongestionado(int tiempo, String id) 
	{
		super(tiempo, id);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		mapa.addCruce(this.getId(), new CruceCongestionado(this.getId()));
	}
	
	public String toString()
	{
		return "Nuevo cruce congestionado " + this.getId();
	}
}
