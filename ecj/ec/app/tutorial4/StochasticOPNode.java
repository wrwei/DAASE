package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class StochasticOPNode extends GPNode {

	private static float BIAS = 1/Float.MAX_VALUE;
	private OperatorGenerator.OPERATORS op = null;
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(OperatorGenerator.getOperator());
		}
	}

	public String toString() {
		if (op == null) {
			return "op";
		}
		else {
			switch (op) {
			case ADD:
				return "+";
			case SUB:
				return "-";
			case MUL:
				return "*";
			default:
				return "/";
			}
		}
	}
	
	@Override
	public void checkConstraints(EvolutionState state, int tree, GPIndividual typicalIndividual,
			Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		if (children.length != 2)
			state.output.error("Incorrect number of children for node " + toStringForError() + " at " + individualBase);
	}


	public int expectedChildren() {
		return 2;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));
		
		op = OperatorGenerator.getOperator();
//		System.out.println("op is " + op);
		switch (op) {
		case ADD:
			children[0].eval(state,thread,input,stack,individual,problem);
	        result = rd.x;

	        children[1].eval(state,thread,input,stack,individual,problem);
	        rd.x = result + rd.x;
			break;
		
		case SUB:
			children[0].eval(state,thread,input,stack,individual,problem);
	        result = rd.x;

	        children[1].eval(state,thread,input,stack,individual,problem);
	        rd.x = result - rd.x;
	        break;
	        
		case MUL:
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;

			children[1].eval(state, thread, input, stack, individual, problem);
			rd.x = result * rd.x;
			break;
			
		default:
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;

			children[1].eval(state, thread, input, stack, individual, problem);
			if (rd.x == 0.0) {
				rd.x = result / BIAS; // create a bias large enough to give low
										// fitness score
				IllegalDivision.getInstance().illegal();
			} else {
				rd.x = result / rd.x;
			}
			break;
		}
	}

}
