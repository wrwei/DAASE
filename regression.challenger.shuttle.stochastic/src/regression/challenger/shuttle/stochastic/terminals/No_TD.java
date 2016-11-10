package regression.challenger.shuttle.stochastic.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import regression.challenger.shuttle.stochastic.problem.ChallengerShuttleORingRegression;
import regression.challenger.shuttle.stochastic.util.DoubleData;

public class No_TD extends AbstractParamNode{

	public String toString() {
		return "no_td";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double x = ((ChallengerShuttleORingRegression) problem).no_td;

		DoubleData rd = ((DoubleData) (input));
		rd.x = x;
	}

}
