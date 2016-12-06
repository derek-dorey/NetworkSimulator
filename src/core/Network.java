package core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	private Map<String,NetworkNode> networkNodes;
	
	//source to destination to message ids
	private Map<String,Map<String,Set<Integer>>> messageIdsBySourceAndDestination;
	
	//message id to messages
	private Map<Integer,Set<Message>> finishedMessages;
	
	public Network(RoutingAlgorithm routingAlg){
		this.stepNumber = 0;
		this.messageCreationPeriod = 1;
		this.routingAlg = routingAlg;
		
		this.networkNodes = new HashMap<>();
		this.messageIdsBySourceAndDestination = new HashMap<>();
		this.finishedMessages = new HashMap<>();
	}

	public static Network fromXml(String in) throws SAXException, IOException, ParserConfigurationException{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)));
		doc.getDocumentElement().normalize();
		return new Network(doc);
	}
	
	private Network(Document doc){
		this.networkNodes = new HashMap<>();
		this.messageIdsBySourceAndDestination = new HashMap<>();
		this.finishedMessages = new HashMap<>();
		
		Element rootElement = (Element) doc.getElementsByTagName("Network").item(0);
		
		//Set field values
		this.stepNumber = Integer.valueOf(rootElement.getAttribute("StepNumber"));
		this.messageCreationPeriod = Integer.valueOf(rootElement.getAttribute("MessageCreationPeriod"));
		this.messageNumber = Integer.valueOf(rootElement.getAttribute("MessageNumber"));
		this.routingAlg = RoutingAlgorithm.valueOf(RoutingAlgorithm.class, rootElement.getAttribute("RoutingAlg"));

        //recreate nodes
        NodeList nodes = doc.getElementsByTagName("Nodes").item(0).getChildNodes();
        for(int i = 0; i<nodes.getLength(); i++){
        	if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE){
	        	NetworkNode n = NetworkNode.fromXml((Element) nodes.item(i), this);
	        	if(n!=null){
	        		networkNodes.put(n.getId(), n);
	        	}
        	}
        }
        
        //recreate connections
        NodeList connections = doc.getElementsByTagName("Connections").item(0).getChildNodes();
        for(int i = 0; i<connections.getLength(); i++){
        	if(connections.item(i).getNodeType() == Node.ELEMENT_NODE && "Connection".equals(((Element)connections.item(i)).getTagName())){
        		Element connection = (Element) connections.item(i); 
        		connectNodes(connection.getAttribute("Node1"), connection.getAttribute("Node2"));
        	}
        }
		//recreate message metadata
		NodeList messageListing = doc.getElementsByTagName("MessageListing").item(0).getChildNodes();
        for(int i = 0; i<messageListing.getLength(); i++){
        	if(messageListing.item(i).getNodeType() == Node.ELEMENT_NODE && "NodeInfo".equals(((Element)messageListing.item(i)).getTagName())){
        		Element nodeInfo = (Element) messageListing.item(i);
        		List<Integer> ids = new ArrayList<>();
        		for(String id : nodeInfo.getAttribute("ids").split(",")){
        			ids.add(Integer.valueOf(id));
        		}
        		String from = nodeInfo.getAttribute("from");
        		String to = nodeInfo.getAttribute("to");
        		
        		if(messageIdsBySourceAndDestination.get(from)==null){
        			messageIdsBySourceAndDestination.put(from, new HashMap<>());
        		}
        		if(messageIdsBySourceAndDestination.get(from).get(to)==null){
        			messageIdsBySourceAndDestination.get(from).put(to, new HashSet<>());
        		}
        		messageIdsBySourceAndDestination.get(from).get(to).addAll(ids);
        	}
        }
        
        //recreate finished messages
        NodeList finishedMessages = doc.getElementsByTagName("FinishedMessages").item(0).getChildNodes();
        for(int i = 0; i<finishedMessages.getLength(); i++){
        	if(finishedMessages.item(i).getNodeType() == Node.ELEMENT_NODE && "FinishedId".equals(((Element)finishedMessages.item(i)).getTagName())){
        		Element finishedId = (Element) finishedMessages.item(i);
        		int id = Integer.valueOf(finishedId.getAttribute("id"));
        		this.finishedMessages.put(id, new HashSet<>());
        		NodeList msgs = finishedId.getChildNodes();
        		for(int j = 0; j<msgs.getLength(); j++){
        			if(msgs.item(j).getNodeType() == Node.ELEMENT_NODE){
	        			Message m = Message.fromXml((Element) msgs.item(j));
	        			if(m != null){
	        				this.finishedMessages.get(id).add(m);
	        			}
        			}
        		}
        	}
        }
        
        
	}
	
	public String toXml() throws ParserConfigurationException, TransformerConfigurationException{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		
		//store the network's primitive fields
		Element rootElement = doc.createElement("Network");
        doc.appendChild(rootElement);
        rootElement.setAttribute("StepNumber", Integer.valueOf(stepNumber).toString());
        rootElement.setAttribute("MessageCreationPeriod", Integer.valueOf(messageCreationPeriod).toString());
        rootElement.setAttribute("RoutingAlg", routingAlg.name());
        rootElement.setAttribute("MessageNumber", Integer.valueOf(messageNumber).toString());
        
        //store the metaData about the messages that have been sent
        //ie: source destination, id Number
        Element messageListing = doc.createElement("MessageListing");
        rootElement.appendChild(messageListing);
        for(String from : messageIdsBySourceAndDestination.keySet()){
        	for(String to : messageIdsBySourceAndDestination.get(from).keySet()){
                Element nodeInfo = doc.createElement("NodeInfo");
                messageListing.appendChild(nodeInfo);
                nodeInfo.setAttribute("from", from);
                nodeInfo.setAttribute("to", to);
                StringJoiner ids = new StringJoiner(",");
                for(Integer id : messageIdsBySourceAndDestination.get(from).get(to)){
                	ids.add(id.toString());
                }
                nodeInfo.setAttribute("ids", ids.toString());
        	}
        }
        
        //store the messages that have already finished their journey
        Element finishedMessages = doc.createElement("FinishedMessages");
        rootElement.appendChild(finishedMessages);
        for(Integer id : this.finishedMessages.keySet()){
        	Element finishedId = doc.createElement("FinishedId");
        	finishedMessages.appendChild(finishedId);
        	finishedId.setAttribute("id", id.toString());
        	for(Message m : this.finishedMessages.get(id)){
        		finishedId.appendChild(m.toXml(doc));
        	}
        }
        
        
        //store the nodes
        Element nodes = doc.createElement("Nodes");
        rootElement.appendChild(nodes);
        for(NetworkNode node : networkNodes.values()){
        	nodes.appendChild(node.toXml(doc));
        }
        
        //store connections
        Element connections = doc.createElement("Connections");
        rootElement.appendChild(connections);
        Set<Set<String>> connectionSet = new HashSet<>();
        for(String nodeId : this.networkNodes.keySet()){
        	for(String nodeId2 : this.networkNodes.get(nodeId).getNeighbourIds()){
        		Set<String> pair = new HashSet<>();
        		pair.add(nodeId);
        		pair.add(nodeId2);
        		connectionSet.add(pair);
        	}
        }
        for(Set<String> connection : connectionSet){
        	Element connectionElement = doc.createElement("Connection");
        	Iterator<String> ittr = connection.iterator();
        	connectionElement.setAttribute("Node1", ittr.next());
        	connectionElement.setAttribute("Node2", ittr.next());
        	connections.appendChild(connectionElement);
        }
        
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new StringWriter());
		
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return result.getWriter().toString();
			
	}
	
	/**
	 * creates a new node with the specified ID
	 * @param id the string ID that the node will have
	 * @return false iff the ID is already taken
	 */
	public boolean createNode(String id) {
		if(networkNodes.containsKey(id)){
			return false;
		}else{
			NetworkNode n = new NetworkNode(id, this);
			//put the node into the network
			networkNodes.put(id, n);
			
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
		if(!networkNodes.containsKey(id)){
			return false;
		}else{
			//put the node into the network
			networkNodes.remove(id);
			
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
		for(String nodeID : new HashSet<>(networkNodes.keySet())){
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
		if(!id1.equals(id2) && networkNodes.containsKey(id1) && networkNodes.containsKey(id2)){
			boolean temp = networkNodes.get(id1).connectTo(networkNodes.get(id2));
			temp = networkNodes.get(id2).connectTo(networkNodes.get(id1)) || temp;
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
		if(networkNodes.containsKey(id1) && networkNodes.containsKey(id2)){
			boolean temp = networkNodes.get(id1).disconnectFrom(networkNodes.get(id2));
			temp = networkNodes.get(id2).disconnectFrom(networkNodes.get(id1)) || temp;
			return temp;
		}else{
			return false;
		}
	}
	
	/**
	 * calls disconnectNodes on each pair of connected nodes
	 */
	public void disconnectAllNodes() {
		for(String nodeId1 : new HashSet<>(networkNodes.keySet())){
			for(String nodeId2 : new HashSet<>(networkNodes.get(nodeId1).getNeighbourIds())){
				disconnectNodes(nodeId1, nodeId2);
			}
		}
	}
	
	/**
	 * Getter for node ID's
	 * 
	 * @return List of all valid node ids
	 */
	public List<String> getNodes() {
		
		ArrayList<String> returnNodes = new ArrayList<String>();
		for(String nodeID: networkNodes.keySet()) {
			returnNodes.add(nodeID);
		}
		
		return returnNodes;
	}
	
	/**
	 * @return a set of neighbor ids to the supplied node, or null if the node does not exist
	 */
	public Set<String> getNodeNeighbors(String nodeId){
		NetworkNode node = this.networkNodes.get(nodeId);
		if(node == null){
			return null;
		}
		return node.getNeighbourIds();
	}
	
	/**
	 * get the message ID for all messages in a given node's buffer
	 * @param nodeId the node who's buffer you want to get
	 * @return an ordered list of message ids, where element 0 is the one that has been there the longest. or null, if the specified node does not exist
	 */
	public List<Integer> getMessageBufferFromNode(String nodeId) {
		if(networkNodes.get(nodeId) != null){
			List<Integer> ids = new ArrayList<>();
			for(Message m : networkNodes.get(nodeId).getMessages()){
				ids.add(m.getId());
			}
			return ids;
		}
		return new ArrayList<>();
	}
	
	/**
	 * 
	 * node, this is not a fast operation
	 * @param messageId the id to check
	 * @return true iff there are instances of messages with this ID still traveling over the network
	 */
	public boolean messageFloating(int messageId){
		for(NetworkNode n : networkNodes.values()){
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
	//UNDUSED
	//public Set<List<String>> getMessagePathsById(int messageId) {
	//	if(finishedMessages.containsKey(Integer.valueOf(messageId))){
	//		Set<List<String>> output = new HashSet<>();
	//		for(Message m : finishedMessages.get(Integer.valueOf(messageId))){
	//			output.add(m.getHistory());
	//		}
	//		return output;
	//	}
	//	return null;
	//}
	
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
		for(NetworkNode n : networkNodes.values()){
			setRoutingAlgorithm(routingAlg, n);
		}
	}
	
	private void setRoutingAlgorithm(RoutingAlgorithm alg, NetworkNode n) {
		n.setRouter(alg.getRouter(n));
	}
	
	/**
	 * @param period the quantity of cycles between creating a message and creating the next.
	 */
	public void setMessageCreationPeriod(int period) {
		if(period > 0){
			this.messageCreationPeriod = period;  
		}else{
			period = 1;
		}
	}
	
	/**
	 * empty all buffers on all nodes in the network
	 */
	public void clearNetworkBuffers() {
		for(NetworkNode n : networkNodes.values()){
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
				if(finishedMessages.containsKey(id)){
					long miniRunningTotal = 0;
					boolean good = false;
					for(Message m : finishedMessages.get(id)){
						miniRunningTotal += m.hops();
						if(m.received()){
							good = true;
						}
					}
					if(good){
						runningTotal+=miniRunningTotal;
						count++;
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
		if(!networkNodes.isEmpty()){
			Set<String> visited = new HashSet<>();
			NetworkNode origen = networkNodes.values().iterator().next();
			visited.add(origen.getId());
			isAConnectedGraphRecursive(origen, visited);
			return visited.containsAll(networkNodes.keySet());
		}
		return false;
	}
	
	private void isAConnectedGraphRecursive(NetworkNode n, Set<String> visited){
		for(String id : n.getNeighbourIds()){
			if(!visited.contains(id)){
				visited.add(id);
				isAConnectedGraphRecursive(networkNodes.get(id), visited);
			}
		}
	}
	
	/**
	 * simulate one cycle
	 */
	public boolean step() {
		if(!this.isAConnectedGraph()){
			return false;
		}
		if((stepNumber++)%messageCreationPeriod == 0 && networkNodes.size()>=2){
			String sender = randomElement(networkNodes.keySet());
			String dest = sender;
			while(sender.equals(dest = randomElement(networkNodes.keySet()))){}
			
			Message m = new Message(messageNumber++, sender, dest);
			
			addMesageIdToMessageIdsBySourceAndDestination(m);
			networkNodes.get(sender).receiveMessage(m);
						
		}
		for(NetworkNode n : networkNodes.values()){
			n.sendMessage();
		}
		for(NetworkNode n : networkNodes.values()){
			n.flushBuffer();
		}
		return true;
	}
	private String randomElement(Set<String> set){
		int i = (int)(Math.random()*((double)set.size()));
		Iterator<String> iter = set.iterator();
		while(i-->0){
			iter.next();
		}
		return iter.next();
	}
	private void addMesageIdToMessageIdsBySourceAndDestination(Message m){
		if(!messageIdsBySourceAndDestination.containsKey(m.getSender())){
			messageIdsBySourceAndDestination.put(m.getSender(), new HashMap<>());
		}
		if(!messageIdsBySourceAndDestination.get(m.getSender()).containsKey(m.getDestination())){
			messageIdsBySourceAndDestination.get(m.getSender()).put(m.getDestination(), new HashSet<>());
		}
		messageIdsBySourceAndDestination.get(m.getSender()).get(m.getDestination()).add(m.getId());
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
		System.out.println("From:"+m.getSender()+" to:"+m.getDestination()+" av. hops:"+this.getAverageHops(m.getSender(), m.getDestination()));
		System.out.println("From:"+m.getSender()+" to:"+m.getDestination()+" av. transmitions:"+this.getAverageTransmitions(m.getSender(), m.getDestination()));
	}
	
	/**
	 * Getter for the nodes in the network
	 * 
	 * @return networkNodes
	 */
	public Map<String,NetworkNode> getNetworkNodes() {
		return networkNodes;
	}
	
}
