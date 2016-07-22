
package classification.decision.stochastic.functionset;

public class GreaterThan extends AbstractNonTerminal {
	
	public String toString() {
		return ">";
	}

	@Override
	public boolean process() {
		return data > value;
	}

}

