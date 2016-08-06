package symbolic.regression.stochastic.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import symbolic.regression.stochastic.util.DoubleData;

public class One extends Stochastic_Terminal {

	@Override
	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		DoubleData rd = ((DoubleData) (input));
		rd.x = 1;
	}

	@Override
	public String toString() {
		return "1";
	}

}
