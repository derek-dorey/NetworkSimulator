package gui;

import core.Network;

public class GUIController {
	
	private final Network network;
	private final NetworkView view;
	
	public GUIController(Network network) {
		
		this.network = network;
		this.view = new NetworkView(network, this);
		NetworkGUI gui = new NetworkGUI(view, this);
		gui.setVisible(true);
		
	}
	
	
	public void create(String nodeName) {
		network.createNode(nodeName);
		view.update();
	}
	
	public void delete(String nodeName) {
		network.removeNode(nodeName);
		view.update();
	}
	
	public void connect(String[] nodes) {
		
		for(int i=0;i<nodes.length-1; i++) {
			network.connect(nodes[i], nodes[i+1]);
			view.update();
		}
	}
	
	public void disconnect(String[] nodes) {
		
		for(int i=0;i<nodes.length-1; i++) {
			network.disconnect(nodes[i], nodes[i+1]);
			view.update();
		}
	}
	
	
	public void setRate(int rate) {
		network.setRate(rate);
		view.update();
	}
	
	
	public void step() {
		network.step();
		view.update();
	}

}
