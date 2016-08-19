package classification.regression.deterministic.functionset;

import classification.regression.deterministic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class And extends AbstractFunctionNode{

	public String toString() {
		return "and";
	}

	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		DoubleData rd = ((DoubleData) (input));

		int l = 0;
		int r = 0;
		children[0].eval(state, thread, input, stack, individual, problem);
//		if (children[0] instanceof AbstractComparisonNode) {
//			functional_cost += ((AbstractComparisonNode)children[0]).functional_cost;
//		}
//		if (children[0] instanceof AbstractFunctionNode) {
//			functional_cost += ((AbstractFunctionNode)children[0]).functional_cost;
//		}
		l = (int) rd.x;

//		children[1].eval(state, thread, input, stack, individual, problem);
//		if (children[1] instanceof AbstractComparisonNode) {
//			functional_cost += ((AbstractComparisonNode)children[1]).functional_cost;
//		}
//		if (children[1] instanceof AbstractFunctionNode) {
//			functional_cost += ((AbstractFunctionNode)children[1]).functional_cost;
//		}
		r = (int) rd.x;
		
		rd.x = l & r;
	}

}
