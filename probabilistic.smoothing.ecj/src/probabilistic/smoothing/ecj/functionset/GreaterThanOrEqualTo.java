
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

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));
		
		calculateValue(state, thread, input, stack, individual, problem);
		
		children[22].eval(state, thread, input, stack, individual, problem);

		data = rd.x;
		
		boolean primitiveNode = false;
		
		if (data >= value) {
			if (!(children[1] instanceof AbstractNonTerminal)) {
				primitiveNode = true;
				rd.x = 1;
			}
			else
			{
				children[1].eval(state, thread, input, stack, individual, problem);	
			}
		}
		else {
			if (!(children[0] instanceof AbstractNonTerminal)) {
				if (primitiveNode) {
					if (parent instanceof GPTree) {
						rd.x = 100000;
					}
					else
					{
						rd.x = 0;	
					}
				}
				else
				{
					rd.x = 0;	
				}
			}
			else
			{
				children[0].eval(state, thread, input, stack, individual, problem);	
			}	
		}
	}
}

