package tp.pr3;

import java.util.Random;

import tp.pr3.util.MyMathsUtil;

public class RulesFib implements GameRules 
{
	private static final int STOP_VALUE = 144;

	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) 
	{
		int num = rand.nextInt() % 10;
		if (num == 0)
		{
			num = 2;
		}
		else
		{
			num = 1;
		}
		board.setCell(pos, num);

	}

	public boolean canMergeNeigbours(Cell self, Cell other)
	{
		return (self.getValue() == 1 && other.getValue() == 1) || (self.getValue() == MyMathsUtil.nextFib(other.getValue()) || (other.getValue() == MyMathsUtil.nextFib(self.getValue())));
	}
	
	@Override
	public int merge(Cell self, Cell other) 
	{
		int points = 0;
		if (canMergeNeigbours(self, other))
		{   
			self.setValue(self.getValue() + other.getValue());
			other.setValue(0);
			points = self.getValue();
		}
		return points;
	}

	@Override
	public int getWinValue(Board board) 
	{
		
		return board.getMaxValue();
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