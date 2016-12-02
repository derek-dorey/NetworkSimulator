package core.routing;

import java.util.HashSet;
import java.util.Set;

import core.Message;
import core.NetworkNode;

/**
 * This class is the flooding algorithm that is suppose to be implemented
 * 
 * This algorithm will flood the network with the message in hopes that it eventually
 * reaches the target
 * 
 * It does this by making sure each node sends the message to all of it's neighbours 
 * 
 * @author Griffin
 *
 */
public class FloodRouter implements Router {

	private final NetworkNode node;

	public FloodRouter(NetworkNode n) {
		this.node = n;
	}

	@Override
	public Set<String> route(Message m) {
		Set<String> out = new HashSet<>(node.getNeighbourIds());
		out.removeAll(m.getHistory());
		return out;
	}
	

	@Override
	public RoutingAlgorithm getAlgorithm() {
		return RoutingAlgorithm.FLOOD;
	}
}
