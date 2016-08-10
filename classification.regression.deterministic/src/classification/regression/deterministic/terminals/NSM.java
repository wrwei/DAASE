package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class NSM extends AbstractAttributeNode {

	public String toString() {
		return "nsm";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double nsm = ((OccupancyClassification) problem).nsm;

		DoubleData rd = ((DoubleData) (input));
		rd.x = nsm;
		ParamCounter.getInstance().addCount("nsm");
	}
}
