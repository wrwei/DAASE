package ec.app.tutorial4;

import ec.util.MersenneTwister;

public class OperatorGenerator {

	private static OperatorGenerator instance = null;
	
	private static int WEIGHT_ADD = 2;
	private static int WEIGHT_SUB = 7;
	private static int WEIGHT_MUL = 7;
	private static int WEIGHT_DIV = 9;
	
	public static enum OPERATORS {ADD, SUB, MUL, DIV}
	
	private static MersenneTwister rand = new MersenneTwister(4357);
	
	protected OperatorGenerator(){}
	
	public static OperatorGenerator getInstance()
	{
		if (instance == null) {
			instance = new OperatorGenerator();
		}
		return instance;
	}
	
	public static OPERATORS getOperator()
	{
		int total_weight = WEIGHT_ADD + WEIGHT_SUB + WEIGHT_MUL + WEIGHT_DIV;
		int median = WEIGHT_ADD + WEIGHT_SUB;
		int q3 = WEIGHT_ADD + WEIGHT_SUB + WEIGHT_MUL;
		
		int num = rand.nextInt(total_weight);
		if (num < median) {
			num = rand.nextInt(median);
			if (num < WEIGHT_ADD) {
				return OPERATORS.ADD;
			}
			else
			{
				return OPERATORS.SUB;
			}
		}
		else {
			if (num < q3) {
				return OPERATORS.MUL;
			}
			else {
				return OPERATORS.DIV;
			}
		}
	}
}
