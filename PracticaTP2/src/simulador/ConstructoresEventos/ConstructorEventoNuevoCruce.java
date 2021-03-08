package simulador.ConstructoresEventos;

import simulador.Eventos.Evento;
import simulador.Eventos.EventoNuevoCruce;
import simulador.Ini.IniSection;


public class ConstructorEventoNuevoCruce extends ConstructorEventos 
{
	/**
	 * Instancia un constructor evento nuevo cruce.
	 */
	public ConstructorEventoNuevoCruce()
	{
		this.etiqueta = "new_junction";
		this.claves = new String[] {"time", "id"};
		this.valoresPorDefecto = new String[] {"", ""};
	}
	
	@Override
	public Evento parser(IniSection section) 
	{
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null) return null;
		else return new EventoNuevoCruce(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
										 ConstructorEventos.identificadorValido(section, "id"));
	}

	@Override
	public String toString() {
		return "Evento Nuevo Cruce";
	}
	
	
}
