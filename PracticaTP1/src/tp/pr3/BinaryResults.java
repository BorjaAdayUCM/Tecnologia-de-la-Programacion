package tp.pr3;

public class BinaryResults 
{
	private boolean encontrado;
	private int pos;
	
	public BinaryResults(boolean encontrado, int pos) 
	{
		this.encontrado = encontrado;
		this.pos = pos;
	}
	
	public boolean isEncontrado() 
	{
		return encontrado;
	}

	public int getPos() 
	{
		return pos;
	}
}
