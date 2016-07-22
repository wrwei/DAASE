
package classification.decision.stochastic.functionset;

public class LessThanOrEqualTo extends AbstractNonTerminal {
	
	public String toString() {
		return "<=";
	}

	@Override
	public boolean process() {
		return data <= value;
	}

}

