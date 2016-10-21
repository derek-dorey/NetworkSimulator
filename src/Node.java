
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node {

	private final String id;
	private final Set<Node> neighbours;

	public Node(String nodeID) {
		id = nodeID;
		neighbours = new HashSet<Node>();
	}

	public int sendTo(String targetNode) {

		System.out.println(id);

		if (this.id.equals(targetNode)) { // we have reached our target node
			return 0;
		}

		int randomNeighbour = (int) (Math.random() * neighbours.size());

		for (Node currentNode : neighbours) {
			if (randomNeighbour-- <= 0) {
				return (currentNode.sendTo(targetNode) + 1); // if we have not
																// reached our
																// target node,
																// forward
																// target ID to
																// a random
																// neighbour
			}
		}

		System.out.println("Failed to locate target or adjacent neighbour at node: " + this.getID());
		return -1;
	}

	public boolean addNeighbour(Node newNeighbour) {
		return neighbours.add(newNeighbour);
	}

	public boolean removeNeighbour(Node oldNeighbour) {
		return neighbours.remove(oldNeighbour);
	}

	public Set<Node> getNeighbours() {
		return Collections.unmodifiableSet(neighbours);
	}

	public String getID() {
		return id;
	}
}
