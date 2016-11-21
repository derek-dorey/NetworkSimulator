package core.routing;

import java.util.HashSet;
import java.util.Set;

import core.Message;
import core.Node;

public class RandomRouter implements Router {

	final Node n;
	
	public RandomRouter(Node n){
		this.n = n;
	}
	
	@Override
	public Set<String> route(Message m) {
		return new HashSet<>();
	}

}
