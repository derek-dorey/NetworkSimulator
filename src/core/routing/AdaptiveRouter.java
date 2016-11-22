package core.routing;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.Message;
import core.Node;

/**
 * This class is the custom algorithm that we are suppose to implement
 * 
 * This algorithm is "random" however it records where it has been before
 * and will not go back there unless it absolutely has to get to it's target
 * 
 * This algorithm also "learns" about the network it is in and gets better over time 
 * 
 * @author Griffin
 *
 */
public class AdaptiveRouter implements Router {

	private final Node node;
	private final Router randomRouter;
	
	private final Map<String,String> recordTabel;
	
	public AdaptiveRouter(Node n) {
		this.node = n;
		this.recordTabel = new HashMap<>();
		randomRouter = new RandomRouter(n);
	}

	@Override
	public Set<String> route(Message m) {
		List<String> history = m.getHistory();
		if(history.size() > 1){
			recordTabel.put(m.getSender(), history.get(history.size()-2));
		}
		if(Collections.frequency(history, node.getId())<=1 && recordTabel.get(m.getDestination()) != null){
			Set<String> out = new HashSet<String>();
			out.add(recordTabel.get(m.getDestination()));
			return out;
		}else{
			return randomRouter.route(m);
		}
	}
}
