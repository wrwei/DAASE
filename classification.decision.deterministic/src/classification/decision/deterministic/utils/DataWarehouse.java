package classification.decision.deterministic.utils;

import java.util.ArrayList;

public class DataWarehouse {

	private static DataWarehouse instance;
	
	private boolean initialised = false;
	private ArrayList<DataEntity> data = new ArrayList<>();
	
	private DataWarehouse()
	{
		
	}
	
	public static DataWarehouse getInstance()
	{
		if (instance == null) {
			instance = new DataWarehouse();
		}
		return instance;
	}
	
	public synchronized void initialise(String s)
	{
		CSVUtil csvUtil = CSVUtil.getInstance();
		ArrayList<String[]> read = csvUtil.readFile(s);
		if (read != null) {
			for(int i = 1; i < read.size(); i++)
			{
				data.add(new DataEntity(read.get(i)));

			}
		}
		initialised = true;
	}
	
	public synchronized DataEntity getData(int index)
	{
		return data.get(index);
	}
	
	public synchronized boolean initialised()
	{
		return initialised;
	}
	
	public synchronized int size()
	{
		return data.size();
	}
}
