
package probabilistic.smoothing.ecj.functionset;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import probabilistic.smoothing.ecj.utils.DoubleData;

public class LessThan extends AbstractNonTerminal {
	
	public String toString() {
		return "<";
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));
		
		calculateValue(state, thread, input, stack, individual, problem);
		
		children[22].eval(state, thread, input, stack, individual, problem);

		double data = rd.x;
		
		if (data < value) {
			children[1].eval(state, thread, input, stack, individual, problem);
		}
		else {
			children[0].eval(state, thread, input, stack, individual, problem);	
		}
	}
}

