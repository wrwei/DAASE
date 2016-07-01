package probabilistic.smoothing.ecj.functionset;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import probabilistic.smoothing.ecj.utils.DoubleData;

public abstract class AbstractNonTerminal extends GPNode {

	protected double value;
	
	public int expectedChildren() {
		//2 nodes for subtree
		//20 nodes to calculate value
		//1 node to store data
		return 23;
	}

	public abstract void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack,
			final GPIndividual individual, final Problem problem); 
	
	public synchronized void calculateValue(final EvolutionState state, final int thread,
			final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem)
	{
		
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
				floatpoint += 1 << (i-2);
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
				div_factor += 1 << (i-2);
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
