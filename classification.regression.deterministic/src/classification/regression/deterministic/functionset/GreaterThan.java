package classification.regression.deterministic.functionset;

import classification.regression.deterministic.terminals.AbstractAttributeNode;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.IllegalActivity;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class GreaterThan extends AbstractComparisonNode {

	public String toString() {
		return ">=";
	}

	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));

		AbstractAttributeNode leftNode = (AbstractAttributeNode) children[0];
		leftType = determineType(leftNode);
		
		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x;
		
		children[1].eval(state, thread, input, stack, individual, problem);

		AbstractFunctionNode rightNode = (AbstractFunctionNode) children[1];
		rightType = rightNode.leftType;

		if (checkIntegrity()) {
			functional_cost = Math.abs(result - rd.x);
			if (result >= rd.x) {
				rd.x = 1;
			}
			else
			{
				rd.x = 0;
			}	
		}
		else
		{
			IllegalActivity.getInstance().illegal();
			rd.x = 0;
		}
		
	}

}
