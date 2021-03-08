package simulador.Swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulador.Controlador.Controlador;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Vehiculo;

@SuppressWarnings("serial")
public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico
{

	protected static final char TECLALIMPIAR = 99;
	protected static final char TECLASELECCIONTODO = 97;
	
	private VentanaPrincipal ventanaPrincipal;
	private PanelBotones panelBotones;
	private PanelObjSim<Vehiculo> panelVehiculos;
	private PanelObjSim<Carretera> panelCarreteras;
	private PanelObjSim<CruceGenerico<?>> panelCruces;
	
	public DialogoInformes(VentanaPrincipal ventanaPrincipal, Controlador controlador)
	{
		super(ventanaPrincipal, true);
		controlador.addObserver(this);
		this.ventanaPrincipal = ventanaPrincipal;
		this.initGUI();
	}

	private void initGUI() 
	{
		this.setTitle("Generar Informes");
		this.setResizable(false);
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");
		this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");
		this.panelBotones = new PanelBotones(this);
		InformationPanel panelInfo = new InformationPanel();
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(1,3));
		panelCentral.add(this.panelVehiculos);
		panelCentral.add(this.panelCarreteras);
		panelCentral.add(this.panelCruces);
		panelPrincipal.add(panelInfo,BorderLayout.PAGE_START);
		panelPrincipal.add(panelCentral,BorderLayout.CENTER);
		panelPrincipal.add(this.panelBotones, BorderLayout.PAGE_END);
		this.add(panelPrincipal);
		this.pack();
	}
	 
	 public void mostrar() 
	 { 
		 this.setVisible(true);
	 }
	
	 private void setMapa(MapaCarreteras mapa) 
	 {
	  this.panelVehiculos.setList(mapa.getVehiculos());
	  this.panelCarreteras.setList(mapa.getCarreteras());
	  this.panelCruces.setList(mapa.getCruces());
	 }
	
	public List<Vehiculo> getVehiculosSeleccionados() 
	{
		return this.panelVehiculos.getSelectedItems();
	}

	public List<Carretera> getCarreterasSeleccionadas() 
	{
		return this.panelCarreteras.getSelectedItems();
	}

	 public List<CruceGenerico<?>> getCrucesSeleccionados() 
	 {
		 return this.panelCruces.getSelectedItems();
	 }
	 
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		SwingUtilities.invokeLater(new Thread(){
			@Override
			public void run(){
				setMapa(mapa);
			}
		});
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos)
	{
		 this.setMapa(mapa);
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) 
	{
		 this.setMapa(mapa);
	}

	public void generaInforme() {
		this.ventanaPrincipal.escribeInforme();
	}
	
	

}
