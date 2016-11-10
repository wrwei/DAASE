package regression.challenger.shuttle.deterministic.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class CSVUtil {

	private static CSVUtil instance = null;
	
	private CSVUtil() {
	}
	
	public static CSVUtil getInstance()
	{
		if (instance == null) {
			instance = new CSVUtil();
		}
		return instance;
	}
	
	public ArrayList<String[]> readFile(String path)
	{
		boolean success = true;
		CSVReader csvReader = null;
		ArrayList<String[]> result = new ArrayList<>();
		try {
			csvReader = new CSVReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			success = false;
			e.printStackTrace();
		}
		if (!success) {
			return null;
		}
		else {
			
			try {
				result.addAll(csvReader.readAll());
			} catch (IOException e) {
				success = false;
				e.printStackTrace();
			}
			if (success) {
				return result;
			}
			else {
				return null;
			}
		}
	}
	
	public static void main(String[] args) {
		CSVUtil csvUtil = CSVUtil.getInstance();
		ArrayList<String[]> result = csvUtil.readFile("data/airfoil_self_noise.dat");
		for(String str: result.get(0))
		{
			System.out.println(str);
		}
		
		for(String str: result.get(1))
		{
			System.out.println(str);
		}
		
//		for(String[] str: result)
//		{
//			for(String s: str)
//			{
//				System.out.println(s);
//			}
//		}
	}
	
}
