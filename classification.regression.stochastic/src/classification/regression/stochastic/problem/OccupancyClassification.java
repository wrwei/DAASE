package classification.regression.stochastic.problem;

import classification.regression.stochastic.utils.DataEntity;
import classification.regression.stochastic.utils.DataWarehouse;
import classification.regression.stochastic.utils.DoubleData;
import classification.regression.stochastic.utils.IllegalDivision;
import classification.regression.stochastic.utils.ParamCounter;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

public class OccupancyClassification extends GPProblem implements SimpleProblemForm {
	private static final long serialVersionUID = 1;

	public double temperature;
	public double humidity;
	public double light;
	public double co2;
	public double hr;
	public long nsm;
	public int ws;
	
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
			double expectedResult;
			double result;
			
			DataWarehouse dw = DataWarehouse.getInstance();
			if (!dw.initialised()) {
				dw.initialise("data/datatraining.txt");
				System.out.println("Expected hits: " + dw.size());
			}

			//prepare an array to store lowest fitness
			double fitnessArray[] = new double[dw.size()];
			
			for(int i=0; i < dw.size(); i++)
			{
				DataEntity de = dw.getData(i);
				temperature = de.getTemperature();
				humidity = de.getHumidity();
				light = de.getLight();
				co2 = de.getCo2();
				hr = de.getHr();
				nsm = de.getNsm();
				ws = de.getWs();
				
				//get occupancy
				expectedResult = de.getOccupancy();

				double fitness_lowest = Double.MAX_VALUE;

				for(int j = 0; j < 100; j ++)
				{
					
					ParamCounter paramCounter = ParamCounter.getInstance();
					paramCounter.clear();

					((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

					double stochastic_cost = input.stochastic_cost;
					double functional_cost = 0.0;
					double fitness_cost;
					
					// reset flag for illegal division
					IllegalDivision.getInstance().reset();
					
					// check for existence of illegal divisions
					if (!IllegalDivision.getInstance().illegalDivision()) {
						//since we are looking for the smallest fitness and not summing up all the fitness, only calculating the abs value of the deviation
						functional_cost = Math.abs(expectedResult-input.x);
						
						result = Math.abs(expectedResult - input.x);
						
						if (result <= 0.001)
						{
							hits++;
						}
						fitness_cost = functional_cost + 0.01*stochastic_cost;
					} else {
						fitness_cost = 100.0;
					}
					
					if (fitness_cost <= fitness_lowest) {
						fitness_lowest = fitness_cost;
					}
				}
				
				fitnessArray[i] = fitness_lowest;
			}
		
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
