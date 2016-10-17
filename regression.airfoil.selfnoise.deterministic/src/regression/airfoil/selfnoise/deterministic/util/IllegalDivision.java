package regression.airfoil.selfnoise.deterministic.util;

public class IllegalDivision {

	private static IllegalDivision instance = null;
	private static boolean flag = false;
	
	protected IllegalDivision() {}
	
	public static IllegalDivision getInstance()
	{
		if (instance == null) {
			instance = new IllegalDivision();
		}
		return instance;
	}
	
	public boolean illegalDivision()
	{
		return flag;
	}
	
	public void illegal()
	{
		flag = true;
	}
	
	public void reset()
	{
		flag = false;
	}
}
