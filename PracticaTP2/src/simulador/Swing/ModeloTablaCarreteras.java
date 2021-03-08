package simulador.Swing;

import java.util.List;

import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Carreteras.Carretera;

@SuppressWarnings("serial")
public class ModeloTablaCarreteras extends ModeloTabla<Carretera> {


	public ModeloTablaCarreteras(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) 
	{
		Object s = null;
		switch (indiceCol) 
		{
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCruceOrigen().getId(); break; 
			case 2: s = this.lista.get(indiceFil).getCruceDestino().getId(); break;
			case 3: s = this.lista.get(indiceFil).getLongitud(); break;
			case 4: s = this.lista.get(indiceFil).getVelocidadMax(); break;
			case 5: s = this.lista.get(indiceFil).getVehiculos(); break;
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
				lista = mapa.getCarreteras();
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
