package simulador.Swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import simulador.Controlador.Controlador;
import simulador.Utilidades.Utils;

@SuppressWarnings("serial")
public class BarraMenu extends JMenuBar 
{
	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) 
	{
		 super();
		 // MANEJO DE FICHEROS
		 JMenu menuFicheros = new JMenu("Ficheros");
		 this.add(menuFicheros);
		 this.creaMenuFicheros(menuFicheros, mainWindow);
		 
		 // SIMULADOR
		 JMenu menuSimulador = new JMenu("Simulador");
		 this.add(menuSimulador);
		 this.creaMenuSimulador(menuSimulador,controlador,mainWindow);
		 
		 // INFORMES
		 JMenu menuReport = new JMenu("Informes");
		 this.add(menuReport);
		 this.creaMenuInformes(menuReport,mainWindow);
	}
	
	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow)
	{
			 JMenuItem generaInformes = new JMenuItem("Generar");
			 generaInformes.addActionListener(new ActionListener() 
			 {
				 @Override
				 public void actionPerformed(ActionEvent e)
				 {
					 mainWindow.generaInforme();
					 mainWindow.setMensaje("Informes generados!");
				 }
			 });
			 
			 menuReport.add(generaInformes);
			 JMenuItem limpiaAreaInformes = new JMenuItem("Limpia");
			 generaInformes.addActionListener(new ActionListener() 
			 {
				 @Override
				 public void actionPerformed(ActionEvent e)
				 {
				 	 mainWindow.limpiaAreaInformes();
				 }
			 });
			 menuReport.add(limpiaAreaInformes);
	}

	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador,VentanaPrincipal mainWindow)
	{
			 JMenuItem ejecuta = new JMenuItem("Ejecuta");
			 ejecuta.addActionListener(new ActionListener() 
			 	{
				 @Override
				 public void actionPerformed(ActionEvent e)
				 {
					 int pasos = mainWindow.getSteps();
					 try
					 {
						 controlador.ejecuta(pasos);
						 mainWindow.setMensaje("Simulador lanzado!");
					 }
					 catch (Exception e1)
					 {
						 Utils.dialogoError(e1.getMessage());
						 controlador.reinicia();
					 }
				 }
			 	}
			 );
			 
			 JMenuItem reinicia = new JMenuItem("Reinicia");
			 reinicia.addActionListener(new ActionListener() 
			 	{
				 @Override
				 public void actionPerformed(ActionEvent e) {
					controlador.reinicia();
					mainWindow.limpiaAreaEventos();
					mainWindow.limpiaAreaInformes();
					mainWindow.setMensaje("Simulador reiniciado!");
				 }
			 	}
			 );

			 menuSimulador.add(ejecuta);
			 menuSimulador.add(reinicia);
	}

	private void creaMenuFicheros(JMenu menu,VentanaPrincipal mainWindow)
	{
		JMenuItem cargar = new JMenuItem("Carga Eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() 
		 {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		 }
		);
		
		JMenuItem salvar = new JMenuItem("Salva Eventos");
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener() 
		 {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaFichero(mainWindow.getPanelEditorEventos());
			}
		 }
		);
		
		JMenuItem salvarInformes = new JMenuItem("Salva Informes");
		salvarInformes.setMnemonic(KeyEvent.VK_R);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		salvarInformes.addActionListener(new ActionListener() 
		 {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaFichero(mainWindow.getPanelInformes());
			}
		 }
		);
		
		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() 
		 {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mainWindow.quit();
			}
		 }
		);
	
		  menu.add(cargar);
		  menu.add(salvar);
		  menu.addSeparator();
		  menu.add(salvarInformes);
		  menu.addSeparator();
		  menu.add(salir);
	}
}
