
package regression.airfoil.selfnoise.deterministic.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import regression.airfoil.selfnoise.deterministic.util.DataWarehouse;
import regression.airfoil.selfnoise.deterministic.util.DayDataEntity;
import regression.airfoil.selfnoise.deterministic.util.DayParamCounter;
import regression.airfoil.selfnoise.deterministic.util.DoubleData;
import regression.airfoil.selfnoise.deterministic.util.IllegalDivision;

public class AirfoilSelfNoiseRegression extends GPProblem implements SimpleProblemForm {
	private static final long serialVersionUID = 1;

	public double frequency;
	public double angle;
	public double chordLength;
	public double velocity;
	public double thickness;
	
	public double pressureLevel;

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
				dw.initialise("data/airfoil_self_noise.dat");
				System.out.println("Expected hits: " + dw.size());
			}

			for(int i=0; i < dw.size(); i++)
			{
				DayDataEntity de = dw.getData(i);
				
				frequency  = de.frequency;
				angle = de.angle;
				chordLength = de.chordLength;
				velocity = de.veolcity;
				thickness = de.thickness;
				
				//get count
				expectedResult = de.pressureLevel;

				//DayParamCounter paramCounter = DayParamCounter.getInstance();
				//paramCounter.clear();

				// reset flag for illegal division
				IllegalDivision.getInstance().reset();
				
				((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

				double functional_cost = 0.0;
				double fitness_cost;
				
				// check for existence of illegal divisions
				if (!IllegalDivision.getInstance().illegalDivision()) {
					
					double actual= input.x;
					
					functional_cost = Math.abs(actual-expectedResult);
					
					result = functional_cost;
					
					if (result <= 0.1)
					{
						hits++;
					}
					fitness_cost = functional_cost; //+ 0.01 * paramCounter.getScore();	
				} else {
					fitness_cost = 10000.0;
				}
				
				sum += fitness_cost;
			}
			
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
}
