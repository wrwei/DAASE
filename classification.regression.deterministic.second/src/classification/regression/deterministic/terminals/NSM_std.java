package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class NSM_std extends AbstractNSMNode{

	public String toString() {
		return "nsm_std";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double nsm_std = ((OccupancyClassification) problem).nsm_std;

		DoubleData rd = ((DoubleData) (input));
		rd.x = nsm_std;
	}

}
