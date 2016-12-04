package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
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
		nodeNetwork.createNode(id);
		for(NetworkListener n: networkListeners){
			n.createNode(id);
		}
	
	}
	
	public void destroyNode(String id) {
		nodeNetwork.destroyNode(id);
		for(NetworkListener n: networkListeners){
			n.destroyNode(id);
		}

	}
	
	public void connectNodes(String idA, String idB) {
		nodeNetwork.connectNodes(idA, idB);
		for(NetworkListener n: networkListeners){
			n.connectNodes(idA, idB);
		}

	}
	
	public void disconnectNodes(String idA, String idB) {
		nodeNetwork.disconnectNodes(idA, idB);
		for(NetworkListener n: networkListeners){
			n.disconnectNodes(idA, idB);
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
			nodeNetwork.toXml(out);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void setAlgorithm(RoutingAlgorithm alg){
		nodeNetwork.setRoutingAlgorithm(alg);
	}
	
	@SuppressWarnings("static-access")
	public void load(InputStream in) {
		try {
			nodeNetwork.fromXml(in);
			
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
			
		}else{
			return;
		}
	}

	public void registerNetwork(Network network) {
		nodeNetwork = network;
	}

}
