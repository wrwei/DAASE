package ec.app.tutorial4;

import java.text.DecimalFormat;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class OPNode2 extends GPNode {

	// bias for illegal division
	private static float BIAS = 1 / Float.MAX_VALUE;

	// op code of this node, default is INVALID
	private Operator opCode = Operator.INVALID;

	// flag determining if this node has been visited
	private boolean visited = false;

	private double add_stochastic_cost = 0.0;
	private double sub_stochastic_cost = 0.0;
	private double mul_stochastic_cost = 0.0;
	private double div_stochastic_cost = 0.0;
	
	//weights
	private int add_weight = 0;
	private int sub_weight = 0;
	private int mul_weight = 0;
	private int div_weight = 0;

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

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	@Override
	public void checkConstraints(EvolutionState state, int tree, GPIndividual typicalIndividual,
			Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		if (children.length != 12)
			state.output.error("Incorrect number of children for node " + toStringForError() + " at " + individualBase);
	}

	public int expectedChildren() {
		return 12;
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {
		double result;

		DoubleData rd = ((DoubleData) (input));

		if (!visited) {
			visited = true;
			//calculate stochastic_cost recursively
			double stochastic_cost = calculateStochasticCost(state, thread, input, stack, individual, problem);
			//return stochastic cost to the input
			rd.stochastic_cost = stochastic_cost;
		}
		
		opCode = StochasticUtil.getOperator(add_weight, sub_weight, mul_weight, div_weight);
		//System.out.println(this.makeCTree(true, true, true));
		if (opCode == Operator.INVALID) {
			state.output.error("INVALID OP CODE " + toStringForError());
		}

		switch (opCode) {
		case ADD:
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;

			children[1].eval(state, thread, input, stack, individual, problem);
			rd.x = result + rd.x;
			OperatorCounter.getInstance().incrementADD();

			break;

		case SUB:
			children[0].eval(state, thread, input, stack, individual, problem);
			result = rd.x;

			children[1].eval(state, thread, input, stack, individual, problem);
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
										// enough
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

	public String makeCTree(boolean parentMadeParens, boolean printTerminalsAsVariables, boolean useOperatorForm) {
		DecimalFormat df = new DecimalFormat("###.##");
		double total_weight = add_weight + sub_weight + mul_weight + div_weight;
		return (parentMadeParens ? "" : "(") + children[0].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
				+ " " + toStringForHumans() + " "
				+ children[1].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
				+ (parentMadeParens ? "" : ")") + "(" + df.format(add_weight/total_weight) + ", "
				+ df.format(sub_weight/total_weight) + ", " + df.format(mul_weight/total_weight) + ", "
				+ df.format(div_weight/total_weight) + ")";
	}
	
	public synchronized double calculateStochasticCost(final EvolutionState state, final int thread,
			final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem) {
		
		double stochastic_sum = 0.0;
		
		//probabilities
		double p1 = 0.0;
		double p2 = 0.0;
		double p3 = 0.0;
		double p4 = 0.0;

		//populate weights
		for (int i = 2; i < 12; i++) {
			children[i].eval(state, thread, input, stack, individual, problem);
			DoubleData rd = ((DoubleData) (input));
			
			int w = (int) rd.x;
			if ((w&1) == 1) {
				add_weight++;
			}
			if ((w&2) == 2) {
				sub_weight++;
			}
			if ((w&4) == 4) {
				mul_weight++;
			}
			if ((w&8) == 8) {
				div_weight++;
			}
		}
		
		//get weight sum
		double weight_sum = add_weight + sub_weight + mul_weight + div_weight;
		//if sum is 0, set stochastic costs to 0.25 (equal probabilities) and return
		if (weight_sum == 0.0) {
			return 0.0;
		}

		//calculate probabilities
		p1 = add_weight / weight_sum;
		p2 = sub_weight / weight_sum;
		p3 = mul_weight / weight_sum;
		p4 = div_weight / weight_sum;

		//
		
		//calculating distance between vectors
		
//		add_stochastic_cost = Math.sqrt((p1-1)*(p1-1) + p2*p2 + p3*p3 + p4*p4);
//		sub_stochastic_cost = Math.sqrt((p2-1)*(p2-1) + p1*p1 + p3*p3 + p4*p4);
//		mul_stochastic_cost = Math.sqrt((p3-1)*(p3-1) + p2*p2 + p1*p1 + p4*p4);
//		div_stochastic_cost = Math.sqrt((p4-1)*(p4-1) + p2*p2 + p3*p3 + p1*p1);
		
		add_stochastic_cost = p1;
		sub_stochastic_cost = p2;
		mul_stochastic_cost = p3;
		div_stochastic_cost = p4;
		
		if (children[0] instanceof OPNode2) {
			OPNode2 temp = (OPNode2) children[0];
			//set visited first
			temp.setVisited(true);
			//calculate stochastic cost recursively
			double res = temp.calculateStochasticCost(state, thread, input, stack, individual, problem);
			stochastic_sum += res;
		}

		if (children[1] instanceof OPNode2) {
			OPNode2 temp = (OPNode2) children[1];
			//set visited first
			temp.setVisited(true);
			//calculate stochastic cost recursively
			double res = temp.calculateStochasticCost(state, thread, input, stack, individual, problem);
			stochastic_sum += res;
		}
		
		double max = Double.MIN_VALUE;

		if (max < add_stochastic_cost) {
			max = add_stochastic_cost;
		}
		if (max < sub_stochastic_cost) {
			max = sub_stochastic_cost;
		}
		if (max < mul_stochastic_cost) {
			max = mul_stochastic_cost;
		}
		if (max < div_stochastic_cost) {
			max = div_stochastic_cost;
		}
		double result = stochastic_sum + (1 - max);
		return result;
	}

}
