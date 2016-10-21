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
		boolean outa = nodeNetwork.get(a).addNeighbour(nodeNetwork.get(b));
		boolean outb = nodeNetwork.get(b).addNeighbour(nodeNetwork.get(a));
		return outa || outb;
	}

	public boolean disconnect(String a, String b) {
		boolean outa = nodeNetwork.get(a).removeNeighbour(nodeNetwork.get(b));
		boolean outb = nodeNetwork.get(b).removeNeighbour(nodeNetwork.get(a));
		return outa || outb;
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
