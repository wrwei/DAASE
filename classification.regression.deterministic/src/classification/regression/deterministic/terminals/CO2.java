package classification.regression.deterministic.terminals;

import classification.regression.deterministic.problem.OccupancyClassification;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class CO2 extends AbstractAttributeNode {

	public String toString() {
		return "co2";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double co2 = ((OccupancyClassification) problem).co2;
		DoubleData rd = ((DoubleData) (input));
		rd.x = co2;
		ParamCounter.getInstance().addCount("co2");
	}
}
