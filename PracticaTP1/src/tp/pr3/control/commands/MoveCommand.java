package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.Direction;
import tp.pr3.Game;
import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.MessageException;

public class MoveCommand extends Command 
{	
	private Direction dir;
	static final String commandInfo = "Move <direcction>";
	static final String helpInfo = "execute a move in one of the directions: "  + Direction.externaliseAll() + '.';
	
	public MoveCommand() {
		super(commandInfo, helpInfo);
	}

	@Override
	public boolean execute(Game game) throws  GameOverException 
	{
		try
		{
			return game.move(dir);
		}
		catch (NullPointerException e)
		{
			System.out.println("Unknown direction for move command.");
			return false;
		}
	}

	@Override
	public Command parse(String[] commandWords, Scanner in) throws MessageException
	{
		Command command = null;
		if(commandWords.length == 1 && commandWords[0].equals("move"))
		{ 
			this.dir = null; command = this;
			throw new MessageException("Move must be followed by a direction: " + Direction.externaliseAll() + '.' + '\n');
		}
		else if (commandWords[0].equals("move") && commandWords.length > 1)
		{
			commandWords[1] = commandWords[1].toLowerCase();
			this.dir = Direction.parse(commandWords[1]);
			command = this;
		}
		return command;
	}
}