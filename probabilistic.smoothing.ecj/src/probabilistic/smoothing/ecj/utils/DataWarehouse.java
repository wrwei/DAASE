package probabilistic.smoothing.ecj.utils;

import java.util.ArrayList;

public class DataWarehouse {

	private static DataWarehouse instance;
	
	private boolean initialised = false;
	private ArrayList<DataEntity> data = new ArrayList<>();
	private int pointer = -1;
	
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
			for(String[] strs: read)
			{
				data.add(new DataEntity(strs));
			}
		}
		pointer = 0;
		initialised = true;
	}
	
	public synchronized DataEntity getNext()
	{
		return data.get(pointer++);
	}
	
}
