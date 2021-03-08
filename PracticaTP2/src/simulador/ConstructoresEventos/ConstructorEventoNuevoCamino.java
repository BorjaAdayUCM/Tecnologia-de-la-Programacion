package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevoCamino;
import simulador.Ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nuevo camino.
	 */
	public ConstructorEventoNuevoCamino()
	{
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", "dirt"};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("dirt")) return null;
		else return new EventoNuevoCamino(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
											 ConstructorEventos.identificadorValido(section, "id"),
											 ConstructorEventos.identificadorValido(section, "src"),
											 ConstructorEventos.identificadorValido(section, "dest"),
											 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 1),
											 ConstructorEventos.parseaIntNoNegativo(section, "length", 1));
	}

	@Override
	public String toString() {
		return "Evento Nuevo Camino";
	}
	
	
}
