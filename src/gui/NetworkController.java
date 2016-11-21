package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import core.Network;
import core.RoutingAlgorithm;

public class NetworkController implements ActionListener {

	private static Network model;
	private static Frame view;
	
	public NetworkController(Network m, Frame v) {
		model = m;
		view = v;
	}
	
	public static void main(String[] args) {
		
		model = new Network(RoutingAlgorithm.RANDOM);
		view = new Frame();
		NetworkController controller = new NetworkController(model, view);
		view.addNetworkController(controller);
		view.addNetworkModel(model);
		view.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String name;
		
		if(e.getActionCommand().equals("New")) {
			
			name = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
        	
        	//input check for empty entry and all spaces
        	if(!name.isEmpty() && !name.trim().isEmpty()){
        		
        		if(!model.getNodes().contains(name)) {  //check the model for a duplicate node
        			model.createNode(name);				//add the unique node to the model
        			view.update(e, name);				//update the view 
        		}
        			
        	}else{
        			
        		name = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
        		
        	}
		}
		
		else if(e.getActionCommand().equals("Delete")) {
			
			name = JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE);
			
			if(model.getNodes().contains(name)) {  //check to see if the node exists
				model.destroyNode(name);			//delete the node from the model
				view.update(e, name); 				//update the view
			}
		}
		
		else if(e.getActionCommand().equals("Connect")) {
			
			view.parent = view.getGraph().getDefaultParent();
			view.v1 = view.getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
	        view.v2 = view.getM().get(JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE));
	        
	        model.connectNodes(view.v1.toString(), view.v2.toString());	//connect the two nodes in the model
	        view.update(e, null);	//update the view
		}
		
		else if(e.getActionCommand().equals("Disconnect")) {
			
			view.v1 = view.getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
	        view.v2 = view.getM().get(JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE));
	        
	        model.disconnectNodes(view.v1.toString(), view.v2.toString());	//disconnect the two nodes in the model
	        view.update(e, null);	//update the view
			
		}
	}
	
}