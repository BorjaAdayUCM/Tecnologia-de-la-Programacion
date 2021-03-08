package simulador.Swing;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class InformationPanel extends JLabel{

	public InformationPanel() {
		super();
		this.setText("<html>Selecciona objetos para generar informes.<br>Usa 'c' para deseleccionar todos.<br>Usa Ctrl + A para seleccionar todos.");
	}
}