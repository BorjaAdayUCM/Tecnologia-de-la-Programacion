package simulador.Objetos.Cruces;

import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Carreteras.CarreteraEntrante;

public class Cruce extends CruceGenerico<CarreteraEntrante>
{
	
	/**
	 * Instancia un cruce.
	 *
	 * @param id del cruce
	 */
	public Cruce(String id) 
	{
		super(id);
	}
	
	protected void actualizaSemaforos()
	{
		if (this.indiceSemaforoVerde == -1) this.carreterasEntrantes.get(++indiceSemaforoVerde).ponSemaforo(true);
		else
		{
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(false);
			this.indiceSemaforoVerde = (this.indiceSemaforoVerde + 1) % this.carreterasEntrantes.size();
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
		}
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) 
	{
		return new CarreteraEntrante(carretera);
	}
}
