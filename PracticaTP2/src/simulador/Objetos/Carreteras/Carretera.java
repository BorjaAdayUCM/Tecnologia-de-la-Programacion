package simulador.Objetos.Carreteras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import simulador.Ini.IniSection;
import simulador.Objetos.ObjetoSimulacion;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Vehiculo;
import simulador.Utilidades.Utils;
import simulador.Utilidades.SortedArrayList;

public class Carretera extends ObjetoSimulacion
{
	protected int longitud;
	protected int velocidadMax;
	protected CruceGenerico<?> cruceOrigen;
	protected CruceGenerico<?> cruceDestino;
	protected List<Vehiculo> vehiculos;
	protected Comparator<Vehiculo> comparadorVehiculo;
	
	/**
	 * Instancia una carretera.
	 *
	 * @param id de la carretera
	 * @param longuitud de la carretera
	 * @param maxSpeed de la carretera
	 * @param cruceOrigen de la carretera
	 * @param cruceDestino2 de la carretera
	 */
	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino)
	{
		super(id);
		this.longitud = length;
		this.velocidadMax = maxSpeed;
		this.cruceOrigen = cruceOrigen;
		this.cruceDestino = cruceDestino;
		this.comparadorVehiculo = new Comparator<Vehiculo>()
		{
			@Override
			public int compare(Vehiculo arg0, Vehiculo arg1) 
			{
				if (arg0.getLocalizacion() < arg1.getLocalizacion()) return 1;
				else if (arg0.getLocalizacion() > arg1.getLocalizacion()) return -1;
				else return 0;
			}
		};
		this.vehiculos = new SortedArrayList<Vehiculo>(this.comparadorVehiculo);
	}
	
	public int getLongitud() 
	{
		return longitud;
	}

	public CruceGenerico<?> getCruceDestino() 
	{
		return cruceDestino;
	}

	@Override
	public void avanza() 
	{
		int velocidadBase = this.calculaVelocidadBase();
		int obstaculos = 0;
		Iterator<Vehiculo> iti = this.vehiculos.iterator();
		while(iti.hasNext())
		{
			Vehiculo v = iti.next();
			if (v.getTiempoDeInfraccion() > 0) obstaculos++;
			else if (!v.isEstEnCruce())
			{
				v.setVelocidadActual(velocidadBase / this.calculaFactorReduccion(obstaculos));
			}
			v.avanza();
		}
		this.vehiculos.sort(this.comparadorVehiculo);
	}
	
	/**
	 * Añade un vehiculo a la carretera.
	 *
	 * @param vehiculo que añade
	 */
	public void entraVehiculo(Vehiculo vehiculo)
	{
		if (!this.vehiculos.contains(vehiculo))
		{
			this.vehiculos.add(vehiculo);
		}
	}
	
	/**
	 * Elimina un vehiculo de la carretera.
	 *
	 * @param vehiculo que se desea eliminar
	 */
	public void saleVehiculo(Vehiculo vehiculo)
	{
		this.vehiculos.remove(vehiculo);
	}
	
	/**
	 * Inserta el vehiculo dado en el cruce
	 *
	 * @param vehiculo que entra
	 */
	public void entraVehiculoAlCruce(Vehiculo vehiculo)
	{
		this.cruceDestino.entraVehiculoAlCruce(this.id, vehiculo);
	}
	
	/**
	 * Calcula velocidad base.
	 *
	 * @return La velocidad base
	 */
	protected int calculaVelocidadBase()
	{
		return Math.min(this.velocidadMax, (this.velocidadMax/(Math.max(this.vehiculos.size(), 1)) +1));
	}
	
	/**
	 * Calcula factor reduccion.
	 *
	 * @param obstaculos de la carretera
	 * @return Factor reducción
	 */
	protected int calculaFactorReduccion(int obstaculos)
	{
		return obstaculos != 0 ? 2 : 1;
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		is.setValue("state", Utils.EstadoCarretera(this.vehiculos));
	}

	@Override
	protected String getNombreSeccion() 
	{
		return "road_report";
	}

	public List<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	public CruceGenerico<?> getCruceOrigen() {
		return this.cruceOrigen;
	}

	public int getVelocidadMax() 
	{
		return velocidadMax;
	}
}
