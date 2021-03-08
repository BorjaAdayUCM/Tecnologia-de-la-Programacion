package tp.pr3.control.commands;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Scanner;

import tp.pr3.Game;
import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.MessageException;

public abstract class Command 
{
	private String helpText;
	private String commandText;
	protected final String commandName;
	
	public Command(String commandInfo, String helpInfo) 
	{
		commandText = commandInfo;
		helpText = helpInfo;
		String[] commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0];
	}
	
	public abstract boolean execute(Game game) throws EmptyStackException, GameOverException, IOException, MessageException;
	
	public abstract Command parse(String[] commandWords, Scanner in) throws MessageException;
	
	public String helpText() 
	{
		return " " + commandText + ": " + helpText;
	}

	public String getCommandName() 
	{
		return commandName;
	}
}