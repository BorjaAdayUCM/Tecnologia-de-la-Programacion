package simulador.Objetos.Carreteras;

import java.util.ArrayList;
import java.util.List;

import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Objetos.Vehiculos.Vehiculo;

public class CarreteraEntrante 
{
	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	protected boolean semaforo;
	
	/**
	 * Instancia una carretera entrante.
	 *
	 * @param carretera a partir de la que se crea
	 */
	public CarreteraEntrante(Carretera carretera)
	{
		this.carretera = carretera;
		this.colaVehiculos = new ArrayList<Vehiculo>();
		this.semaforo = false;
	}

	public List<Vehiculo> getColaVehiculos() 
	{
		return colaVehiculos;
	}
	
	public void ponSemaforo(boolean color)
	{
		this.semaforo = color;
	}
	
	/**
	 * Avanza primer vehiculo (lo mueve a la siguienteCarretera).
	 *
	 * @throws ErrorDeSimulacion Durante la simulación.
	 */
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion
	{
		if (!this.colaVehiculos.isEmpty())
		{
			this.colaVehiculos.get(0).moverASiguienteCarretera();
			this.colaVehiculos.remove(0);
		}
	}

	@Override
	public String toString() 
	{
		return "(" + this.carretera + "," + (this.semaforo ? "green" : "red") + "," + this.colaVehiculos + ")";
	}

	public Carretera getCarretera() {
		return this.carretera;
	}

	public boolean tieneSemaforoVerde() {
		return this.semaforo;
	}
}