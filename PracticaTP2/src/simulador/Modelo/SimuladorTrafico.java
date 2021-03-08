package simulador.Modelo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import simulador.Eventos.Evento;
import simulador.Execpciones.ErrorDeEvento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.MapaCarreteras;
import simulador.Swing.Observador;
import simulador.Swing.ObservadorSimuladorTrafico;
import simulador.Utilidades.SortedArrayList;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico>
{
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private List<ObservadorSimuladorTrafico> observadores;

	/**
	 * Instancia un nuevo simulador  de trafico.
	 */
	public SimuladorTrafico()
	{
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>()
		{
			@Override
			public int compare(Evento arg0, Evento arg1) 
			{
				return arg0.getTiempo().compareTo(arg1.getTiempo());
			}
			
		};
		this.eventos = new SortedArrayList<Evento>(cmp);
		this.observadores = new ArrayList<>();
	}

	/**
	 * Ejecuta el simulador.
	 *
	 * @param pasosSimulacion unidades de tiempo transcurridas
	 * @param ficheroSalida donde se guardara el resultado de la simulacion
	 * @throws ErrorDeSimulacion Error durante la simulacion.
	 * @throws ErrorDeEvento Error en la creacion de un evento.
	 * @throws IOException Error en el archivo de salida.
	 */
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion, IOException
	{
		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1, i = 0;
		OutputStreamWriter w = new OutputStreamWriter(ficheroSalida);
		BufferedWriter bw = new BufferedWriter(w);
		String report = "";
		while (this.contadorTiempo <= limiteTiempo)
		{
			try
			{
				while(i < this.eventos.size() && this.eventos.get(i).getTiempo() == this.contadorTiempo)
				{
					this.eventos.get(i).ejecuta(this.mapa);
					this.eventos.remove(i);
				}
				this.mapa.actualizar();
				report += mapa.generateReport(this.contadorTiempo);
				this.contadorTiempo++;
				this.notificaAvanza();
			}
			catch (ErrorDeSimulacion e)
			{
				this.notificaError(e);
				throw e;
			}
		}
		bw.write(report);
		bw.flush();
	}
	
	/**
	 * Inserta evento.
	 *
	 * @param evento que se desea insertar
	 * @throws ErrorDeSimulacion
	 */
	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		if (e != null) 
		{
			if (e.getTiempo() < this.contadorTiempo) 
			{
				ErrorDeSimulacion err = new ErrorDeSimulacion("El tiempo de evento es mayor al tiempo de ejecucion");
				this.notificaError(err);
				throw err;
			} 
			else 
			{
				this.eventos.add(e);
				this.notificaNuevoEvento();
			}
		} 
		else 
		{
			ErrorDeSimulacion err = new ErrorDeSimulacion("Se ha intentado insertar un evento nulo.");
			this.notificaError(err);
			throw err;
		}
	}

	public void reinicia()
	{
		this.contadorTiempo = 0;
		this.eventos.clear();
		this.mapa.clear();
		this.notificaReinicia();
	}
	
	public String generaInforme()
	{
		return this.mapa.generateReport(this.contadorTiempo);
	}
	
	@Override
	public void addObservador(ObservadorSimuladorTrafico o)
	{
		if (o != null && !this.observadores.contains(o)) this.observadores.add(o);
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) 
	{
		if (o != null && this.observadores.contains(o)) this.observadores.remove(o);
	}
	
	private void notificaNuevoEvento() 
	{
		for (ObservadorSimuladorTrafico o : this.observadores) o.addEvento(this.contadorTiempo, this.mapa, this.eventos); 
	}
	
	private void notificaError(ErrorDeSimulacion e) 
	{
		for (ObservadorSimuladorTrafico o : this.observadores) o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, e); 
	}

	private void notificaAvanza()
	{
		for (ObservadorSimuladorTrafico o : this.observadores) o.avanza(this.contadorTiempo, this.mapa, this.eventos);
	}
	
	private void notificaReinicia()
	{
		for (ObservadorSimuladorTrafico o : this.observadores) o.reinicia(this.contadorTiempo, this.mapa, this.eventos);
	}
}
