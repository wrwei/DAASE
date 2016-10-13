package regression.bike.sharing.deterministic.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import regression.bike.sharing.deterministic.util.DoubleData;

public class Ten extends GPNode{

	@Override
	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		DoubleData rd = ((DoubleData) (input));
		rd.x = 1.0;
	}

	@Override
	public String toString() {
		return "10";
	}

}
