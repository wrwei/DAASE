package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class _unused_Cosine extends GPNode{

	@Override
	public void eval(final EvolutionState state,
	        final int thread,
	        final GPData input,
	        final ADFStack stack,
	        final GPIndividual individual,
	        final Problem problem) {
        double result;
        DoubleData rd = ((DoubleData)(input));

        children[0].eval(state,thread,input,stack,individual,problem);
        result = Math.cos(rd.x);
        rd.x = result;
        }
	
    public int expectedChildren() { return 1; }

	@Override
	public String toString() {
		return "cos";
	}

}
