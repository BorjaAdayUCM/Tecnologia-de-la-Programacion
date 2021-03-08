package simulador.Swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import simulador.ConstructoresEventos.ConstructorEventos;
import simulador.Utilidades.ParserEventos;

@SuppressWarnings("serial")
public class PopUpMenu extends JPopupMenu implements ActionListener
{
	private VentanaPrincipal mainWindow;
	
	public PopUpMenu(VentanaPrincipal mainWindow) {
		this.mainWindow = mainWindow;
		
		JMenu plantillas = new JMenu("Nueva plantilla");
		this.add(plantillas);

		this.addSeparator();
		
		JMenuItem Load = new JMenuItem("Cargar");
		Load.setActionCommand("Cargar");
		Load.addActionListener(this);
		this.add(Load);

		JMenuItem Save = new JMenuItem("Guardar");
		Save.setActionCommand("Guardar");
		Save.addActionListener(this);
		this.add(Save);

		JMenuItem Clear = new JMenuItem("Limpiar");
		Clear.setActionCommand("Limpiar");
		Clear.addActionListener(this);
		this.add(Clear);

		this.creaMenuPlantilla(plantillas, mainWindow);
	}

	private void creaMenuPlantilla(JMenu plantillas, VentanaPrincipal mainWindow) {
		for (ConstructorEventos ce : ParserEventos.getConstrutoresEventos()) {
			JMenuItem mi = new JMenuItem(ce.toString());
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.inserta(ce.template() + '\n');
				}
			});
			plantillas.add(mi);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("cargar")) this.mainWindow.cargaFichero();
		else if (e.getActionCommand().equalsIgnoreCase("guardar")) this.mainWindow.salvaFichero(this.mainWindow.getPanelEditorEventos());
		else if (e.getActionCommand().equalsIgnoreCase("limpiar")) this.mainWindow.limpiaAreaEventos();
	}
}
