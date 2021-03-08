package tp.pr3;

import java.util.Random;

public interface GameRules 
{
	default boolean canMergeNeigbours(Cell self, Cell other)
	{
		return (self.equals(other));
	}
	
	int merge(Cell self, Cell other);
	
	int getWinValue(Board board);
	
	boolean win(Board board);
	
	void addNewCellAt(Board board, Position pos, Random rand);
	
	default boolean lose(Board board)
	{
		boolean possible = true;
		if (board.getPosArray() == 0)
		{
			boolean encontrado = false;
			int row = 0, col = 0;
			while (row < board.getBoardSize() && !encontrado)
			{
				col = 0;
				while (col < board.getBoardSize() && !encontrado)
				{
					Position posActual = new Position(row,col);
					Position posRight = posActual.neighbour(Direction.RIGHT);
					Position posDown = posActual.neighbour(Direction.DOWN);
					if ((posRight.positionOk(board.getBoardSize()) && (canMergeNeigbours(board.getCell(posActual), board.getCell(posRight))))) encontrado = true;
					if ((posDown.positionOk(board.getBoardSize()) && (canMergeNeigbours(board.getCell(posActual), board.getCell(posDown))))) encontrado = true;
					col++;
				}
				row++;
			}
			if (!encontrado) possible = false;
		}
		return !possible;
	}
	
	default Board createBoard(int size)
	{
		return new Board(size);
	}
	
	default void addNewCell(Board board, Random rand)
	{
		int choice = (rand.nextInt(board.getPosArray()));
		Position pos = board.getArrayEmpty()[choice];
		board.delete(pos);
		addNewCellAt(board, pos, rand);	
	}
	
	default void initBoard(Board board, int numCells, Random rand)
	{
		for (int row = 0; row < board.getBoardSize(); row++)
		{
			for (int col = 0; col < board.getBoardSize(); col++)
			{
				Position pos = new Position(row,col);
				board.insert(pos);
				board.setCell(pos, 0);
			}
		}
		for (int i = 0; i < numCells; i++)
		{
			addNewCell(board,rand);
		}
	}
}