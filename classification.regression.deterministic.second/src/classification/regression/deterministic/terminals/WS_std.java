package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class WS_std extends AbstractWSNode {

	public String toString() {
		return "ws_std";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double ws_std = ((OccupancyClassification) problem).ws_std;
		DoubleData rd = ((DoubleData) (input));
		rd.x = ws_std;
	}
}
