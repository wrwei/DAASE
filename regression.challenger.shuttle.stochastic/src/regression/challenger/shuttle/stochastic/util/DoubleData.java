package regression.challenger.shuttle.stochastic.util;

import ec.gp.*;

public class DoubleData extends GPData {
	public double x; // return value
	public double stochastic_cost = 0.0;

	public void copyTo(final GPData gpd) // copy my stuff to another DoubleData
	{
		((DoubleData) gpd).x = x;
	}
}
