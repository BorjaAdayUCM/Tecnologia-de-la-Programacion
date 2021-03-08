package tp.pr3.control.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import tp.pr3.Game;
import tp.pr3.exceptions.MessageException;
import tp.pr3.util.MyStringUtils;

public class LoadCommand extends Command {

	private File file;
	static final String commandInfo = "Load <filename>";
	static final String helpInfo = "load a saved game in a file.";

	public LoadCommand() 
	{
		super(commandInfo, helpInfo);
	}

	@Override
	public boolean execute(Game game) throws IOException, MessageException
	{
		FileReader fr = new FileReader(this.file);
		BufferedReader br = new BufferedReader (fr);
		game.load(br);
		fr.close();
		return true;
	}

	@Override
	public Command parse(String[] commandWords, Scanner in)
			throws MessageException {
		Command command = null;
		if(commandWords[0].equals("load") && commandWords.length == 1)
		{ 
			command = this;
			throw new MessageException("Load must be followed by a filename." + '\n');
		}
		else if (commandWords[0].equals("load") && commandWords.length > 2)
		{
			command = this;
			throw new MessageException("Invalid filename: the filename contains spaces." + '\n');
		}
		else if (commandWords[0].equals("load") && commandWords.length == 2)
		{
			this.file = confirmFileNameStringForWrite(commandWords[1]);
			command = this;
		}
		return command;
	}

	private File confirmFileNameStringForWrite(String filenameString) throws MessageException 
	{
		String loadName = filenameString;
		File file = null;
		if (MyStringUtils.validFileName(loadName)) 
		{
			file = new File(loadName);
			if(!file.exists())	
			{
				throw new MessageException("File not found" + '\n');
			}
		} 
		else 
		{
			throw new MessageException("Invalid filename: the filename contains invalid characters." + '\n');
		}
		return file;
	}
}
