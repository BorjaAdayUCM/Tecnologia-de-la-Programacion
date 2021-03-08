package simulador.Objetos.Cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Ini.IniSection;
import simulador.Objetos.ObjetoSimulacion;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Carreteras.CarreteraEntrante;
import simulador.Objetos.Vehiculos.Vehiculo;
import simulador.Utilidades.Utils;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion
{
	protected int indiceSemaforoVerde;
	protected List<T> carreterasEntrantes;
	protected Map<String,T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;
	
	/**
	 * Instancia un cruce generico.
	 *
	 * @param id del cruce
	 */
	public CruceGenerico(String id)
	{
		super(id);
		this.indiceSemaforoVerde = -1;
		this.carreterasEntrantes = new ArrayList<T>();
		this.mapaCarreterasEntrantes = new HashMap<String,T>();
		this.carreterasSalientes = new HashMap<CruceGenerico<?>, Carretera>();
	}

	/**
	 * Devuelve la carretera que va de un cruce a otro.
	 *
	 * @param cruce de destino
	 * @return La carretera que une los dos cruces
	 */
	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce)
	{
		return this.carreterasSalientes.get(cruce);
	}
	
	/**
	 * Agrega una carreteraEntrante al cruce.
	 *
	 * @param idCarretera de la carreteraEntrante
	 * @param carretera que va a ser entrante del cruce
	 */
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) 
	{
		T carreteraIn = creaCarreteraEntrante(carretera);
		this.carreterasEntrantes.add(carreteraIn);
		this.mapaCarreterasEntrantes.put(idCarretera, carreteraIn);
	}
	
	/**
	 * Crea una carreteraEntrante.
	 *
	 * @param carretera que va a ser entrante
	 * @return La carretera dada convertida en carreteraEntrante
	 */
	abstract protected T creaCarreteraEntrante(Carretera carretera);
	 
	/**
	 * Añade la carretera dada como carreteraSaliente al cruce.
	 *
	 * @param destino cruce de destino
	 * @param carretera que va a ser saliente
	 */
	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carretera)
	{
		this.carreterasSalientes.put(destino, carretera);
	}
	 
	/**
	 * Añade el vehiculo dado a la cola del cruceDestino de la carretera dada.
	 *
	 * @param idCarretera con el cruceDestino al que añadir el vehiculo
	 * @param vehiculo que se quiere añadir a la cola
	 */
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo)
	{
		this.mapaCarreterasEntrantes.get(idCarretera).getColaVehiculos().add(vehiculo);
	}
	
	@Override
	public void avanza() throws ErrorDeSimulacion
	{
		if(!this.carreterasEntrantes.isEmpty())
		{
			if (this.indiceSemaforoVerde == -1) this.actualizaSemaforos();
			else
			{
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).avanzaPrimerVehiculo();
				this.actualizaSemaforos();
			}
		}
	}
	
	/**
	 * Actualiza semaforos con las normas del tipo de cada cruce.
	 */
	abstract protected void actualizaSemaforos();
	
	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		is.setValue("queues", Utils.List(this.carreterasEntrantes.toString()));
	}
	
	@Override
	protected String getNombreSeccion() 
	{
		return "junction_report";
	}

	public List<T> getCarreteras() {
		return this.carreterasEntrantes;
	}
	
	public List<T> getCarreterasSemaforo(boolean semaforo)
	{
		List<T> carreteras = new ArrayList<T>();
		for(int i = 0; i < this.carreterasEntrantes.size(); i++)
		{
			if (semaforo && i == this.indiceSemaforoVerde) carreteras.add(this.carreterasEntrantes.get(i));
			else if (!semaforo && i != this.indiceSemaforoVerde) carreteras.add(this.carreterasEntrantes.get(i));
		}
		return carreteras;
	}
}
