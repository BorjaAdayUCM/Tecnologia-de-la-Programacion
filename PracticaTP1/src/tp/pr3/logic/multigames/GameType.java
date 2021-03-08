package tp.pr3.logic.multigames;

import tp.pr3.GameRules;
import tp.pr3.Rules2048;
import tp.pr3.RulesFib;
import tp.pr3.RulesInverse;


public enum GameType
{	
	ORIG("2048, original version", "original", new Rules2048()),
	FIB("2048, fibonacci version", "fib", new RulesFib()),
	INV("2048, inverse version", "inverse", new RulesInverse());
	
	private String userFriendlyName;
	private String parameterName;
	private GameRules correspondingRules;
	
	private GameType(String friendly, String param, GameRules rules)
	{
		userFriendlyName = friendly;
		parameterName = param;
		correspondingRules = rules;
	}
	
	public static GameType parse(String param)
	{
		for (GameType gameType : GameType.values()) 
		{
			if (gameType.parameterName.equals(param))
			{
				return gameType;
			}
		}
		return null;
	}
	
	public static String externaliseAll () 
	{
		String s = "";
		for (GameType type : GameType.values())
		{
			s = s + " " + type.externalise() + ",";
		}
		return s.substring(1, (s.length() - 1));
	}
	
	public GameRules getRules() 
	{
		return correspondingRules;
	}
	
	public String toString() 
	{
		return userFriendlyName;
	}
	
	public String externalise () 
	{
		return parameterName;
	}


}