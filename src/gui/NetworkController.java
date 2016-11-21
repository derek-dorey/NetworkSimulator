package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import core.Network;
import core.routing.RoutingAlgorithm;

/***
 * The NetworkController processes user inputs by:
 * -checking for valid inputs
 * -if they are valid, the NetworkController updates the model (Network), then notifies the view (Frame) of the model's changes
 * @author derekdorey
 *
 */

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
	
	/***
	 * Primary action listener for user inputs.
	 */

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
				model.destroyNode(name);		   //delete the node from the model
				view.update(e, name); 			   //update the view
			}
			else {
				JOptionPane.showMessageDialog(view, "The node you are trying to delete does not exist");
			}
		}
		
		else if(e.getActionCommand().equals("Connect")) {
			
			view.parent = view.getGraph().getDefaultParent();
			view.v1 = view.getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
	        view.v2 = view.getM().get(JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE));
	        
	        if(!(model.getNodes().contains(view.v1.toString()) && model.getNodes().contains(view.v2.toString()))) { //check whether both nodes exist in the model
	        	JOptionPane.showMessageDialog(view, "One or both of the nodes do not exist. Please ensure that you are connecting existing nodes");
	        	return;				//one or more invalid inputs, don't update the model or the view
	        }
	        
	        model.connectNodes(view.v1.toString(), view.v2.toString());	//connect the two nodes in the model
	        view.update(e, null);	//update the view
		}
		
		else if(e.getActionCommand().equals("Disconnect")) {
			
			view.v1 = view.getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
	        view.v2 = view.getM().get(JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE));
	        
	        if(!(model.getNodes().contains(view.v1.toString()) && model.getNodes().contains(view.v2.toString()))) { //check whether both nodes exist in the model
	        	JOptionPane.showMessageDialog(view, "One or both of the nodes do not exist. Please ensure that you are disconnecting existing nodes");
	        	return;				//one or more invalid inputs, don't update the model or the view
	        }
	        
	        model.disconnectNodes(view.v1.toString(), view.v2.toString());	//disconnect the two nodes in the model
	        view.update(e, null);	//update the view	
		}
		
		else if(e.getActionCommand().equals("Set Rate")) {
			
			int setRate;
			
			try {
				setRate = Integer.parseInt(view.textField.getText());		//parse integer from textField
			} catch (NumberFormatException n) {								//catch numberFormatException
				JOptionPane.showMessageDialog(view,"Invalid input, please enter a positive integer value");
				view.update(e,null);
				return;											//invalid integer, don't update model
			}
			
			if(setRate>0) {										//check valid integer
				model.setMessageCreationPeriod(setRate);		//set the model's new rate
			}
			else {
				return;											//negative input, don't update model/view
			}
			view.update(e, null);  		//update the view
		}
	}
}