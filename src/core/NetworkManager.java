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
	
	public boolean createNode(String id) {
		recordHistory();
		if(nodeNetwork.createNode(id)){
			for(NetworkListener n: networkListeners){
				n.createNode(id);
			}
			return true;
		}
		return false;
	}
	
	public boolean destroyNode(String id) {
		recordHistory();
		if(nodeNetwork.destroyNode(id)){
			for(NetworkListener n: networkListeners){
				n.destroyNode(id);
			}
			return true;
		}
		return false;
	}
	
	public boolean connectNodes(String idA, String idB) {
		recordHistory();
		if(nodeNetwork.connectNodes(idA, idB)){
			for(NetworkListener n: networkListeners){
				n.connectNodes(idA, idB);
			}
			return true;
		}
		return false;
	}
	
	public boolean disconnectNodes(String idA, String idB) {
		recordHistory();
		if(nodeNetwork.disconnectNodes(idA, idB)){
			for(NetworkListener n: networkListeners){
				n.disconnectNodes(idA, idB);
			}
			return true;
		}
		return false;
	}
	
	public boolean step() {
		recordHistory();
		if(nodeNetwork.step()){
			Map<String, List<Integer>> status = getNetworkBuffers();
			for(NetworkListener n: networkListeners){
				n.updateMessages(status);
			}
			return true;
		}
		return false;
	}
	
	
	public boolean undo() {
		if(!history.isEmpty()){
			return load(history.pop());
		}
		return false;
	}
	
	public boolean save(OutputStream out) {
		try {
			out.write(nodeNetwork.toXml().getBytes());
			return true;
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}
	
	public void setAlgorithm(RoutingAlgorithm alg){
		recordHistory();
		nodeNetwork.setRoutingAlgorithm(alg);
	}
	
	public boolean load(String in) {
		Network temp;
		try {
			temp = Network.fromXml(in);
		} catch (Throwable t) {
			return false;
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
		return true;
		
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
