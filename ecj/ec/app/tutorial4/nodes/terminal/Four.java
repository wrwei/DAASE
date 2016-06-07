package ec.app.tutorial4.nodes.terminal;

import ec.EvolutionState;
import ec.Problem;
import ec.app.tutorial4.DoubleData;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

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
