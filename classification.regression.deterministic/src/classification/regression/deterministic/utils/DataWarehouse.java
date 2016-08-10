package classification.regression.deterministic.utils;

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
	
	public double getStDeviation(String name)
	{
		
		double sum = 0.0;			
		double mean = 0.0;

		switch (name) {
		case "temperature":
			mean = getMean("temperature");
			for(DataEntity de: data)
			{
				sum += (de.getTemperature() - mean) * (de.getTemperature() - mean); 
			}
			return Math.sqrt(sum/size()); 
			
		case "co2":
			mean = getMean("co2");
			for(DataEntity de: data)
			{
				sum += (de.getCo2() - mean) * (de.getCo2() - mean); 
			}
			return Math.sqrt(sum/size());
			
		case "humidity":
			mean = getMean("humidity");
			for(DataEntity de: data)
			{
				sum += (de.getHumidity() - mean) * (de.getHumidity() - mean); 
			}
			return Math.sqrt(sum/size());
			
		case "light":
			mean = getMean("light");
			for(DataEntity de: data)
			{
				sum += (de.getLight() - mean) * (de.getLight() - mean); 
			}
			return Math.sqrt(sum/size());
			
		case "hr":
			mean = getMean("hr");
			for(DataEntity de: data)
			{
				sum += (de.getHr() - mean) * (de.getHr() - mean); 
			}
			return Math.sqrt(sum/size());
			
		case "nsm":
			mean = getMean("nsm");
			for(DataEntity de: data)
			{
				sum += (de.getNsm() - mean) * (de.getNsm() - mean); 
			}
			return Math.sqrt(sum/size());
			
		case "ws":
			mean = getMean("ws");
			for(DataEntity de: data)
			{
				sum += (de.getWs() - mean) * (de.getWs() - mean); 
			}
			return Math.sqrt(sum/size());
			
		default:
			return 0.0;
		}
		
	}
	
	public double getMean(String name)
	{		
		double sum = 0.0;

		switch (name) {
		case "temperature":
			for(DataEntity de: data)
			{
				sum += de.getTemperature();
			}
			return sum / size();
			
		case "co2":
			for(DataEntity de: data)
			{
				sum += de.getCo2();
			}
			return sum / size();

		case "humidity":
			for(DataEntity de: data)
			{
				sum += de.getHumidity();
			}
			return sum / size();

		case "light":
			for(DataEntity de: data)
			{
				sum += de.getLight();
			}
			return sum / size();

		case "hr":
			for(DataEntity de: data)
			{
				sum += de.getHr();
			}
			return sum / size();

		case "nsm":
			for(DataEntity de: data)
			{
				sum += de.getNsm();
			}
			return sum / size();

		case "ws":
			for(DataEntity de: data)
			{
				sum += de.getWs();
			}
			return sum / size();

		default:
			return 0.0;
		}
	}
	
	public String getStatistics()
	{
		String str = "";
		str += "temperature - Mean: " + getMean("temperature") + " Std: " + getStDeviation("temperature") + "\n";
		str += "humidity - Mean: " + getMean("humidity") + " Std: " + getStDeviation("humidity") + "\n";
		str += "light - Mean: " + getMean("light") + " Std: " + getStDeviation("light") + "\n";
		str += "co2 - Mean: " + getMean("co2") + " Std: " + getStDeviation("co2") + "\n";
		str += "hr - Mean: " + getMean("hr") + " Std: " + getStDeviation("hr") + "\n";
		str += "nsm - Mean: " + getMean("nsm") + " Std: " + getStDeviation("nsm") + "\n";
		str += "ws - Mean: " + getMean("ws") + " Std: " + getStDeviation("ws") + "\n";
		
		return str;
	}
	
}
