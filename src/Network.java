import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Network {
	
	private Map<String,Node> nodeNetwork;
	
	public Network() {
		nodeNetwork = new HashMap<>();
	}
	
	public boolean create(String id) {
		if(nodeNetwork.containsKey(id)) {
			return false;
		}
		
		Node newNode = new Node(id);
		nodeNetwork.put(id, newNode);
		return true;
	}
	
	public boolean remove(String id) {
		
		if(!nodeNetwork.containsKey(id)) {
			return false;
		}
		
		Set<Node> nodeNeighbours = new HashSet<Node>();
		nodeNeighbours = nodeNetwork.get(id).getNeighbours();
		
		
		for(Node currentNeighbour : nodeNeighbours) {
			disconnect(nodeNetwork.get(id).getID(),currentNeighbour.getID()); //disconnect the node from all neighbours (and all neighbours from the node)
		}
		nodeNetwork.remove(id);
		return true;
	}
	
	public boolean connect(String a, String b) {
		
		if(!nodeNetwork.containsKey(a) || !nodeNetwork.containsKey(b)) {  //check if both nodes exist in the network
			return false;
		}
		
		nodeNetwork.get(a).addNeighbour(nodeNetwork.get(b)); //add b to a's neighbours
		nodeNetwork.get(b).addNeighbour(nodeNetwork.get(a)); //add a to b's neighbours
		return true;
	}
	
	public boolean disconnect(String a, String b) {
		
		if(!nodeNetwork.containsKey(a) || !nodeNetwork.containsKey(b)) {  //check if both nodes exist in the network
			return false;
		}
		
		nodeNetwork.get(a).removeNeighbour(nodeNetwork.get(b)); //remove b from a's neighbours
		nodeNetwork.get(b).removeNeighbour(nodeNetwork.get(a)); //remove a from b's neighbours
		return true;
	}
	
	public int send(String from, String to) {
		
		if(nodeNetwork.containsKey(from) && nodeNetwork.containsKey(to)) {
			return nodeNetwork.get(from).sendTo(nodeNetwork.get(to).getID());
		}
		
		else return 0;
	}
	
	public static void main(String args[]) {

		Network testNetwork = new Network();
		
		testNetwork.create("a");
		testNetwork.create("b");
		testNetwork.create("c");
		testNetwork.create("d");
		testNetwork.create("e");
	
		testNetwork.connect("a", "b");
		testNetwork.connect("a", "c");
		testNetwork.connect("c", "d");
		testNetwork.connect("d", "b");
		testNetwork.connect("a", "e");
		testNetwork.connect("b", "e");
		
		testNetwork.remove("d");
		
		int number_of_hops = testNetwork.send("a", "e");
		
		System.out.println("number of hops: " + number_of_hops);
	}
	
}
