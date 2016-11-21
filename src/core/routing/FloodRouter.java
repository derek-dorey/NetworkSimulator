package core.routing;

import java.util.HashSet;
import java.util.Set;

import core.Message;
import core.Node;

public class FloodRouter implements Router {

	private final Node node;

	public FloodRouter(Node n) {
		this.node = n;
	}

	@Override
	public Set<String> route(Message m) {
		Set<String> out = new HashSet<>(node.getNeighbourIds());
		out.removeAll(m.getHistory());
		return out;
	}
}
