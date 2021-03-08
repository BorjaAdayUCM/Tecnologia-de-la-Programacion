package tp.pr3;

import java.util.Random;

public class RulesInverse implements GameRules 
{
	private static final int STOP_VALUE = 1;
	
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) 
	{
		int num = rand.nextInt() % 10;
		if (num == 0)
		{
			num = 1024;
		}
		else
		{
			num = 2048;
		}
		board.setCell(pos, num);
	}

	@Override
	public int merge(Cell self, Cell other)
	{
		int points=0;
		if (canMergeNeigbours(self, other))
		{   
			
			self.setValue(self.getValue() / 2);
			other.setValue(0);
			points = 2048 / self.getValue();
		}
		return points;
	}

	@Override
	public int getWinValue(Board board) 
	{
		
		return board.getMinValue();
	}

	@Override
	public boolean win(Board board) 
	{		
		if(getWinValue(board) == STOP_VALUE)
		{
			return true;
		}
		return false;
	}
}