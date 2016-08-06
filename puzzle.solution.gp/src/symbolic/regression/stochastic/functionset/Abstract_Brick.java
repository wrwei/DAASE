package symbolic.regression.stochastic.functionset;

import ec.EvolutionState;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public abstract class Abstract_Brick extends GPNode {

	// bias for illegal division
	private static float BIAS = 1 / Float.MAX_VALUE;
	
	private String id;
	
	private int x;
	private int y;
	private int orientation;
	

	public String toString() {
		return id + " " + x + "-" + y + " :" + orientation;
	}

	@Override
	public void checkConstraints(EvolutionState state, int tree, GPIndividual typicalIndividual,
			Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		if (children.length != 4)
			state.output.error("Incorrect number of children for node " + toStringForError() + " at " + individualBase);
	}

	public int expectedChildren() {
		return 4;
	}

//	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
//			final GPIndividual individual, final Problem problem) {
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
//	}

//	public String makeCTree(boolean parentMadeParens, boolean printTerminalsAsVariables, boolean useOperatorForm) {
//		DecimalFormat df = new DecimalFormat("###.##");
//		double total_weight = add_weight + sub_weight + mul_weight + div_weight;
//		return (parentMadeParens ? "" : "(") + children[0].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
//				+ " " + toStringForHumans() + " "
//				+ children[1].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
//				+ (parentMadeParens ? "" : ")") + "(" + df.format(add_weight/total_weight) + ", "
//				+ df.format(sub_weight/total_weight) + ", " + df.format(mul_weight/total_weight) + ", "
//				+ df.format(div_weight/total_weight) + " |" + df.format(overall_stochastic_cost) + ")";
//	}
	
	
//	public synchronized void calculateWeights(final EvolutionState state, final int thread,
//			final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem)
//	{
//		add_weight = 0;
//		sub_weight = 0;
//		mul_weight = 0;
//		div_weight = 0;
//
//		//populate weights
//		for (int i = 2; i < 12; i++) {
//			DoubleData rd = ((DoubleData) (input));
//
//			children[i].eval(state, thread, input, stack, individual, problem);
//			
//			int w = (int) rd.x;
//			if ((w&1) == 1) {
//				add_weight++;
//			}
//			if ((w&2) == 2) {
//				sub_weight++;
//			}
//			if ((w&4) == 4) {
//				mul_weight++;
//			}
//			if ((w&8) == 8) {
//				div_weight++;
//			}
//		}
//	}
//	
//	public synchronized double calculateStochasticCost(final EvolutionState state, final int thread,
//			final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem) {
//		double stochastic_sum = 0.0;
//
//			double add_stochastic_cost = 0.0;
//			double sub_stochastic_cost = 0.0;
//			double mul_stochastic_cost = 0.0;
//			double div_stochastic_cost = 0.0;
//
//			add_weight = 0;
//			sub_weight = 0;
//			mul_weight = 0;
//			div_weight = 0;
//			
//			//probabilities
//			double p1 = 0.0;
//			double p2 = 0.0;
//			double p3 = 0.0;
//			double p4 = 0.0;
//
//			//populate weights
//			for (int i = 2; i < 12; i++) {
//				children[i].eval(state, thread, input, stack, individual, problem);
//				DoubleData rd = ((DoubleData) (input));
//				
//				int w = (int) rd.x;
//				if ((w&1) == 1) {
//					add_weight++;
//				}
//				if ((w&2) == 2) {
//					sub_weight++;
//				}
//				if ((w&4) == 4) {
//					mul_weight++;
//				}
//				if ((w&8) == 8) {
//					div_weight++;
//				}
//			}
//			
//			//get weight sum
//			double weight_sum = add_weight + sub_weight + mul_weight + div_weight;
//			
//			//if sum is 0, set stochastic costs to 0.25 (equal probabilities) and return
//			if (weight_sum == 0.0) {
//				p1 = 0.25;
//				p2 = 0.25;
//				p3 = 0.25;
//				p4 = 0.25;
//			}
//			else
//			{
//				//calculate probabilities
//				p1 = add_weight / weight_sum;
//				p2 = sub_weight / weight_sum;
//				p3 = mul_weight / weight_sum;
//				p4 = div_weight / weight_sum;
//			}
//
//			add_stochastic_cost = p1;
//			sub_stochastic_cost = p2;
//			mul_stochastic_cost = p3;
//			div_stochastic_cost = p4;
//			
//			double max = Double.MIN_VALUE;
//
//			if (max < add_stochastic_cost) {
//				max = add_stochastic_cost;
//			}
//			if (max < sub_stochastic_cost) {
//				max = sub_stochastic_cost;
//			}
//			if (max < mul_stochastic_cost) {
//				max = mul_stochastic_cost;
//			}
//			if (max < div_stochastic_cost) {
//				max = div_stochastic_cost;
//			}
//
//			if (children[0] instanceof OPNode) {
//				OPNode temp = (OPNode) children[0];
//				double res = temp.calculateStochasticCost(state, thread, input, stack, individual, problem);
//				stochastic_sum += res;
//			}
//
//			if (children[1] instanceof OPNode) {
//				OPNode temp = (OPNode) children[1];
//				double res = temp.calculateStochasticCost(state, thread, input, stack, individual, problem);
//				stochastic_sum += res;
//			}
//			node_stochastic_cost = 1 - max;
//			double result = stochastic_sum + node_stochastic_cost;
//
//			overall_stochastic_cost = result;
//			return result;
//	}
	

	

}
