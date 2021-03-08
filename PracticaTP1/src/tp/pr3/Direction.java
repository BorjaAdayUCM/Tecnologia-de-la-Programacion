package tp.pr3;

public enum Direction 
{
		UP("up"),
		DOWN("down"),
		LEFT("left"),
		RIGHT("right");
		
		private String userCommand;
		
		private Direction(String direction)
		{
			userCommand = direction;
		}
		
		public static Direction parse(String name)
		{
			for (Direction direction: Direction.values())
			{
				if (direction.userCommand.equals(name)) return direction;
			}
			return null;
		}
		
		public static String externaliseAll()
		{
			String s = "";
			for (Direction direction: Direction.values())
			{
				s = s + " " + direction.userCommand + ",";
			}
			return s.substring(1, s.length() - 1);
		}
}