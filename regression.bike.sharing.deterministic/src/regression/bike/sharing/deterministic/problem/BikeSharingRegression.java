/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package regression.bike.sharing.deterministic.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import regression.bike.sharing.deterministic.util.DataWarehouse;
import regression.bike.sharing.deterministic.util.DayDataEntity;
import regression.bike.sharing.deterministic.util.DoubleData;
import regression.bike.sharing.deterministic.util.IllegalDivision;

public class BikeSharingRegression extends GPProblem implements SimpleProblemForm {
	private static final long serialVersionUID = 1;

	//season (1-4)
	public double season;
	//year (0:2011, 1:2012)
	public double year;
	//month (1-12)
	public double month;
	//hour (0-23)
	public double hour;
	//whether day is holiday or not (1:0)
	public double holiday;
	//day of the week
	public double weekday;
	//if day is working day (1:0)
	public double workingday;
	//weather condition (1-4)
	public double weathersit;
	//normalised temperature (div to 41)
	public double temp;
	//normalised feeling temp (div to 50)
	public double atemp;
	//normlaised humidity
	public double hum;
	//normlalised wind speed
	public double windspeed;
//	//count of casual users
//	public double casual;
//	//count of registered users
//	public double registered;
	
	//total count of bike rentals
	public double cnt;
	

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
				dw.initialise("data/day.csv");
				System.out.println("Expected hits: " + dw.size());
			}

			for(int i=0; i < dw.size(); i++)
			{
				DayDataEntity de = dw.getData(i);
				season = de.season;
				year = de.year;
				month = de.month;
				holiday = de.holiday;
				weekday = de.weekday;
				workingday = de.workingday;
				weathersit = de.weathersit;
				temp = de.temp;
				atemp = de.atemp;
				hum = de.hum;
				windspeed = de.windspeed;
				
				//get count
				expectedResult = de.cnt;

				DayParamCounter paramCounter = DayParamCounter.getInstance();
				paramCounter.clear();

				// reset flag for illegal division
				IllegalDivision.getInstance().reset();
				
				((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

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
					fitness_cost = functional_cost + 0.01 * paramCounter.getScore();	
				} else {
					fitness_cost = 100.0;
				}
				
				sum += fitness_cost;
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
