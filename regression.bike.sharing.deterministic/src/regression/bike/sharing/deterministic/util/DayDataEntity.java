package regression.bike.sharing.deterministic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DayDataEntity {

		//season (1-4)
		public double season;
		//year (0:2011, 1:2012)
		public double year;
		//month (1-12)
		public double month;
		//hour (0-23)
		public double hour;
		//whether day is holiday or not (1:0)
		public double holiday;
		//day of the week
		public double weekday;
		//if day is working day (1:0)
		public double workingday;
		//weather condition (1-4)
		public double weathersit;
		//normalised temperature (div to 41)
		public double temp;
		//normalised feeling temp (div to 50)
		public double atemp;
		//normlaised humidity
		public double hum;
		//normlalised wind speed
		public double windspeed;
//		//count of casual users
//		public double casual;
//		//count of registered users
//		public double registered;
		
		//total count of bike rentals
		public double cnt;
		
	
	public DayDataEntity(String[] data)
	{
		season = parseDouble(data[2]);
		year = parseDouble(data[3]);
		month = parseDouble(data[4]);
		holiday = parseDouble(data[5]);
		weekday = parseDouble(data[6]);
		workingday = parseDouble(data[7]);
		weathersit = parseDouble(data[8]);
		temp = parseDouble(data[9]);
		atemp = parseDouble(data[10]);
		hum = parseDouble(data[11]);
		windspeed = parseDouble(data[12]);
		cnt = parseDouble(data[15]);
		
	}
	
	@Override
	public String toString() {
		return   "season: " + season + "\n"
				+ "year: " + year + "\n"
				+ "month: " + month + "\n"
				+ "holiday: " + holiday + "\n"
				+ "weekday: " + weekday + "\n"
				+ "workingday: " + workingday + "\n"
				+ "weathersit: " + weathersit + "\n"
				+ "temp: " + temp + "\n"
				+ "atemp: " + atemp + "\n"
				+ "hum: " + hum + "\n"
				+ "windspeed: " + windspeed + "\n"
				+ "count: " + cnt + "\n";
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

	public static void main(String[] args) {
		CSVUtil csvUtil = CSVUtil.getInstance();
		ArrayList<String[]> result = csvUtil.readFile("data/day.csv");

		for(String str: result.get(1))
		{
			System.out.println(str);
		}
		
		DayDataEntity dataEntity = new DayDataEntity(result.get(1));
		System.out.println(dataEntity);

	}
}
