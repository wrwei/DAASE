package ec.app.tutorial4;

import java.util.concurrent.ConcurrentHashMap;

import ec.gp.GPNode;

public class NodeRegister {

	protected static NodeRegister instance = null;
	protected static ConcurrentHashMap<GPNode, ?> nodes = new ConcurrentHashMap<>();
	
	private NodeRegister() {}
	
	public static NodeRegister getInstance()
	{
		if (instance == null) {
			instance = new NodeRegister();
		}
		return instance;
	}
	
	public boolean exist(GPNode node)
	{
		return nodes.containsKey(node);
	}
	
	public void insertNode(GPNode node)
	{
		nodes.put(node, null);
	}
}
