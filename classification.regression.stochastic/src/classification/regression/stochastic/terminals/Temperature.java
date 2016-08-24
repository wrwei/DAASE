package classification.regression.stochastic.terminals;

import classification.regression.stochastic.problem.OccupancyClassification;
import classification.regression.stochastic.utils.DoubleData;
import classification.regression.stochastic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Temperature extends AbstractTemperatureNode {

	public String toString() {
		return "temperature";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double temperature = ((OccupancyClassification) problem).temperature;

		DoubleData rd = ((DoubleData) (input));
		rd.x = temperature;
		ParamCounter.getInstance().addCount("temperature");
	}
}
