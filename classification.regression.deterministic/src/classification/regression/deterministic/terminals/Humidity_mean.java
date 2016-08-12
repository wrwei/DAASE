package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Humidity_mean extends AbstractHumidityNode{

	public String toString() {
		return "humidity_mean";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double humidity_mean = ((OccupancyClassification) problem).humidity_mean;
		DoubleData rd = ((DoubleData) (input));
		rd.x = humidity_mean;
	}
}
