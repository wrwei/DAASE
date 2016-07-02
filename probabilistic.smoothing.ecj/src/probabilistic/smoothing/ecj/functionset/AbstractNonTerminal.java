package probabilistic.smoothing.ecj.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import probabilistic.smoothing.ecj.utils.DoubleData;
import probabilistic.smoothing.ecj.utils.EvaluationObserver;
import probabilistic.smoothing.ecj.utils.ParamCounter;

public abstract class AbstractNonTerminal extends GPNode {

	protected double value = -1.0;
	protected double data = -1.0;
	protected boolean calc_value = false;
	
	public int expectedChildren() {
		//2 nodes for subtree
		//20 nodes to calculate value
		//1 node to store data
		return 23;
	}
	
	public abstract boolean process();

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {

		DoubleData rd = ((DoubleData) (input));
		
		//calculate the values of each node only once
		if (parent instanceof GPTree) {
			calculateValue(state, thread, input, stack, individual, problem);	
		}
		
		if (process()) {
			//if child 1 is a terminal
			if (!(children[1] instanceof AbstractNonTerminal)) {
				
				//if should not eval, do nothing
				if (EvaluationObserver.getInstance().shouldNotEval()) {
						
				}
				//else set primitive node, set tree val to 1, signal do not eval
				else
				{
					rd.x = 1;
					EvaluationObserver.getInstance().doNotEval();
				}
				
				//if child 0 is a terminal
				if (!(children[0] instanceof AbstractNonTerminal)) {
					//if parent is GPTree, it means this tree is useless, give high fitness
					if (parent instanceof GPTree) {
						rd.x = 100000;
						EvaluationObserver.getInstance().doNotEval();
					}
				}
			}
			else
			{
				children[1].eval(state, thread, input, stack, individual, problem);	
			}
		}
		else {
			if (!(children[0] instanceof AbstractNonTerminal)) {
				//if should not eval, do nothing
				if (EvaluationObserver.getInstance().shouldNotEval()) {
						
				}
				//else set primitive node, set tree val to 0, signal do not eval
				else
				{
					rd.x = 0;
					EvaluationObserver.getInstance().doNotEval();
				}
				
				//if child 0 is a terminal
				if (!(children[1] instanceof AbstractNonTerminal)) {
					//if parent is GPTree, it means this tree is useless, give high fitness
					if (parent instanceof GPTree) {
						rd.x = 100000;
						EvaluationObserver.getInstance().doNotEval();
					}
				}
			}
			else
			{
				children[0].eval(state, thread, input, stack, individual, problem);	
			}
		}
	}
	
	public synchronized void calculateValue(final EvolutionState state, final int thread,
			final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem)
	{
		calc_value = true;
		//12 bits to calculate the decimal part
		int decimal = 0;
		//get decimal
		for (int i = 2; i < 14; i++) {
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				decimal += 1 << (i-2);
			}
		}
		
		//4 bits to caluculate the float
		int floatpoint = 0;
		for(int i = 14; i < 18; i++)
		{
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				floatpoint += 1 << (i-14);
			}
		}
		
		//4 bits to calculate div factor
		int div_factor = 1;
		for(int i = 18; i < 22; i++)
		{
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				div_factor += 1 << (i-18);
			}
		}
		
		//add decimal part
		value += decimal;
		
		//get factor part
		double factor = floatpoint;
		
		//make factor less than 1 first
		while(factor >= 1)
		{
			factor /= 10;
		}
		
		//div by 10 until div_factor is 0
		while(div_factor > 0)
		{
			factor /= 10;
			div_factor--;
		}
		
		//this gives a double value that is able to represent any number in the data set
		value += factor;
		
		children[22].eval(state, thread, input, stack, individual, problem);
		DoubleData rd = ((DoubleData) (input));
		data = rd.x;
		
		ParamCounter.getInstance().addCount(children[22].toString());
		
		if (children[0] instanceof AbstractNonTerminal) {
			AbstractNonTerminal child0 = (AbstractNonTerminal) children[0];
			child0.calculateValue(state, thread, input, stack, individual, problem);
		}
		
		if (children[1] instanceof AbstractNonTerminal) {
			AbstractNonTerminal child1 = (AbstractNonTerminal) children[1];
			child1.calculateValue(state, thread, input, stack, individual, problem);
		}	
	}
	
	@Override
	public String makeCTree(boolean parentMadeParens, boolean printTerminalsAsVariables, boolean useOperatorForm) {
//		DecimalFormat df = new DecimalFormat("###.##");
		if (!calc_value) {
			System.out.println("Value not calc!");
		}
		return (parentMadeParens ? "" : "(") + children[0].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
				+ " " + toStringForHumans() + " "
				+ children[1].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
				+ (parentMadeParens ? "" : ")") + "(" + children[22].toString() + ": " +  value + ")";

	}
	
	public static void main(String[] args) {
		int a = 1;
		int b = a << 0;
		int c = a << 1;
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
}
