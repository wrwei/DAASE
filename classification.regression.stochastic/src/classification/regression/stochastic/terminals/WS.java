package classification.regression.stochastic.terminals;

import classification.regression.stochastic.problem.OccupancyClassification;
import classification.regression.stochastic.utils.DoubleData;
import classification.regression.stochastic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class WS extends AbstractWSNode {

	public String toString() {
		return "ws";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double ws = ((OccupancyClassification) problem).ws;
		DoubleData rd = ((DoubleData) (input));
		rd.x = ws;
		ParamCounter.getInstance().addCount("ws");
	}
}
