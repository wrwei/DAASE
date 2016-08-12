package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class HumidityRatio_mean extends AbstractHumidityRatioNode {

	public String toString() {
		return "hr_mean";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double hr_mean = ((OccupancyClassification) problem).hr_mean;

		DoubleData rd = ((DoubleData) (input));
		rd.x = hr_mean;
	}

}
