package core.routing;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import core.Message;
import core.NetworkNode;

/**
 * This class is the random algorithm that is suppose to be implemented
 * 
 * This algorithm will randomly choose a neighbour in the current node to send a message
 * this will be done repeatedly until the message reaches it's target 
 * 
 * @author Griffin
 *
 */
public class RandomRouter implements Router {

	private final NetworkNode n;
	
	public RandomRouter(NetworkNode n){
		this.n = n;
	}
	
	@Override
	public Set<String> route(Message m) {
		Set<String> set = new HashSet<>();
		int index = (int)(Math.random()*((double)n.getNeighbourIds().size()));
		Iterator<String> iter = n.getNeighbourIds().iterator();
		while(index-->0){iter.next();}
		set.add(iter.next());
		return set;
	}

	@Override
	public RoutingAlgorithm getAlgorithm() {
		return RoutingAlgorithm.RANDOM;
	}
}
