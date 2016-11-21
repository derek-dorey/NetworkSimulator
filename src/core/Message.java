package core;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private boolean received = false;
	private final int id;
	private final String sender;
	private final String dest;
	private final List<String> history;
	
	public Message(Message m){
		this.id = m.id;
		this.sender = m.sender;
		this.dest = m.dest;
		this.history = new ArrayList<>(m.history);
	}
	
	public Message(int id, String sender, String dest) {
		this.id = id;
		this.sender = sender;
		this.dest = dest;
		this.history = new ArrayList<>();
	}

	public String getSender(){
		return sender;
	}
	
	public String getDestination(){
		return dest;
	}
	
	public int getId(){
		return id;
	}
	
	public void recordNode(String nodeId){
		history.add(nodeId);
	}
	
	public List<String> getHistory(){
		return new ArrayList<>(history);
	}

	public void setReceived(boolean b){
		received = b;
	}
	
	public boolean received(){
		return received;
	}

	public int hops() {
		return history.size()-1;
	}
}
