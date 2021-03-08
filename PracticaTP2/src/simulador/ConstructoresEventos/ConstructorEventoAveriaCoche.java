package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoAveriaCoche;
import simulador.Ini.IniSection;

public class ConstructorEventoAveriaCoche extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento averia coche.
	 */
	public ConstructorEventoAveriaCoche()
	{
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] {"time", "vehicles", "duration"};
		this.valoresPorDefecto = new String[] {"", "", ""};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null) return null;
		else
			return new EventoAveriaCoche(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
										  ConstructorEventos.parseaIntNoNegativo(section, "duration", 1),
										  ConstructorEventos.parseaList(section, "vehicles"));
	}

	@Override
	public String toString() {
		return "Evento Averia Coche";
	}
}