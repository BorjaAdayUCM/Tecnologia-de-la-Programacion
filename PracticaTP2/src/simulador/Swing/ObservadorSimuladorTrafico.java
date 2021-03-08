package simulador.Swing;

import java.util.List;

import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;

public interface ObservadorSimuladorTrafico {
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e);
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
}