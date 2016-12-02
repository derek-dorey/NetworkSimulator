package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import gui.GraphHandler;

public class NetworkManager {
	
	private GraphHandler networkListener;
	private Network nodeNetwork;
	
	public NetworkManager() {
		
	}

	public void registerNetworkListener(GraphHandler gh) {
		networkListener = gh;
	}
	
	public void createNode(String id) {
		
	}
	
	public void destroyNode(String id) {
		
	}
	
	public void connectNodes(String idA, String idB) {
		
	}
	
	public void disconnectNodes(String idA, String idB) {
		
	}
	
	public void step() {
		
	}
	
	public void undo() {
		
	}
	
	public void save(OutputStream out) {
		try {
			nodeNetwork.toXml(out);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
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
		
	}

	public void registerNetwork(Network network) {
		nodeNetwork = network;
	}

}
