import java.util.HashMap;
import java.util.Map;

public class Network {

	private Map<String, Node> nodeNetwork;

	public Network() {
		nodeNetwork = new HashMap<>();
	}

	public boolean create(String id) {
		if (nodeNetwork.containsKey(id)) {
			return false;
		}

		nodeNetwork.put(id, new Node(id));
		return true;
	}

	public boolean remove(String id) {
		if (!nodeNetwork.containsKey(id)) {
			return false;
		}

		for (String n : nodeNetwork.keySet()) {
			disconnect(id, n); // disconnect the node from all neighbours (and
								// all neighbours from the node)
		}
		nodeNetwork.remove(id);
		return true;
	}

	public boolean connect(String a, String b) {
		if(a.equals(b)){
			return false;
		}
		if(nodeNetwork.containsKey(a) && nodeNetwork.containsKey(b)){
			boolean outa = nodeNetwork.get(a).addNeighbour(nodeNetwork.get(b));
			boolean outb = nodeNetwork.get(b).addNeighbour(nodeNetwork.get(a));
			return outa || outb;
		}else{
			return false;
		}
	}

	public boolean disconnect(String a, String b) {
		if(nodeNetwork.containsKey(a) && nodeNetwork.containsKey(b)){
			boolean outa = nodeNetwork.get(a).removeNeighbour(nodeNetwork.get(b));
			boolean outb = nodeNetwork.get(b).removeNeighbour(nodeNetwork.get(a));
			return outa || outb;
		}else{
			return false;
		}
	}

	public int send(String from, String to) {
		try {
			if (nodeNetwork.containsKey(from) && nodeNetwork.containsKey(to)) {
				return nodeNetwork.get(from).sendTo(nodeNetwork.get(to).getID());
			} else {
				System.out.println("\n\nOne or more of the nodes involved do not exist");
				return -1;
			}
		} catch (StackOverflowError e) {
			System.out.println("\n\nUnable to get to the target node");
			return -1;
		}
	}

	public static void main(String args[]) {

		new UI(new Network()).run();

	}
}
