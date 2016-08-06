package symbolic.regression.stochastic.util;

public class StochasticityNormaliser {

	private static StochasticityNormaliser instance = null;
	protected boolean run = false;
	
	private StochasticityNormaliser() {}
	
	public static StochasticityNormaliser getInstance() 
	{
		if (instance == null) {
			instance = new StochasticityNormaliser();
		}
		return instance;
	}
	
	public boolean isRun() {
		return run;
	}
	
	public double normalise(double arg1, double arg2)
	{
		run = true;
		int a1 = (int) arg1;
		int a2 = (int) arg2;
		
		int d1 = 0;
		int d2 = 0;
		while(a1/10 != 0)
		{
			d1++;
			a1 /= 10;
		}
		
		while(a2/10 != 0)
		{
			d2++;
			a2 /= 10;
		}
		
		double result = 1.0;
		
		if (d1 == d2) {
			return result/10;
		}
		else if (d1 < d2) {
			while(d1 != d2)
			{
				d1++;
				result /= 10;
			}
			result /= 10;
			return result;
		}
		else {
			while(d1 != d2)
			{
				d2++;
				result *= 10;
			}
			result *= 10;
			return result;
		}
	}
	
	public static void main(String[] args) {
		double a1 = 100.03223;
		double a2 = 10.5324;
		double a = StochasticityNormaliser.getInstance().normalise(a1, a2);
		System.out.println(a);
	}
}
