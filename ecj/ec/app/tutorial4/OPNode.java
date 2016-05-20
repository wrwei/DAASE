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
	private double stochasticity = 0.0;
	private Operator stocasticityIndex = Operator.INVALID;
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
				
				stochasticity = calculateStochasticity(add, sub, mul, div);
				
				System.out.println(opCode + " - " + stocasticityIndex);
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
				rd.x = result / BIAS; // create a bias large enough to give low enough
				IllegalDivision.getInstance().illegal();
			} else {
				rd.x = result / rd.x;
			}
	        OperatorCounter.getInstance().incrementDIV();
			break;
		default:
			state.output.error("Illegal Operator!!! " + toStringForError());
			break;
		}
		
	}

	public String makeCTree(boolean parentMadeParens, boolean printTerminalsAsVariables, boolean useOperatorForm)
    {
		return (parentMadeParens ? "" : "(") + 
                children[0].makeCTree(false, printTerminalsAsVariables, useOperatorForm) + " " + 
                toStringForHumans() + " " + children[1].makeCTree(false, printTerminalsAsVariables, useOperatorForm) + 
                (parentMadeParens ? "" : ")");
    }
	
	public synchronized double calculateStochasticity(int w1, int w2, int w3, int w4)
	{
		double p1 = 0.0;
		double p2 = 0.0;
		double p3 = 0.0;
		double p4 = 0.0;
		
		int weight_sum = w1 + w2 + w3 + w4;
		if (weight_sum == 0.0) {
			stocasticityIndex = StochasticUtil.getInstance().getOperator(w1, w2, w3, w4);
			return 0.25;
		}
		
		p1 = w1/weight_sum;
		p2 = w2/weight_sum;
		p3 = w3/weight_sum;
		p4 = w4/weight_sum;
		
		double min = Double.MAX_VALUE;
		double temp1 = Math.abs(p1-1) + p2 + p3 + p4;
		double temp2 = Math.abs(p2-1) + p1 + p3 + p4;
		double temp3 = Math.abs(p3-1) + p2 + p1 + p4;
		double temp4 = Math.abs(p4-1) + p2 + p3 + p1;
		if (min > temp1) {
			min = temp1;
			stocasticityIndex = Operator.ADD;
		}
		if (min > temp2) {
			min = temp2;
			stocasticityIndex = Operator.SUB;
		}
		if (min > temp3) {
			min = temp3;
			stocasticityIndex = Operator.MUL;
		}
		if (min > temp4) {
			min = temp4;
			stocasticityIndex = Operator.DIV;
		}
		return min;
	}
	
}
