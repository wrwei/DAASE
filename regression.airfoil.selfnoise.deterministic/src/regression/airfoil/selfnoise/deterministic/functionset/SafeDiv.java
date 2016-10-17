package regression.airfoil.selfnoise.deterministic.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import regression.airfoil.selfnoise.deterministic.util.DoubleData;
import regression.airfoil.selfnoise.deterministic.util.IllegalDivision;

public class SafeDiv extends GPNode {
	private static float BIAS = 1 / Float.MAX_VALUE;

	public String toString() {
		return "/";
	}

	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x;

		children[1].eval(state, thread, input, stack, individual, problem);
		if (rd.x == 0.0) {
			rd.x = result/BIAS; // create a bias large enough to give low
									// fitness score
			IllegalDivision.getInstance().illegal();
		} else {
			rd.x = result / rd.x;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(10 & 2);
	}
}
