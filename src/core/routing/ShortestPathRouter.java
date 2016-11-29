package core.routing;

import java.util.HashSet;
import java.util.Set;
import core.Message;
import core.NetworkNode;

/**
 * This class is the Shortest path algorithm that is suppose to be implemented
 * 
 * This algorithm will get the message for the starting point to the ending
 * point in the shortest amount of hops
 * 
 * It does this by first going through the network and finding a path with the least
 * hops by comparing it to every possible path from the starting point to the end
 * 
 * @author Griffin
 *
 */
public class ShortestPathRouter implements Router {

	private final NetworkNode node;

	public ShortestPathRouter(NetworkNode n) {
		this.node = n;
	}

	@Override
	public Set<String> route(Message m) {
		Set<String> out = new HashSet<>();
		Set<String> neighborIds = node.getNeighbourIds();
		int hops = 0;
		
		try{
		bigLoop: while (true) {
			for (String id : neighborIds) {
				if (isNumberOfHopsToDest(node.getNeighbourFromId(id), m.getDestination(), hops)) {
					out.add(id);
					break bigLoop;
				}
			}
			hops++;
		}
		}catch(StackOverflowError e){
			System.out.println("The search for the shortest path failed. This means that there is no path shorter than the max stack size.");
			return new HashSet<>();
		}
		return out;
	}

	/**
	 * Method that checks the number of hops from start to destination
	 * 
	 * @param n
	 * @param destId
	 * @param hops
	 * 
	 * @return true if destination is start and if there is a valid path from start to end
	 * @return false if if statements are missed (meaning valid path does not exist)
	 */
	private boolean isNumberOfHopsToDest(NetworkNode n, String destId, int hops) {
		if (hops == 0) {
			return n.getId().equals(destId);
		} else {
			for (String neighborId : n.getNeighbourIds()) {
				if (isNumberOfHopsToDest(n.getNeighbourFromId(neighborId), destId, hops - 1)) {
					return true;
				}
			}
		}
		return false;
	}

}
