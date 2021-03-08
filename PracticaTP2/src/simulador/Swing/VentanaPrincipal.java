package simulador.Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Vehiculo;
import simulador.Utilidades.Utils;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame
{
	// MODEL PART - VIEW CONTROLLER MODEL
	private Controlador controlador;
	private File ficheroActual;
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);
	
	// SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };
	private PanelTabla<Evento> panelColaEventos;
	private PanelAreaTexto panelEditorEventos; 
	private PanelAreaTexto panelInformes; 
	
	// MENU AND TOOL BAR
	private JFileChooser fc; 
	private ToolBar toolbar;
	
	// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	
	// STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;
	
	// INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera", "Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen", "Destino", "Longitud", "Vel. Max", "Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
	private PanelTabla<Vehiculo> panelVehiculos; 
	private PanelTabla<Carretera> panelCarreteras; 
	private PanelTabla<CruceGenerico<?>> panelCruces;
	
	// REPORT DIALOG
	private DialogoInformes dialogoInformes;
	
	//HEBRA
	private Thread hebraEjecucion;
	
	public VentanaPrincipal(String ficheroEntrada, Controlador ctrl) throws IOException 
	{ 
		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
		this.initGUI();
	
	}
	
	private void initGUI() throws IOException 
	{ 
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		
		this.addWindowListener(new WindowListener() 
		{
			@Override
			public void windowActivated(WindowEvent arg0) {};
			@Override
			public void windowClosed(WindowEvent arg0) {};
			
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				quit();
			}; 
			
			@Override
			public void windowDeactivated(WindowEvent e) {};
			@Override
			public void windowDeiconified(WindowEvent e) {};
			@Override
			public void windowIconified(WindowEvent e) {};
			@Override
			public void windowOpened(WindowEvent e) {};
		});
		
		JPanel panelPrincipal = this.creaPanelPrincipal(); 
		this.setContentPane(panelPrincipal);
		
		// BARRA DE ESTADO INFERIOR
		// (contiene una JLabel para mostrar el estado delsimulador) 
		this.addBarraEstado(panelPrincipal);
		
		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior) 
		JPanel panelCentral = this.createPanelCentral(); 
		panelPrincipal.add(panelCentral,BorderLayout.CENTER);
		
		// PANEL SUPERIOR
		this.createPanelSuperior(panelCentral);
		
		// MENU
		BarraMenu menubar = new BarraMenu(this, this.controlador); 
		this.setJMenuBar(menubar);
		
		// PANEL INFERIOR
		this.createPanelInferior(panelCentral); 
		
		// BARRA DE HERRAMIENTAS 
		this.addToolBar(panelPrincipal);
		
		// FILE CHOOSER
		this.fc = new JFileChooser();
		
		// REPORT DIALOG (OPCIONAL)
		this.dialogoInformes = new DialogoInformes(this, this.controlador);
		
		this.pack();
		this.setVisible(true);
	}


	private void addToolBar(JPanel panelPrincipal) 
	{
		this.toolbar = new ToolBar(this, this.controlador);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);

	}

	private void createPanelInferior(JPanel panelCentral) 
	{
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout (panelInferior, BoxLayout.X_AXIS));
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new GridLayout(3,1));
		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos", new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo, this.controlador));
		this.panelCarreteras = new PanelTabla<Carretera>("Carretras", new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera, this.controlador));
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces", new ModeloTablaCruces(VentanaPrincipal.columnIdCruce, this.controlador));
		this.componenteMapa = new ComponenteMapa(this.controlador);
		// añadir un ScroolPane al panel inferior donde se coloca la componente.
		panelIzquierda.add(this.panelVehiculos);
		panelIzquierda.add(this.panelCarreteras);
		panelIzquierda.add(this.panelCruces);
		panelInferior.add(panelIzquierda);
		panelInferior.add(new JScrollPane(this.componenteMapa),BorderLayout.EAST);
		panelCentral.add(panelInferior);
	}

	private void createPanelSuperior(JPanel panelCentral) throws FileNotFoundException
	{
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout (panelSuperior,BoxLayout.X_AXIS));
		this.panelEditorEventos = new PanelEditorEventos("Eventos: ", "", true, this);
		if (this.ficheroActual != null) this.panelEditorEventos.setTexto(this.leeFichero(this.ficheroActual));
		this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ", new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
		this.panelInformes = new PanelInformes("Informes: ", false, this.controlador);
		panelSuperior.add(this.panelEditorEventos);
		panelSuperior.add(this.panelColaEventos);
		panelSuperior.add(this.panelInformes);
		
		panelCentral.add(panelSuperior);
	}

	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador !", this.controlador);
		panelPrincipal.add(this.panelBarraEstado,BorderLayout.PAGE_END);
	}

	private JPanel creaPanelPrincipal() {
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BorderLayout());
		return panelSuperior;
	}

	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(2,1)); 
		return panelCentral;
	}
	
	public void cargaFichero()
	{
		this.fc.setCurrentDirectory(new File("Comprobaciones"));
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			File fichero = this.fc.getSelectedFile();
			try 
			{
				String s = this.leeFichero(fichero);
				this.ficheroActual = fichero;
				this.panelEditorEventos.setTexto(s);
				this.controlador.setFichero(new FileInputStream(fichero));
			}
			catch (FileNotFoundException e) 
			{
				this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
			}
		}
	}

	@SuppressWarnings("resource")
	private String leeFichero(File fichero) throws FileNotFoundException 
	{
		this.panelEditorEventos.setBorde(fichero.getName());
		this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() + " de eventos cargado en el editor!");
		return new Scanner(fichero).useDelimiter("\\A").next();
	}

	public void salvaFichero(PanelAreaTexto panel)
	{
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			escribeFichero(file, panel.getTexto());
		}
	}

	private void escribeFichero(File fichero, String s)
	{
		try {
			PrintWriter pw = new PrintWriter(fichero);
			pw.print(s);
			pw.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private void muestraDialogoError(String mensaje)
	{
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void quit()
	{
		if (JOptionPane.showOptionDialog(null, "¿Quieres salir?", "Confirmar salida.", JOptionPane.YES_NO_OPTION, 
		   JOptionPane.QUESTION_MESSAGE, Utils.loadImage("iconos/exit.png"), new Object[] { "Si", "No" }, "Si") == 0) 
		   System.exit(0);
	}

	public PanelAreaTexto getPanelEditorEventos() {
		return panelEditorEventos;
	}

	public PanelAreaTexto getPanelInformes() {
		return panelInformes;
	}

	public int getSteps() {
		return this.toolbar.getSteps();
	}
	
	public int getTime()
	{
		return this.toolbar.getTime();
	}
	
	public int getDelay() {
		return this.toolbar.getDelay();
	}

	public void limpiaAreaInformes() {
		
		this.panelInformes.limpiar();
	}
	
	public void limpiaAreaEventos() {
		
		this.panelEditorEventos.limpiar();
	}
	
	public String getTextoEditorEventos() {
		return this.panelEditorEventos.getTexto();
	}

	public void setMensaje(String string) {
		 this.panelBarraEstado.setMensaje(string);
	}

	public void inserta(String string) {
		this.panelEditorEventos.inserta(string);
	}

	public void generaInforme()
	{
	 this.dialogoInformes.setEnabled(true);
	 this.dialogoInformes.mostrar(); 
	}
	
	public void escribeInforme() {
		MapaCarreteras mapa = new MapaCarreteras();
		mapa.setCarreteras(this.dialogoInformes.getCarreterasSeleccionadas());
		mapa.setCruces(this.dialogoInformes.getCrucesSeleccionados());
		mapa.setVehiculos(this.dialogoInformes.getVehiculosSeleccionados());
		this.panelInformes.setTexto(mapa.generateReport(this.getTime() - 1));
	}
	
	public void ejecuta(int pasos, int delay) {
		this.hebraEjecucion = new Thread(){
			@Override
			public void run()
			{
				int i = 0;
				while(!Thread.interrupted() && i < pasos)
				{
					try {
						controlador.ejecuta(1);
						Thread.sleep(delay);
						i++;
					}
					catch (IOException | InterruptedException | ErrorDeSimulacion e)
					{
						Thread.currentThread().interrupt();
						if (e instanceof ErrorDeSimulacion)
						{
							Utils.dialogoError(e.getMessage());
							controlador.reinicia();
						}
					}
					
				}
				estadoToolBar(true);
			}
		};
		hebraEjecucion.start();
	}

	public void estadoToolBar(boolean activo) {
		this.toolbar.estadoToolBar(activo);
	}

	public void parar() {
		if(this.hebraEjecucion != null && !this.hebraEjecucion.isInterrupted())
			this.hebraEjecucion.interrupt();
	}
}