package core;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import core.routing.RoutingAlgorithm;

public class NetworkManager {
	
	private Set<NetworkListener> networkListeners = new HashSet<>();
	private Network nodeNetwork;
	private Stack<String> history = new Stack<String>();
	
	public NetworkManager() {
	}

	public void registerNetworkListener(NetworkListener nl) {
		networkListeners.add(nl);
	}
	
	public void createNode(String id) {
		recordHistory();
		if(nodeNetwork.createNode(id)){
			for(NetworkListener n: networkListeners){
				n.createNode(id);
			}
		}
	}
	
	public void destroyNode(String id) {
		recordHistory();
		if(nodeNetwork.destroyNode(id)){
			for(NetworkListener n: networkListeners){
				n.destroyNode(id);
			}
		}
	}
	
	public void connectNodes(String idA, String idB) {
		recordHistory();
		if(nodeNetwork.connectNodes(idA, idB)){
			for(NetworkListener n: networkListeners){
				n.connectNodes(idA, idB);
			}
		}
	}
	
	public void disconnectNodes(String idA, String idB) {
		recordHistory();
		if(nodeNetwork.disconnectNodes(idA, idB)){
			for(NetworkListener n: networkListeners){
				n.disconnectNodes(idA, idB);
			}
		}
	
	}
	
	public void step() {
		recordHistory();
		nodeNetwork.step();
		
		Map<String, List<Integer>> status = getNetworkBuffers();
		for(NetworkListener n: networkListeners){
			n.updateMessages(status);
		} 
	}
	
	
	public void undo() {
		if(!history.isEmpty()){
			load(history.pop());
		}
	}
	
	public void save(OutputStream out) {
		try {
			out.write(nodeNetwork.toXml().getBytes());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public void setAlgorithm(RoutingAlgorithm alg){
		recordHistory();
		nodeNetwork.setRoutingAlgorithm(alg);
	}
	
	public void load(String in) {
		Network temp;
		try {
			temp = Network.fromXml(in);
		} catch (Throwable t) {
			System.err.println("Network reload failed.");
			t.printStackTrace();
			return;
		}
		nodeNetwork = temp;
		Map<String,List<Integer>> status = getNetworkBuffers();
		
		for(NetworkListener n: networkListeners){
			n.clearAll();
			for(String nodeId : nodeNetwork.getNodes()){
				n.createNode(nodeId);
			}
			for(String nodeId : nodeNetwork.getNodes()){
				for(String nodeId2 : nodeNetwork.getNodeNeighbors(nodeId)){
					n.connectNodes(nodeId, nodeId2);
				}
			}
			n.updateMessages(status);
		}
		
	}
	
	public void setRate(int rate) {
		recordHistory();
		if(rate>0) {
			nodeNetwork.setMessageCreationPeriod(rate);
		}
	}
	
	private Map<String, List<Integer>> getNetworkBuffers() {
		
		Map<String, List<Integer>> nodeBuffers = new HashMap<String, List<Integer>>();
		
		for(String nodeID : nodeNetwork.getNodes()) {
			nodeBuffers.put(nodeID, nodeNetwork.getMessageBufferFromNode(nodeID));
		}
		
		return nodeBuffers;	
	}

	private void recordHistory(){
		try {
			history.push(this.nodeNetwork.toXml());
		} catch (TransformerConfigurationException | ParserConfigurationException e) {
			System.err.println("Recording history failed.");
			e.printStackTrace();
		}
	}
	
	public void registerNetwork(Network network) {
		nodeNetwork = network;
	}

}
