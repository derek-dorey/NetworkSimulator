package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;
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
		//nodeNetwork.undo();
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
	
	public void load(InputStream in) {
		try {
			nodeNetwork = Network.fromXml(new BufferedReader(new InputStreamReader(in))
					  .lines().collect(Collectors.joining("\n")));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
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
