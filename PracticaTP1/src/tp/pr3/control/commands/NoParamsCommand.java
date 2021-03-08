package tp.pr3.control.commands;

import java.util.Scanner;

public abstract class NoParamsCommand extends Command 
{
	public NoParamsCommand(String commandInfo, String helpInfo) 
	{
		super(commandInfo, helpInfo);
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) 
	{
		if (commandWords.length == 1)
		{
			if (this.commandName.equalsIgnoreCase(commandWords[0]))
			{
				return this;
			}
		}
		return null;
	}
}
