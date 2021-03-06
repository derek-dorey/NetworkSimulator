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
 * @author Derek Dorey & Griffin Barrett
 *
 */
public class Network {
	
	public int rate = 1;		//the rate at which new messages will be injected into the simulation (i.e, for rate = 5, every 5th step will inject a new message)
	public static int stepCount = 0;

	private Map<String, Node> nodeNetwork;
	private Set<Message> results;

	/**
	 * Network Constructor
	 */
	public Network() {
		results = new HashSet<>();
		nodeNetwork = new HashMap<>();
	}

	/**
	 * creates a node within the network with the string which was
	 * taken in as an argument 
	 * @return boolean
	 */
	public boolean createNode(String id) {
		if (nodeNetwork.containsKey(id)) {
			return false;
		}

		nodeNetwork.put(id, new Node(this, id));
		return true;
	}
	
	public boolean emptyResult(){
		if(results.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if it has the node with the string identifier entered
	 * For testing purposes
	 * @author Benjamin Tobalt
	 * @return boolean
	 */
	public boolean hasNode(String id){
		if (nodeNetwork.containsKey(id)) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * returns the rate
	 * @return integer value of rate
	 */
	public int getRate(){
		
		return this.rate;
	}
	
	
	/**
	 * returns true if the node ID's entered are neighbors
	 * For testing connect and disconnect
	 * @author bentobalt
	 * @return boolean
	 */
	public boolean hasConnection(String a, String b){
		
		//parse through the neighbors of node (String a)
		for(Node c :nodeNetwork.get(a).getNeighbours()){
			
			//check if any of the neighbors id is (string b)
			if(c.getID() == b){
				return true;
			}else{
				return false;
			}
		}
		return false;
		
	}
	

	/**
	 * removes the node with the same id 
	 * @return boolean
	 */
	public boolean removeNode(String id) {
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

	
	/**
	 * connects the two nodes with the id's enter if they exist 
	 * @return boolean
	 *
	 */
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
	
	/**
	 * disconnects the two nodes with the id's enter if they exist 
	 * @return boolean
	 *
	 */
	public boolean disconnect(String a, String b) {
		if (nodeNetwork.containsKey(a) && nodeNetwork.containsKey(b)) {
			boolean outa = nodeNetwork.get(a).removeNeighbour(nodeNetwork.get(b));
			boolean outb = nodeNetwork.get(b).removeNeighbour(nodeNetwork.get(a));
			return outa || outb;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Adds the Message to the 'set' holding the messages  
	 * @param Message 
	 */

	public void record(Message m) {
		try {
			results.add(m);
		} catch (NullPointerException e) {
			System.out.println("invalid call to record");
			System.out.println(m.toString());
			System.out.println(results.toString());
		}
	}

	
	
	/**
	 * The Network only supports 1 simulation at a time
	 * @param steps
	 * @param sendInterval
	 */
	public synchronized void simulate(int steps, int sendInterval) {
		if (nodeNetwork.keySet().size() < 2) {
			System.out.println("The network is too small for a meaningful simulation");
			return;
		}

		//results = new HashSet<>();
		
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

	/**
	 * randomly inserts a new message into a node's buffer
	 */
	private void createNewRandomMessage() {
		String[] keys = nodeNetwork.keySet().toArray(new String[] {});

		int from = (int) (Math.random() * keys.length);
		int to = from;

		while (from == to) {
			to = (int) (Math.random() * keys.length);
		}

		send(keys[from], keys[to]); //randomly insert a new message into a node's buffer
	}

	/**
	 * Creates Message Object
	 * @param from
	 * @param to
	 */
	private void send(String from, String to) {
		nodeNetwork.get(from).receive(new Message(from, to)); 
	}

	/**
	 * Getter for the nodes of the network
	 * @return Collection of nodes
	 */
	public Collection<Node> getNodes(){
		return Collections.unmodifiableCollection(nodeNetwork.values());
	}
	
	/**
	 * Calculates the average hops
	 * @return average as a Double
	 */
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
	
	/**
	 *Steps through the simulation
	 */
	public void step() {
		
		if((stepCount++)%rate==0) {  //create a new message when the number of calls to step() equals the simulation rate
			createNewRandomMessage();
		}
		
		for (Node n : nodeNetwork.values()) {
			n.send();
		}
		
		for (Node n : nodeNetwork.values()) {  //now that the messages have been forwarded, reset their flags for the next call to step()
			for (Message m : n.getBufferContents()) {
				m.setNotSent();
			}
		}
		
	}

	public static void main(String args[]) {

		new UI(new Network()).run();

	}
	
	/**
	 * Setter for rate 
	 * @param rate
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public int numberOfNodes() {
		return nodeNetwork.size();
	}
	
	
	
}
