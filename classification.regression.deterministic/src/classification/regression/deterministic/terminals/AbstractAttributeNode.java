package classification.regression.deterministic.terminals;

import ec.gp.GPNode;

public abstract class AbstractAttributeNode extends GPNode{

	public double stochastic_cost = 0.0;
	
	@Override
	public int expectedChildren() {
		return 0;
	}
}
