package ec.app.tutorial4;

public class OperatorCounter {

	private static int ADD = 0;
	private static int SUB = 0;
	private static int MUL = 0;
	private static int DIV = 0;
	
	private static OperatorCounter instance = null;
	
	private OperatorCounter() {}
	
	public static OperatorCounter getInstance()
	{
		if (instance == null) {
			instance = new OperatorCounter();
		}
		return instance;
	}
	
	public void incrementADD()
	{
		ADD++;
	}
	
	public void incrementSUB()
	{
		SUB++;
	}
	
	public void incrementMUL()
	{
		MUL++;
	}
	
	public void incrementDIV()
	{
		DIV++;
	}
	
	public void incrementOperatorCount(int index)
	{
		switch (index) {
		case 0:
			ADD++;
			break;
		case 1:
			SUB++;
			break;
		case 2:
			MUL++;
			break;
		case 3:
			DIV++;
			break;
		default:
			break;
		}
	}
	
	public static int getADD() {
		return ADD;
	}
	
	public static int getSUB() {
		return SUB;
	}
	
	public static int getMUL() {
		return MUL;
	}
	
	public static int getDIV() {
		return DIV;
	}
	
	public static void print()
	{
		String s = "ADD: " + ADD + "\n";
		s += "SUB: " + SUB + "\n";
		s += "MUL: " + MUL + "\n";
		s += "DIV: " + DIV + "\n";
	}
	
}
