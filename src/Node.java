
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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
	private Collection<Message> buffer1;// new this cycle
	private Collection<Message> buffer2;// to be sent this cycle
	private final Network network;

	public Node(Network network, String nodeID) {
		id = nodeID;
		neighbours = new HashSet<>();
		buffer1 = new ArrayList<>();
		buffer2 = new ArrayList<>();
		this.network = network;
	}

	public void receive(Message m) {
		if (m.destination.equals(id)) {
			network.record(m);
		} else {
			buffer1.add(m);
		}
	}

	public void prepairToSend() {
		buffer2 = buffer1;
		buffer1 = new ArrayList<>();
	}

	public void send() {
		for (Message m : buffer2) {
			m.incHops();
			send(m);
		}
	}

	//sends message to random neighbour, determined by a random number
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

	public boolean addNeighbour(Node newNeighbour) {
		return neighbours.add(newNeighbour);
	}

	public boolean removeNeighbour(Node oldNeighbour) {
		return neighbours.remove(oldNeighbour);
	}

	//getters and setters
	public Set<Node> getNeighbours() {
		return Collections.unmodifiableSet(neighbours);
	}

	public String getID() {
		return id;
	}
}
