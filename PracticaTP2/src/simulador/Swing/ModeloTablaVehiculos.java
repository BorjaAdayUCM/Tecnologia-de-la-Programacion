package simulador.Swing;

import java.util.List;

import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Vehiculos.Vehiculo;

@SuppressWarnings("serial")
public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> 
{

	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl) 
	{
		super(columnIdEventos, ctrl);
	}

	@Override
	public Object getValueAt(int indiceFil, int indiceCol) 
	{
		Object s = null;
		switch (indiceCol) 
		{
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCarretera(); break; 
			case 2: s = this.lista.get(indiceFil).isHaLlegado() ? "arrived" : this.lista.get(indiceFil).getLocalizacion(); break;
			case 3: s = this.lista.get(indiceFil).getVelocidadAct(); break;
			case 4: s = this.lista.get(indiceFil).getKilometraje(); break;
			case 5: s = this.lista.get(indiceFil).getTiempoDeInfraccion(); break;
			case 6: s = this.lista.get(indiceFil).getItinerario(); break;
			default: assert (false);
		}
		return s; 
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				lista = mapa.getVehiculos();
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.fireTableStructureChanged();
	}

}
