package simulador.Swing;

import java.util.List;

import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;

@SuppressWarnings("serial")
public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico 
{
	public PanelInformes(String titulo, boolean editable, Controlador ctrl) 
	{ 
		super(titulo, editable);
		ctrl.addObserver(this);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e){};

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				inserta(mapa.generateReport(tiempo - 1));
			}
		});
	};

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {};

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {};
}
