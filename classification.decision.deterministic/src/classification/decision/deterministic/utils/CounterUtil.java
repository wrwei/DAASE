package classification.decision.deterministic.utils;

import java.util.ArrayList;

public class CounterUtil {

	private static CounterUtil instance = new CounterUtil();
	
	
	private int[] a = new int[] {0,0,0,0,0,0,0};
	private int treeDepth = 1;
	
	private ArrayList<Double> temps = new ArrayList<>();
	private ArrayList<Double> humidities = new ArrayList<>();
	private ArrayList<Double> lights = new ArrayList<>();
	private ArrayList<Double> co2s = new ArrayList<>();
	private ArrayList<Double> hrs = new ArrayList<>();
	private ArrayList<Integer> nsms = new ArrayList<>();
	private ArrayList<Integer> wss = new ArrayList<>();
	
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
		
		temps.clear();
		humidities.clear();
		lights.clear();
		hrs.clear();
		co2s.clear();
		nsms.clear();
		wss.clear();
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
	
	public void addTemperature(double value)
	{
		temps.add(value);
	}
	
	public double getTemperatureMean()
	{
		if (temps.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double temp: temps)
		{
			sum += temp;
		}
		return sum/temps.size();
	}
	
	public double getTemperatureSD()
	{
		if (temps.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getTemperatureMean();
		for(double temp: temps)
		{
			sum += (temp - mean) * (temp - mean);
		}
		return Math.sqrt(sum/temps.size());
	}
	
	
	public void addHumidity(double value)
	{
		humidities.add(value);
	}
	
	public double getHumidityMean()
	{
		if (humidities.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double h: humidities)
		{
			sum += h;
		}
		return sum/humidities.size();
	}
	
	public double getHumiditySD()
	{
		if (humidities.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getHumidityMean();
		for(double humidity: humidities)
		{
			sum += (humidity - mean) * (humidity - mean);
		}
		return Math.sqrt(sum/humidities.size());
	}
	
	public void addLight(double value)
	{
		lights.add(value);
	}
	
	public double getLightMean()
	{
		if (lights.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double l: lights)
		{
			sum += l;
		}
		return sum/lights.size();
	}
	
	public double getLightSD()
	{
		if (lights.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getLightMean();
		for(double l: lights)
		{
			sum += (l - mean) * (l - mean);
		}
		return Math.sqrt(sum/lights.size());
	}
	
	public void addCO2(double value)
	{
		co2s.add(value);
	}
	
	public double getCO2Mean()
	{
		if (co2s.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double c: co2s)
		{
			sum += c;
		}
		return sum/co2s.size();
	}
	
	public double getCO2SD()
	{
		if (co2s.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getCO2Mean();
		for(double c: co2s)
		{
			sum += (c - mean) * (c - mean);
		}
		return Math.sqrt(sum/co2s.size());
	}
	
	public void addHR(double value)
	{
		hrs.add(value);
	}
	
	public double getHRMean()
	{
		if (hrs.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double h: hrs)
		{
			sum += h;
		}
		return sum/hrs.size();
	}
	
	public double getHRSD()
	{
		if (hrs.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getHRMean();
		for(double h: hrs)
		{
			sum += (h - mean) * (h - mean);
		}
		return Math.sqrt(sum/hrs.size());
	}
	
	public void addNSM(int value)
	{
		nsms.add(value);
	}
	
	public double getNSMMean()
	{
		if (nsms.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double n: nsms)
		{
			sum += n;
		}
		return sum/nsms.size();
	}
	
	public double getNSMSD()
	{
		if (nsms.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getNSMMean();
		for(double n: nsms)
		{
			sum += (n - mean) * (n - mean);
		}
		return Math.sqrt(sum/nsms.size());
	}
	
	public void addWS(int value)
	{
		wss.add(value);
	}
	
	public double getWSMean()
	{
		if (wss.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		for(double w: wss)
		{
			sum += w;
		}
		return sum/wss.size();
	}
	
	public double getWSSD()
	{
		if (wss.size() == 0) {
			return -1.0;
		}
		double sum = 0.0;
		double mean = getWSMean();
		for(double w: wss)
		{
			sum += (w - mean) * (w - mean);
		}
		return Math.sqrt(sum/wss.size());
	}
	
	public boolean tempEmpty()
	{
		return temps.size() == 0;
	}
	
	public boolean humidityEmpty()
	{
		return humidities.size() == 0;
	}
	
	public boolean lightsEmpty()
	{
		return lights.size() == 0;
	}
	
	public boolean co2sEmpty()
	{
		return co2s.size() == 0;
	}
	
	public boolean hrsEmpty()
	{
		return hrs.size() == 0;
	}
	
	public boolean nsmsEmpty()
	{
		return nsms.size() == 0;
	}
	
	public boolean wssEmpty()
	{
		return wss.size() == 0;
	}
}
