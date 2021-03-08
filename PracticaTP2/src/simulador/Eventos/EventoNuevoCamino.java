package simulador.Eventos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Carreteras.Camino;
import simulador.Objetos.Cruces.CruceGenerico;

public class EventoNuevoCamino extends EventoNuevaCarretera 
{
	/**
	 * Instancia un evento nuevo camino.
	 *
	 * @param tiempo de ejecucion
	 * @param id del camino
	 * @param origen (cruce de origen)
	 * @param destino (cruce de destino)
	 * @param velocidadMax del camino
	 * @param longitud del camino
	 */
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMax, int longitud) 
	{
		super(tiempo, id, origen, destino, velocidadMax, longitud);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion 
	{
		CruceGenerico<?> cruceOrigen = mapa.getCruce(getCruceOrigenId());
		CruceGenerico<?> cruceDestino = mapa.getCruce(getCruceDestinoId());
		mapa.addCarretera(this.getId(), cruceOrigen, cruceDestino, new Camino(this.getId(), this.getLongitud(), this.getVelocidadMax(), cruceOrigen, cruceDestino));
	}
	
	public String toString()
	{
		return "Nuevo camino " + this.getId();
	}
}
