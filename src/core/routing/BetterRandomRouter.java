package core.routing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import core.Message;
import core.NetworkNode;

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
public class BetterRandomRouter implements Router {

	private final NetworkNode node;
	
	private final Map<String,String> recordTabel;
	
	public BetterRandomRouter(NetworkNode n) {
		this.node = n;
		this.recordTabel = new HashMap<>();
	}

	private Set<String> randomElementInSetFromCollection(Collection<String> c){
		List<String> posibilities = new ArrayList<>(c); 
		Set<String> destination = new HashSet<>();
		int index = (int)(Math.random()*((double)posibilities.size()));
		Iterator<String> iter = posibilities.iterator(); 
		while(index-->0){iter.next();}
		destination.add(iter.next());
		return destination;
	}
	
	@Override
	public Set<String> route(Message m) {
		List<String> posibilities = new ArrayList<>(node.getNeighbourIds());
		posibilities.removeAll(m.getHistory());
		if(!posibilities.isEmpty()){
			return randomElementInSetFromCollection(posibilities);
		}else{
			posibilities = new ArrayList<>(node.getNeighbourIds());
			List<String> history = new ArrayList<>(m.getHistory());
			history.retainAll(posibilities);
			Map<String,Integer> frequency = new HashMap<>();
			for(String s : history){
				if(frequency.get(s) == null){
					frequency.put(s,Integer.valueOf(0));
				}
				frequency.put(s, frequency.get(s)+1);
			}
			
			Integer goalFrequency = Integer.MAX_VALUE;
			for(Integer i : frequency.values()){
				if(i<goalFrequency){
					goalFrequency = i;
				}
			}
			
			posibilities = new ArrayList<>();
			for(Entry<String,Integer> e : frequency.entrySet()){
				if(e.getValue().equals(goalFrequency)){
					posibilities.add(e.getKey());
				}
			}
			return randomElementInSetFromCollection(posibilities);
			
		}
	}

	@Override
	public RoutingAlgorithm getAlgorithm() {
		return RoutingAlgorithm.BETTER_RANDOM;
	}
}
