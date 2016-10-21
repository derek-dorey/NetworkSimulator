
import java.util.Set;

public class Node {

	private String id;
	private Set<Node> neighbours;
	
	public Node(String nodeID) {}
	
	public int sendTo(String targetNode) {
		return 0;
	}
	
	public boolean addNeighbour(Node newNeighbour) {
		return true;
	}
	
	public boolean removeNeighbour(Node oldNeighbour) {
		return true;
	}
	
	public Set<Node> getNeighbours() {
		return neighbours;
	}
	
	public String getID() {
		return id;
	}
	
}
