package simulador.Eventos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Carreteras.Autopista;
import simulador.Objetos.Cruces.CruceGenerico;
	
public class EventoNuevaAutopista extends EventoNuevaCarretera
{
	private int numCarriles;
	
	/**
	 * Instancia un evento nueva autopista.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id de la autopista
	 * @param origen (cruce de origen)
	 * @param destino (cruce de destino)
	 * @param velocidadMax de la autopista
	 * @param longitud de la autopista
	 * @param numCarriles de la autopista
	 */
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMax, int longitud, int numCarriles) 
	{
		super(tiempo, id, origen, destino, velocidadMax, longitud);
		this.numCarriles = numCarriles;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion 
	{
		CruceGenerico<?> cruceOrigen = mapa.getCruce(getCruceOrigenId());
		CruceGenerico<?> cruceDestino = mapa.getCruce(getCruceDestinoId());
		mapa.addCarretera(this.getId(), cruceOrigen, cruceDestino, new Autopista(this.getId(), this.getLongitud(), this.getVelocidadMax(), cruceOrigen, cruceDestino, this.numCarriles));
	}
	
	public String toString()
	{
		return "Nueva autopista " + this.getId();
	}
}
