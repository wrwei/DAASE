package classification.decision.deterministic.utils;

public class CounterUtil {

	private static CounterUtil instance = new CounterUtil();
	
	
	private int[] a = new int[] {0,0,0,0,0,0,0};
	private int treeDepth = 1;
	
	private CounterUtil() {}
	
	public static CounterUtil getInstance()
	{
		if (instance == null) {
			instance = new CounterUtil();
		}
		
		return instance;
	}
	
	public synchronized void clear()
	{
		a = new int[] {0,0,0,0,0,0,0};
		treeDepth = 1;
	}
	
	public synchronized void addParamCount(String param)
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
	
	public int getParamScore()
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
	
	public void increaseTreeDepth()
	{
		treeDepth++;
	}
	
	public int getTreeDepth() {
		return treeDepth;
	}
	
}
