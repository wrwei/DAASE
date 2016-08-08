package classification.decision.deterministic.utils;

public class Normaliser {

	public static Normaliser instance = null;
	
	protected double temp = 1.0;
	protected double humidity = 1.0;
	protected double light = 0.1;
	protected double co2 = 0.1;
	protected double hr = 1000.0;
	protected double nsm = 0.0001;
	protected double ws = 10.0;
	
	private Normaliser() {}
	
	public static Normaliser getInstance()
	{
		if (instance ==  null) {
			instance = new Normaliser();
		}
		return instance;
	}
	
	public double getTemp() {
		return temp;
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
	
	public double getNsm() {
		return nsm;
	}
	
	public double getWs() {
		return ws;
	}
}
