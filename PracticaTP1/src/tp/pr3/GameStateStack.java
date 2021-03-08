package tp.pr3;


public class GameStateStack 
{
	public static final int CAPACITY = 2;
	private GameState[] buffer = new GameState[CAPACITY];
	private int puntero = 0;
	private int tam = 0;
	
	public static int getCapacity() 
	{
		return CAPACITY;
	}

	public int getTam() 
	{
		return tam;
	}

	public void setTam(int tam) 
	{
		this.tam = tam;
	}
	
	public void setPuntero(int puntero) {
		this.puntero = puntero;
	}

	public GameState pop() 
	{	
		try
		{
		this.puntero--;
		return this.buffer[this.puntero];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.puntero = CAPACITY-1;
			return this.buffer[this.puntero];
		}
	}
	
	public void push(GameState state) 
	{
		try
		{
		this.buffer[this.puntero] = state;
		this.puntero++;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			this.puntero = 0;
			this.buffer[this.puntero]= state;
			this.puntero++;
		}
	}

	public void reset()
	{
		this.puntero = 0;
		this.tam = 0;
	}
}