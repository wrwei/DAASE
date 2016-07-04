package probabilistic.smoothing.ecj.terminals;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import probabilistic.smoothing.ecj.problem.OccupancyClassification;
import probabilistic.smoothing.ecj.utils.DoubleData;

public class HumidityRatio extends GPNode{
    
	public String toString() { return "hr"; }

    public int expectedChildren() { return 0; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        	DoubleData rd = ((DoubleData)(input));
        	rd.x = ((OccupancyClassification)problem).hr;
        }
}
