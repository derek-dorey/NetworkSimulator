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
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import core.routing.RoutingAlgorithm;

public class NetworkManager {
	
	private Set<NetworkListener> networkListeners = new HashSet<>();
	private Network nodeNetwork;
	
	public NetworkManager() {
	}

	public void registerNetworkListener(NetworkListener nl) {
		networkListeners.add(nl);
		
	}
	
	public void createNode(String id) {
		if(nodeNetwork.createNode(id)){
			for(NetworkListener n: networkListeners){
				n.createNode(id);
			}
		}
	}
	
	public void destroyNode(String id) {
		if(nodeNetwork.destroyNode(id)){
			for(NetworkListener n: networkListeners){
				n.destroyNode(id);
			}
		}
	}
	
	public void connectNodes(String idA, String idB) {
		if(nodeNetwork.connectNodes(idA, idB)){
			for(NetworkListener n: networkListeners){
				n.connectNodes(idA, idB);
			}
		}
	}
	
	public void disconnectNodes(String idA, String idB) {
		if(nodeNetwork.disconnectNodes(idA, idB)){
			for(NetworkListener n: networkListeners){
				n.disconnectNodes(idA, idB);
			}
		}
	
	}
	
	public void step() {
		nodeNetwork.step();
		/*for(NetworkListener n: networkListeners){
			//network is missing a method which returns (Map<String,List<integers>> status)
			//n.updateMessages(status);
			
		}*/
	
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
		nodeNetwork.setRoutingAlgorithm(alg);
	}
	
	@SuppressWarnings("static-access")
	public void load(InputStream in) {
		try {
			nodeNetwork.fromXml(new BufferedReader(new InputStreamReader(in))
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
		if(rate>0) {
			nodeNetwork.setMessageCreationPeriod(rate);
		}
	}
	
	public Map<String, List<Integer>> getBuffers() {
		
		Map<String, List<Integer>> nodeBuffers = new HashMap<String, List<Integer>>();
		
		for(String nodeID : nodeNetwork.getNodes()) {
			nodeBuffers.put(nodeID, nodeNetwork.getMessageBufferFromNode(nodeID));
		}
		
		return nodeBuffers;	
	}

	public void registerNetwork(Network network) {
		nodeNetwork = network;
	}

}
