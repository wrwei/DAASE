package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class HumidityRatio extends AbstractAttributeNode {

	public String toString() {
		return "hr";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double hr = ((OccupancyClassification) problem).hr;

		DoubleData rd = ((DoubleData) (input));
		rd.x = hr;
		ParamCounter.getInstance().addCount("hr");
	}
}
