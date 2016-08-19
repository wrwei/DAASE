package classification.regression.deterministic.functionset;

import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.IllegalActivity;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class FactorNode_Div extends AbstractFactorNode{

	public String toString() {
		return "factor_div";
	}

	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));
		
		determineType(children[0]);

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x;

		children[1].eval(state, thread, input, stack, individual, problem);
		if (rd.x == 0.0) {
			rd.x = 0; // create a bias
			IllegalActivity.getInstance().illegal();
		} else {
			rd.x = result / rd.x;
		}

	}
}