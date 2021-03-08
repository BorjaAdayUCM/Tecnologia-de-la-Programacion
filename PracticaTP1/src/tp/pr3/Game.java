package tp.pr3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Random;

import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.MessageException;
import tp.pr3.logic.multigames.GameType;
import tp.pr3.util.MyStringUtils;

public class Game 
{
	private Board board;
	private int boardSize;
	private int initCells;
	private long seed;
	private Random _myRandom;
	private int score;
	private int bestValue;
	private boolean finished;
	private GameStateStack undoStack = new GameStateStack();
	private GameStateStack redoStack = new GameStateStack();
	private GameRules rules;
	private GameType type;
	
	public Game(int boardSize, int initCells, long seed , GameType type)
	{
		this.boardSize = boardSize;
		this.initCells = initCells;
		this.seed = seed;
		this.rules = type.getRules();
		this.finished = false;
		this.type = type;
		this.board = new Board(boardSize);
		this.reset();
	}

	public Board getBoard() 
	{
		return board;
	}

	public void setBoard(Board board) 
	{
		this.board = board;
	}

	public int getBoardSize() 
	{
		return boardSize;
	}

	public boolean isFinished() 
	{
		return finished;
	}
	
	public void setFinished(boolean finished) 
	{
		this.finished = finished;
	}

	public GameRules getRules() 
	{
		return rules;
	}
	
	public void updateGame(int boardSize, int initCells, long seed, GameType type)
	{
		this.boardSize = boardSize;
		this.initCells = initCells;
		this.seed = seed;
		this.type = type;
		this.rules = type.getRules();
		this.reset();
	}
	
	@Override
	public String toString() 
	{
		String juego = this.board.toString() + '\n' + "Best Value: " + this.bestValue + MyStringUtils.repeat(" ", (7 * this.boardSize) + this.boardSize - 20) + "Score: " + this.score + '\n';
		return juego;
	}
	
	public boolean move(Direction dir) throws GameOverException
	{
		GameState aux = this.getState();
		MoveResults result = this.board.executeMove(dir, this.rules);
		if(result.isMoved())
		{	
			this.score += result.getPoints();
			this.bestValue = this.rules.getWinValue(board);
			if (this.rules.win(board)) throw new GameOverException(false);
			else
			{
				this.rules.addNewCell(board, this._myRandom);
				if(this.rules.lose(board)) throw new GameOverException(true);
			}
			this.undoStack.push(aux);
			if(this.undoStack.getTam() < GameStateStack.getCapacity()) this.undoStack.setTam(this.undoStack.getTam() + 1);
			this.redoStack.reset();
			return true;
		}
		return false;
	}

	public void reset()
	{
		this._myRandom = new Random(this.seed);
		this.rules.initBoard(this.board, this.initCells, _myRandom);
		this.bestValue = this.rules.getWinValue(board);
		this.score = 0;
		this.redoStack.reset();
		this.undoStack.reset();
	}

	public GameState getState()
	{
		return new GameState(this.score, this.board.getState());
	}
	
	public void setState(GameState aState)
	{
		this.score = aState.getScore();
		this.board.setState(aState.getBoardState());
	}

	public void undo() throws EmptyStackException
	{
		if(this.undoStack.getTam() > 0)
		{
			this.redoStack.push(this.getState());
			this.setState(this.undoStack.pop());
			this.bestValue= this.rules.getWinValue(board);
			this.redoStack.setTam(this.redoStack.getTam() + 1);
			this.undoStack.setTam(this.undoStack.getTam() - 1);
			this.board.generateArrayEmpty();
		}
		else throw new EmptyStackException();
	}
	
	public void redo() throws EmptyStackException
	{	
		if(this.redoStack.getTam() > 0)
		{
			this.undoStack.push(this.getState());
			this.setState(this.redoStack.pop());
			this.bestValue= this.rules.getWinValue(board);
			this.redoStack.setTam(this.redoStack.getTam() - 1); 
			this.undoStack.setTam(this.undoStack.getTam() + 1);
			this.board.generateArrayEmpty();
		}
		else throw new EmptyStackException();
	}
	
	public void store(BufferedWriter bw) throws IOException
	{
		this.board.store(bw);
		bw.write(this.initCells + "	" + this.score + "	" + this.type.externalise());
		bw.flush();
	}
	
	public void load(BufferedReader br) throws IOException, MessageException
	{
		if (br.readLine().equals("This file stores a saved 2048 game"))
		{
			GameState aux = this.getState();
			int inits = this.initCells;
			int size = this.boardSize;
			try
			{
				br.readLine(); String[] parts = br.readLine().split("\\s+");
				int newBoardSize = parts.length;
				if(this.boardSize != newBoardSize) 
				{
					this.boardSize = newBoardSize;
					this.board = new Board(this.boardSize);
				}
				this.initCells = 0;
				this.reset();
				Position pos = new Position(0,0);
				for(int col = 0; col < this.boardSize; col++)
				{
					this.board.setCell(pos, Integer.parseInt(parts[col]));
					pos.setCol(col + 1);
				}
				this.board.load(br);
				this.board.generateArrayEmpty();
				br.readLine();
				parts = br.readLine().split("\\s+");
				this.initCells = Integer.parseInt(parts[0]);
				if(this.initCells < 1 || this.initCells > this.boardSize * this.boardSize) throw new MessageException();
				this.score = Integer.parseInt(parts[1]);
				this.type =  GameType.parse(parts[2]);
				this.rules = this.type.getRules();
				this.bestValue = this.rules.getWinValue(this.board);
				br.close();
				System.out.println("Game successfully loaded from file: " + this.type.toString() + '.' + '\n');
			}
			catch (Exception e)
			{
				if (this.boardSize != size) this.board = new Board(size); this.boardSize = size;
				this.initCells = inits;
				this.setState(aux);
				this.bestValue = this.rules.getWinValue(this.board);
				this.board.generateArrayEmpty();
				if (e instanceof MessageException) throw new MessageException("The file provide is corrupted, change the initCells." + '\n' + '\n' + this);
				else if (e instanceof NullPointerException) throw new MessageException("The file provide is corrupted, the gameType is unknown."+ '\n'+ '\n'+ this);
				else throw new MessageException("The data of the file provide is corrupted." + '\n'+'\n'+ this);
			}
		}
		else
		{
			throw new MessageException("This is not a 2048 file." + '\n'+ '\n'+ this);
		}
	}
	
}