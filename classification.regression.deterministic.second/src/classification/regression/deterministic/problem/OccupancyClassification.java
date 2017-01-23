package classification.regression.deterministic.problem;

import classification.regression.deterministic.utils.DataEntity;
import classification.regression.deterministic.utils.DataWarehouse;
import classification.regression.deterministic.utils.DoubleData;
import classification.regression.deterministic.utils.IllegalActivity;
import classification.regression.deterministic.utils.ParamCounter;
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
	public double temperature_mean;
	public double temperature_std;
	
	public double humidity;
	public double humidity_mean;
	public double humidity_std;
	
	public double light;
	public double light_mean;
	public double light_std;
	
	public double co2;
	public double co2_mean;
	public double co2_std;
	
	public double hr;
	public double hr_mean;
	public double hr_std;
	
	public double nsm;
	public double nsm_mean;
	public double nsm_std;
	
	public int ws;
	public double ws_mean;
	public double ws_std;
	
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
			double expectedResult = 0.0;
			
			DataWarehouse dw = DataWarehouse.getInstance();
			
			if (!dw.initialised()) {
				dw.initialise("data/datatraining.txt");
				
				temperature_mean = dw.getMean("temperature");
				temperature_std = dw.getStDeviation("temperature");
				
				humidity_mean = dw.getMean("humidity");
				humidity_std = dw.getStDeviation("humidity");
				
				light_mean = dw.getMean("light");
				light_std = dw.getStDeviation("light");
				
				co2_mean = dw.getMean("co2");
				co2_std = dw.getStDeviation("co2");
				
				hr_mean = dw.getMean("hr");
				hr_std = dw.getStDeviation("hr");
				
				nsm_mean = dw.getMean("nsm");
				nsm_std = dw.getStDeviation("nsm");
				
				ws_mean = dw.getMean("ws");
				ws_std = dw.getStDeviation("ws");
				
				System.out.println(dw.getStatistics());
				System.out.println("Expected hits: " + dw.size());
			}

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

				ParamCounter paramCounter = ParamCounter.getInstance();
				paramCounter.clear();

				// reset flag for illegal activity
				IllegalActivity.getInstance().reset();
				
				((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

				double functional_cost = 0.0;
				double fitness_cost;
				
				// check for existence of illegal activity
				if (!IllegalActivity.getInstance().illegalActivity()) {
					
					double actual= input.x;
					functional_cost = Math.abs(actual - expectedResult);
					
//					if (expectedResult == 0) {
//						functional_cost = actual;
//					}
//					else
//					{
//						functional_cost = 7 - actual;
//					}
					
//					functional_cost = Math.abs(actual-expectedResult);

//					if ((expectedResult == 0 && actual == 0) || (expectedResult == 1 && actual > 0)) {
//						hits++;
//					}
					
					if (functional_cost == 0) {
						hits++;
					}
					
					fitness_cost = functional_cost + paramCounter.getScore();	
				} else {
					sum = Double.MAX_VALUE - 100;
					break;
					
					//fitness_cost = 1000.0 * IllegalActivity.getInstance().getCount();
					//System.out.println(IllegalActivity.getInstance().getCount());
				}
				sum += fitness_cost;
			}
			
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
