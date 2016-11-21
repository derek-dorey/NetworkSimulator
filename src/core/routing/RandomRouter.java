package core.routing;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import core.Message;
import core.Node;

public class RandomRouter implements Router {

	private final Node n;
	
	public RandomRouter(Node n){
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

}
