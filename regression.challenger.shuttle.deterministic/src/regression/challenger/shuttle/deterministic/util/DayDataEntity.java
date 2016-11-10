package regression.challenger.shuttle.deterministic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DayDataEntity {

	public double no_td;
	public double psi;
	public double temp;
	public double temporalOrder;
	
	public double no_Orings;
		
	
	public DayDataEntity(String[] data)
	{
		parseLine(data[0]);
	}
	
	@Override
	public String toString() {
		return   "no_td: " + no_td+ "\n"
				+ "psi: " + psi + "\n"
				+ "temp: " + temp + "\n"
				+ "temporalOrder: " + temporalOrder + "\n"
				+ "no_Orings: " + no_Orings + "\n";
	}
	
	public void parseLine(String s)
	{
		ArrayList<String> columns = new ArrayList<>();
		for(String str: s.split(" "))
		{
			System.err.println(str);
			columns.add(str);
		}
		no_Orings = parseDouble(columns.get(0));
		no_td = parseDouble(columns.get(1));
		temp = parseDouble(columns.get(2));
		psi = parseDouble(columns.get(3));
		temporalOrder = parseDouble(columns.get(4));
	}
	
	public double parseDouble(String s)
	{
		return Double.parseDouble(s);
	}
	
	public int parseInt(String s)
	{
		return Integer.parseInt(s);
	}
	

	public static void main(String[] args) {
		CSVUtil csvUtil = CSVUtil.getInstance();
		ArrayList<String[]> result = csvUtil.readFile("data/o-ring-erosion-only.data");

		DayDataEntity dataEntity = new DayDataEntity(result.get(0));
		System.out.println(dataEntity);

	}
}
