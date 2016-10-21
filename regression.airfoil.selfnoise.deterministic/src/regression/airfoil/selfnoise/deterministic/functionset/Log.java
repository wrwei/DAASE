package regression.airfoil.selfnoise.deterministic.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import regression.airfoil.selfnoise.deterministic.util.DoubleData;

public class Log extends GPNode {
	
	public String toString() {
		return "log";
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
		
		if (result > 0) {
			rd.x = Math.log(result);
		}
		else
		{
			rd.x = 1;
		}
	}
}
