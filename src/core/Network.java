package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.routing.RoutingAlgorithm;


/**
 * This class acts as the model. It is the only class that the view or controller should interact with.
 * 
 * This class intentionally implements the module pattern.
 * This means that none of the classes or other design patterns used are exposed at all.
 * Only constructs available in pure java are used in the public interface, with the exception of the RoutingAlgorithm enum.
 * 
 * @author Griffin
 *
 */
public class Network {
	
	private int stepNumber;
	private int messageCreationPeriod;
	private RoutingAlgorithm routingAlg;
	private int messageNumber = 0;
	
	//nodeID to node
	private Map<String,Node> nodes;
	
	//source to destination to message ids
	private Map<String,Map<String,Set<Integer>>> messageIdsBySourceAndDestination;
	
	//message id to messages
	private Map<Integer,Set<Message>> finishedMessages;
	
	public Network(RoutingAlgorithm routingAlg){
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
			Node n = new Node(id, this);
			//put the node into the network
			nodes.put(id, n);
			
			//give the node a router
			setRoutingAlgorithm(routingAlg, n);
			
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
	
	public ArrayList<String> getNodes() {
		
		ArrayList<String> returnNodes = new ArrayList<String>();
		for(String nodeID: nodes.keySet()) {
			returnNodes.add(nodeID);
		}
		
		return returnNodes;
	}
	
	/**
	 * get the message ID for all messages in a given node's buffer
	 * @param nodeId the node who's buffer you want to get
	 * @return an ordered list of message ids, where element 0 is the one that has been there the longest. or null, if the specified node does not exist
	 */
	public List<Integer> getMessageBufferFromNode(String nodeId) {
		if(nodes.containsKey(nodeId)){
			List<Integer> ids = new ArrayList<>();
			for(Message m : nodes.get(nodeId).getMessages()){
				ids.add(m.getId());
			}
			return ids;
		}
		return null;
	}
	
	/**
	 * 
	 * node, this is not a fast operation
	 * @param messageId the id to check
	 * @return true iff there are instances of messages with this ID still traveling over the network
	 */
	public boolean messageFloating(int messageId){
		for(Node n : nodes.values()){
			for(Message m : n.getMessages()){
				if(m.getId() == messageId){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * gets a collection of all of the full paths of all messages with the specified ID that are NOT floating around the network
	 * messages that get duplicated have the same id
	 * if the routing algorithm does not duplicate messages, then this will return a set with size 1
	 * 
	 * If there is duplication, than each path will extend all the way back to source node, even if the instance of the message was created somewere else.
	 * the 0th id will be for the source node. The 1st id will be for the 1st node that the message arrived at and so on. 
	 * 
	 * @param messageId the messageID of the messages that you are looking for
	 * @return Set of paths of nodeIDs
	 */
	public Set<List<String>> getMessagePathsById(int messageId) {
		if(finishedMessages.containsKey(Integer.valueOf(messageId))){
			Set<List<String>> output = new HashSet<>();
			for(Message m : finishedMessages.get(Integer.valueOf(messageId))){
				output.add(m.getHistory());
			}
			return output;
		}
		return null;
	}
	
	/**
	 * @param messageId the id of the message that you want the source of
	 * @return the node id for the node that the message started at
	 */
	//TODO maybe implement? I don't see a need
	//public String getMessageSource(int messageId) {
	//	return null;
	//}
	
	/**
	 * @param messageId the id of the message that you want the destination of
	 * @return the node id for the node that the message was ment for
	 */
	//TODO maybe implement? I don't see a need
	//public String getMessageDestination(int messageId) {
	//	return null;
	//}
	
	/**
	 * changes the routing algorithm used by the network
	 * 
	 * implicitly calls clearNetworkHistory()
	 * 
	 * @param alg the new routing algorithm to use
	 */
	public void setRoutingAlgorithm(RoutingAlgorithm alg) {
		clearNetworkHistory();
		for(Node n : nodes.values()){
			setRoutingAlgorithm(routingAlg, n);
		}
	}
	
	private void setRoutingAlgorithm(RoutingAlgorithm alg, Node n) {
		n.setRouter(alg.getRouter(n));
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
		for(Node n : nodes.values()){
			n.dropQueue();
		}
	}
	
	/**
	 * clears all recorded metrics
	 * Implicitly calls clearNetworkBuffers()
	 */
	public void clearNetworkHistory() {
		clearNetworkBuffers();
		finishedMessages = new HashMap<>();
		messageIdsBySourceAndDestination = new HashMap<>();
		this.stepNumber = 0;
		this.messageNumber = 0;
	}
	
	/**
	 * Specify a source and a destination and this will return the average hops of all messages 
	 * with the specified source and destination that have successfully made it to their destination.
	 * 
	 * Hops are only counted on the messages that actually get to their destination
	 * 
	 * @param sourceId the id of the source node
	 * @param destinationId the id of the destination node
	 * @return the average hops that a message takes to get to it's destination, or a number <0 if there are no such finished messages
	 */
	public double getAverageHops(String sourceId, String destinationId) {
		if(messageIdsBySourceAndDestination.containsKey(sourceId) && messageIdsBySourceAndDestination.get(sourceId).containsKey(destinationId)){
			long runningTotal = 0;
			int count = 0;
			Set<Integer> ids = messageIdsBySourceAndDestination.get(sourceId).get(destinationId);
			for(Integer id : ids){
				if(finishedMessages.containsKey(id)){
					for(Message m : finishedMessages.get(id)){
						if(m.received()){
							runningTotal += m.hops();
							count++;
							break;
						}
					}
				}
			}
			if(count > 0){
				return ((double)runningTotal)/((double)count);
			}
		}
		return -1;
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
		if(messageIdsBySourceAndDestination.containsKey(sourceId) && messageIdsBySourceAndDestination.get(sourceId).containsKey(destinationId)){
			long runningTotal = 0;
			int count = 0;
			Set<Integer> ids = messageIdsBySourceAndDestination.get(sourceId).get(destinationId);
			for(Integer id : ids){
				if(finishedMessages.containsKey(id) && messageFloating(id)){
					count++;
					for(Message m : finishedMessages.get(id)){
						if(m.received()){
							runningTotal += m.hops();
						}
					}
				}
			}
			if(count > 0){
				return ((double)runningTotal)/((double)count);
			}
		}
		return -1;
	}
	
	/**
	 * @return true iff there is at least one path to get from every node to every other node.
	 */
	public boolean isAConnectedGraph() {
		if(!nodes.isEmpty()){
			Set<String> visited = new HashSet<>();
			Node origen = nodes.values().iterator().next();
			visited.add(origen.getId());
			isAConnectedGraphRecursive(origen, visited);
			return nodes.keySet().containsAll(visited);
		}
		return false;
	}
	
	private void isAConnectedGraphRecursive(Node n, Set<String> visited){
		for(String id : n.getNeighbourIds()){
			if(!visited.contains(id)){
				visited.add(id);
				isAConnectedGraphRecursive(nodes.get(id), visited);
			}
		}
	}
	
	/**
	 * simulate one cycle
	 */
	public void step() {
		if((stepNumber++)%messageCreationPeriod == 0 && nodes.size()>=2){
			String sender = randomElement(nodes.keySet());
			String dest = sender;
			while(sender.equals(dest = randomElement(nodes.keySet()))){}
			Message m = new Message(messageNumber++, sender, dest);
			nodes.get(sender).receiveMessage(m);
		}
		for(Node n : nodes.values()){
			n.sendMessage();
		}
		for(Node n : nodes.values()){
			n.flushBuffer();
		}
	}
	private String randomElement(Set<String> set){
		int i = (int)(Math.random()*((double)set.size()));
		Iterator<String> iter = set.iterator();
		while(i-->0){
			iter.next();
		}
		return iter.next();
	}
	
	/**
	 * @param  the message that has finished it's trek
	 * 
	 */
	protected void finalizeMessage(Message m){
		if(!finishedMessages.containsKey(m.getId())){
			finishedMessages.put(m.getId(), new HashSet<>());
		}
		finishedMessages.get(m.getId()).add(m);
	}
	
}
