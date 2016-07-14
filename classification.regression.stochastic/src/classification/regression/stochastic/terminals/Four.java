package classification.regression.stochastic.terminals;

import classification.regression.stochastic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Four extends Stochastic_Terminal {

	@Override
	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		DoubleData rd = ((DoubleData) (input));
		rd.x = 4;
	}

	@Override
	public String toString() {
		return "4";
	}

}
