package simulador.Utilidades;

import simulador.ConstructoresEventos.ConstructorEventoAveriaCoche;
import simulador.ConstructoresEventos.ConstructorEventoNuevaAutopista;
import simulador.ConstructoresEventos.ConstructorEventoNuevaBicicleta;
import simulador.ConstructoresEventos.ConstructorEventoNuevaCarretera;
import simulador.ConstructoresEventos.ConstructorEventoNuevoCamino;
import simulador.ConstructoresEventos.ConstructorEventoNuevoCoche;
import simulador.ConstructoresEventos.ConstructorEventoNuevoCruce;
import simulador.ConstructoresEventos.ConstructorEventoNuevoCruceCircular;
import simulador.ConstructoresEventos.ConstructorEventoNuevoCruceCongestionado;
import simulador.ConstructoresEventos.ConstructorEventoNuevoVehiculo;
import simulador.ConstructoresEventos.ConstructorEventos;
import simulador.Eventos.Evento;
import simulador.Ini.IniSection;

public class ParserEventos 
{
	private static ConstructorEventos[] eventos =
	{
		new ConstructorEventoNuevoCruce(),
		new ConstructorEventoNuevaCarretera(),
		new ConstructorEventoNuevoVehiculo(),
		new ConstructorEventoAveriaCoche(),
		new ConstructorEventoNuevoCoche(),
		new ConstructorEventoNuevaBicicleta(),
		new ConstructorEventoNuevaAutopista(),
		new ConstructorEventoNuevoCamino(),
		new ConstructorEventoNuevoCruceCircular(),
		new ConstructorEventoNuevoCruceCongestionado()
	};
	
	/**
	 * Busca el evento con el que se corresponde la IniSection.
	 *
	 * @param sec la Inisection.
	 * @return Evento que se halla en la Inisection
	 */
	public static Evento parseaEvento(IniSection sec)
	{
		int i = 0;
		boolean seguir = true;
		Evento e = null;
		while (i < ParserEventos.eventos.length && seguir)
		{
			e = ParserEventos.eventos[i].parser(sec);
			if (e != null) seguir = false;
			else i++;
		}
		return e;
	}

	public static ConstructorEventos[] getConstrutoresEventos() {
		return eventos;
	}

	
}
