package tp.pr3.util;

public class MyMathsUtil 
{
	public static int nextFib(int previous)
	{
		double phi = (1 + Math.sqrt(5)) / 2;
		return (int) Math.round(phi * previous);
	}
}
