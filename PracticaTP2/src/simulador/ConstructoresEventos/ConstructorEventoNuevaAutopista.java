package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevaAutopista;
import simulador.Ini.IniSection;


public class ConstructorEventoNuevaAutopista extends ConstructorEventos
{
	/**
	 * Instancia un constructor evento nueva autopista.
	 */
	public ConstructorEventoNuevaAutopista()
	{
		this.etiqueta = "new_road";
		this.claves = new String[] {"time", "id", "src", "dest", "max_speed", "length", "lanes", "type"};
		this.valoresPorDefecto = new String[] {"", "", "", "", "", "", "", "lanes"};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("lanes")) return null;
		else return new EventoNuevaAutopista(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
											 ConstructorEventos.identificadorValido(section, "id"),
											 ConstructorEventos.identificadorValido(section, "src"),
											 ConstructorEventos.identificadorValido(section, "dest"),
											 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 1),
											 ConstructorEventos.parseaIntNoNegativo(section, "length", 1),
											 ConstructorEventos.parseaIntNoNegativo(section, "lanes", 1));
	}

	@Override
	public String toString() {
		return "Evento Nueva Autopista";
	}
	
	
}
