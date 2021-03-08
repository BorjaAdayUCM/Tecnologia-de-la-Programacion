package tp.pr3.control.commands;

import java.util.EmptyStackException;

import tp.pr3.Game;

public class UndoCommand extends NoParamsCommand  
{
	static final String commandInfo = "Undo";
	static final String helpInfo = "undo the last command.";
	
	public UndoCommand() 
	{
		super(commandInfo, helpInfo);
	}
	
	@Override
	public boolean execute(Game game) throws EmptyStackException
	{
			game.undo();
			System.out.println("Undoing one move." + '\n');
			return true;
	}
}