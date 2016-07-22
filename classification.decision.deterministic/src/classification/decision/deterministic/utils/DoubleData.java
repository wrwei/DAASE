package classification.decision.deterministic.utils;

import ec.gp.GPData;

public class DoubleData extends GPData {
	
	public double x; // return value
	public double stochastic_cost = 0.0;

	public void copyTo(final GPData gpd) // copy my stuff to another DoubleData
	{
		((DoubleData) gpd).x = x;
	}
}
