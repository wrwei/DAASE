package classification.regression.stochastic.functionset;

import classification.regression.stochastic.utils.DoubleData;
import classification.regression.stochastic.utils.IllegalDivision;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Div extends AbstractFunctionNode {

	public String toString() {
		return "/";
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
		if (rd.x == 0.0) {
			rd.x = 10000; // create a bias
			IllegalDivision.getInstance().illegal();
		} else {
			rd.x = result / rd.x;
		}
	}

}
