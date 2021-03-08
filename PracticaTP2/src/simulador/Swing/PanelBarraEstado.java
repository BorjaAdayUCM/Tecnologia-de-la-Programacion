package simulador.Swing;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;

@SuppressWarnings("serial")
public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico
{
	private JLabel infoEjecucion;
	
	 public PanelBarraEstado(String mensaje, Controlador controlador)
	 {
		 this.setLayout(new FlowLayout(FlowLayout.LEFT));
		 this.infoEjecucion = new JLabel(mensaje);
		 this.add(this.infoEjecucion);
		 this.setBorder(BorderFactory.createBevelBorder(1));
		 controlador.addObserver(this);
	 }
	 
	 public void setMensaje(String mensaje)
	 {
		this.infoEjecucion.setText(mensaje);
	 }

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e)
	{
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				infoEjecucion.setText(e.getMessage());
			}
		});
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				infoEjecucion.setText("Paso: " + tiempo + " del Simulador");
			}
		});
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		this.infoEjecucion.setText("Evento añadido al simulador");
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Apéndice de método generado automáticamente
		
	}

}
