package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevoCoche;
import simulador.Ini.IniSection;


public class ConstructorEventoNuevoCoche extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nuevo coche.
	 */
	public ConstructorEventoNuevoCoche()
	{
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "itinerary", "max_speed", "resistance", "fault_probability", "seed", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", "", "car"};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("car")) return null;
		else
			return new EventoNuevoCoche(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 						ConstructorEventos.identificadorValido(section, "id"),
 				 					 	ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 					 	ConstructorEventos.parseaList(section, "itinerary"),
				 					 	ConstructorEventos.parseaIntNoNegativo(section, "resistance", 1),
				 					 	ConstructorEventos.parseaIntNoNegativo(section, "max_fault_duration", 1),
				 					 	ConstructorEventos.parseaLong(section, "seed"),
				 					 	ConstructorEventos.parseaDouble(section, "fault_probability"));	
	}

	@Override
	public String toString() {
		return "Evento Nuevo Coche";
	}
	
	
}
