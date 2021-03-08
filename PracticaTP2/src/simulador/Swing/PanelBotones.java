package simulador.Swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelBotones extends JPanel {

	public PanelBotones(DialogoInformes dialogoInformes) {
		JButton Cancel = new JButton("Cancelar");
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogoInformes.setVisible(false);
			}
		});
		JButton Generate = new JButton("Generar");
		Generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogoInformes.generaInforme();
				dialogoInformes.setVisible(false);
			}
		});
		this.add(Cancel);
		this.add(Generate);
	}

}
