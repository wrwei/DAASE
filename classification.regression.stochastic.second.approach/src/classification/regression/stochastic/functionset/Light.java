package classification.regression.stochastic.functionset;

import classification.regression.stochastic.problem.OccupancyClassification;
import classification.regression.stochastic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class Light extends AbstractAttributeNode {

	public String toString() {
		return "light";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double humidity = ((OccupancyClassification) problem).light;

		children[0].eval(state, thread, input, stack, individual, problem);
		DoubleData rd = ((DoubleData) (input));
		double value = rd.x;

		rd.x = humidity * value;

	}

}
