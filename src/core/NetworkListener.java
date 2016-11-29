package core;

import java.util.List;
import java.util.Map;

public interface NetworkListener {
	/**
	 * Called when a new Node is created
	 * @param id the String identifier of the new node.
	 */
	public void createNode(String id);
	
	/**
	 * Called when a node is removed
	 * @param id the String id of the node being removed
	 */
	public void destroyNode(String id);
	
	/**
	 * Called when a new undirected connection is formed
	 * @param id1 The first node
	 * @param id2 The second node
	 */
	public void connectNodes(String id1, String id2);
	
	/**
	 * Called when a connection is broken
	 * @param id1 The first node
	 * @param id2 The second node
	 */
	public void disconnectNodes(String id1, String id2);
	
	/**
	 * Called when the messages in the buffers of the nodes is changed
	 * @param status a Map of node identifiers to list of message identifiers, in descending order
	 */
	public void updateMessages(Map<String,List<Integer>> status);
	
	/**
	 * Called when all nodes and all connections are destroyed
	 * 
	 * This is usually followed by the creation of new nodes and connections
	 */
	public void clearAll();
	
}
