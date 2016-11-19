package core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class acts as the model. It is the only class that the view or controller should interact with.
 * 
 * This class intentionally implements the modual pattern.
 * This means that none of the classes or other design patterns used are exposed at all.
 * Only constructs available in pure java are used in the public interface, with the exception of the RoutingAlgorithm enum.
 * 
 * @author Griffin
 *
 */
public class Network {
	private RouterFactory routerFac;
	private int stepNumber;
	private int messageCreationPeriod;
	private RoutingAlgorithm routingAlg;
	
	//nodeID to node
	private Map<String,Node> nodes;
	
	//source to destination to message ids
	private Map<String,Map<String,Set<Integer>>> messageIdsBySourceAndDestination;
	
	//message id to messages
	private Map<Integer,Set<Message>> finishedMessages;
	
	public Network(RoutingAlgorithm routingAlg){
		this.routerFac = new RouterFactory();
		this.stepNumber = 0;
		this.messageCreationPeriod = 1;
		this.routingAlg = routingAlg;
		
		this.nodes = new HashMap<>();
		this.messageIdsBySourceAndDestination = new HashMap<>();
		this.finishedMessages = new HashMap<>();
	}
	
	/**
	 * creates a new node with the specified ID
	 * @param id the string ID that the node will have
	 * @return false iff the ID is already taken
	 */
	public boolean createNode(String id) {
		if(nodes.containsKey(id)){
			return false;
		}else{
			//put the node into the network
			nodes.put(id, new Node(id));
			
			//create space for message ids with this as a source
			messageIdsBySourceAndDestination.put(id, new HashMap<>());
			
			//create space for message ids with this as a destination
			for(String source : messageIdsBySourceAndDestination.keySet()){
				messageIdsBySourceAndDestination.get(source).put(id, new HashSet<>());
			}
			return true;
		}
	}
	
	/**
	 * Closes all connections that the specified node has, and then removes the specified node from the network
	 * @param id the ID of the node to be destroyed
	 * @return true if there was a node with that ID to remove and the removal was completed successfully.
	 */
	public boolean destroyNode(String id) {
		if(!nodes.containsKey(id)){
			return false;
		}else{
			//put the node into the network
			nodes.remove(id);
			
			//remove space for message ids with this as a source
			messageIdsBySourceAndDestination.remove(id);
			
			//remove space for message ids with this as a destination
			for(String source : messageIdsBySourceAndDestination.keySet()){
				messageIdsBySourceAndDestination.get(source).remove(id);
			}
			return true;
		}
	}
	
	/**
	 * calls destroyNode for each node in the network
	 */
	public void destroyAllNodes() {
		//new set to prevent concurrent modification error
		for(String nodeID : new HashSet<>(nodes.keySet())){
			destroyNode(nodeID);
		}
	}
	
	/**
	 * creates a connection between 2 specified nodes
	 * @param id1 one node's id
	 * @param id2 the other node's id
	 * @return true iff a new distinct connection was formed successfully.
	 */
	public boolean connectNodes(String id1, String id2) {
		if(nodes.containsKey(id1) && nodes.containsKey(id2)){
			boolean temp = nodes.get(id1).connectTo(nodes.get(id2));
			temp = nodes.get(id2).connectTo(nodes.get(id1)) || temp;
			return temp;
		}else{
			return false;
		}
	}
	
	/**
	 * disconnects 2 specified nodes
	 * @param id1 one node's id
	 * @param id2 the other node's id
	 * @return true iff the two nodes were connected before, and are no longer connected
	 */
	public boolean disconnectNodes(String id1, String id2) {
		if(nodes.containsKey(id1) && nodes.containsKey(id2)){
			boolean temp = nodes.get(id1).disconnectFrom(nodes.get(id2));
			temp = nodes.get(id2).disconnectFrom(nodes.get(id1)) || temp;
			return temp;
		}else{
			return false;
		}
	}
	
	/**
	 * calls disconnectNodes on each pair of connected nodes
	 */
	public void disconnectAllNodes() {
		for(String nodeId1 : nodes.keySet()){
			for(String nodeId2 : nodes.get(nodeId1).getNeighbourIds()){
				disconnectNodes(nodeId1, nodeId2);
			}
		}
	}
	
	/**
	 * get the message ID for all messages in a given node's buffer
	 * @param nodeId the node who's buffer you want to get
	 * @return an ordered list of message ids, where element 0 is the one that has been there the longest
	 */
	public List<Integer> getMessageBufferFromNode(String nodeId) {
		return null;
	}
	/**
	 * gets a collection of all of the full paths of all messages with the specified ID
	 * messages that get duplicated have the same id
	 * if the routing algorithm does not duplicate messages, then this will return a set with size 1
	 * 
	 * If there is duplication, than each path will extend all the way back to source node, even if the instance of the message was created somewere else.
	 * the 0th id will be for the source node. The 1st id will be for the 1st node that the message arrived at and so on. 
	 * 
	 * @param messageId the messageID that you are looking for
	 * @return Set of paths of nodeIDs
	 */
	public Set<List<String>> getMessagePathsById(int messageId) {
		return null;
	}
	
	/**
	 * @param messageId the id of the message that you want the source of
	 * @return the node id for the node that the message started at
	 */
	public String getMessageSource(int messageId) {
		return null;
	}
	
	/**
	 * @param messageId the id of the message that you want the destination of
	 * @return the node id for the node that the message was ment for
	 */
	public String getMessageDestination(int messageId) {
		return null;
	}
	
	/**
	 * changes the routing algorithm used by the network
	 * 
	 * implicitly calls clearNetworkHistory()
	 * 
	 * @param alg the new routing algorithm to use
	 */
	public void setRoutingAlgorithm(RoutingAlgorithm alg) {
	}
	
	/**
	 * @param period the quantity of cycles between creating a message and creating the next.
	 */
	public void setMessageCreationPeriod(int period) {
	}
	
	/**
	 * empty all buffers on all nodes in the network
	 */
	public void clearNetworkBuffers() {
	}
	
	/**
	 * clears all recorded metrics
	 * Implicitly calls clearNetworkBuffers()
	 */
	public void clearNetworkHistory() {
		
	}
	
	/**
	 * Specify a source and a destination and this will return the average hops of all messages 
	 * with the specified source and destination that have successfully made it to their destination.
	 * 
	 * Hops are only counted on the messages that actually get to their destination
	 * 
	 * @param sourceId the id of the source node
	 * @param destinationId the id of the destination node
	 * @return the average hops that a message takes to get to it's destination
	 */
	public double getAverageHops(String sourceId, String destinationId) {
		return 0;
	}
	
	/**
	 * Specify a source and a destination and this will return the average transmissions of all messages 
	 * with the specified source and destination that do not have copies still being transmitted.
	 * 
	 * @param sourceId the id of the source node
	 * @param destinationId the id of the destination node
	 * @return the average transmissions that a message takes to get to it's destination
	 */
	public double getAverageTransmitions(String sourceId, String destinationId) {
		return 0;
	}
	
	/**
	 * @return true iff there is at least one path to get from every node to every other node.
	 */
	public boolean isAConnectedGraph() {
		return false;
	}
	
	/**
	 * simulate one cycle
	 */
	public void step() {
	}
}
