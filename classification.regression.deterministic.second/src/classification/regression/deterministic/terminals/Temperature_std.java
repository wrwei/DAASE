package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Temperature_std extends AbstractTemperatureNode {

	public String toString() {
		return "temperature_std";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double temperature_std = ((OccupancyClassification) problem).temperature_std;

		DoubleData rd = ((DoubleData) (input));
		rd.x = temperature_std;
	}
}
