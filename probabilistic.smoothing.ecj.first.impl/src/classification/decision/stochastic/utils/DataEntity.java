package classification.decision.stochastic.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataEntity {

	protected GregorianCalendar date;
	protected double temperature;
	protected double humidity;
	protected double light;
	protected double co2;
	protected double hr;
	protected int occupancy;
	
	//derived attributes
	protected long nsm;
	protected int ws;
	
	public DataEntity(String[] data)
	{
		date = parseDate(data[1]);
		temperature = parseDouble(data[2]);
		humidity = parseDouble(data[3]);
		light = parseDouble(data[4]);
		co2 = parseDouble(data[5]);
		hr = parseDouble(data[6]);
		occupancy = parseInt(data[7]);
		
		GregorianCalendar temp = new GregorianCalendar(date.get(Calendar.YEAR),date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

		nsm = (date.getTimeInMillis() - temp.getTimeInMillis())/1000;
		
		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || date.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY) {
			ws = 0;
		}
		else
		{
			ws = 1;
		}
	}
	
	@Override
	public String toString() {
		return "date: " + date.getTime() + "\n"
				+ "temperatur: " + temperature + "\n"
				+ "humidity: " + humidity + "\n"
				+ "light: " + light + "\n"
				+ "co2: " + co2 + "\n"
				+ "hr: " + hr + "\n"
				+ "occupacy: " + occupancy + "\n"
				+ "nsm: " + nsm + "\n"
				+ "ws: " + ws + "\n";
	}
	
	public double parseDouble(String s)
	{
		return Double.parseDouble(s);
	}
	
	public int parseInt(String s)
	{
		return Integer.parseInt(s);
	}
	
	public GregorianCalendar parseDate(String s)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public double getTemperature() {
		return temperature;
	}
	
	public double getHumidity() {
		return humidity;
	}
	
	public double getLight() {
		return light;
	}
	
	public double getCo2() {
		return co2;
	}
	
	public double getHr() {
		return hr;
	}
	
	public int getOccupancy() {
		return occupancy;
	}
	
	public long getNsm() {
		return nsm;
	}
	
	public int getWs() {
		return ws;
	}
	
	public static void main(String[] args) {
		CSVUtil csvUtil = CSVUtil.getInstance();
		ArrayList<String[]> result = csvUtil.readFile("data/datatraining.txt");

		for(String str: result.get(1))
		{
			System.out.println(str);
		}
		
		DataEntity dataEntity = new DataEntity(result.get(1));
		System.out.println(dataEntity);
//		DataEntity entity = new DataEntity();
//		GregorianCalendar date = entity.parseDate("2015-02-04 17:51:00");
//		
//		GregorianCalendar temp = new GregorianCalendar(date.get(Calendar.YEAR),date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
//		
//		
//		System.out.println(date.getTime());
//		
//		System.out.println(temp.getTime());

	}
}
