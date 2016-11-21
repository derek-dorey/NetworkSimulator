package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.routing.Router;

public class Node {
	private final String id;
	private final Network network;
	private Map<String,Node> neighbours;
	
	private LinkedList<Message> queue;
	private Set<Message> buffer;
	
	private Router router;
	
	public Node(String id, Network network) {
		this.id = id;
		this.network = network;
		neighbours = new HashMap<>();
		buffer = new HashSet<>();
	}
	
	public boolean connectTo(Node newNeighbour){
		if(neighbours.containsKey(newNeighbour.getId())){
			return false;
		}else{
			neighbours.put(newNeighbour.getId(), newNeighbour);
			return true;
		}
	}
	
	public boolean disconnectFrom(Node exNeighbour){
		if(!neighbours.containsKey(exNeighbour.getId())){
			return false;
		}else{
			neighbours.remove(exNeighbour.getId());
			return true;
		}
	}
	
	public boolean isNeighbour(Node n){
		return isNeighbour(n.getId());
	}
	
	public boolean isNeighbour(String id){
		return neighbours.containsKey(id);
	}
	
	public List<Message> getMessages(){
		return null;
	}
	
	public Set<String> getNeighbourIds(){
		return neighbours.keySet();
	}
	
	public Node getNeighbourFromId(String id){
		return neighbours.get(id);
	}
	
	public String getId(){
		return id;
	}

	public void flushBuffer(){
		for(Message m : buffer){
			m.recordNode(getId());
			queue.push(m);
		}
		buffer = new HashSet<>();
	}
	
	public void dropQueue() {
		flushBuffer();
		while(!queue.isEmpty()){
			network.finalizeMessage(queue.pop());
		}
	}
	
	public void receiveMessage(Message m){
		if(m.getDestination().equals(this.getId())){
			m.recordNode(getId());
			m.setReceived(true);
			network.finalizeMessage(m);
		}
		buffer.add(m);
	}
	
	public void sendMessage(){
		if(!queue.isEmpty()){
			Message toSend = queue.pop();
			Set<String> destinations = router.route(toSend);
			if(!neighbours.keySet().containsAll(destinations)){
				throw new RuntimeException("The router retruned (an) invalid destination(s)");
			}
			if(destinations.isEmpty()){
				//we are being told to drop the message
				toSend.setReceived(false);
				network.finalizeMessage(toSend);
			}else{
				//we are sending the message out to one or more nodes
				List<String> dests = new ArrayList<String>(destinations);
				neighbours.get(dests.get(0)).receiveMessage(toSend);
				for(int i = 1; i<dests.size(); i++){
					neighbours.get(dests.get(i)).receiveMessage(new Message(toSend));
				}
			}
		}
	}

	public void setRouter(Router router) {
		this.router = router;
	}
}








