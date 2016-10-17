package regression.airfoil.selfnoise.deterministic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DayDataEntity {

	public double frequency;
	public double angle;
	public double chordLength;
	public double veolcity;
	public double thickness;
	
	public double pressureLevel;
		
	
	public DayDataEntity(String[] data)
	{
		parseLine(data[0]);
	}
	
	@Override
	public String toString() {
		return   "frequency: " + frequency + "\n"
				+ "angle: " + angle + "\n"
				+ "chordLength: " + chordLength + "\n"
				+ "veolcity: " + veolcity + "\n"
				+ "thickness: " + thickness + "\n"
				+ "pressureLevel: " + pressureLevel + "\n";
	}
	
	public void parseLine(String s)
	{
		ArrayList<String> columns = new ArrayList<>();
		for(String str: s.split("\t"))
		{
			columns.add(str);
		}
		frequency = parseDouble(columns.get(0));
		angle = parseDouble(columns.get(1));
		chordLength = parseDouble(columns.get(2));
		veolcity = parseDouble(columns.get(3));
		thickness = parseDouble(columns.get(4));
		
		pressureLevel = parseDouble(columns.get(5));
		
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
		ArrayList<String[]> result = csvUtil.readFile("data/airfoil_self_noise.dat");

		DayDataEntity dataEntity = new DayDataEntity(result.get(1));
		System.out.println(dataEntity);

	}
}
