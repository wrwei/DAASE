package classification.decision.stochastic.terminals;

import classification.decision.stochastic.problem.OccupancyClassification;
import classification.decision.stochastic.utils.DoubleData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class Light extends GPNode {


    public String toString() { return "light"; }

    public int expectedChildren() { return 0; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        	DoubleData rd = ((DoubleData)(input));
        	rd.x = ((OccupancyClassification)problem).light;
        }
    


}
