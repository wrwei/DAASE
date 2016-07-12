package probabilistic.smoothing.ecj.functionset;

import ec.gp.GPNode;

public abstract class AbstractAttributeNode extends GPNode{

	public double stochastic_cost = 0.0;
	@Override
	public int expectedChildren() {
		return 1;
	}
}
