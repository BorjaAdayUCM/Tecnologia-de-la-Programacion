package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevoCruceCongestionado;
import simulador.Ini.IniSection;


public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nuevo cruce congestionado.
	 */
	public ConstructorEventoNuevoCruceCongestionado()
	{
		this.etiqueta = "new_junction";
		this.claves = new String[] {"time", "id", "type"};
		this.valoresPorDefecto = new String[] {"", "", "mc"};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("mc")) return null;
		else return new EventoNuevoCruceCongestionado(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
												 	  ConstructorEventos.identificadorValido(section, "id"));
	}

	@Override
	public String toString() {
		return "Evento Nuevo Cruce Congestionado";
	}
	
	
}
