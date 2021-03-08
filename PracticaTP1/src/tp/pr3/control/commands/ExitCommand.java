package tp.pr3.control.commands;

import tp.pr3.Game;

public class ExitCommand extends NoParamsCommand
{
	static final String commandInfo = "Exit";
	static final String helpInfo = "terminate the program.";
	
	public ExitCommand() 
	{
		super(commandInfo, helpInfo);
	}

	@Override
	public boolean execute(Game game) 
	{
		game.setFinished(true);
		return false;
	}
}
