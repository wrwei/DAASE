/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.tutorial4;
import ec.util.*;
import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;

public class MultiValuedRegression extends GPProblem implements SimpleProblemForm
    {
    private static final long serialVersionUID = 1;

    public double currentX;
    public double currentY;
    
    public int p0 = 0;
    public int p1 = 1;
    public int p2 = 2;
    public int p3 = 3;
    public int p4 = 4;
    public int p5 = 5;
    
    public void setup(final EvolutionState state,
        final Parameter base)
        {
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof DoubleData))
            state.output.fatal("GPData class must subclass from " + DoubleData.class,
                base.push(P_DATA), null);
        }
        
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum)
        {
        if (!ind.evaluated)  // don't bother reevaluating
            {
            DoubleData input = (DoubleData)(this.input);
        
            int hits = 0;
            double sum = 0.0;
            double expectedResult;
            double result;
            for (int y=0;y<10;y++)
            {
                currentX = state.random[threadnum].nextDouble();
                currentY = state.random[threadnum].nextDouble();
                expectedResult = currentX*currentX*currentY + currentX*currentY + currentY;
                
                // reset flag for illegal division
                IllegalDivision.getInstance().reset();
                
                ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);
                
                //check for existence of illegal divisions
                if (!IllegalDivision.getInstance().illegalDivision()) {
                	result = Math.abs(expectedResult - input.x);
                    if (result <= 0.01) hits++;
                    sum += result; 
				}
                else
                {
                	sum = Double.MAX_VALUE;
                }
            }

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness)ind.fitness);
            f.setStandardizedFitness(state, sum);
            f.hits = hits;
            ind.evaluated = true;
            
            //OperatorCounter.print();
            }
        }
    }

