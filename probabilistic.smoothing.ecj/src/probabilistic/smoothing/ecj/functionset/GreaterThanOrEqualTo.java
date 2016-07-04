
package probabilistic.smoothing.ecj.functionset;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import probabilistic.smoothing.ecj.utils.DoubleData;

public class GreaterThanOrEqualTo extends AbstractNonTerminal {
	
	public String toString() {
		return ">=";
	}

	@Override
	public boolean process() {
		return data >= value;
	}

}

