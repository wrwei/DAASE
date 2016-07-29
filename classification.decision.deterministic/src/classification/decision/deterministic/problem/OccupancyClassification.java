package classification.decision.deterministic.problem;

import classification.decision.deterministic.utils.CounterUtil;
import classification.decision.deterministic.utils.DataEntity;
import classification.decision.deterministic.utils.DataWarehouse;
import classification.decision.deterministic.utils.DoubleData;
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
			
			double temp_mean = 0, temp_sd = 0;
			double humidty_mean = 0, humidity_sd = 0;
			double light_mean = 0, light_sd = 0;
			double co2_mean = 0, co2_sd = 0;
			double hr_mean = 0, hr_sd = 0;
			double nsm_mean = 0, nsm_sd = 0;
			double ws_mean = 0, ws_sd = 0;
			
			DataWarehouse dw = DataWarehouse.getInstance();
			if (!dw.initialised()) {
				dw.initialise("data/datatraining.txt");
				System.out.println("Expected hits: " + dw.size());
				
				System.out.println(dw.getStatistics());
				
				temp_mean = dw.getMean("temperature");
				temp_sd = dw.getStDeviation("temperature");
				
				humidty_mean = dw.getMean("humidity");
				humidity_sd = dw.getStDeviation("humidity");
				
				light_mean = dw.getMean("light");
				light_sd = dw.getStDeviation("light");
				
				co2_mean = dw.getMean("co2");
				co2_sd = dw.getStDeviation("co2");
				
				hr_mean = dw.getMean("hr");
				hr_sd = dw.getStDeviation("hr");
				
				nsm_mean = dw.getMean("nsm");
				nsm_sd = dw.getStDeviation("nsm");
				
				ws_mean = dw.getMean("ws");
				ws_sd = dw.getStDeviation("ws");
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
				
				
				double fitness = 0.0;
				if (!counterUtil.tempEmpty()) {
						fitness += counterUtil.getTemperatureMean() - temp_mean; 
						fitness += counterUtil.getTemperatureSD() - temp_sd;
				}
				else
				{
					fitness += 10000;
				}
				
				if (!counterUtil.humidityEmpty()) {
						fitness += counterUtil.getHumidityMean() - humidty_mean;
						fitness += counterUtil.getHumiditySD() - humidity_sd;
				}
				else
				{
					fitness += 15000;
				}
				
				if (!counterUtil.lightsEmpty()) {
						fitness += counterUtil.getLightMean() - light_mean;
						fitness += counterUtil.getLightSD() - light_sd;
				}
				else
				{
					fitness += 100000;
				}
				
				if (!counterUtil.co2sEmpty()) {
						fitness += counterUtil.getCO2Mean() - co2_mean;
						fitness += counterUtil.getCO2SD() - co2_sd;
				}
				else
				{
					fitness += 500000;
				}
				
				if (!counterUtil.hrsEmpty()) {
						fitness += counterUtil.getHRMean() - hr_mean;
						fitness += counterUtil.getHRSD() - hr_sd;
				}
				else
				{
					fitness += 500;
				}
				
				if (!counterUtil.nsmsEmpty()) {
						fitness += counterUtil.getNSMMean() - nsm_mean;
						fitness += counterUtil.getNSMSD() - nsm_sd;	
				}
				else
				{
					fitness += 12000000;
				}
				
				if (!counterUtil.wssEmpty()) {
						fitness += counterUtil.getWSMean() - ws_mean;
						fitness += counterUtil.getWSSD() - ws_sd;
				}
				else {
					fitness += 1000;
				}
				
				fitness = 0.0001 * (fitness) + result;
				
				sum += fitness;
			}
			
			if (sum < 0) {
				sum = Double.MAX_VALUE - 100;
			}
		
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
