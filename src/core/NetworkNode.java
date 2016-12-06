package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.routing.Router;
import core.routing.RoutingAlgorithm;

/**
 * This class adds connections, removes connections from nodes, 
 * sends messages between nodes, and runs the simulation.
 * 
 * 
 * @author Derek Dorey & Griffin Barrett
 *
 */
public class NetworkNode {
	private final String id;
	private final Network network;
	private Map<String,NetworkNode> neighbours;
	
	private LinkedList<Message> queue;
	private Set<Message> buffer;
	
	private Set<Integer> acceptedMessages = new HashSet<>();
	
	private Router router;
	
	/**
	 * constructor for node object
	 * 
	 * @param id
	 * @param network
	 */
	public NetworkNode(String id, Network network) {
		this.id = id;
		this.network = network;
		neighbours = new HashMap<>();
		buffer = new HashSet<>();
		queue = new LinkedList<>();
	}
	
	
	public static NetworkNode fromXml(Element item, Network network) {
		if("Node".equals(item.getTagName())){
			NetworkNode n = new NetworkNode(item.getAttribute("id"), network);
			n.router = RoutingAlgorithm.valueOf(RoutingAlgorithm.class, item.getAttribute("router")).getRouter(n);
			NodeList msgs = item.getElementsByTagName("Messages").item(0).getChildNodes();
			for(int i = 0; i<msgs.getLength(); i++){
				if(msgs.item(i).getNodeType() == Node.ELEMENT_NODE){
					Message m = Message.fromXml((Element) msgs.item(i));
					if(m != null){
						n.queue.add(m);
					}
				}
			}
			return n;
		}
		return null;
	}

	public Node toXml(Document doc) {
		Element elm = doc.createElement("Node");
		elm.setAttribute("id", id);
		elm.setAttribute("router", router.getAlgorithm().name());
		
		Element ms = doc.createElement("Messages");
		elm.appendChild(ms);
		
		for(Message m : queue){
			ms.appendChild(m.toXml(doc));
		}
		
		return elm;
	}
	/**
	 * checks if the two new neighbours are connected
	 * 
	 * @param newNeighbour
	 * 
	 * @return false if not
	 * @return true if they are
	 */
	public boolean connectTo(NetworkNode newNeighbour){
		if(neighbours.containsKey(newNeighbour.getId())){
			return false;
		}else{
			neighbours.put(newNeighbour.getId(), newNeighbour);
			return true;
		}
	}
	
	/**
	 * checks if the two former neighbours are disconnected
	 * 
	 * @param exNeighbour
	 * 
	 * @return false if not
	 * @return true if they are
	 */
	public boolean disconnectFrom(NetworkNode exNeighbour){
		if(!neighbours.containsKey(exNeighbour.getId())){
			return false;
		}else{
			neighbours.remove(exNeighbour.getId());
			return true;
		}
	}
	
	/**
	 * checks if specific node is a neighbour of a node
	 * 
	 * @param n
	 * 
	 * @return true if they are
	 * @return false if not
	 */
	public boolean isNeighbour(NetworkNode n){
		return isNeighbour(n.getId());
	}
	
	/**
	 * checks if specific node is a neighbour of a node
	 * 
	 * @param id
	 * 
	 * @return true if they are
	 * @return false if not
	 */
	public boolean isNeighbour(String id){
		return neighbours.containsKey(id);
	}
	
	public List<Message> getMessages(){
		return new ArrayList<>(queue);
	}
	
	/**
	 * Getter for neighbour id's
	 * 
	 * @return neighbours.keySet()
	 * 
	 */
	public Set<String> getNeighbourIds(){
		return neighbours.keySet();
	}
	
	/**
	 * Getter for neighbour based on a string id
	 * 
	 * @param id
	 * 
	 * @return neighbours.get(id)
	 */
	public NetworkNode getNeighbourFromId(String id){
		return neighbours.get(id);
	}
	
	/**
	 * Getter for string id
	 * 
	 * @return id
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * resets the buffer and makes a new hashset for it
	 */
	public void flushBuffer(){
		for(Message m : buffer){
			m.recordNode(getId());
			queue.add(m);
		}
		buffer = new HashSet<>();
	}
	/**
	 * resets the buffer and makes a new hashset for it
	 * while the queue is not empty will pop any existing messages
	 */
	public void dropQueue() {
		flushBuffer();
		while(!queue.isEmpty()){
			network.finalizeMessage(queue.pop());
		}
	}
	
	/**
	 * receives the message sent to it from another node
	 * records where the message came from and setReceived is 
	 * turned to true
	 * 
	 * the message m is added to the buffer
	 * 
	 * @param m
	 * 
	 */
	public void receiveMessage(Message m){
		if(m.getDestination().equals(this.getId())){
			m.recordNode(getId());
			m.setReceived(acceptedMessages.add(m.getId()));
			network.finalizeMessage(m);
		}else{
			buffer.add(m);
		}
	}
	
	/**
	 * sends the message sent to it from another node
	 * can cancel the message if destination is empty
	 * 
	 */
	public void sendMessage(){
		if(!queue.isEmpty()){
			Message toSend = queue.pop();
			Set<String> destinations = router.route(toSend);
			if(!neighbours.keySet().containsAll(destinations)){
				throw new RuntimeException("The router retruned (an) invalid destination(s)");
			}
			if(destinations.isEmpty()){
				//we are being told to drop the message
				toSend.setReceived(false);
				network.finalizeMessage(toSend);
			}else{
				//we are sending the message out to one or more nodes
				List<String> dests = new ArrayList<String>(destinations);
				neighbours.get(dests.get(0)).receiveMessage(toSend);
				for(int i = 1; i<dests.size(); i++){
					neighbours.get(dests.get(i)).receiveMessage(new Message(toSend));
				}
			}
		}
	}
	
	/**
	 * setter for router
	 * 
	 * @param router
	 * 
	 */
	public void setRouter(Router router) {
		this.router = router;
	}

	
	public boolean hasNeighbours() {
		return !neighbours.isEmpty();
	}
}








