package tp.pr3.control.commands;

import java.util.EmptyStackException;

import tp.pr3.Game;

public class RedoCommand extends NoParamsCommand  
{
	static final String commandInfo = "Redo";
	static final String helpInfo = "redo the last undone command.";
	
	public RedoCommand() 
	{
		super(commandInfo, helpInfo);
	}
	
	@Override
	public boolean execute(Game game) throws EmptyStackException
	{
			game.redo();
			System.out.println("Redoing one move." + '\n');
			return true;
	}
}
