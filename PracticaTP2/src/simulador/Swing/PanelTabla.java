package simulador.Swing;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class PanelTabla<T> extends JPanel {
	
	private ModeloTabla<T> modelo;

	public PanelTabla(String bordeId, ModeloTabla<T> modelo) {
		this.setLayout(new GridLayout(1, 1));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), bordeId));
		this.modelo = modelo;
		JTable tabla = new JTable(this.modelo);
		this.add(new JScrollPane(tabla));
	}
}
