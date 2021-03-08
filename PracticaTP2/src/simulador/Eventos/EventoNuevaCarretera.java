package simulador.Eventos;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Cruces.CruceGenerico;

public class EventoNuevaCarretera extends Evento 
{
	private String id;  
	private int velocidadMax;  
	private int longitud;  
	private String cruceOrigenId;  
	private String cruceDestinoId;
	
	/**
	 * Instancia un evento nueva carretera.
	 *
	 * @param tiempo de ejecucion del evento
	 * @param id de la carretera
	 * @param origen (cruce de origen)
	 * @param destino (cruce de destino)
	 * @param velocidadMax de la carretera
	 * @param longitud de la carretera
	 */
	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino, int velocidadMax, int longitud)
	{
		super(tiempo);
		this.id = id;
		this.cruceOrigenId = origen;
		this.cruceDestinoId = destino;
		this.velocidadMax = velocidadMax;
		this.longitud = longitud;
	}
	
	public String getId() 
	{
		return id;
	}

	public int getVelocidadMax() 
	{
		return velocidadMax;
	}

	public int getLongitud() 
	{
		return longitud;
	}

	public String getCruceOrigenId() 
	{
		return cruceOrigenId;
	}

	public String getCruceDestinoId() 
	{
		return cruceDestinoId;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion
	{
		CruceGenerico<?> cruceOrigen = mapa.getCruce(cruceOrigenId);
		CruceGenerico<?> cruceDestino = mapa.getCruce(cruceDestinoId);
		mapa.addCarretera(this.id, cruceOrigen, cruceDestino, new Carretera(this.id, this.longitud, this.velocidadMax, cruceOrigen, cruceDestino));
	}
	
	public String toString()
	{
		return "Nueva carretera " + this.getId();
	}
}
