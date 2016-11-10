package regression.challenger.shuttle.stochastic.util;

public class DayParamCounter {

	private static DayParamCounter instance = new DayParamCounter();
	
	
	private int[] a = new int[] {0,0,0,0};
	
	private DayParamCounter() {}
	
	public static DayParamCounter getInstance()
	{
		if (instance == null) {
			instance = new DayParamCounter();
		}
		
		return instance;
	}
	
	public synchronized void clear()
	{
		a = new int[] {0,0,0,0};
	}
	
	public synchronized void addCount(String param)
	{
		if (param.equals("no_td")) {
			a[0] = 1;
		}
		else if (param.equals("psi")) {
			a[1] = 1;
		}
		else if (param.equals("temp")) {
			a[2] = 1;
		}
		else if (param.equals("temporalOrder")) {
			a[3] = 1;
		}
		else {
			System.err.println(param);
		}
	}
	
	public int getScore()
	{
		int result = 0;
		int total = 0;
		for(int i : a)
		{
			if (i == 0) {
				result += 20;
			}
			else if (i == 1) {
				total++;
			}
			else if (i > 1) {
				result += 40;
			}
		}
		if (total == 4) {
			return 0;
		}
		else if (total == 0) {
			return 10000;
		}
		else
		{
			return result;
		}
	}
	
}
