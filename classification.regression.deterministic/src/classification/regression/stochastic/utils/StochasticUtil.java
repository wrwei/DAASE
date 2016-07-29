package classification.regression.stochastic.utils;

import ec.util.MersenneTwister;

public class StochasticUtil {

	private static StochasticUtil instance = null;
	
	private static MersenneTwister rand = new MersenneTwister(4357);
	
	protected StochasticUtil(){}
	
	public static StochasticUtil getInstance()
	{
		if (instance == null) {
			instance = new StochasticUtil();
		}
		return instance;
	}
	
	public synchronized static int nextInt(int upperBound)
	{
		return nextInt(upperBound);
	}
	
	public synchronized static Operator getOperator(int WEIGHT_ADD, int WEIGHT_SUB, int WEIGHT_MUL, int WEIGHT_DIV)
	{
		int total_weight = WEIGHT_ADD + WEIGHT_SUB + WEIGHT_MUL + WEIGHT_DIV;
		int median = WEIGHT_ADD + WEIGHT_SUB;
		int q3 = WEIGHT_ADD + WEIGHT_SUB + WEIGHT_MUL;
		
		//if total weight is 0, then all 4 OPs have equal chance of spawning
		if (total_weight == 0) {
			int op = rand.nextInt(4);
			if (op < 2) {
				op = rand.nextInt(2);
				if (op == 0) {
					return Operator.ADD;
				}
				else
				{
					return Operator.SUB;
				}
			}
			else
			{
				if (op == 2) {
					return Operator.MUL;
				}
				else
				{
					return Operator.DIV;
				}
			}
		}
		
		else {
			int num = rand.nextInt(total_weight);
			if (num < median) {
				num = rand.nextInt(median);
				if (num < WEIGHT_ADD) {
					return Operator.ADD;
				}
				else
				{
					return Operator.SUB;
				}
			}
			else {
				if (num < q3) {
					return Operator.MUL;
				}
				else {
					return Operator.DIV;
				}
			}
		}
	}
}
