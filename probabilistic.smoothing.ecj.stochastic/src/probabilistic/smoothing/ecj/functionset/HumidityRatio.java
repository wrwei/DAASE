package probabilistic.smoothing.ecj.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import probabilistic.smoothing.ecj.problem.OccupancyClassification;
import probabilistic.smoothing.ecj.utils.DoubleData;

public class HumidityRatio extends AbstractAttributeNode {

	public String toString() {
		return "hr";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double humidity = ((OccupancyClassification) problem).hr;

		children[0].eval(state, thread, input, stack, individual, problem);
		DoubleData rd = ((DoubleData) (input));
		double value = rd.x;

		rd.x = humidity * value;

	}
}
