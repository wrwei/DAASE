package classification.regression.deterministic.functionset;

import classification.regression.deterministic.terminals.AbstractCO2Node;
import classification.regression.deterministic.terminals.AbstractHumidityNode;
import classification.regression.deterministic.terminals.AbstractHumidityRatioNode;
import classification.regression.deterministic.terminals.AbstractLightNode;
import classification.regression.deterministic.terminals.AbstractNSMNode;
import classification.regression.deterministic.terminals.AbstractTemperatureNode;
import classification.regression.deterministic.terminals.AbstractWSNode;
import ec.gp.GPNode;

public abstract class AbstractFactorNode extends GPNode{

	public String type = "";

	public void determineType(GPNode node)
	{
		if (node instanceof AbstractTemperatureNode) {
			type = "temperature";
		}
		else if (node instanceof AbstractHumidityNode) {
			type = "humidity";
		}
		else if (node instanceof AbstractCO2Node) {
			type = "co2";
		}
		else if (node instanceof AbstractHumidityRatioNode) {
			type = "hr";
		}
		else if (node instanceof AbstractLightNode) {
			type = "light";
		}
		else if (node instanceof AbstractNSMNode) {
			type = "nsm";
		}
		else if (node instanceof AbstractWSNode) {
			type = "ws";
		}
	}
}
