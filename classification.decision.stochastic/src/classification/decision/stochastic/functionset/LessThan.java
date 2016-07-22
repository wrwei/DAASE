
package classification.decision.stochastic.functionset;

public class LessThan extends AbstractNonTerminal {
	
	public String toString() {
		return "<";
	}

	@Override
	public boolean process() {
		return data < value;
	}

}

