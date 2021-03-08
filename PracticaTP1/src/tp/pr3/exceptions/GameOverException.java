package tp.pr3.exceptions;

@SuppressWarnings("serial")
public class GameOverException extends Exception {
	
	private boolean lose;
	
	public GameOverException(boolean lose)
	{
		super();
		this.lose = lose;
	}
	
	public boolean getLose()
	{
		return this.lose;
	}
}
