package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class HumidityRatio_std extends AbstractHumidityRatioNode {

	public String toString() {
		return "hr_std";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double hr_std = ((OccupancyClassification) problem).hr_std;

		DoubleData rd = ((DoubleData) (input));
		rd.x = hr_std;
	}

}
