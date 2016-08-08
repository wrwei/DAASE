package classification.decision.deterministic.functionset;

import classification.decision.deterministic.utils.DoubleData;
import classification.decision.deterministic.utils.CounterUtil;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import ec.util.Parameter;

public abstract class AbstractNonTerminal extends GPNode {

	protected double value = -1.0;
	protected double data = -1.0;
	protected boolean calc_value = false;
	
	public int expectedChildren() {
		//2 nodes for subtree
		//8 + 4 + 4 = 25 nodes to calculate value
		//1 node to store data
		return 19;
	}
	
	public abstract boolean process();
	
	@Override
	public void checkConstraints(EvolutionState state, int tree,
			GPIndividual typicalIndividual, Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		if (children.length != 19)
			state.output.error("Incorrect number of children for node " + toStringForError() + " at " + individualBase);
	}

	public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem) {

		DoubleData rd = ((DoubleData) (input));
		
		//calculate the values of each node once for each tree
		if (parent instanceof GPTree) {
			calculateValueAndData(state, thread, input, stack, individual, problem);	
		}
		
		if (process()) {
			//if child 1 is a terminal
			if (!(children[1] instanceof AbstractNonTerminal)) {
				rd.x = 1;
				
				//if child 0 is a terminal
				if (!(children[0] instanceof AbstractNonTerminal)) {
					//if parent is GPTree, it means this tree is useless, give high fitness
					if (parent instanceof GPTree) {
						rd.x = 20;
					}
				}
			}
			else
			{
				children[1].eval(state, thread, input, stack, individual, problem);	
//				CounterUtil.getInstance().increaseTreeDepth();
			}
		}
		else {
			if (!(children[0] instanceof AbstractNonTerminal)) {
				rd.x = 0;
				
				//if child 0 is a terminal
				if (!(children[1] instanceof AbstractNonTerminal)) {
					//if parent is GPTree, it means this tree is useless, give high fitness
					if (parent instanceof GPTree) {
						rd.x = 20;
					}
				}
			}
			else
			{
				children[0].eval(state, thread, input, stack, individual, problem);	
//				CounterUtil.getInstance().increaseTreeDepth();
			}
		}
	}
	
	public synchronized void calculateValueAndData(final EvolutionState state, final int thread,
			final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem)
	{
		//signal that the value of this node has been calculated 
		calc_value = true;
		
		value = 0;
		
		//8 bits to calculate the decimal part
		int decimal = 0;
		
		//8 bits for decimal
		for (int i = 2; i < 10; i++) {
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				decimal += 1 << (i-2);
			}
		}
		
//		System.out.println("decimal is: " + decimal);
		
		//4 bits to caluculate the float
		int floatpoint = 0;
		for(int i = 10; i < 14; i++)
		{
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				floatpoint += 1 << (i-10);
			}
		}
		
//		System.out.println("float is: " + floatpoint);

		
		//4 bits to calculate div factor
		int div_factor = 1;
		for(int i = 14; i < 18; i++)
		{
			DoubleData rd = ((DoubleData) (input));

			children[i].eval(state, thread, input, stack, individual, problem);
			int result = (int) rd.x;
			if (result == 1) {
				div_factor += 1 << (i-14);
			}
		}
		
//		System.out.println("div fac is: " + div_factor);

		
		//add decimal part
		value += (double)decimal;
		
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
		
		//get the data from children 22
		children[18].eval(state, thread, input, stack, individual, problem);
		DoubleData rd = ((DoubleData) (input));
		data = (double)rd.x;
		
		//add the count to the param counter
		CounterUtil.getInstance().addParamCount(children[18].toString());
		
		//calculate value and data recursively for child 0
		if (children[0] instanceof AbstractNonTerminal) {
			AbstractNonTerminal child0 = (AbstractNonTerminal) children[0];
			child0.calculateValueAndData(state, thread, input, stack, individual, problem);
		}
		
		//calculate value and data recursively for child 1
		if (children[1] instanceof AbstractNonTerminal) {
			AbstractNonTerminal child1 = (AbstractNonTerminal) children[1];
			child1.calculateValueAndData(state, thread, input, stack, individual, problem);
		}	
	}
	
	@Override
	public String makeCTree(boolean parentMadeParens, boolean printTerminalsAsVariables, boolean useOperatorForm) {
//		DecimalFormat df = new DecimalFormat("###.##");
		if (!calc_value) {
			System.out.println("Value not calc!");
		}
		return (parentMadeParens ? "" : "(") + "0"
				+ " " + toStringForHumans() + " "
				+ "1"
				+ (parentMadeParens ? "" : ")") + "(" + children[18].toString() + ": " +  value + ")";
//		return (parentMadeParens ? "" : "(") + children[0].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
//				+ " " + toStringForHumans() + " "
//				+ children[1].makeCTree(false, printTerminalsAsVariables, useOperatorForm)
//				+ (parentMadeParens ? "" : ")") + "(" + children[18].toString() + ": " +  value + ")";

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
