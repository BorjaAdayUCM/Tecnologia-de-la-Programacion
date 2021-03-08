package tp.pr3;

public class MoveResults 
{
	private boolean moved;
	private int points;

	public MoveResults(boolean moved, int points) {
		this.moved = moved;
		this.points = points;
	}

	public boolean isMoved() {
		return moved;
	}

	public int getPoints() {
		return points;
	}
}
