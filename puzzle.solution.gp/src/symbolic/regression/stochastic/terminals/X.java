package symbolic.regression.stochastic.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import symbolic.regression.stochastic.problem.MultiValuedRegression;
import symbolic.regression.stochastic.util.DoubleData;

public class X extends GPNode {
	public String toString() {
		return "x";
	}

	/*
	 * public void checkConstraints(final EvolutionState state, final int tree,
	 * final GPIndividual typicalIndividual, final Parameter individualBase) {
	 * super.checkConstraints(state,tree,typicalIndividual,individualBase); if
	 * (children.length!=0) state.output.error(
	 * "Incorrect number of children for node " + toStringForError() + " at " +
	 * individualBase); }
	 */
	public int expectedChildren() {
		return 0;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		DoubleData rd = ((DoubleData) (input));
		rd.x = ((MultiValuedRegression) problem).currentX;
	}
}
