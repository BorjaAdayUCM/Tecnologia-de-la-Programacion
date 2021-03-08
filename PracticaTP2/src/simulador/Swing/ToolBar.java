package simulador.Swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Utilidades.Utils;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {

	private JButton botonCargar;
	private JButton botonSalvar;
	private JButton botonClean;
	private JButton botonCheckIn;
	private JButton botonStart;
	private JButton botonStop;
	private JButton botonReset;
	private JButton botonGeneraReports;
	private JButton botonLimpiaReport;
	private JButton botonGuardaReport;
	private JButton botonExit;
	private JSpinner delay;
	private JSpinner steps;
	private JTextField time;

	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		controlador.addObserver(this);

		JButton botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(Utils.loadImage("iconos/open.png"));
		botonCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		this.botonCargar = botonCargar;
		this.add(this.botonCargar);
		
		JButton botonSalvar = new JButton();
		botonSalvar.setToolTipText("Salva un fichero de eventos");
		botonSalvar.setIcon(Utils.loadImage("iconos/save.png"));
		botonSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaFichero(mainWindow.getPanelEditorEventos());
				mainWindow.setMensaje("Eventos guardados!");
			}
		});
		this.botonSalvar = botonSalvar;
		this.add(this.botonSalvar);

		JButton botonClean = new JButton();
		botonClean.setToolTipText("Limpia el editor de eventos ");
		botonClean.setIcon(Utils.loadImage("iconos/clear.png"));
		botonClean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaAreaEventos();
				mainWindow.setMensaje("Editor de eventos limpiado!");
			}
		});
		this.botonClean = botonClean;
		this.add(this.botonClean);
		
		this.addSeparator(new Dimension(50, 50));

		JButton botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		botonCheckIn.setIcon(Utils.loadImage("iconos/events.png"));
		botonCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try 
				{
					byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
					controlador.cargaEventos(new ByteArrayInputStream(contenido));
					mainWindow.setMensaje("Eventos cargados al simulador!");
				} 
				catch (Exception e1) {
					Utils.dialogoError(e1.getMessage());
					mainWindow.setMensaje("Error en la carga");
					controlador.reinicia();
				}
			}
		});
		this.botonCheckIn = botonCheckIn;
		this.add(this.botonCheckIn);

		JButton botonStart = new JButton();
		botonStart.setToolTipText("Lanza el simulador");
		botonStart.setIcon(Utils.loadImage("iconos/play.png"));
		botonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try 
				{
					mainWindow.estadoToolBar(false);
					mainWindow.ejecuta(mainWindow.getSteps(), mainWindow.getDelay());
					mainWindow.setMensaje("Simulador lanzado!");
				} 
				catch (Exception e1) 
				{
				Utils.dialogoError(e1.getMessage());
				controlador.reinicia();
				}
			}
		});
		this.botonStart = botonStart;
		this.add(this.botonStart);
		
		JButton botonStop = new JButton();
		botonStop.setToolTipText("Para el simulador");
		botonStop.setIcon(Utils.loadImage("iconos/stop.png"));
		botonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.estadoToolBar(true);
				mainWindow.parar();
				mainWindow.setMensaje("Simulador parado!");
			}
		});
		this.botonStop = botonStop;
		this.add(this.botonStop);

		JButton botonReset = new JButton();
		botonReset.setToolTipText("Reinicia el simulador");
		botonReset.setIcon(Utils.loadImage("iconos/reset.png"));
		botonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reinicia();
				mainWindow.limpiaAreaInformes();
				mainWindow.setMensaje("Simulador reiniciado!");
			}
		});
		this.botonReset = botonReset;
		this.add(this.botonReset);

		this.addSeparator(new Dimension(20, 20));
		
		this.add(new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 1, 5000, 1));
		this.delay.setToolTipText("Delay de la simulación: 1-5000");
		this.delay.setMaximumSize(new Dimension(70, 45));
		this.delay.setMinimumSize(new Dimension(70, 45));
		this.delay.setValue(1);
		this.add(this.delay);
		this.addSeparator(new Dimension(10, 10));

		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 45));
		this.steps.setMinimumSize(new Dimension(70, 45));
		this.steps.setValue(1);
		this.add(this.steps);
		this.addSeparator(new Dimension(10, 10));

		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 45));
		this.time.setMinimumSize(new Dimension(70, 45));
		this.time.setEditable(false);
		this.add(this.time);

		this.addSeparator(new Dimension(50, 50));

		JButton botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Generar informes");
		botonGeneraReports.setIcon(Utils.loadImage("iconos/report.png"));
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generaInforme();
				mainWindow.setMensaje("Informes generados!");
			}
		});
		this.botonGeneraReports = botonGeneraReports;
		this.add(this.botonGeneraReports);

		JButton botonLimpiaReport = new JButton();
		botonLimpiaReport.setToolTipText("Limpia informes");
		botonLimpiaReport.setIcon(Utils.loadImage("iconos/delete_report.png"));
		botonLimpiaReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaAreaInformes();
				mainWindow.setMensaje("Informes limpiados!");
			}
		});
		this.botonLimpiaReport = botonLimpiaReport;
		this.add(this.botonLimpiaReport);
		
		JButton botonGuardaReport = new JButton();
		botonGuardaReport.setToolTipText("Guarda informe");
		botonGuardaReport.setIcon(Utils.loadImage("iconos/save_report.png"));
		botonGuardaReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaFichero(mainWindow.getPanelInformes());
				mainWindow.setMensaje("Informes guardados!");
			}
		});
		this.botonGuardaReport = botonGuardaReport;
		this.add(this.botonGuardaReport);

		this.addSeparator(new Dimension(50, 50));

		JButton botonExit = new JButton();
		botonExit.setToolTipText("Salir");
		botonExit.setIcon(Utils.loadImage("iconos/exit.png"));
		botonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.quit();
			}
		});
		this.botonExit = botonExit;
		this.add(this.botonExit);
	}
	
	public void estadoToolBar(boolean activo)
	{
		this.botonCargar.setEnabled(activo);
		this.botonSalvar.setEnabled(activo);
		this.botonClean.setEnabled(activo);
		this.botonCheckIn.setEnabled(activo);
		this.botonStart.setEnabled(activo);
		this.botonReset.setEnabled(activo);
		this.botonGeneraReports.setEnabled(activo);
		this.botonLimpiaReport.setEnabled(activo);
		this.botonGuardaReport.setEnabled(activo);
		this.botonExit.setEnabled(activo);
	}

	public int getSteps() {
		return (int) this.steps.getValue();
	}
	
	public int getDelay() {
		return (int) this.delay.getValue();
	}
	
	public int getTime()
	{
		return Integer.parseInt(this.time.getText());
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				time.setText(""+tiempo);
			}
		});
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				delay.setValue(1);
				steps.setValue(1);
				time.setText("0");
			}
		});
		
	}

}
