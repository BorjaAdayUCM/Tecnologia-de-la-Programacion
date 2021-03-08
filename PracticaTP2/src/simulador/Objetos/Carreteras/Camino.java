package simulador.Objetos.Carreteras;

import simulador.Ini.IniSection;
import simulador.Objetos.Cruces.CruceGenerico;

public class Camino extends Carretera 
{

	/**
	 * Instancia un camino.
	 *
	 * @param id del camino
	 * @param longuitud del camino
	 * @param maxSpeed  del camino
	 * @param cruceOrigen del camino
	 * @param cruceDestino del camino
	 */
	public Camino(String id, int longuitud, int maxSpeed, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) 
	{
		super(id, longuitud, maxSpeed, cruceOrigen, cruceDestino);
	}

	@Override
	protected int calculaVelocidadBase() 
	{
		return this.velocidadMax;
	}

	@Override
	protected int calculaFactorReduccion(int obstaculos) 
	{
		return obstaculos + 1;
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		super.completaDetallesSeccion(is);
		is.setValue("type", "dirt");
	}
}
