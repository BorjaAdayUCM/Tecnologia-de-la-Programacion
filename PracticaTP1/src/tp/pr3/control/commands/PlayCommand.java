package tp.pr3.control.commands;

import java.util.Random;
import java.util.Scanner;
import tp.pr3.Game;
import tp.pr3.exceptions.MessageException;
import tp.pr3.logic.multigames.GameType;

public class PlayCommand extends Command
{
	protected long randomSeed;
	protected int initCells, boardSize;
	protected GameType gameType;
	protected Scanner in;
	
	static final String commandInfo = "Play <game>";
	static final String helpInfo = "start a new game of one of the games types: " + GameType.externaliseAll() + '.';
	
	public PlayCommand() 
	{
		super(commandInfo, helpInfo);
	}

	@Override
	public boolean execute(Game game) 
	{
			if (gameType != null)
			{
				if (this.boardSize != game.getBoardSize())	game.setBoard(game.getRules().createBoard(this.boardSize));
				game.updateGame(this.boardSize, this.initCells, this.randomSeed, this.gameType);
				return true;
			}
			return false;
	}
		
	@Override
	public Command parse(String[] commandWords, Scanner in) throws MessageException 
	{
		Command command = null;
		if(commandWords[0].equals("play") && commandWords.length == 1)
		{
			this.gameType = null; command = this;
			throw new MessageException("Play must be followed by a type of game: " + GameType.externaliseAll() + '.' + '\n');
		}
		else if (commandWords[0].equals("play") && commandWords.length > 1)
		{
			command = this;
			commandWords[1] = commandWords[1].toLowerCase();
			try
			{
				this.gameType = GameType.parse(commandWords[1]); 
				if (this.gameType == null) throw new NullPointerException();
			}
			catch (NullPointerException e)
			{
				System.out.println("Unknown type for play command.");
				return command;
			}
			String cadena;
			this.boardSize = - 1;
			while (this.boardSize == -1)
			{
				System.out.print("Please enter the size of the board: "); cadena = in.nextLine();
				if(cadena.equals(""))
				{
					this.boardSize = 4; 
					System.out.print("  Using the default size of the board: 4" + '\n' + '\n');
				}
				else 
				{
					String[] parts = cadena.split(" ");
					try
					{
						if (Integer.parseInt(parts[0]) <= 1) throw new MessageException("The size of the board must be more than 1." );
						else this.boardSize = Integer.parseInt(parts[0]);
					}
					catch (MessageException e)
					{
						System.out.println(e.getMessage());
					}
					catch (NumberFormatException e)
					{
						System.out.println("Please provide a single positive integer or press return.");
					}
					System.out.println();
				}
			}
			this.initCells = -1;
			while (this.initCells == -1)
			{
			System.out.print("Please enter the number of initial cells: ");
				cadena = in.nextLine();
				if(cadena.equals(""))
				{
					this.initCells = 2; 
					System.out.print("  Using the default number of initial cells: 2" + '\n' + '\n');
				}
				else
				{
					String[] parts = cadena.split(" ");
					try
					{
						if (Integer.parseInt(parts[0]) < 1 || Integer.parseInt(parts[0]) > this.boardSize * this.boardSize) throw new MessageException("The number of initial cells must be positive and less than the number of cells on the board.");
						else this.initCells = Integer.parseInt(parts[0]);
					}
					catch (NumberFormatException e)
					{
						System.out.println("Please provide a single positive integer or press return.");
					}
					catch (MessageException e)
					{
						System.out.println(e.getMessage());
					}
					System.out.println();
				}
			}
			this.randomSeed = -1;
			while (this.randomSeed == -1)
			{
				System.out.print("Please enter the seed for the pseudo-random number generator: ");
				cadena =in.nextLine();
				if(cadena.equals(""))
				{
					this.randomSeed = new Random().nextInt(1000);
					System.out.print("  Using the default seed for the pseudo-random number generator: " + this.randomSeed + '\n' + '\n');
				}
				else
				{
					String[] parts = cadena.split(" ");
					try
					{
						this.randomSeed = Integer.parseInt(parts[0]);
					}
					catch (NumberFormatException e)
					{
						System.out.print("Please provide a single positive integer or press return." + '\n');
					}
					System.out.println();
				}
			}
		}
	return command;
	}
}