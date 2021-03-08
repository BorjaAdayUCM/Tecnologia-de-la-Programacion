package tp.pr3.control.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tp.pr3.Game;
import tp.pr3.exceptions.MessageException;
import tp.pr3.util.MyStringUtils;

public class SaveCommand extends Command 
{
	private boolean filename_confirmed;
	private File file;
	public static final String filenameInUseMsg = "The file already exists. Do you want to overwrite it ? (Y/N) : ";
	static final String commandInfo = "Save <filename>";
	static final String helpInfo = "save the actual state of the game.";
	
	public SaveCommand() 
	{
		super(commandInfo, helpInfo);
	}
	
	@Override
	public boolean execute(Game game) throws IOException
	{
		FileWriter fw = new FileWriter(this.file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("This file stores a saved 2048 game" + "\r\n" + "\r\n");
		game.store(bw);
		System.out.println("Game successfully saved to file. Use load command to reload it.");
		fw.close();
		return false;
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) throws MessageException 
	{
		Command command = null;
		if(commandWords[0].equals("save") && commandWords.length == 1)
		{ 
			this.filename_confirmed = false; command = this;
			throw new MessageException("Save must be followed by a filename." + '\n');
		}
		else if (commandWords[0].equals("save") && commandWords.length > 2)
		{
			throw new MessageException("Invalid filename: the filename contains spaces." + '\n');
		}
		else if (commandWords[0].equals("save") && commandWords.length == 2)
		{
			String fileName = confirmFileNameStringForWrite(commandWords[1], in);
			this.file = new File(fileName);
			command = this;
		}
		return command;
	}

	private String confirmFileNameStringForWrite(String filenameString, Scanner in) throws MessageException 
	{
		String loadName = filenameString;
		filename_confirmed = false;
		while (!filename_confirmed) 
		{
			if (MyStringUtils.validFileName(loadName)) 
			{
				File file = new File(loadName);
				if(!file.exists())	this.filename_confirmed = true;
				else loadName = getLoadName(filenameString, in);
			} 
			else 
			{
				throw new MessageException("Invalid filename: the filename contains invalid characters." + '\n');
			}
		}
		return loadName;
	}
	
	public String getLoadName(String filenameString, Scanner in) throws MessageException 
	{
		String newFilename = null;
		boolean yesOrNo = false;
		while (!yesOrNo) 
		{
			System.out.print(filenameInUseMsg);
			String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			if (responseYorN.length == 1)
			{
				switch (responseYorN[0]) 
				{
					case "y": yesOrNo = true; newFilename = filenameString; this.filename_confirmed = true; break;
					case "n": yesOrNo = true; System.out.print("Please enter another filename: "); newFilename = in.nextLine(); break;
					default: System.out.println("Please answer 'Y' or 'N'."); break;
				}
				if (newFilename.split(" ").length > 1) throw new MessageException("Invalid filename: the filename contains spaces." + '\n');
			} 
			else 
			{
				System.out.println("Please answer 'Y' or 'N'.");
			}
		}
		return newFilename;
	}
}
