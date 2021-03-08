package simulador.Utilidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;


@SuppressWarnings("serial")
public final class SortedArrayList<E> extends ArrayList<E>
{
	private Comparator<E> cmp;
	
	/**
	 * Instancia un lista ordenada segun un comparador
	 *
	 * @param cmp comparador
	 */
	public SortedArrayList(Comparator<E> cmp)
	{
		this.cmp = cmp;
	}

	@Override
	public boolean add(E e)
	{
		if (!this.contains(e))
		{
			super.add(e);
			this.sort(cmp);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		Iterator<? extends E> iti = c.iterator();
		while (iti.hasNext())
		{
			this.add(iti.next());
		}
		return false;
	}

	@Override
	public void add(int index, E element)
	{
		throw new UnsupportedOperationException("No se puede realizar esta accion sobre una lista orenada.");
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		throw new UnsupportedOperationException("No se puede realizar esta accion sobre una lista orenada.");
	}
	
	@Override
	public E set(int index, E element)
	{
		throw new UnsupportedOperationException("No se puede realizar esta accion sobre una lista orenada.");
	}
}
