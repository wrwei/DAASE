package regression.bike.sharing.deterministic.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import regression.bike.sharing.deterministic.problem.BikeSharingRegression;
import regression.bike.sharing.deterministic.util.DoubleData;

public class Weekday extends AbstractParamNode{

	public String toString() {
		return "weekday";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double x = ((BikeSharingRegression) problem).weekday;

		DoubleData rd = ((DoubleData) (input));
		rd.x = x;
	}

}
