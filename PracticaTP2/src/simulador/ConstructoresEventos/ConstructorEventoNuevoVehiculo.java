package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevoVehiculo;
import simulador.Ini.IniSection;


public class ConstructorEventoNuevoVehiculo extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nuevo vehiculo.
	 */
	public ConstructorEventoNuevoVehiculo()
	{
		this.etiqueta = "new_vehicle";
		this.claves = new String[] {"time", "id", "itinerary", "max_speed"};
		this.valoresPorDefecto = new String[] {"", "", "", ""};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null) return null;
		else return new EventoNuevoVehiculo(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 						 	ConstructorEventos.identificadorValido(section, "id"),
 				 						 	ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
 				 						 	ConstructorEventos.parseaList(section, "itinerary"));	
	}

	@Override
	public String toString() {
		return "Evento Nuevo Vehiculo";
	}
	
	
}
