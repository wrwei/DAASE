package classification.regression.deterministic.functionset;

import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class GreaterThan extends AbstractFunctionNode {

	public String toString() {
		return ">";
	}

	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x;

		children[1].eval(state, thread, input, stack, individual, problem);
		if (result > rd.x) {
			rd.x = 1;
		}
		else
		{
			rd.x = 0;
		}
	}

}
