/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package ec.app.tutorial4;

import ec.util.*;

import javax.swing.event.TreeSelectionEvent;

import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;

public class MultiValuedRegression2 extends GPProblem implements SimpleProblemForm {
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
				//expectedResult = currentX * currentY + currentY;
				
				double fitness_lowest = Double.MAX_VALUE;

				for(int i = 0; i < 100; i ++)
				{
					((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

					double stochastic_cost = input.stochastic_cost;
					double functional_cost = 0.0;
					double fitness_cost;
					
					// reset flag for illegal division
					IllegalDivision.getInstance().reset();
					
					// check for existence of illegal divisions
					if (!IllegalDivision.getInstance().illegalDivision()) {
						//since we are looking for the smallest fitness and not summing up all the fitness, only calculating the abs value of the deviation
						functional_cost = (expectedResult-input.x)*(expectedResult-input.x);
						
						result = Math.abs(expectedResult - input.x);
						
						if (result <= 0.01)
							hits++;
						
						fitness_cost = functional_cost + stochastic_cost;
						
					
					} else {
						//give worst possible fitness score for illegal division
						fitness_cost = 10000.0;
					}
					
					if (fitness_cost <= fitness_lowest) {
						fitness_lowest = fitness_cost;
					}
				}
				
				fitnessArray[y] = fitness_lowest;
			}
			
			for(int i = 0; i < 20; i++)
			{
				sum += fitnessArray[i];
			}
			
			if (sum < 0) {
				sum = Double.MAX_VALUE;
			}
			
			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, sum);
			f.hits = hits;
			ind.evaluated = true;
		}
	}
		
	public void reset(GPNode node)
	{
		if (node instanceof OPNode) {
			OPNode opNode = (OPNode) node;
			opNode.reset();
			if (node.children[0] instanceof OPNode) {
				reset(node.children[0]);
			}
			if (node.children[1] instanceof OPNode) {
				reset(node.children[1]);
			}
		}
	}
}
