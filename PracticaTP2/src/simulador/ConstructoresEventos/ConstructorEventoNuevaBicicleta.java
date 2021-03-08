package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevaBicicleta;
import simulador.Ini.IniSection;


public class ConstructorEventoNuevaBicicleta extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nueva bicicleta.
	 */
	public ConstructorEventoNuevaBicicleta()
	{
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "itinerary", "max_speed", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "bike"};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("bike")) return null;
		else
			return new EventoNuevaBicicleta(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 						ConstructorEventos.identificadorValido(section, "id"),
 				 					 	ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 					 	ConstructorEventos.parseaList(section, "itinerary"));	
	}

	@Override
	public String toString() {
		return "Evento Nueva Bicicleta";
	}
	
	
}
