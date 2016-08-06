package symbolic.regression.stochastic.util;

public class Board {

	public static Board instance = null;
	
	private int x;
	private int y;
	
	private Board()
	{
		
	}
	
	public Board getInstance()
	{
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	
}
