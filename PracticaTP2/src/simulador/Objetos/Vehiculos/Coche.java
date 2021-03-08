package simulador.Objetos.Vehiculos;

import java.util.List;
import java.util.Random;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Ini.IniSection;
import simulador.Objetos.Cruces.CruceGenerico;

public class Coche extends Vehiculo 
{
	private int kmUltimaAveria;
	private int resistenciaKm;
	private int duracionMaximaAveria;
	private double probabilidadDeAveria;
	private Random numAleatorio;

	/**
	 * Instancia un coche.
	 *
	 * @param id del coche.
	 * @param velocidadMaxima del coche.
	 * @param resistencia a una averia del coche.
	 * @param probabilidad de averia del coche.
	 * @param semilla que genera el comportamiento aleatoria del coche.
	 * @param duracionMaximaInfraccion , tiempo maximo de averia
	 * @param itinerario  del coche.
	 * @throws ErrorDeEvento
	 */
	public Coche(String id, int velocidadMaxima, int resistencia, double probabilidad, long semilla, int duracionMaximaInfraccion, List<CruceGenerico<?>> itinerario) throws ErrorDeSimulacion 
	{
		super(id, velocidadMaxima, itinerario);
		this.kmUltimaAveria = 0;
		this.resistenciaKm = resistencia;
		this.duracionMaximaAveria = duracionMaximaInfraccion;
		this.probabilidadDeAveria = probabilidad;
		this.numAleatorio = new Random(semilla);
	}
	
	@Override
	public void avanza()
	{
		if (this.getTiempoDeInfraccion() > 0) this.kmUltimaAveria = this.kilometraje;
		else if (this.kilometraje - this.kmUltimaAveria > this.resistenciaKm && this.numAleatorio.nextDouble() < this.probabilidadDeAveria)
			this.setTiempoAveria(this.numAleatorio.nextInt(this.duracionMaximaAveria) + 1);
		super.avanza();
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		super.completaDetallesSeccion(is);
		is.setValue("type", "car");
	}
}