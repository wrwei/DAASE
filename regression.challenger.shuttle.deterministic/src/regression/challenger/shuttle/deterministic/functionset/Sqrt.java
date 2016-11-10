package regression.challenger.shuttle.deterministic.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import regression.challenger.shuttle.deterministic.util.DoubleData;

public class Sqrt extends GPNode {
	
	public String toString() {
		return "sqrt";
	}

	public int expectedChildren() {
		return 1;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x;
		
		if (result >= 0) {
			rd.x = Math.sin(result);	
		}
		else
		{
			rd.x = 1;
		}
		
	}
}
