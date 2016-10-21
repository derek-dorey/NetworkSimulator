public class Message {
	public final String start;
	public final String destination;
	private int hops = 0;

	public Message(String start, String destination) {
		this.start = start;
		this.destination = destination;
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

	// no equals() or hashcode() because the only equality for a Message should
	// be identity
}
