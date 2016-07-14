
package probabilistic.smoothing.ecj.functionset;

public class GreaterThanOrEqualTo extends AbstractNonTerminal {
	
	public String toString() {
		return ">=";
	}

	@Override
	public boolean process() {
		return data >= value;
	}

}

