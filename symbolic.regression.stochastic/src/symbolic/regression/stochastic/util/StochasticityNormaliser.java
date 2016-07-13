package symbolic.regression.stochastic.util;

public class StochasticityNormaliser {

	private static StochasticityNormaliser instance = null;
	
	private StochasticityNormaliser() {}
	
	public static StochasticityNormaliser getInstance() 
	{
		if (instance == null) {
			instance = new StochasticityNormaliser();
		}
		return instance;
	}
}
