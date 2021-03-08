package simulador.Objetos.Carreteras;

import simulador.Execpciones.ErrorDeSimulacion;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante 
{
	private int intevaloDeTiempo;
	private int unidadesDeTiempoUsadas;
	private boolean usoCompleto;
	private boolean usadaPorUnVehiculo;
	
	/**
	 * Instancia una carretera entrante con intervalo.
	 *
	 * @param carretera a partir de la que se crea.
	 * @param intervaloDeTiempo con el que funciona.
	 */
	public CarreteraEntranteConIntervalo(Carretera carretera, int intervaloDeTiempo) 
	{
		super(carretera);
		this.intevaloDeTiempo = intervaloDeTiempo;
		this.unidadesDeTiempoUsadas = 0;
		this.usadaPorUnVehiculo = false;
		this.usoCompleto = true;
	}
	
	public int getIntevaloDeTiempo() 
	{
		return intevaloDeTiempo;
	}
	
	public boolean isUsoCompleto() 
	{
		return usoCompleto;
	}

	public boolean isUsadaPorUnVehiculo() 
	{
		return usadaPorUnVehiculo;
	}
	
	public void setIntevaloDeTiempo(int intevaloDeTiempo) 
	{
		this.intevaloDeTiempo = intevaloDeTiempo;
	}
	
	public void setUnidades(int unidades)
	{
		this.unidadesDeTiempoUsadas = 0;
	}
	
	/**
	 * Reinicia el estado de la carretera.
	 */
	public void reset()
	{
		this.unidadesDeTiempoUsadas = 0;
		this.usadaPorUnVehiculo = false;
		this.usoCompleto = true;
	}

	@Override
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion 
	{
		this.unidadesDeTiempoUsadas++;
		if (this.getColaVehiculos().isEmpty()) this.usoCompleto = false;
		else
		{
			this.getColaVehiculos().get(0).moverASiguienteCarretera();
			this.getColaVehiculos().remove(0);
			this.usadaPorUnVehiculo = true;
		}
	}
	
	/**
	 * Devuelve si el tiempo se ha consumido por completo.
	 *
	 * @return Si el tiempo usado es mayor o igual al intervalo de tiempo.
	 */
	public boolean tiempoConsumido()
	{
		return this.unidadesDeTiempoUsadas >= this.intevaloDeTiempo;
	}
	
	@Override
	public String toString() 
	{
		return "(" + this.carretera + "," + (this.semaforo ? "green:" + (this.intevaloDeTiempo - this.unidadesDeTiempoUsadas) : "red") + "," + this.colaVehiculos + ")";
	}
}
