package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.MersenneTwister;
import ec.util.Parameter;

public class OPNode extends GPNode {

	private static float BIAS = 1/Float.MAX_VALUE;
	private int opCode = -1;
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(OperatorGenerator.getOperator());
		}
	}

	public String toString() {
		switch (opCode) {
		case 0:
			return "+";
		case 1:
			return "-";
		case 2:
			return "*";
		case 3:
			return "/";
		default:
			return "invalid";
		}
	}
	
	@Override
	public void checkConstraints(EvolutionState state, int tree, GPIndividual typicalIndividual,
			Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		if (children.length != 6)
			state.output.error("Incorrect number of children for node " + toStringForError() + " at " + individualBase);
	}


	public int expectedChildren() {
		return 6;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;
		int add;
		int sub;
		int mul;
		int div;
		
		DoubleData rd = ((DoubleData) (input));
		synchronized (this) {
			
			IntegerData id = new IntegerData();
			
			children[2].eval(state,thread,id,stack,individual,problem);
			add = (int) id.x;
			
			children[3].eval(state,thread,id,stack,individual,problem);
			sub = (int) id.x;
			
			children[4].eval(state,thread,id,stack,individual,problem);
			mul = (int) id.x;
			
			children[5].eval(state,thread,id,stack,individual,problem);
			div = (int) id.x;
			
			opCode = getOperator(add, sub, mul, div);
			
			if (opCode >=0 && opCode <=3) {
				
			}
			else {
				state.output.error("INVALID OP CODE " + toStringForError());
			}
			
		}
		switch (opCode) {
		case 0:
			children[0].eval(state,thread,input,stack,individual,problem);
	        result = rd.x;

	        children[1].eval(state,thread,input,stack,individual,problem);
	        rd.x = result + rd.x;
	        OperatorCounter.getInstance().incrementADD();

			break;
		
		case 1:
			children[0].eval(state,thread,input,stack,individual,problem);
	        result = rd.x;

	        children[1].eval(state,thread,input,stack,individual,problem);
	        rd.x = result - rd.x;
	        OperatorCounter.getInstance().incrementSUB();
	        break;
	        
		case 2:
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;

			children[1].eval(state, thread, input, stack, individual, problem);
			rd.x = result * rd.x;
	        OperatorCounter.getInstance().incrementMUL();

			break;
			
		case 3:
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
	        OperatorCounter.getInstance().incrementDIV();
			break;
		}
	}
	
	public int getOperator(int WEIGHT_ADD, int WEIGHT_SUB, int WEIGHT_MUL, int WEIGHT_DIV)
	{
		MersenneTwister rand = new MersenneTwister(4357);
		int total_weight = WEIGHT_ADD + WEIGHT_SUB + WEIGHT_MUL + WEIGHT_DIV;
		int median = WEIGHT_ADD + WEIGHT_SUB;
		int q3 = WEIGHT_ADD + WEIGHT_SUB + WEIGHT_MUL;
		
		int num = rand.nextInt(total_weight);
		if (num < median) {
			num = rand.nextInt(median);
			if (num < WEIGHT_ADD) {
				return 0;
			}
			else
			{
				return 1;
			}
		}
		else {
			if (num < q3) {
				return 2;
			}
			else {
				return 3;
			}
		}
	}
	
//	@Override
//	public GPNode lightClone() {
//		generateOP();
//		return super.lightClone();
//	}
//	
//	@Override
//	public Object clone() {
//		op = OperatorGenerator.getOperator();
//		for(GPNode node: children)
//		{
//			if (node instanceof StochasticOPNode) {
//				((StochasticOPNode)node).generateOP();
//			}
//		}
//		return super.clone();
//	}
	
//	@Override
//	public void resetNode(EvolutionState state, int thread) {
//		generateOP();
//		super.resetNode(state, thread);
//	}
}
