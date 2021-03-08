package tp.pr3.exceptions;

@SuppressWarnings("serial")
public class MessageException extends Exception{
	
	private String message;
	
	public MessageException(String m)
	{
		super();
		this.message = m;
	}
	
	public MessageException()
	{
		super();
	}

	public String getMessage() 
	{
		return message;
	}

}
