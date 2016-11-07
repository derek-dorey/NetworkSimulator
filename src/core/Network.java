package core;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gui.UI;

/**
 * This class adds connections, removes connections from nodes and runs the simulation.
 * Also implements the random path algorithumn and calculates average number of hops.
 * 
 * @author Griffin Barrett
 *
 */
public class Network {
	
	public int rate = 1;		//the rate at which new messages will be injected into the simulation (i.e, for rate = 5, every 5th step will inject a new message)
	public static int stepCount = 0;

	private Map<String, Node> nodeNetwork;
	private Set<Message> results;

	public Network() {
		nodeNetwork = new HashMap<>();
	}

	public boolean create(String id) {
		if (nodeNetwork.containsKey(id)) {
			return false;
		}

		nodeNetwork.put(id, new Node(this, id));
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

	//connects two nodes with given names
	public boolean connect(String a, String b) {
		if (a.equals(b)) {
			return false;
		}
		if (nodeNetwork.containsKey(a) && nodeNetwork.containsKey(b)) {
			boolean outa = nodeNetwork.get(a).addNeighbour(nodeNetwork.get(b));
			boolean outb = nodeNetwork.get(b).addNeighbour(nodeNetwork.get(a));
			return outa || outb;
		} else {
			return false;
		}
	}
	
	//disconnects two nodes with given names
	public boolean disconnect(String a, String b) {
		if (nodeNetwork.containsKey(a) && nodeNetwork.containsKey(b)) {
			boolean outa = nodeNetwork.get(a).removeNeighbour(nodeNetwork.get(b));
			boolean outb = nodeNetwork.get(b).removeNeighbour(nodeNetwork.get(a));
			return outa || outb;
		} else {
			return false;
		}
	}

	public void record(Message m) {
		results.add(m);
	}

	// The Network only supports 1 simulation at a time
	public synchronized void simulate(int steps, int sendInterval) {
		if (nodeNetwork.keySet().size() < 2) {
			System.out.println("The network is too small for a meaningful simulation");
			return;
		}

		results = new HashSet<>();
		
		step();		//step through the simulation according to the user-defined rate

		// sender to (receiver to hop counts)
		Map<String, Map<String, List<Integer>>> collect = new HashMap<>();

		for (Message m : results) {
			if (!collect.containsKey(m.start)) {
				collect.put(m.start, new HashMap<>());
			}
			Map<String, List<Integer>> senderMap = collect.get(m.start);
			if (!senderMap.containsKey(m.destination)) {
				senderMap.put(m.destination, new ArrayList<>());
			}
			senderMap.get(m.destination).add(m.getHops());
		}

		for (String sender : collect.keySet()) {
			Map<String, List<Integer>> senderMap = collect.get(sender);
			for (String destination : senderMap.keySet()) {
				List<Integer> hopCounts = senderMap.get(destination);
				double av = calculateAverage(hopCounts);
				System.out.println("Messages sent from '" + sender + "' to '" + destination + "' took an average of "
						+ av + " hops");
			}
		}

	}

	private void createNewRandomMessage() {
		String[] keys = nodeNetwork.keySet().toArray(new String[] {});

		int from = (int) (Math.random() * keys.length);
		int to = from;

		while (from == to) {
			to = (int) (Math.random() * keys.length);
		}

		send(keys[from], keys[to]);
	}

	private void send(String from, String to) {
		nodeNetwork.get(from).receive(new Message(from, to));
	}

	public Collection<Node> getNodes(){
		return Collections.unmodifiableCollection(nodeNetwork.values());
	}
	
	private double calculateAverage(List<Integer> numbers) {
		if (numbers.isEmpty()) {
			return 0.0;
		} else {
			int sum = 0;
			for (Integer i : numbers) {
				sum += i;
			}
			return ((double) sum) / ((double) numbers.size());
		}
	}
	
	public void step() {
		
		if((stepCount++)%rate==0) {
			createNewRandomMessage();
		}
		
		for (Node n : nodeNetwork.values()) {
			n.send();
		}
	}

	public static void main(String args[]) {

		new UI(new Network()).run();

	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
}
