package core;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Makes a print statement for the user that displays the start and destination
 * of the message with the average hops.
 * no equals() or hashcode() because the only equality for a Message should
 * be identity
 * 
 * @author Griffin Barrett & Derek Dorey
 *
 */
public class Message {
	
	private boolean received = false;
	private final int id;
	private final String sender;
	private final String dest;
	private final List<String> history;
	
	/**
	 * Message Constructor
	 * 
	 * @param m
	 */
	public Message(Message m){
		this.id = m.id;
		this.sender = m.sender;
		this.dest = m.dest;
		this.history = new ArrayList<>(m.history);
	}
	
	/**
	 * Constructor for Message 
	 * 
	 * @param sender
	 * @param dest
	 */
	public Message(int id, String sender, String dest) {
		this.id = id;
		this.sender = sender;
		this.dest = dest;
		this.history = new ArrayList<>();
	}
	
	/**
	 * setter for sender
	 * 
	 * @return sender
	 */
	public String getSender(){
		return sender;
	}
	
	/**
	 * Getter for destination
	 * 
	 * @return dest
	 */
	public String getDestination(){
		return dest;
	}
	
	/**
	 * Getter for ID
	 * 
	 * @return id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Records the node passed by parameter 
	 * 
	 * @param nodeId
	 */
	public void recordNode(String nodeId){
		history.add(nodeId);
	}
	
	/**
	 * Getter for History of message
	 * 
	 * @return ArrayList<>(history)
	 */
	public List<String> getHistory(){
		return new ArrayList<>(history);
	}
	
	/**
	 * Setter for received
	 * 
	 * @param b
	 */
	public void setReceived(boolean b){
		received = b;
	}
	
	/**
	 * Getter for received
	 * 
	 * @return received
	 */
	public boolean received(){
		return received;
	}

	/**
	 * Getter for hops
	 * 
	 * @return history.size()-1
	 */
	public int hops() {
		return history.size()-1;
	}

	public Node toXml(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}
}
