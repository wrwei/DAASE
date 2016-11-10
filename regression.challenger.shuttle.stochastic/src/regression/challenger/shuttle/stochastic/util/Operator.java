package regression.challenger.shuttle.stochastic.util;

public enum Operator {
	INVALID(-1), ADD(0), SUB(1), MUL(2), DIV(3), SQRT(4);

	private final int op;

	private Operator(int op) {
		this.op = op;
	}
}
