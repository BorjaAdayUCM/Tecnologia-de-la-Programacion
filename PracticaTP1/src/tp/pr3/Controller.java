package tp.pr3;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Scanner;

import tp.pr3.control.commands.Command;
import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.MessageException;
import tp.pr3.util.CommandParser;

public class Controller 
{
	private Game game;
	private Scanner in;

	public Controller(Game game, Scanner in) 
	{
		this.game = game;
		this.in = in;
	}
	
	public void print(Object text)
	{
		System.out.print(text);
	}
	
	public void run() throws EmptyStackException, MessageException, GameOverException, IOException
	{
		System.out.println(this.game);
		String option = "";
		while (!this.game.isFinished())
		{
			System.out.print("Command > ");
			option = in.nextLine();
			String[] parts = option.split(" ");
			parts[0] = parts[0].toLowerCase();
			try
			{
				Command command = CommandParser.parseCommand(parts, in);
				try 
				{
					if (command.execute(this.game)) System.out.print(this.game);
				}
				catch (NullPointerException e)
				{
					System.out.println("Unknown command. Use 'help' to see the available commands.");
				}
				catch (EmptyStackException e)
				{
					System.out.println(command.getCommandName() + " is not available.");
				}
				catch(GameOverException e)
				{
					System.out.println(this.game);
					if(e.getLose()) System.out.println("Game Over.");
					else System.out.println("Well Done !");
					this.game.setFinished(true);
				}
				System.out.println();
			}
			catch (MessageException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}