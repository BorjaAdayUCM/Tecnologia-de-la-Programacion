package simulador.Swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class PanelEditorEventos extends PanelAreaTexto 
{
	public PanelEditorEventos(String titulo, String texto, boolean editable, VentanaPrincipal mainWindow) 
	{
		super(titulo,editable);
		this.setTexto(texto);
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		this.areatexto.add(popUp); 
		this.areatexto.addMouseListener(new MouseListener() 
		{//hay que ponerlo en ambos por problemas de inconsistencias al usar mac/windows
			@Override
			public void mousePressed(MouseEvent e) 
			{
				//if (e.isPopupTrigger() && areatexto.isEnabled()) popUp.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() && areatexto.isEnabled()) popUp.show(e.getComponent(), e.getX(), e.getY());
			};
			@Override
			public void mouseClicked(MouseEvent e) {};
			@Override
			public void mouseEntered(MouseEvent e) {};
			@Override
			public void mouseExited(MouseEvent e) {};
		});
	}
}
