package simulador.Swing;

import java.util.List;

import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Cruces.CruceGenerico;

@SuppressWarnings("serial")
public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>> {

	public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) 
	{
		Object s = null;
		switch (indiceCol) 
		{
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCarreterasSemaforo(true); break;
			case 2: s = this.lista.get(indiceFil).getCarreterasSemaforo(false); break;
			default: assert (false);
		}
		return s; 
	}
	
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				lista = mapa.getCruces();
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.fireTableStructureChanged();
	}

}
