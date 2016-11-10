
package regression.challenger.shuttle.stochastic.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import regression.challenger.shuttle.stochastic.util.DataWarehouse;
import regression.challenger.shuttle.stochastic.util.DayDataEntity;
import regression.challenger.shuttle.stochastic.util.DayParamCounter;
import regression.challenger.shuttle.stochastic.util.DoubleData;
import regression.challenger.shuttle.stochastic.util.IllegalDivision;

public class ChallengerShuttleORingRegression extends GPProblem implements SimpleProblemForm {
	private static final long serialVersionUID = 1;

	public double no_td;
	public double psi;
	public double temp;
	public double temporalOrder;
	
	public double no_Orings;

	public void setup(final EvolutionState state, final Parameter base) {
		super.setup(state, base);
		if (!(input instanceof DoubleData))
			state.output.fatal("GPData class must subclass from " + DoubleData.class, base.push(P_DATA), null);
	}

	public void evaluate(final EvolutionState state, final Individual ind, final int subpopulation,
			final int threadnum) {
		if (!ind.evaluated) 
		{ 
			DoubleData input = (DoubleData) (this.input);

			int hits = 0;
			double sum = 0.0;
			double result;
			double expectedResult = 0.0;
			
			

			DataWarehouse dw = DataWarehouse.getInstance();
			
			if (!dw.initialised()) {
				dw.initialise("data/o-ring-erosion-only.data");
				System.out.println("Expected hits: " + dw.size());
			}

			double fitnessArray[] = new double[dw.size()];
			
			for(int i=0; i < dw.size(); i++)
			{
				DayDataEntity de = dw.getData(i);
				
				no_td  = de.no_td;
				psi = de.psi;
				temp = de.temp;
				temporalOrder = de.temporalOrder;
				
				//get count
				expectedResult = de.no_Orings;

				double fitness_lowest = Double.MAX_VALUE;

				for(int j = 0; j < 100; j ++)
				{
					
					DayParamCounter paramCounter = DayParamCounter.getInstance();
					paramCounter.clear();
					
					// reset flag for illegal division
					IllegalDivision.getInstance().reset();
					
					((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

					double stochastic_cost = input.stochastic_cost;
					double functional_cost = 0.0;
					double fitness_cost;
					
					// check for existence of illegal divisions
					if (!IllegalDivision.getInstance().illegalDivision()) {
						functional_cost = Math.abs(expectedResult-input.x);
						
						result = Math.abs(expectedResult - input.x);
						
						if (result <= 0.01)
						{
							hits++;
						}
						
						fitness_cost = functional_cost + 0.01*stochastic_cost + 0.01 * paramCounter.getScore();	
					} else {
						fitness_cost = 100.0;
					}
					
					if (fitness_cost <= fitness_lowest) {
						fitness_lowest = fitness_cost;
					}
				}
				fitnessArray[i] = fitness_lowest;
			}
			for(int i = 0; i < dw.size(); i++)
			{
				sum += fitnessArray[i];
			}
			
			if (sum < 0) {
				sum = Double.MAX_VALUE;
			}
			
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
