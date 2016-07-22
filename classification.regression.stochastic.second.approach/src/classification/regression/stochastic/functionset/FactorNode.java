package classification.regression.stochastic.functionset;

import java.text.DecimalFormat;

import classification.regression.stochastic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class FactorNode extends GPNode {

	protected double value = -1.0;

	public int expectedChildren() {
		// 17 + 4 + 4 = 25 nodes to calculate value
		return 25;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		// 17 bits to calculate the decimal part
		int decimal = 0;

		// 17 bits for decimal
		for (int i = 0; i < 17; i++) {
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				decimal += 1 << (i);
			}
		}

		// 4 bits to caluculate the float
		int floatpoint = 0;
		for (int i = 17; i < 21; i++) {
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				floatpoint += 1 << (i - 17);
			}
		}

		// 4 bits to calculate div factor
		int div_factor = 1;
		for (int i = 21; i < 25; i++) {
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				div_factor += 1 << (i - 21);
			}
		}

		// add decimal part
		value += (double) decimal;

		// get factor part
		double factor = floatpoint;

		// make factor less than 1 first
		while (factor >= 1) {
			factor /= 10;
		}

		// div by 10 until div_factor is 0
		while (div_factor > 0) {
			factor /= 10;
			div_factor--;
		}

		// this gives a double value that is able to represent any number in the
		// data set
		value += factor;

		DoubleData rd = ((DoubleData) (input));
		rd.x = value;
	}

	@Override
	public String makeCTree(boolean parentMadeParens, boolean printTerminalsAsVariables, boolean useOperatorForm) {
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(value) + "";
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(value);
	}
}
