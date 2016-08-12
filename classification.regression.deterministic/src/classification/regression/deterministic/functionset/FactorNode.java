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
		return 8;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		
		DoubleData rd = ((DoubleData) (input));

		int decimal = 0;
		
		//8 bits for decimal
		for (int i = 0; i < 7; i++) {
			
			children[i].eval(state, thread, input, stack, individual, problem);
			int temp = (int) rd.x;
			if (temp == 1) {
				decimal += 1 << (i-2);
			}
		}

		result = decimal;
		
		children[7].eval(state, thread, input, stack, individual, problem);
		int temp = (int)rd.x;
		if (temp == 1) {
			value = result;
			rd.x = result;
		}
		else
		{
			value = 1/result;
			rd.x = 1/result;
		}
	}

}
