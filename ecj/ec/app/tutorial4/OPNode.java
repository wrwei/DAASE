package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class OPNode extends GPNode {

	private static float BIAS = 1/Float.MAX_VALUE;
	private Operator opCode = Operator.INVALID;
	private boolean visited = false;
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(StochasticUtil.getOperator(10, 6, 9, 9));
		}
	}

	public String toString() {
		switch (opCode) {
		case ADD:
			return "+";
		case SUB:
			return "-";
		case MUL:
			return "*";
		case DIV:
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
		
		if (!visited) {
			visited = true;
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
				
				opCode = StochasticUtil.getInstance().getOperator(add, sub, mul, div);
				
				if (opCode == Operator.INVALID) {
					state.output.error("INVALID OP CODE " + toStringForError());
				}
			}
		}
		
		switch (opCode) {
		case ADD:
			children[0].eval(state,thread,input,stack,individual,problem);
	        result = rd.x;

	        children[1].eval(state,thread,input,stack,individual,problem);
	        rd.x = result + rd.x;
	        OperatorCounter.getInstance().incrementADD();

			break;
		
		case SUB:
			children[0].eval(state,thread,input,stack,individual,problem);
	        result = rd.x;

	        children[1].eval(state,thread,input,stack,individual,problem);
	        rd.x = result - rd.x;
	        OperatorCounter.getInstance().incrementSUB();
	        break;
	        
		case MUL:
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;

			children[1].eval(state, thread, input, stack, individual, problem);
			rd.x = result * rd.x;
	        OperatorCounter.getInstance().incrementMUL();

			break;
			
		case DIV:
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
//		super.resetNode(state, thread);
//	}
}
