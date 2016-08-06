package symbolic.regression.stochastic.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Brick_F extends Abstract_Brick {
	
	protected boolean layout[][] = { 
	         {false,false,false,false,false},
	         {false,true,true,false,false},
	         {true,true,false,false,false},
	         {false,true,false,false,false},
	         {false,false,false,false,false}
	        };


	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
//		double result;
//
//		DoubleData rd = ((DoubleData) (input));
//
//		
//		//if this node is the root of a tree, calculate stochastic cost recursively once
//		if (parent instanceof GPTree) {
//			rd.stochastic_cost = calculateStochasticCost(state, thread, input, stack, individual, problem);
//		}
//		
//		calculateWeights(state, thread, input, stack, individual, problem);
//		opCode = StochasticUtil.getOperator(add_weight, sub_weight, mul_weight, div_weight);
//		
//		if (opCode == Operator.INVALID) {
//			state.output.error("INVALID OP CODE " + toStringForError());
//		}
//
//		switch (opCode) {
//		case ADD:
//			children[0].eval(state, thread, input, stack, individual, problem);
//			result = rd.x;
//
//			children[1].eval(state, thread, input, stack, individual, problem);
//			rd.x = result + rd.x;
//			break;
//
//		case SUB:
//			children[0].eval(state, thread, input, stack, individual, problem);
//			result = rd.x;
//
//			children[1].eval(state, thread, input, stack, individual, problem);
//			rd.x = result - rd.x;
//			break;
//
//		case MUL:
//			children[0].eval(state, thread, input, stack, individual, problem);
//			result = rd.x;
//
//			children[1].eval(state, thread, input, stack, individual, problem);
//			rd.x = result * rd.x;
//			break;
//
//		case DIV:
//			children[0].eval(state, thread, input, stack, individual, problem);
//			result = rd.x;
//
//			children[1].eval(state, thread, input, stack, individual, problem);
//			if (rd.x == 0.0) {
//				rd.x = 10000; // create a bias
//				IllegalDivision.getInstance().illegal();
//			} else {
//				rd.x = result / rd.x;
//			}
//			break;
//			
//		default:
//			state.output.error("Illegal Operator!!! " + toStringForError());
//			break;
//		}
	}

}
