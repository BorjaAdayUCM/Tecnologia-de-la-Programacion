package tp.pr3.control.commands;

import tp.pr3.Game;
import tp.pr3.util.CommandParser;

public class HelpCommand extends NoParamsCommand 
{

	static final String commandInfo = "Help";
	static final String helpInfo = "print this help message.";
	
	public HelpCommand() 
	{
		super(commandInfo, helpInfo);
	}

	@Override
	public boolean execute(Game game) 
	{
		String help = CommandParser.commandHelp();
		System.out.print(help);
		return false;
	}
}
