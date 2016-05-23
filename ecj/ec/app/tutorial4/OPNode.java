package ec.app.tutorial4;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class OPNode extends GPNode {

	//bias for illegal division
	private static float BIAS = 1/Float.MAX_VALUE;
	
	//op code of this node, default is INVALID
	private Operator opCode = Operator.INVALID;
	
	//stochasticity of the selected op
	private double stochasticity = 0.0;
	//selected op based on stochastic costs
	private Operator stochasticityIndex = Operator.INVALID;
	
	//flag determining if this node has been visited
	private boolean visited = false;
	
	private double add_stochastic_cost = 0.0;
	private double sub_stochastic_cost = 0.0;
	private double mul_stochastic_cost = 0.0;
	private double div_stochastic_cost = 0.0;
	
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
		
		visited = true;
		calculateStochasticCosts(state, thread, input, stack, individual, problem);
		opCode = stochasticityIndex;
		
//		System.out.println(stochasticityIndex + " - " + stochasticity);
		if (opCode == Operator.INVALID) {
			state.output.error("INVALID OP CODE " + toStringForError());
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
	
	public synchronized double[] calculateStochasticCosts(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem)
	{
		if (!visited) {
			double p1 = 0.0;
			double p2 = 0.0;
			double p3 = 0.0;
			double p4 = 0.0;
			
			IntegerData id = new IntegerData();
			
			children[2].eval(state,thread,id,stack,individual,problem);
			int w1 = (int) id.x;
			
			children[3].eval(state,thread,id,stack,individual,problem);
			int w2 = (int) id.x;
			
			children[4].eval(state,thread,id,stack,individual,problem);
			int w3 = (int) id.x;
			
			children[5].eval(state,thread,id,stack,individual,problem);
			int w4 = (int) id.x;
			
			double weight_sum = w1 + w2 + w3 + w4;
			if (weight_sum == 0.0) {
				stochasticityIndex = StochasticUtil.getInstance().getOperator(w1, w2, w3, w4);
				add_stochastic_cost = 0.25;
				sub_stochastic_cost = 0.25;
				mul_stochastic_cost = 0.25;
				div_stochastic_cost = 0.25;
				double[] result = {add_stochastic_cost, sub_stochastic_cost, mul_stochastic_cost, div_stochastic_cost};
				return result;
			}
			
			p1 = w1/weight_sum;
			p2 = w2/weight_sum;
			p3 = w3/weight_sum;
			p4 = w4/weight_sum;
			
			double min = Double.MAX_VALUE;
			add_stochastic_cost = Math.abs(p1-1) + p2 + p3 + p4;
			sub_stochastic_cost = Math.abs(p2-1) + p1 + p3 + p4;
			mul_stochastic_cost = Math.abs(p3-1) + p2 + p1 + p4;
			div_stochastic_cost = Math.abs(p4-1) + p2 + p3 + p1;
			
			if (min > add_stochastic_cost) {
				min = add_stochastic_cost;
				stochasticityIndex = Operator.ADD;
			}
			if (min > sub_stochastic_cost) {
				min = sub_stochastic_cost;
				stochasticityIndex = Operator.SUB;
			}
			if (min > mul_stochastic_cost) {
				min = mul_stochastic_cost;
				stochasticityIndex = Operator.MUL;
			}
			if (min > div_stochastic_cost) {
				min = div_stochastic_cost;
				stochasticityIndex = Operator.DIV;
			}
			stochasticity = min;
			double[] result = {add_stochastic_cost, sub_stochastic_cost, mul_stochastic_cost, div_stochastic_cost};
			return result;
		}
		else {
			double p1 = 0.0;
			double p2 = 0.0;
			double p3 = 0.0;
			double p4 = 0.0;
			
			IntegerData id = new IntegerData();
			
			children[2].eval(state,thread,id,stack,individual,problem);
			int w1 = (int) id.x;
			
			children[3].eval(state,thread,id,stack,individual,problem);
			int w2 = (int) id.x;
			
			children[4].eval(state,thread,id,stack,individual,problem);
			int w3 = (int) id.x;
			
			children[5].eval(state,thread,id,stack,individual,problem);
			int w4 = (int) id.x;
			
			double weight_sum = w1 + w2 + w3 + w4;
			if (weight_sum == 0.0) {
				double[] result = {add_stochastic_cost, sub_stochastic_cost, mul_stochastic_cost, div_stochastic_cost};
				return result;
			}
			
			p1 = w1/weight_sum;
			p2 = w2/weight_sum;
			p3 = w3/weight_sum;
			p4 = w4/weight_sum;
			
			double min = Double.MAX_VALUE;
			add_stochastic_cost = ((Math.abs(p1-1) + p2 + p3 + p4) + add_stochastic_cost)/2;
			sub_stochastic_cost = ((Math.abs(p2-1) + p1 + p3 + p4) + sub_stochastic_cost)/2;
			mul_stochastic_cost = ((Math.abs(p3-1) + p2 + p1 + p4) + mul_stochastic_cost)/2;
			div_stochastic_cost = ((Math.abs(p4-1) + p2 + p3 + p1) + div_stochastic_cost)/2;
			
			if (children[0] instanceof OPNode) {
				OPNode temp = (OPNode) children[0];
				double[] res = temp.calculateStochasticCosts(state, thread, input, stack, individual, problem);
				add_stochastic_cost = ((res[0]) + add_stochastic_cost)/2;
				sub_stochastic_cost = ((res[1]) + sub_stochastic_cost)/2;
				mul_stochastic_cost = ((res[2]) + mul_stochastic_cost)/2;
				div_stochastic_cost = ((res[3]) + div_stochastic_cost)/2;
			}
			
			if (children[1] instanceof OPNode) {
				OPNode temp = (OPNode) children[1];
				double[] res = temp.calculateStochasticCosts(state, thread, input, stack, individual, problem);
				add_stochastic_cost = ((res[0]) + add_stochastic_cost)/2;
				sub_stochastic_cost = ((res[1]) + sub_stochastic_cost)/2;
				mul_stochastic_cost = ((res[2]) + mul_stochastic_cost)/2;
				div_stochastic_cost = ((res[3]) + div_stochastic_cost)/2;
			}
		
			if (min > add_stochastic_cost) {
				min = add_stochastic_cost;
				stochasticityIndex = Operator.ADD;
			}
			if (min > sub_stochastic_cost) {
				min = sub_stochastic_cost;
				stochasticityIndex = Operator.SUB;
			}
			if (min > mul_stochastic_cost) {
				min = mul_stochastic_cost;
				stochasticityIndex = Operator.MUL;
			}
			if (min > div_stochastic_cost) {
				min = div_stochastic_cost;
				stochasticityIndex = Operator.DIV;
			}
			stochasticity = min;
			double[] result = {add_stochastic_cost, sub_stochastic_cost, mul_stochastic_cost, div_stochastic_cost};
			return result;
		}
		
	}
	
}
