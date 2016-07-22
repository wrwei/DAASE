package classification.decision.stochastic.utils;

public class EvaluationObserver {

	protected static EvaluationObserver instance;
	private boolean shouldNotEval = false;
	
	private EvaluationObserver() {}
	
	public static EvaluationObserver getInstance()
	{
		if (instance == null) {
			instance = new EvaluationObserver();
		}
		return instance;
	}
	
	public void reset()
	{
		shouldNotEval = false;
	}
	
	public void doNotEval()
	{
		shouldNotEval = true;
	}
	
	public boolean shouldNotEval()
	{
		return shouldNotEval;
	}
}
