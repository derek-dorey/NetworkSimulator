package core;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Node class takes care of Creating nodes, and sets and removes neighbours.
 * Also sends and receives the messages that the simulation passes.
 * 
 * @author Derek Dorey & Griffin Barrett
 *
 */
public class Node {

	private final String id;
	private final Set<Node> neighbours;
	
	//This is a LinkedList because it is both a list and a queue
	private LinkedList<Message> buffer;	
	
	private final Network network;
	
	/**
	 * constructor for node object
	 */
	public Node(Network network, String nodeID) {
		id = nodeID;
		neighbours = new HashSet<>();
		buffer = new LinkedList<>();
		this.network = network;
	}

	/**
	 * method that receives the message and lets the network edit it's
	 * results via record method
	 */
	public void receive(Message m) {
		if (m.destination.equals(id)) {
			network.record(m);
		} else {
			buffer.add(m);
		}
	}
	
	/**
	 * method that prepares a message to be sent and
	 * ensure that each message is only forwarded once per step
	 */
	public void send() {
		Message m = buffer.poll(); 
		if(m != null) {		
			m.incHops();
			
			if(!m.getSent()) { 
				send(m);
				m.setSent();
			}
		}
	}

	/**
	 * method that sends a message to a random neighbour, 
	 * determined by a random number
	 */
	private void send(Message m) {
		int randomNeighbour = (int) (Math.random() * neighbours.size());

		for (Node currentNode : neighbours) {
			if (randomNeighbour-- <= 0) {
				currentNode.receive(m);
				return;
			}
		}
		System.out.println(m + " was not forwarded from node:" + id);
	}

	/**
	 * adds a neighbour to a node
	 */
	public boolean addNeighbour(Node newNeighbour) {
		return neighbours.add(newNeighbour);
	}

	/**
	 * removes a neighbour from a node
	 */
	public boolean removeNeighbour(Node oldNeighbour) {
		return neighbours.remove(oldNeighbour);
	}
	
	/**
	 * gets the neighbours of the node
	 */
	public Set<Node> getNeighbours() {
		return Collections.unmodifiableSet(neighbours);
		
	}

	/**
	 * gets the string ID of the node
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * gets the contents of the buffer within the node
	 */
	public List<Message> getBufferContents(){
		return Collections.unmodifiableList(buffer);
	}
	
	/**
	 * gets the network that the node is in
	 */
	public Network getNetwork() {
		return network;
	}
}
