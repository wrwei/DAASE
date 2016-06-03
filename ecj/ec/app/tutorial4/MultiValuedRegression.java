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

public class MultiValuedRegression extends GPProblem implements SimpleProblemForm {
	private static final long serialVersionUID = 1;

	public double currentX;
	public double currentY;

//	public int p0 = 0;
//	public int p1 = 1;
//	public int p2 = 2;
//	public int p3 = 3;
//	public int p4 = 4;
//	public int p5 = 5;

	public void setup(final EvolutionState state, final Parameter base) {
		super.setup(state, base);

		// verify our input is the right class (or subclasses from it)
		if (!(input instanceof DoubleData))
			state.output.fatal("GPData class must subclass from " + DoubleData.class, base.push(P_DATA), null);
	}

	public void evaluate(final EvolutionState state, final Individual ind, final int subpopulation,
			final int threadnum) {
		if (!ind.evaluated) // don't bother reevaluating
		{
			DoubleData input = (DoubleData) (this.input);

			int hits = 0;
			double sum = 0.0;
			double expectedResult;
			double result;
			for (int y = 0; y < 20; y++) {
				currentX = (double) state.random[threadnum].nextInt(40);
				currentY = (double) state.random[threadnum].nextInt(40);
				expectedResult = currentX * currentX * currentY + currentX * currentY + currentY;
				expectedResult = currentX * currentY + currentY;

				// reset flag for illegal division
				IllegalDivision.getInstance().reset();

				for(int i = 0; i < 100; i ++)
				{
					((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual) ind), this);

					//System.out.println(((GPIndividual) ind).trees[0].child.makeCTree(true, true, true));
					double stochastic_cost = input.stochastic_cost;
					double functional_cost = 0.0;
					
					// check for existence of illegal divisions
					if (!IllegalDivision.getInstance().illegalDivision()) {
						functional_cost = 1.0/20.0*1.0/100.0*(expectedResult-input.x)*(expectedResult-input.x);
						result = Math.abs(expectedResult - input.x);
						//System.out.println("Testing at iteration "+i+"  "+result);
						if (result <= 50.0)
							hits++;
						sum += functional_cost + stochastic_cost;
						//sum += stochastic_cost;
//						System.out.println("expected result: " + expectedResult + " input.x: " + input.x);
						//System.out.println("functional cost: " + functional_cost + " stochastic_cost: " + stochastic_cost);
					
					} else {
						//give worst possible fitness score for illegal division
						sum += 1.0;
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
	
	public boolean isStochasticTree(GPTree tree)
	{
		return isStochasticNode(tree.child);
	}
	
	public boolean isStochasticNode(GPNode node)
	{
		if (node instanceof OPNode2) {
			return true;
		}
		else {
			for(GPNode n: node.children)
			{
				return isStochasticNode(n);
			}
		}
		return false;
	}
}
