package simulador.Controlador;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeEvento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Ini.Ini;
import simulador.Ini.IniSection;
import simulador.Modelo.SimuladorTrafico;
import simulador.Swing.ObservadorSimuladorTrafico;
import simulador.Utilidades.ParserEventos;

public class Controlador 
{	
	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasoSimulacion;

	/**
	 * Instancia un nuevo controlador.
	 *
	 * @param simulador
	 * @param tiempoLimite limite de ejecucion
	 * @param ficheroEntrada de tipo ini
	 * @param ficheroSalida de resultados
	 */
	public Controlador(SimuladorTrafico simulador,Integer tiempoLimite, InputStream ficheroEntrada, OutputStream ficheroSalida) 
	{
		this.simulador = simulador;
		this.ficheroSalida = ficheroSalida;
		this.ficheroEntrada = ficheroEntrada;
		this.pasoSimulacion = tiempoLimite;
	}

	/**
	 * Ejecuta la carga de eventos y lanza la simulacion.
	 * @throws ErrorDeSimulacion 
	 * @throws ErrorDeEvento 
	 * @throws IOException 
	 */
	public void ejecuta() throws IOException, ErrorDeEvento, ErrorDeSimulacion
	{
		this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(this.pasoSimulacion, this.ficheroSalida);
	}
	
	public void ejecuta(int pasos) throws ErrorDeSimulacion, IOException
	{
		this.simulador.ejecuta(pasos, this.ficheroSalida);
	}
	
	public void reinicia()
	{
		this.simulador.reinicia();
	}
	
	/**
	 * Carga eventos del archivo de entrada.
	 *
	 * @param inStream archivo de entrada
	 * @throws ErrorDeEvento evento desconocido
	 * @throws IOException	
	 * @throws ErrorDeSimulacion 
	 */
	public void cargaEventos(InputStream inStream) throws IOException, ErrorDeEvento, ErrorDeSimulacion
	{
			Ini ini = new Ini(inStream);
			for (IniSection sec : ini.getSections())
			{
				Evento e = ParserEventos.parseaEvento(sec);
				if (e != null) this.simulador.insertaEvento(e);
				else throw new ErrorDeEvento("Evento desconocido: " + sec.getTag() + ".");
			}
	}
	
	public void addObserver(ObservadorSimuladorTrafico o)
	{
		this.simulador.addObservador(o);
	}

	public void removeObserver(ObservadorSimuladorTrafico o) 
	{
		this.simulador.removeObservador(o);
	}

	public void setFichero(FileInputStream fileInputStream) {
		this.ficheroEntrada = fileInputStream;
	}

	public void setFicheroSalida(OutputStream ficheroSalida) {
		this.ficheroSalida = ficheroSalida;
	}
	
	
}
