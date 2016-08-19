package classification.regression.deterministic.utils;

public class HitCountUtility {

	public static HitCountUtility instance = null;
	
	
	public int total_hit = 0;
	public int temp_hit = 0;
	public int humidity_hit = 0;
	public int light_hit = 0;
	public int co2_hit = 0;
	public int hr_hit = 0;
	public int nsm_hit = 0;
	public int ws_hit = 0;
	
	private HitCountUtility() {}
	
	public static HitCountUtility getInstance()
	{
		if (instance == null) {
			instance = new HitCountUtility();
		}
		return instance;
	}
	
	
}
