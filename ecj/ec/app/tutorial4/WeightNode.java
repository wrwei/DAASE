package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.MersenneTwister;

public class WeightNode extends GPNode {

	private int x;

	@Override
	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		IntegerData rd = ((IntegerData) (input));

		// right now we just randomise
		//MersenneTwister rand = new MersenneTwister(4357);
		x = state.random[0].nextInt(3);
		//x = rand.nextInt(5);
		rd.x = x;
	}

	@Override
	public String toString() {
		return x + "";
	}

}
