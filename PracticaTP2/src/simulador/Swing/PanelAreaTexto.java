package simulador.Swing;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
abstract public class PanelAreaTexto extends JPanel
{
	protected JTextArea areatexto;
	
	public PanelAreaTexto(String titulo, boolean editable) 
	{ 
		this.setLayout(new GridLayout(1,1));
		this.areatexto = new JTextArea(40, 30); 
		this.areatexto.setEditable(editable);
		this.add(new JScrollPane(this.areatexto)); 
		this.setBorde(titulo);
	}
	
	public void setBorde(String titulo)
	{ 
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), titulo));
	}
	
	public String getTexto()
	{
		return this.areatexto.getText();
	}
	
	public void setTexto(String texto) 
	{
		this.areatexto.setText(texto);
	} 
	
	public void limpiar() 
	{
		this.areatexto.setText("");
		this.setBorde("Eventos: ");
	}
	
	public void inserta(String valor) 
	{
		this.areatexto.insert(valor, this.areatexto.getCaretPosition()); 
	}
}
