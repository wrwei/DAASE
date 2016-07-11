package probabilistic.smoothing.ecj.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import probabilistic.smoothing.ecj.utils.DataEntity;
import probabilistic.smoothing.ecj.utils.DataWarehouse;
import probabilistic.smoothing.ecj.utils.DoubleData;
import probabilistic.smoothing.ecj.utils.EvaluationObserver;
import probabilistic.smoothing.ecj.utils.ParamCounter;

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
			}
			else
			{
				for(int i=0; i < dw.size(); i++)
				{
					//prepare data
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

					ParamCounter paramCounter = ParamCounter.getInstance();
					paramCounter.clear();
					
					EvaluationObserver.getInstance().reset();;
					
					//eval tree
					((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

//					System.out.println(((GPIndividual) ind).trees[0].child.makeLispTree());
					
					//get the result for fitness and hits
					result = Math.abs(expectedResult - input.x);
					
					//if result is 0, hit++
					if (result == 0.0 && paramCounter.getScore() == 0) {
						hits++;
					}
					
					//if result is not 0, add to sum
					else
					{
						sum += paramCounter.getScore();
						sum += result;
					}
				}
			}
			
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
