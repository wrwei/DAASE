package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class _unused_Cube extends GPNode{

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
        result = rd.x * rd.x * rd.x;
        rd.x = result;
        }
	
	@Override
	public void checkConstraints(EvolutionState state, int tree, GPIndividual typicalIndividual,
			Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		if (children.length != 1) {
			state.output.error("Cube operator should have one operand");
		}
	}
	
    public int expectedChildren() { return 1; }

	@Override
	public String toString() {
		return "^3";
	}

}
