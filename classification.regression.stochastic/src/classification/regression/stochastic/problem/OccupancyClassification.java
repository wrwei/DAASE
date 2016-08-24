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
	public double nsm;
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
			double result;
			double expectedResult = 0.0;
			double sum_mean = 0.0;
			
			DataWarehouse dw = DataWarehouse.getInstance();
			
			
			
			if (!dw.initialised()) {
				dw.initialise("data/datatraining.txt");
				sum_mean = dw.getMeanSum();
				System.out.println(dw.getStatistics());
				System.out.println("Expected hits: " + dw.size());
				System.out.println("Mean Sum: " + sum_mean);
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
				nsm = de.getNsm() ;
				ws = de.getWs();
				
				//get occupancy
				expectedResult = de.getOccupancy();

				double fitness_lowest = Double.MAX_VALUE;

				for(int j = 0; j < 10; j ++)
				{
					ParamCounter paramCounter = ParamCounter.getInstance();
					paramCounter.clear();

					// reset flag for illegal division
					IllegalDivision.getInstance().reset();
					
					((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

					double stochastic_cost = input.stochastic_cost;
					double functional_cost = 0.0;
					double fitness_cost;
					
					// check for existence of illegal divisions
					if (!IllegalDivision.getInstance().illegalDivision()) {
						//since we are looking for the smallest fitness and not summing up all the fitness, only calculating the abs value of the deviation
						double threshold = sum_mean;
						
						double actual=(input.x<threshold)?0:1;
						
						functional_cost = Math.abs(actual-expectedResult);
						
						result = Math.abs(actual - expectedResult);
						//System.out.println("result is   "+result);
						
						if (result == 0)
						{
							hits++;
						}
						fitness_cost = functional_cost + 0.01 * paramCounter.getScore() + 0.01*stochastic_cost;	
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
			//System.out.println(ParamCounter.getInstance().getScore());
			
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
