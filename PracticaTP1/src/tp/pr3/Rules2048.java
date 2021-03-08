package tp.pr3;

import java.util.Random;


public class Rules2048 implements GameRules 
{
	private static final int STOP_VALUE = 2048;
	
	@Override
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int num = rand.nextInt() % 10;
		if (num == 0)
		{
			num = 4;
		}
		else
		{
			num = 2;
		}
		board.setCell(pos, num);
	}

	@Override
	public int merge(Cell self, Cell other) {
		int points=0;
		if (canMergeNeigbours(self, other))
		{   
			self.setValue(self.getValue() + other.getValue());
			other.setValue(0);
			points = self.getValue();
		}
		return points;
	}

	@Override
	public int getWinValue(Board board) {
		
		 return board.getMaxValue();
	}

	@Override
	public boolean win(Board board) {
		if(getWinValue(board) == STOP_VALUE)
		{
			return true;
		}
		return false;
	}
}
