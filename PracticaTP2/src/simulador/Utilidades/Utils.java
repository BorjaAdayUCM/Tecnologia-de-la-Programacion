package simulador.Utilidades;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import simulador.Objetos.Vehiculos.Vehiculo;

public abstract class Utils 
{
	
	/**
	 * Maquetacion: elimina espacios y ultimo caracter.
	 *
	 * @param texto que se quiere modificar
	 * @return texto modificado
	 */
	public static String List(String texto) 
	{
		texto = texto.substring(1, texto.length() - 1);
		return texto.replace(" ",  "");
	}
	
	/**
	 *  Maquetacion. Dada una lista de vehiculos, se queda solo con los ids.
	 *
	 * @param texto que se quiere modificar
	 * @return texto modificado
	 */
	
	public static String ListVehicles(String[] texto) 
	{
		String s = "[";
		for (int i = 0; i < texto.length - 1; i++)
		{
			s += texto[i] + ", ";
		}
		s += texto[texto.length - 1] + "]";
		return s;
	}
	
	public static String EstadoCarretera(java.util.List<Vehiculo> vehiculos)
	{
		String s = "";
		if (!vehiculos.isEmpty())
		{
			for (int i = 0; i < vehiculos.size() - 1; i++)
			{
				s += "(" + vehiculos.get(i) + "," + vehiculos.get(i).getLocalizacion() + "),";
			}
			s += "(" + vehiculos.get(vehiculos.size() - 1) + "," + vehiculos.get(vehiculos.size() - 1).getLocalizacion() + ")";
		}
		else s = "";
		return s;
	}
	
	
	public static void dialogoError(String mensaje)
	{
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static ImageIcon loadImage(String s) {
		ImageIcon iconLoad = new ImageIcon(s);
		Image imagen1 = iconLoad.getImage();
		Image otraimg1 = imagen1.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(otraimg1);
	}
}