package ec.app.tutorial4;

import ec.gp.GPData;

public class IntegerData extends GPData {

	public int x;
	
	public void copyTo(final GPData gpd)   // copy to another DoubleData
    {
		((IntegerData)gpd).x = x; 
    }
}
