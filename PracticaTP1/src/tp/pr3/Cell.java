package tp.pr3;

public class Cell 
{
	private int value;
	
	public Cell() 
	{
		this.value = 0;
	}
	
	public int getValue() 
	{
		return value;
	}

	public void setValue(int value) 
	{
		this.value = value;
	}
	
	public boolean isEmpty()
	{
		return (this.value == 0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (value != other.value)
			return false;
		return true;
	}

	public int doMerge(Cell neighbour, GameRules rules)
	{
		return rules.merge(this, neighbour);
	}
}
