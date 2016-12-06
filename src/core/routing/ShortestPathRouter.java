package core.routing;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import core.Message;
import core.NetworkNode;

/**
 * This class is the Shortest path algorithm that is suppose to be implemented
 * 
 * This algorithm will get the message for the starting point to the ending
 * point in the shortest amount of hops
 * 
 * It does this by first going through the network and finding a path with the
 * least hops by comparing it to every possible path from the starting point to
 * the end
 * 
 * @author Griffin, Ben
 *
 */
public class ShortestPathRouter implements Router {

	private final NetworkNode node;

	public ShortestPathRouter(NetworkNode n) {
		this.node = n;

	}

	@Override
	public Set<String> route(Message m) {
		Map<String, NetworkNode> nodes = new HashMap<>();
		Map<String, Integer> distances = new HashMap<>();
		// destination to source
		Map<String, String> path = new HashMap<>();
		LinkedList<String> checking = new LinkedList<>();

		nodes.put(node.getId(), node);
		distances.put(node.getId(), Integer.valueOf(0));
		checking.add(node.getId());

		String currentId = node.getId();
		while ((!checking.isEmpty()) && !m.getDestination().equals(currentId = checking.poll())) {
			NetworkNode currentNode = nodes.get(currentId);
			for (String id : currentNode.getNeighbourIds()) {
				nodes.put(id, currentNode.getNeighbourFromId(id));
				if (!distances.containsKey(id) || distances.get(id) < distances.get(currentNode)) {
					path.put(id, currentId);
					distances.put(id, distances.get(currentNode) + 1);
				}
				if(!checking.contains(id)){
					checking.add(id);
					Collections.sort(checking, new Comparator<String>() {
						@Override
						public int compare(String arg0, String arg1) {
							return distances.get(arg1) - distances.get(arg0);
						}
					});
				}
			}
		}
		if(m.getDestination().equals(currentId)){
			String lastId = currentId;
			while(!node.getId().equals(currentId)){
				lastId = currentId;
				currentId = path.get(currentId);
			}
			Set<String> out = new HashSet<>();
			out.add(lastId);
			return out;
			
		}else{
			//return an empty set, we could not find a path 
			return new HashSet<>();
		}
	}

	@Override
	public RoutingAlgorithm getAlgorithm() {
		return RoutingAlgorithm.SHORTEST_PATH;
	}
}
