package tp.pr3;

public class Position 
{
	private int row;
	private int col;

	public Position()
	{
		this.row = 0;
		this.col = 0;
	}
	
	public Position(int row, int col) 
	{
		this.row = row;
		this.col = col;
	}
	
	public int getRow() 
	{
		return row;
	}
	
	public void setRow(int row) 
	{
		this.row = row;
	}
	
	public int getCol() 
	{
		return col;
	}
	
	public void setCol(int col) 
	{
		this.col = col;
	}
	
	public boolean positionOk(int boardSize)
	{
		return ((this.row >= 0) && (this.row < boardSize) && (this.col >= 0) && (this.col < boardSize));
	}
	
	public Position neighbour(Direction dir)
	{
		Position neighbour = new Position(this.row, this.col);
		switch(dir)
		{
		case UP: neighbour.row -= 1; break;
		case DOWN: neighbour.row += 1; break;
		case LEFT: neighbour.col -= 1; break;
		case RIGHT: neighbour.col += 1; break;
		}
		return neighbour;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	public boolean comparPos(Position pos)
	{
		return ((this.getRow() < pos.getRow()) || ((this.getRow() == pos.getRow()) && ((this.getCol() < pos.getCol()))));
	}
}