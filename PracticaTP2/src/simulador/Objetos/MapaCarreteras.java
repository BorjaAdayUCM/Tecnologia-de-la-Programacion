package simulador.Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import simulador.Execpciones.ErrorDeEvento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Cruces.CruceGenerico;
import simulador.Objetos.Vehiculos.Vehiculo;

public class MapaCarreteras 
{
	private List<Carretera> carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;
	
	/**
	 * Instancia un  mapa de carreteras.
	 */
	public MapaCarreteras()
	{
		this.carreteras = new ArrayList<>();
		this.cruces = new ArrayList<>();
		this.vehiculos = new ArrayList<>();
		this.mapaDeCarreteras = new HashMap<>();
		this.mapaDeCruces = new HashMap<>();
		this.mapaDeVehiculos = new HashMap<>();
	}
	
	public List<CruceGenerico<?>> getCruces() {
		return this.cruces;
	}

	public List<Carretera> getCarreteras() {
		return this.carreteras;
	}
	
	public List<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	public void setCarreteras(List<Carretera> carreteras) {
		this.carreteras = carreteras;
	}

	public void setCruces(List<CruceGenerico<?>> cruces) {
		this.cruces = cruces;
	}

	public void setVehiculos(List<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}
	
	/**
	 * Añade un cruce al mapa de carreteras.
	 *
	 * @param idCruce del cruce que se desea insertar
	 * @param cruce que se desea insertar
	 * @throws ErrorDeEvento el cruce ya existia en el mapa.
	 */
	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws ErrorDeSimulacion
	{
		if (!mapaDeCruces.containsKey(idCruce))
		{
			cruces.add(cruce);
			mapaDeCruces.put(idCruce, cruce);
		}
		else throw new ErrorDeSimulacion("El cruce que se quiere añadir ya existe.");
	} 
	
	/**
	 * Añade un vehiculo al mapa de vehiculo.
	 *
	 * @param idVehiculo del vehiculo que se desea añadir
	 * @param vehiculo que se desea insertar
	 * @throws ErrorDeEvento El coche ya existe en el mapa.
	 * @throws ErrorDeSimulacion Error durante la simulación.
	 */
	public void addVehiculo(String idVehiculo, Vehiculo vehiculo) throws ErrorDeSimulacion
	{
		if (!mapaDeVehiculos.containsKey(idVehiculo))
		{
			vehiculos.add(vehiculo);
			mapaDeVehiculos.put(idVehiculo, vehiculo);
			vehiculo.moverASiguienteCarretera();
		}
		else throw new ErrorDeSimulacion("El coche que se quiere añadir ya existe.");
	}
	
	/**
	 * Añade una carretera al mapa de carreteras.
	 *
	 * @param idCarretera de la carretera que se desea añadir
	 * @param cruceOrigen de la carretera
	 * @param cruceDestino de la carretera
	 * @param carretera que se desea añadir
	 * @throws ErrorDeSimulacion ya existia la carretera
	 */
	public void addCarretera(String idCarretera, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino, Carretera carretera) throws ErrorDeSimulacion
	{
		if (!this.mapaDeCarreteras.containsKey(idCarretera))
		{
			this.carreteras.add(carretera);
			this.mapaDeCarreteras.put(idCarretera, carretera);
			cruceOrigen.addCarreteraSalienteAlCruce(cruceDestino,carretera);
			cruceDestino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}
		else throw new ErrorDeSimulacion("La carretera que se quiere añadir ya existe.");
	}
	
	/**
	 * Genera el reporte de un tiempo dado.
	 *
	 * @param tiempo en el que se genera el reporte 
	 * @return El reporte
	 */
	public String generateReport(int tiempo)
	{
		String report = "";
		Iterator<CruceGenerico<?>> itiCruce = this.cruces.iterator();
		while (itiCruce.hasNext()) report += itiCruce.next().generaInforme(tiempo) + '\n';
		Iterator<Carretera> itiCarreteras = this.carreteras.iterator();
		while (itiCarreteras.hasNext()) report += itiCarreteras.next().generaInforme(tiempo) + '\n';
		Iterator<Vehiculo> itiVehiculos = this.vehiculos.iterator();
		while (itiVehiculos.hasNext()) report += itiVehiculos.next().generaInforme(tiempo) + '\n';
		return report;
	}
	
	/**
	 * Actualiza el estado del mapa de la carretera.
	 *
	 * @throws ErrorDeSimulacion 
	 */
	public void actualizar() throws ErrorDeSimulacion 
	{
		Iterator<Carretera> itiCarreteras = this.carreteras.iterator();
		while (itiCarreteras.hasNext()) itiCarreteras.next().avanza();
		Iterator<CruceGenerico<?>> itiCruce = this.cruces.iterator();
		while (itiCruce.hasNext()) itiCruce.next().avanza();
	}

	public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion
	{
		if (this.mapaDeCruces.containsKey(id)) return this.mapaDeCruces.get(id);
		else throw new ErrorDeSimulacion("No se ha encontrado el cruce " + id + " en el mapa de cruces.");
	}
	
	public Carretera getCarretera(String id) throws ErrorDeSimulacion
	{
		if (this.mapaDeCarreteras.containsKey(id))	return this.mapaDeCarreteras.get(id);
		else throw new  ErrorDeSimulacion("No se ha encontrado la carretera " + id + " en el mapa de carreteras.");
	}
	
	public Vehiculo getVehiculo(String id) throws ErrorDeSimulacion
	{
		if (this.mapaDeVehiculos.containsKey(id)) return this.mapaDeVehiculos.get(id);
		else throw new  ErrorDeSimulacion("No se ha encontrado el vehículo " + id + " en el mapa de vehículos.");
	}
	
	public void clear()
	{
		this.carreteras.clear();
		this.cruces.clear();
		this.vehiculos.clear();
		this.mapaDeCarreteras.clear();
		this.mapaDeCruces.clear();
		this.mapaDeVehiculos.clear();
	}
}