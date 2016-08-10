package classification.regression.deterministic.functionset;

import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.IllegalDivision;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Log_base_e extends AbstractFunctionNode {

	public String toString() {
		return "ln";
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

		if (result < 0) {
			IllegalDivision.getInstance().illegal();
			rd.x = 10000;
		}
		else
		{
			rd.x = Math.log(result);
		}
	}
}
