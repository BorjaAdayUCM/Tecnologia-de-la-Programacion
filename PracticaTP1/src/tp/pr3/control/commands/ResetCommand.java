package tp.pr3.control.commands;

import tp.pr3.Game;

public class ResetCommand extends NoParamsCommand 
{
	static final String commandInfo = "Reset";
	static final String helpInfo = "start a new game.";
	
	public ResetCommand() 
	{
		super(commandInfo, helpInfo);
	}
	
	@Override
	public boolean execute(Game game) 
	{
		game.reset();
		return true;
	}
}
