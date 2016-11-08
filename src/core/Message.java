package core;
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
	public final String start;
	public final String destination;
	private int hops = 0;
	private boolean sent;
	

	/**
	 * Constructor for Message 
	 * 
	 * @param start
	 * @param destination
	 */
	public Message(String start, String destination) {
		this.start = start;
		this.destination = destination;
		this.sent = false;
	}

	/**
	 * Increments variable hops by 1
	 */
	public void incHops() {
		hops++;
	}

	/**
	 * @return hops
	 */
	public int getHops() {
		return hops;
	}

	
	/**
	 * @return Message in printable string form
	 */
	@Override
	public String toString() {
		return "Message from:'" + start + "' to:'" + destination + "' with:" + hops + " hops";
	}
	
	
	/**
	 * Getter for sent flag
	 */
	public boolean getSent() {
		return this.sent;
	}
	
	/**
	 * Setter for sent flag
	 */
	public void setNotSent() {
		this.sent = false;
	}
	
	/**
	 * Setter for sent flag
	 */
	public void setSent() {
		this.sent = true;
	}

}
