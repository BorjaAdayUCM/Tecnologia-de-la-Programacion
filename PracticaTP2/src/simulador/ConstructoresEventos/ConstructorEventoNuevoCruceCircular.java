package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevoCruceCircular;
import simulador.Ini.IniSection;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nuevo cruce circular.
	 */
	public ConstructorEventoNuevoCruceCircular()
	{
		this.etiqueta = "new_junction";
		this.claves = new String[] {"time", "id", "min_time_slice", "max_time_slice", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "rr"};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("rr")) return null;
		else return new EventoNuevoCruceCircular(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
												 ConstructorEventos.identificadorValido(section, "id"),
												 ConstructorEventos.parseaIntNoNegativo(section, "min_time_slice", 1),
												 ConstructorEventos.parseaIntNoNegativo(section, "max_time_slice", 2));
	}

	@Override
	public String toString() {
		return "Evento Nuevo Cruce Circular";
	}
	
	
}
