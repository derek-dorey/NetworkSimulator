package core;
/**
 * Makes a print statement for the user that displays the start and destination
 * of the message with the average hops.
 * no equals() or hashcode() because the only equality for a Message should
 * be identity
 * 
 * @author Griffin Barrett
 *
 */
public class Message {
	public final String start;
	public final String destination;
	private int hops = 0;
	private boolean sent;
	

	public Message(String start, String destination) {
		this.start = start;
		this.destination = destination;
		this.sent = false;
	}

	public void incHops() {
		hops++;
	}

	public int getHops() {
		return hops;
	}

	@Override
	public String toString() {
		return "Message from:'" + start + "' to:'" + destination + "' with:" + hops + " hops";
	}
	
	public boolean getSent() {
		return this.sent;
	}
	
	public void setNotSent() {
		this.sent = false;
	}
	
	public void setSent() {
		this.sent = true;
	}

}
