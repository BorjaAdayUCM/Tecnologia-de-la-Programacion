package simulador.Objetos.Vehiculos;

import java.util.List;

import simulador.Execpciones.ErrorDeEvento;
import simulador.Execpciones.ErrorDeSimulacion;
import simulador.Ini.IniSection;
import simulador.Objetos.ObjetoSimulacion;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Cruces.CruceGenerico;

public class Vehiculo extends ObjetoSimulacion
{
	protected Carretera carretera;
	protected int velocidadMax;
	protected int VelocidadAct;
	protected int kilometraje;
	protected int localizacion;
	protected int tiempoAveria;
	protected boolean haLlegado;
	protected boolean estEnCruce;
	protected List<CruceGenerico<?>> itinerario;
	
	/**
	 * Instancia un vehiculo.
	 *
	 * @param id del vehiculo
	 * @param velocidadMax del vehiculo
	 * @param itinerario del vehiculo
	 * @throws ErrorDeEvento the error de evento
	 */
	public Vehiculo(String id, int velocidadMax, List<CruceGenerico<?>> itinerario) throws ErrorDeSimulacion
	{
		super(id);
		this.carretera = null;
		this.velocidadMax = velocidadMax;
		this.VelocidadAct = 0;
		this.kilometraje = 0;
		this.localizacion = 0;
		this.tiempoAveria = 0;
		this.haLlegado = false;
		this.estEnCruce = false;
		this.itinerario = itinerario;
	}
	
	public int getLocalizacion()
	{
		return this.localizacion;
	}
	
	public int getTiempoDeInfraccion()
	{
		return this.tiempoAveria;
	}

	public boolean isEstEnCruce() 
	{
		return estEnCruce;
	}
	
	public void setVelocidadActual(int velocidad)
	{
		if (velocidad < 0) this.VelocidadAct = 0;
		else if (velocidad > this.velocidadMax) this.VelocidadAct = this.velocidadMax;
		else this.VelocidadAct = velocidad;
	}
	
	public void setTiempoAveria(int duracionAveria)
	{
		if (carretera != null)
		{
			this.tiempoAveria = duracionAveria;
			if (this.tiempoAveria > 0) this.VelocidadAct = 0;
		}
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is)
	{
		is.setValue("speed", this.VelocidadAct);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : "(" + this.carretera + "," + this.getLocalizacion() + ")");
	}
	
	@Override
	public void avanza() 
	{
		if (this.tiempoAveria > 0) this.tiempoAveria--;
		else if(!this.estEnCruce)
		{
			this.localizacion += this.VelocidadAct;
			this.kilometraje += this.VelocidadAct;
			if (this.localizacion >= this.carretera.getLongitud())
			{
				this.kilometraje -= (this.localizacion - this.carretera.getLongitud());
				this.localizacion = this.carretera.getLongitud();
				this.carretera.getCruceDestino().entraVehiculoAlCruce(this.carretera.getId(), this);
				this.estEnCruce = true;
				this.VelocidadAct = 0;
			}
		}
	}
	
	/**
	 * Mueve el coche sobre el que se ejecuta a la siguiete carretera que marca su itinerario.
	 *
	 * @throws ErrorDeSimulacion no se encuentra la carretera siguiente
	 */
	public void moverASiguienteCarretera() throws ErrorDeSimulacion
	{
		if (this.carretera != null) this.carretera.saleVehiculo(this);
		if (this.itinerario.size() == 1)
		{
			this.carretera = null;
			this.haLlegado = true;
			this.localizacion = 0;
			this.VelocidadAct = 0;
			this.itinerario.remove(0);
		}
		else
		{
			Carretera carreteraSiguiente = this.itinerario.get(0).carreteraHaciaCruce(this.itinerario.get(1));
			if (carreteraSiguiente == null) throw new ErrorDeSimulacion("No se ha encontrado la carretera correspondiente a los cruces: " + this.itinerario.get(0) + " y " + this.itinerario.get(1));
			else
			{
				this.carretera = carreteraSiguiente;
				this.localizacion = 0;
				carreteraSiguiente.entraVehiculo(this);
				this.itinerario.remove(0);
				this.estEnCruce = false;
			}
		}
	}
	
	@Override
	protected String getNombreSeccion() 
	{
		return "vehicle_report";
	}
	
	public boolean isHaLlegado() {
		return haLlegado;
	}

	public Carretera getCarretera() {
		return carretera;
	}/* Añadida para las tablas*/

	public int getVelocidadAct() {
		return VelocidadAct;
	}/* Añadida para las tablas*/

	public int getKilometraje() {
		return kilometraje;
	}/* Añadida para las tablas*/

	public List<CruceGenerico<?>> getItinerario() {
		return itinerario;
	}/* Añadida para las tablas*/
}
