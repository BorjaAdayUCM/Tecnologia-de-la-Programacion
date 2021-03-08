package tp.pr3.util;

import java.util.Scanner;

import tp.pr3.control.commands.Command;
import tp.pr3.control.commands.ExitCommand;
import tp.pr3.control.commands.HelpCommand;
import tp.pr3.control.commands.LoadCommand;
import tp.pr3.control.commands.MoveCommand;
import tp.pr3.control.commands.PlayCommand;
import tp.pr3.control.commands.RedoCommand;
import tp.pr3.control.commands.ResetCommand;
import tp.pr3.control.commands.SaveCommand;
import tp.pr3.control.commands.UndoCommand;
import tp.pr3.exceptions.MessageException;

public class CommandParser 
{
	private static Command[ ] availableCommands = {new HelpCommand(), new MoveCommand(), new UndoCommand(), new RedoCommand(), new ResetCommand(), new PlayCommand(), new SaveCommand(), new LoadCommand(), new ExitCommand()};
	
	public static Command parseCommand(String[] commandWords, Scanner in) throws MessageException
	{
		Command command = null;
		for (Command c : availableCommands)
		{
			command = c.parse(commandWords, in);
			if (command != null)
			{
				return command;
			}
		}
		return command;
	}
	
	public static String commandHelp()
	{
		String help = "The available commands are: " + '\n';
		for (Command c : availableCommands)
		{
			help += c.helpText() + '\n';
		}
		return help;
	}
}
