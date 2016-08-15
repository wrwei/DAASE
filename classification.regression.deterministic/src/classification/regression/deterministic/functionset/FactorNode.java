package classification.regression.deterministic.functionset;

import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class FactorNode extends GPNode {

	private double value = 0.0;
	
	public String toString() {
		return value+"";
	}

	public int expectedChildren() {
		return 10;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		

		int decimal = 0;
		DoubleData rd = ((DoubleData) (input));

		//2 bits for decimal
		for (int i = 0; i < 2; i++) {

			children[i].eval(state, thread, input, stack, individual, problem);
			int temp = (int) rd.x;
			if (temp == 1) {
				decimal += 1 << i;
			}
		}
		
		//4 bits for fraction
		int fraction = 0;
		for(int i = 2; i < 6; i++)
		{

			children[i].eval(state, thread, input, stack, individual, problem);
			int temp = (int) rd.x;
			if (temp == 1) {
				fraction += 1 << (i-2);
			}
		}
		
		//4 bits to calculate div factor
		int div_factor = 1;
		for(int i = 6; i < 10; i++)
		{

			children[i].eval(state, thread, input, stack, individual, problem);
			int temp = (int) rd.x;
			if (temp == 1) {
				div_factor += 1 << (i-6);
			}
		}
		
		//get factor part
		double factor = fraction;
		
		//make factor less than 1 first
		while(factor >= 1)
		{
			factor /= 10;
		}
		
		//div by 10 until div_factor is 0
		while(div_factor > 0)
		{
			factor /= 10;
			div_factor--;
		}

		result = decimal + factor;
		
		value = result;
		rd.x = result;
		
	}

}
