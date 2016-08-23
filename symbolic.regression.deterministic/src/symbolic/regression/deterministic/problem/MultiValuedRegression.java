/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package symbolic.regression.deterministic.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import symbolic.regression.deterministic.util.DoubleData;
import symbolic.regression.deterministic.util.IllegalDivision;

public class MultiValuedRegression extends GPProblem implements SimpleProblemForm {
	private static final long serialVersionUID = 1;

	public double currentX;
	public double currentY;

	public void setup(final EvolutionState state, final Parameter base) {
		super.setup(state, base);
		if (!(input instanceof DoubleData))
			state.output.fatal("GPData class must subclass from " + DoubleData.class, base.push(P_DATA), null);
	}

	public void evaluate(final EvolutionState state, final Individual ind, final int subpopulation,
			final int threadnum) {
		// don't bother reevaluating
		if (!ind.evaluated) 
		{	
			DoubleData input = (DoubleData) (this.input);

			int hits = 0;
			double sum = 0.0;
			double expectedResult;
			double result;
			
			//prepare an array to store lowest fitness
			double fitnessArray[] = new double[20];
			
			for (int y = 0; y < 20; y++) {
				currentX = (double) state.random[threadnum].nextDouble();
				currentY = (double) state.random[threadnum].nextDouble();
				expectedResult = currentX * currentX * currentY + currentX * currentY + currentY;
				
				double fitness_lowest = Double.MAX_VALUE;


				((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

				double functional_cost = 0.0;
				double fitness_cost;
				
				// reset flag for illegal division
				IllegalDivision.getInstance().reset();
				
				// check for existence of illegal divisions
				if (!IllegalDivision.getInstance().illegalDivision()) {
					//since we are looking for the smallest fitness and not summing up all the fitness, only calculating the abs value of the deviation
					functional_cost = Math.abs(expectedResult-input.x);
					
					result = Math.abs(expectedResult - input.x);
					
					if (result <= 0.01)
					{
						hits++;
					}
					fitness_cost = functional_cost;
				} else {
					fitness_cost = 100.0;
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
