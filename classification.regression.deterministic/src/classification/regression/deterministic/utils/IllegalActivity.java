package classification.regression.deterministic.utils;

public class IllegalActivity {

	private static IllegalActivity instance = null;
	private static boolean flag = false;
	private int count = 0;
	
	protected IllegalActivity() {}
	
	public static IllegalActivity getInstance()
	{
		if (instance == null) {
			instance = new IllegalActivity();
		}
		return instance;
	}
	
	public boolean illegalActivity()
	{
		return flag;
	}
	
	public void illegal()
	{
		count++;
		flag = true;
	}
	
	public void reset()
	{
		count = 0;
		flag = false;
	}
	
	public int getCount() {
		return count;
	}
}
