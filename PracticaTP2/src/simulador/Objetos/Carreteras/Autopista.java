package simulador.Objetos.Carreteras;

import simulador.Ini.IniSection;
import simulador.Objetos.Cruces.CruceGenerico;

public class Autopista extends Carretera 
{
	private int numCarriles;

	/**
	 * Instancia una autopista.
	 *
	 * @param id de la autopista
	 * @param longuitud de la autopista
	 * @param maxSpeed  de la autopista
	 * @param cruceOrigen de la autopista
	 * @param cruceDestino de la autopista
	 * @param numCarriles de la autopista
	 */
	public Autopista(String id, int longuitud, int maxSpeed, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino, int numCarriles) 
	{
		super(id,longuitud, maxSpeed, cruceOrigen, cruceDestino);
		this.numCarriles = numCarriles;
	}
	
	@Override
	protected int calculaVelocidadBase() 
	{
		return Math.min(this.velocidadMax, (this.velocidadMax * this.numCarriles /(Math.max(this.vehiculos.size(), 1)) + 1));
	}

	@Override
	protected int calculaFactorReduccion(int obstaculos) 
	{
		return obstaculos < this.numCarriles ? 1 : 2;
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		super.completaDetallesSeccion(is);
		is.setValue("type", "lanes");
	}
}
