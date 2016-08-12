package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class CO2_mean extends AbstractCO2Node{

	public String toString() {
		return "co2_mean";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double co2_mean = ((OccupancyClassification) problem).co2_mean;
		DoubleData rd = ((DoubleData) (input));
		rd.x = co2_mean;
	}
}
