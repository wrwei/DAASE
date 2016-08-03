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
			double expectedResult;
			double result;
			
			DataWarehouse dw = DataWarehouse.getInstance();
			
			if (!dw.initialised()) {
				dw.initialise("data/datatraining.txt");
				System.out.println(dw.getStatistics());
				System.out.println("Expected hits: " + dw.size());
			}

			for(int i=0; i < dw.size(); i++)
			{
				DataEntity de = dw.getData(i);
				temperature = de.getTemperature()*0.1;
				humidity = de.getHumidity()*0.1;
				light = de.getLight()*0.01;
				co2 = de.getCo2()*0.01;
				hr = de.getHr();
				nsm = de.getNsm() * 0.00001;
				ws = de.getWs();
				
				//get occupancy
				expectedResult = de.getOccupancy();

				ParamCounter paramCounter = ParamCounter.getInstance();
				paramCounter.clear();

				// reset flag for illegal division
				IllegalDivision.getInstance().reset();
				
				((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

				double functional_cost = 0.0;
				double fitness_cost;
				
				// check for existence of illegal divisions
				if (!IllegalDivision.getInstance().illegalDivision()) {
					//since we are looking for the smallest fitness and not summing up all the fitness, only calculating the abs value of the deviation
					functional_cost = Math.abs(expectedResult-input.x);
					
					result = Math.abs(expectedResult - input.x);
					
					if (result <= 0.00001)
					{
						hits++;
					}
					fitness_cost = functional_cost + 0.01 * paramCounter.getScore();
				} else {
					fitness_cost = 100.0;
				}
				
				sum += fitness_cost;
			}
			
			if (Double.isInfinite(sum) || Double.isNaN(sum)) {
				sum = Double.MAX_VALUE - 100;
			}
			
			//System.out.println(sum);
		
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
