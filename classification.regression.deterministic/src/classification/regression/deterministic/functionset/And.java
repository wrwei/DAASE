package classification.regression.deterministic.functionset;

import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class And extends AbstractFunctionNode{

	public String toString() {
		return "and";
	}

	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		DoubleData rd = ((DoubleData) (input));

		int l = 0;
		int r = 0;
		children[0].eval(state, thread, input, stack, individual, problem);
		l = (int) rd.x;

		children[1].eval(state, thread, input, stack, individual, problem);
		r = (int) rd.x;
		
		rd.x = l & r;
	}

}
