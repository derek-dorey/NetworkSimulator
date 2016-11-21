package core.routing;

import java.util.HashSet;
import java.util.Set;

import core.Message;
import core.Node;

public class ShortestPathRouter implements Router {

	private final Node node;

	public ShortestPathRouter(Node n) {
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

	private boolean isNumberOfHopsToDest(Node n, String destId, int hops) {
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
