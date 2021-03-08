package simulador.Objetos.Cruces;

import java.util.Iterator;

import simulador.Ini.IniSection;
import simulador.Objetos.Carreteras.Carretera;
import simulador.Objetos.Carreteras.CarreteraEntranteConIntervalo;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo>
{

	/**
	 * Instancia un cruce congestionado.
	 *
	 * @param id del cruceCongestionado
	 */
	public CruceCongestionado(String id) {
		super(id);
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) 
	{
		return new CarreteraEntranteConIntervalo(carretera, 0);
	}

	@Override
	protected void actualizaSemaforos()
	{
		
		Iterator<CarreteraEntranteConIntervalo> iti = this.carreterasEntrantes.iterator();
		CarreteraEntranteConIntervalo carretera = new CarreteraEntranteConIntervalo(null,0), carreteraMax =  new CarreteraEntranteConIntervalo(null,0);	
		while (iti.hasNext())
		{
			carretera = iti.next();
			if (carretera.getColaVehiculos().size() > carreteraMax.getColaVehiculos().size() && !carretera.equals(this.carreterasEntrantes.get(this.indiceSemaforoVerde)))
			{
				carreteraMax = carretera;
		
			}
		}
		if (this.indiceSemaforoVerde == -1)
		{
			if(this.carreterasEntrantes.indexOf(carreteraMax) == -1) this.indiceSemaforoVerde = 0;
			else this.indiceSemaforoVerde = this.carreterasEntrantes.indexOf(carreteraMax);
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).setIntevaloDeTiempo(Math.max(carreteraMax.getColaVehiculos().size() / 2, 1));
		}
		else
		{
			CarreteraEntranteConIntervalo c = this.carreterasEntrantes.get(this.indiceSemaforoVerde);
			if (c.tiempoConsumido())
			{
				c.ponSemaforo(false);
				c.reset();
				if(this.carreterasEntrantes.indexOf(carreteraMax) == -1) this.indiceSemaforoVerde = (this.indiceSemaforoVerde + 1) % this.carreterasEntrantes.size();
				else this.indiceSemaforoVerde = this.carreterasEntrantes.indexOf(carreteraMax);
				this.carreterasEntrantes.get(indiceSemaforoVerde).setIntevaloDeTiempo(Math.max(carreteraMax.getColaVehiculos().size() / 2, 1));
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
			}
		}
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) 
	{
		super.completaDetallesSeccion(is);
		is.setValue("type", "mc");
	}
}
