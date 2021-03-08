package tp.pr3;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Scanner;

import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.MessageException;
import tp.pr3.logic.multigames.GameType;

public class Game2048 
{
	public static void main(String[] args) throws EmptyStackException, MessageException, GameOverException, IOException
	{
		try
		{
			int sizeBoard = 4, initCells = 2; long seed = new Random().nextInt(1000);
			if(args.length == 3)
			{
				sizeBoard = Integer.parseInt(args[0]);
				initCells = Integer.parseInt(args[1]);
				seed = Long.parseLong(args[2]);
			}
			else if(args.length == 2)
			{
				sizeBoard = Integer.parseInt(args[0]);
				initCells = Integer.parseInt(args[1]);
			}
			if ((sizeBoard < 2) || (initCells < 1) || (initCells > sizeBoard * sizeBoard)||(args.length == 1) ||(args.length > 3 ))
			{
				System.out.println("Invalid initial configuration.");
			}
			else
			{
			Controller controller = new Controller(new Game(sizeBoard, initCells, seed, GameType.parse("original")), new Scanner(System.in));
			controller.run();
			}
		} 
		catch (NumberFormatException e)
		{
			System.out.println("The command-line must be numbers.");
		}
	}
}