package classification.regression.deterministic.functionset;

import classification.regression.deterministic.terminals.NSM;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.IllegalDivision;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class DayMod48 extends AbstractFunctionNode{

	public String toString() {
		return "daymod";
	}

	public int expectedChildren() {
		return 1;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));

		if (children[0] instanceof NSM) {
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;
			rd.x = (result)%48;
		}
		else
		{
			rd.x = 0;
			IllegalDivision.getInstance().illegal();
		}
	}

}
