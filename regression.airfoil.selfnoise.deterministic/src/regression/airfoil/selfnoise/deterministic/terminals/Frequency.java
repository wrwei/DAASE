package regression.airfoil.selfnoise.deterministic.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import regression.airfoil.selfnoise.deterministic.problem.AirfoilSelfNoiseRegression;
import regression.airfoil.selfnoise.deterministic.util.DoubleData;

public class Frequency extends AbstractParamNode{

	public String toString() {
		return "frequency";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double x = ((AirfoilSelfNoiseRegression) problem).frequency;

		DoubleData rd = ((DoubleData) (input));
		rd.x = x;
	}

}
