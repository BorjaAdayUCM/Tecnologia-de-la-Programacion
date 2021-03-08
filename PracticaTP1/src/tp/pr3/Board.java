package tp.pr3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import tp.pr3.Board;
import tp.pr3.util.MyStringUtils;

public class Board 
{
	private Cell[][]_board;
	private int boardSize;
	private Position[] arrayEmpty;
	private int posArray;
	
	public Board(int boardSize)
	{
		this.posArray = 0;
		this.boardSize = boardSize;
		this._board = new Cell[this.boardSize][this.boardSize];
		this.arrayEmpty = new Position[this.boardSize * this.boardSize];
		for (int row = 0; row < boardSize; row++)
		{
			for (int col = 0; col < boardSize; col++)
			{
				this.arrayEmpty[this.posArray] = new Position(row,col);
				this.posArray++;
				this._board[row][col]= new Cell();
			}
		}
	}
	
	public Cell getCell(Position pos) 
	{
		return _board[pos.getRow()][pos.getCol()];
	}

	public void setCell(Position pos, int value)
	{
		this._board[pos.getRow()][pos.getCol()].setValue(value);
	}

	public int getBoardSize() 
	{
		return boardSize;
	}
	
	public Position[] getArrayEmpty() 
	{
		return arrayEmpty;
	}
	
	public int getPosArray() 
	{
		return posArray;
	}

	@Override
	public String toString() 
	{
		int cellSize = 7;
		String space = " ";
		String vDelimitier = "|";
		String hDelimitier = "-";		
		String board = "";
		for (int row = 0; row < this.boardSize; row++)
		{
			board += space + MyStringUtils.repeat(hDelimitier, cellSize * boardSize + boardSize - 1) + space + '\n';
			for (int col = 0; col < this.boardSize; col++)
			{
				String numero = String.valueOf(_board[row][col].getValue());
				if (_board[row][col].isEmpty())
				{
					board += vDelimitier + MyStringUtils.repeat(space, cellSize);
				}
				else
				{
					board += vDelimitier + MyStringUtils.centre(numero, cellSize);
				}
			}
			board += vDelimitier + '\n';
		}
		board += space + MyStringUtils.repeat(hDelimitier, cellSize * boardSize + boardSize - 1);
		return board;
	}
	
	public MoveResults executeMove(Direction dir, GameRules rules)
	{
		boolean moved = false;
		int points = 0;
		Position posActual = new Position();
		Position posCompar = new Position();
		Direction direction;
		int limite, aux,j = 0,j_sumador = 1;
		switch(dir)
		{
			case UP:    limite = boardSize - 1; direction = Direction.DOWN;  break;
			case DOWN:  j_sumador = -1; j = boardSize - 1; limite = 0; direction = Direction.UP; break;
			case LEFT:  limite = boardSize - 1; direction = Direction.RIGHT; break;
			case RIGHT: j_sumador = -1; j = boardSize - 1; limite = 0; direction = Direction.LEFT; break;
			default: limite = 0; direction = Direction.LEFT;
		}
			for ( int i = 0; i < this.boardSize; i++)
			{
				aux = j;
				for (;j != limite; j+= j_sumador)
				{
					switch(dir)
					{
						case UP: posActual.setRow(j); posActual.setCol(i); break;
						case DOWN: posActual.setRow(j); posActual.setCol(i); break;
						default: posActual.setRow(i); posActual.setCol(j); break;
					}
					posCompar = posActual.neighbour(direction);
					while ((posCompar.positionOk(boardSize) && (_board[posActual.getRow()][posActual.getCol()].isEmpty())))
					{
						if (!_board[posCompar.getRow()][posCompar.getCol()].isEmpty())
						{
							this.setCell(posActual, _board[posCompar.getRow()][posCompar.getCol()].getValue());
							this.setCell(posCompar, 0);
							this.insert(posCompar);
							this.delete(posActual);
							moved = true;
						}
						
						if (dir == Direction.UP ||dir == Direction.DOWN)
						{
							posCompar.setRow(posCompar.getRow() + j_sumador);
						}
						else
						{
							posCompar.setCol(posCompar.getCol() + j_sumador);
						}
					}
					posCompar = posActual.neighbour(direction);
					if (!_board[posActual.getRow()][posActual.getCol()].isEmpty())
					{
						while ((posCompar.positionOk(boardSize) && (_board[posCompar.getRow()][posCompar.getCol()].isEmpty())))
						{
							if (dir == Direction.UP ||dir == Direction.DOWN)
							{
								posCompar.setRow(posCompar.getRow() + j_sumador);
							}
							else
							{
								posCompar.setCol(posCompar.getCol() + j_sumador);
							}
						}
						if (posCompar.positionOk(boardSize))
						{
							int movePoints = this._board[posActual.getRow()][posActual.getCol()].doMerge(_board[posCompar.getRow()][posCompar.getCol()], rules);
							if (movePoints == 0)
							{
								Position posVecino = posActual.neighbour(direction);
								if (!posCompar.equals(posVecino))
								{ 	moved = true;
									this.setCell(posVecino, _board[posCompar.getRow()][posCompar.getCol()].getValue());
									this.setCell(posCompar, 0);
									this.insert(posCompar);
									this.delete(posVecino);
								}
							}
							else
							{
								
								insert(posCompar);
								moved = true;
								points += movePoints;
							}
						}
					}
				}
				j = aux;
			}
		MoveResults result = new MoveResults(moved, points);
		return result;
	}
	
	public BinaryResults buscarBinario(Position pos)
	{
		int ini = 0, fin = this.posArray - 1, mitad = 0;
		boolean encontrado = false;
		while ((ini <= fin) && !encontrado)
		{
			mitad = (ini + fin) / 2;
			if (pos.comparPos(this.arrayEmpty[mitad])) fin = mitad - 1;
			else if (this.arrayEmpty[mitad].comparPos(pos)) ini = mitad + 1;
			else encontrado = true;
		}
		if (!encontrado) mitad = ini;
		BinaryResults result = new BinaryResults(encontrado, mitad);
		return result;
	}
	
	public void delete(Position pos)
	{
		BinaryResults result = buscarBinario(pos);
		if (result.isEncontrado())
		{
			int mitad = result.getPos();
			for (;mitad < this.posArray - 1; mitad++)
			{
				this.arrayEmpty[mitad] = this.arrayEmpty[mitad + 1];
			}
			this.arrayEmpty[this.posArray - 1] = new Position();
			this.posArray--;
		}
	}
	
	public void insert(Position pos)
	{
		BinaryResults result = buscarBinario(pos);
		int mitad = result.getPos();
		if (!result.isEncontrado())
		{
			for (int i = this.posArray; i > mitad; i--)
			{
			this.arrayEmpty[i] = this.arrayEmpty[i - 1];
			}
			this.arrayEmpty[mitad] = new Position(pos.getRow(), pos.getCol());
			this.posArray++;
		}
	}    

	public int[][] getState()
	{
		int[][] board = new int[this.boardSize][this.boardSize] ;
		for (int row = 0; row < this.boardSize; row++)
		{
			for (int col = 0; col < this.boardSize; col++)
			{
				board[row][col] = this._board[row][col].getValue();
			}
		}
		return board;
	}
	
	public void setState(int[][] aState)
	{
		for (int row = 0; row < this.boardSize; row++)
		{
			for (int col = 0; col < this.boardSize; col++)
			{
				this._board[row][col].setValue(aState[row][col]);
			}
		}
	}

	public int getMaxValue()
	{
		int max = 0;
		for(int i = 0 ; i < boardSize; i++)
		{
			for(int j = 0; j < boardSize; j++)
			{
				if(max < this._board[i][j].getValue()) max = this._board[i][j].getValue();
			}
		}
		return max;
	}
	
	public int getMinValue()
	{
		int min=2048;
		for(int i = 0 ; i < this.boardSize; i++)
		{
			for(int j = 0; j < this.boardSize; j++)
			{
				if((!this._board[i][j].isEmpty()) && (min > this._board[i][j].getValue())) min = this._board[i][j].getValue();
			}
		}
		return min;
	}
	
	public void generateArrayEmpty()
	{
		for ( int i = 0; i < this.boardSize; i++)
		{
			for(int j = 0; j < this.boardSize; j++)
			{
				Position pos = new Position(i,j);
				if(this._board[i][j].isEmpty())
				{
					this.insert(pos);
				}
				else 
				{
					this.delete(pos);
				}
			}
		}
	}
	
	public void store(BufferedWriter br) throws IOException
	{
		for(int i = 0; i < this.boardSize; i++)
		{
			for(int j = 0; j < this.boardSize - 1; j++)
			{
				br.write(this._board[i][j].getValue() + "	");
			}
			br.write(this._board[i][this.boardSize - 1].getValue()+ "\r\n");
		}
		br.newLine();
	}
	
	public void load(BufferedReader br) throws IOException
	{
		String [] parts;
		for(int row = 1 ;row < this.boardSize; row++)
		{
			parts = br.readLine().split("\\s+");
			for(int col = 0; col < this.boardSize; col++)
			{
				this._board[row][col].setValue(Integer.parseInt(parts[col]));
			}
		}
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
		
	}
}