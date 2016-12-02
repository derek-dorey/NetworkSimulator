package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import gui.GraphHandler;

public class NetworkManager {
	
	private GraphHandler networkListener;
	private Network nodeNetwork;
	
	public NetworkManager(Network n) {
		nodeNetwork = n;
	}

	public void registerNetworkListener(GraphHandler gh) {
		networkListener = gh;
	}
	
	public void createNode(String id) {
		
		nodeNetwork.createNode(id);
		
	}
	
	public void destroyNode(String id) {
		nodeNetwork.destroyNode(id);
		
	}
	
	public void connectNodes(String idA, String idB) {
		nodeNetwork.connectNodes(idA, idB);
	}
	
	public void disconnectNodes(String idA, String idB) {
		nodeNetwork.disconnectNodes(idA, idB);
	}
	
	public void step() {
		nodeNetwork.step();
	}
	
	public void undo() {
		//ask
	}
	
	public void save(OutputStream out) {
		try {
			nodeNetwork.toXml(out);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void setAlgorithum(){
		
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
