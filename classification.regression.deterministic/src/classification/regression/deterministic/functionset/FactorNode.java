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
		return 6;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		

 		double decimal = 1;
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
		double fraction = 0;
		for(int i = 2; i < 6; i++)
		{

			children[i].eval(state, thread, input, stack, individual, problem);
			int temp = (int) rd.x;
			if (temp == 1) {
				fraction += 1 << (i-2);
			}
		}
		
		
		//make factor less than 1 first
		while(fraction >= 1)
		{
			fraction /= 10.0;
		}
		
		result = decimal + fraction;
		if (result == 0.0) {
			System.out.println("decimal: " + decimal + " fraction: " + fraction);
		}
		
		value = result;
		
		if (value == 0.0) {
			System.out.println("value: " + value);
		}
		rd.x = result;
		
	}

}
