package simulador.Objetos.Cruces;

import simulador.Ini.IniSection;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Carreteras.CarreteraEntranteConIntervalo;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> 
{
	private int minValorIntervalo;
	private int maxValorIntervalo;
	
	/**
	 * Instancia un cruce circular.
	 *
	 * @param id del cruceCircular
	 * @param minValorIntervalo del cruceCircular
	 * @param maxValorIntervalo del cruceCircular
	 */
	public CruceCircular(String id, int minValorIntervalo, int maxValorIntervalo) 
	{
		super(id);
		this.minValorIntervalo = minValorIntervalo;
		this.maxValorIntervalo = maxValorIntervalo;
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) 
	{
		return new CarreteraEntranteConIntervalo(carretera, this.maxValorIntervalo);
	}

	@Override
	protected void actualizaSemaforos()
	{
		if (this.indiceSemaforoVerde == -1) this.carreterasEntrantes.get(++this.indiceSemaforoVerde).ponSemaforo(true);
		else
		{
			CarreteraEntranteConIntervalo c = this.carreterasEntrantes.get(this.indiceSemaforoVerde);
			if (c.tiempoConsumido())
			{
				c.ponSemaforo(false);
				if (c.isUsoCompleto()) c.setIntevaloDeTiempo(Math.min(c.getIntevaloDeTiempo() + 1, this.maxValorIntervalo));
				else if (!c.isUsadaPorUnVehiculo()) c.setIntevaloDeTiempo(Math.max(c.getIntevaloDeTiempo() - 1, this.minValorIntervalo));
				this.indiceSemaforoVerde = (this.indiceSemaforoVerde + 1) % this.carreterasEntrantes.size();
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
				c.reset();
			}
		}
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		super.completaDetallesSeccion(is);
		is.setValue("type", "rr");
	}
}