
import java.util.HashSet;
import java.util.Set;

public class Node {

	private String id;
	private Set<Node> neighbours;
	
	public Node(String nodeID) {
		
		id = nodeID;
		neighbours = new HashSet<Node>();
	}
	
	public int sendTo(String targetNode) {
		
		System.out.println(id);
		
		int count=0;
		int randomNeighbour = (int)(Math.random()*neighbours.size());
		
		if(this.id==targetNode) {        //we have reached our target node
			return 0;
		}
		
		for(Node currentNode : neighbours) {
			if(randomNeighbour == count) {
				return (currentNode.sendTo(targetNode)+1);	//if we have not reached our target node, forward target ID to a random neighbour
			}
			count++;
		}
		
		System.out.println("Failed to locate target or adjacent neighbour at node: " + this.getID());
		return -1;		
	}
	
	public boolean addNeighbour(Node newNeighbour) {
		
		if(neighbours.isEmpty()) {
			neighbours.add(newNeighbour);
		}
		
		else if(neighbours.contains(newNeighbour)) {
			return false;
		}
		
		else {neighbours.add(newNeighbour);}
		return true;
	}
	
	public boolean removeNeighbour(Node oldNeighbour) {
		
		if(!neighbours.contains(oldNeighbour)) {
			return false;
		}
		
		else {neighbours.remove(oldNeighbour);}
		return true;
	}
	
	public Set<Node> getNeighbours() {
		return neighbours;
	}
	
	public String getID() {
		return id;
	}
	
	
}
