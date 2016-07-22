package classification.decision.deterministic.utils;

public class ParamCounter {

	private static ParamCounter instance = new ParamCounter();
	
	
	private int[] a = new int[] {0,0,0,0,0,0,0};
	
	private ParamCounter() {}
	
	public static ParamCounter getInstance()
	{
		if (instance == null) {
			instance = new ParamCounter();
		}
		
		return instance;
	}
	
	public synchronized void clear()
	{
		a = new int[] {0,0,0,0,0,0,0};
	}
	
	public synchronized void addCount(String param)
	{
		if (param.equals("temperature")) {
			a[0] = 1;
		}
		else if (param.equals("humidity")) {
			a[1] = 1;
		}
		else if (param.equals("light")) {
			a[2] = 1;
		}
		else if (param.equals("co2")) {
			a[3] = 1;
		}
		else if (param.equals("hr")) {
			a[4] = 1;
		}
		else if (param.equals("nsm")) {
			a[5] = 1;
		}
		else if (param.equals("ws")) {
			a[6] = 1;
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
				result +=2;
			}
			else if (i == 1) {
				total++;
			}
			else if (i > 1) {
				result += i * 2;
			}
		}
		
		if (total == 7) {
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
