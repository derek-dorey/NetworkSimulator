package core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Node {
	private final String id;
	private Map<String,Node> neighbours;
	
	protected Node(String id) {
		this.id = id;
		neighbours = new HashMap<>();
	}
	
	protected boolean connectTo(Node newNeighbour){
		if(neighbours.containsKey(newNeighbour.getId())){
			return false;
		}else{
			neighbours.put(newNeighbour.getId(), newNeighbour);
			return true;
		}
	}
	
	protected boolean disconnectFrom(Node exNeighbour){
		if(!neighbours.containsKey(exNeighbour.getId())){
			return false;
		}else{
			neighbours.remove(exNeighbour.getId());
			return true;
		}
	}
	
	protected boolean isNeighbour(Node n){
		return isNeighbour(n.getId());
	}
	
	protected boolean isNeighbour(String id){
		return neighbours.containsKey(id);
	}
	
	public List<Message> getMessages(){
		return null;
	}
	
	public Set<String> getNeighbourIds(){
		return neighbours.keySet();
	}
	
	public String getId(){
		return id;
	}

	public void dropBuffer() {
		// TODO Auto-generated method stub
		
	}
}
