package classification.regression.deterministic.utils;

public class IllegalActivity {

	private static IllegalActivity instance = null;
	private static boolean flag = false;
	
	protected IllegalActivity() {}
	
	public static IllegalActivity getInstance()
	{
		if (instance == null) {
			instance = new IllegalActivity();
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
