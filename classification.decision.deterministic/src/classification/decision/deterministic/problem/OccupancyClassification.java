package classification.decision.deterministic.problem;

import javax.swing.event.TreeSelectionEvent;

import classification.decision.deterministic.utils.DataEntity;
import classification.decision.deterministic.utils.DataWarehouse;
import classification.decision.deterministic.utils.DoubleData;
import classification.decision.deterministic.utils.CounterUtil;
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

				CounterUtil counterUtil = CounterUtil.getInstance();
				counterUtil.clear();
				
				//eval tree
				((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

				double fitness_cost = 0.0;
				
//				System.out.println(((GPIndividual) ind).trees[0].child.makeLispTree());
				
				//get the result for fitness and hits
				result = Math.abs(expectedResult - input.x);
				
				//if result is 0, hit++
				if (result == 0.0) {
					hits++;
				}
				fitness_cost = result + 0.01*counterUtil.getParamScore(); // + 0.01*1/counterUtil.getTreeDepth();
				if (counterUtil.getParamScore() != 0) {
					fitness_cost += 0.01*1/counterUtil.getTreeDepth();
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
