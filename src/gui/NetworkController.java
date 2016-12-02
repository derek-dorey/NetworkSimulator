package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	protected static ActionListener radioButtonListener; 
	
	public NetworkController(Network m, Frame v) {
		model = m;
		view = v;
	}
	
	public static void main(String[] args) {
		
		//instantiate the model with default set to random routing algorithm
		model = new Network(RoutingAlgorithm.RANDOM);
		
		//instantiate the view
		view = new Frame();
		//instantiate the controller with references to the model and the view
		NetworkController controller = new NetworkController(model, view);
		
		view.addNetworkController(controller);
		view.addNetworkModel(model);
		view.setVisible(true);
		
		//set the routing algorithm according to the user's selection
		radioButtonListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand().equals("Flooding")) {
						model.setRoutingAlgorithm(RoutingAlgorithm.FLOOD);
					}
					
					else if(e.getActionCommand().equals("Random")) {
						model.setRoutingAlgorithm(RoutingAlgorithm.RANDOM);
					}
					
					else if(e.getActionCommand().equals("Fastest Route")) {
						model.setRoutingAlgorithm(RoutingAlgorithm.SHORTEST_PATH);
					}
					
					else if(e.getActionCommand().equals("Local")) {
						model.setRoutingAlgorithm(RoutingAlgorithm.BETTER_RANDOM);
					}
				}
			};
	}
	
	/***
	 * Primary action listener for user inputs.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String name;
		
		if(e.getActionCommand().equals("New")) {
			
			name = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
        	
			if(model.getNodes().contains(name)) {  //check to see if the node exists
				JOptionPane.showMessageDialog(view, "The node you are trying to create already exists.");
				return;
			}
			
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
			
			boolean result;
			
			String v1 = JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE);
			String v2 = JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE);
			
			view.v1 = view.getM().get(v1);
			view.v2 = view.getM().get(v2);
			
	        result = model.connectNodes(v1, v2);	//connect the two nodes in the model
	        
	        if(result) {
	        	view.update(e, null);	//update the view
	        }
	        
	        else {
	        	JOptionPane.showMessageDialog(view, "One or both of the nodes do not exist. Please ensure that you are disconnecting existing nodes");
	        	return;				//one or more invalid inputs, don't update the model or the view
	        }
	       
		}
		
		else if(e.getActionCommand().equals("Disconnect")) {
			
			String v1 = JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE);
			String v2 = JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE);
			
			view.v1 = view.getM().get(v1);
			view.v2 = view.getM().get(v2);
	        
	        if(!(model.getNodes().contains(v1) && model.getNodes().contains(v2))) { //check whether both nodes exist in the model
	        	JOptionPane.showMessageDialog(view, "One or both of the nodes do not exist. Please ensure that you are disconnecting existing nodes");
	        	return;				//one or more invalid inputs, don't update the model or the view
	        }
	        
	        model.disconnectNodes(v1,v2);	//disconnect the two nodes in the model
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
		
		else if(e.getActionCommand().equals("Step")) {
			model.step();
			view.update(e,null);
		}
		
		else if(e.getActionCommand().equals("Save")) {
			
			//set default directory for saved files
			String filePath = System.getProperty("user.dir") + "/SavedFiles/";
			
			JFileChooser fileSaver = new JFileChooser();
			fileSaver.setCurrentDirectory(new File(filePath));
			FileNameExtensionFilter saveFilter = new FileNameExtensionFilter(".xml files","xml");
			fileSaver.setFileFilter(saveFilter);
			
			//open save menu
			int userSelection = fileSaver.showSaveDialog(view);
			
			//check if user saved a file
			if(userSelection == JFileChooser.APPROVE_OPTION) {
				
				File destination = fileSaver.getSelectedFile();
				Path p = destination.toPath();
				
				try {
					model.toXml(Files.newOutputStream(p));
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				}
					
			}
		}
		
		else if(e.getActionCommand().equals("Load")) {
			
			String filePath = System.getProperty("user.dir") + "/SavedFiles/";
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(filePath));
			FileNameExtensionFilter chooseFilter = new FileNameExtensionFilter(".xml files","xml");
			fileChooser.setFileFilter(chooseFilter);
			
			//open selection menu
			int userSelection = fileChooser.showOpenDialog(view);
			
			if(userSelection == JFileChooser.APPROVE_OPTION) {
				
				File selection = fileChooser.getSelectedFile();
				Path p = selection.toPath();
				
				try {
		
					model = model.fromXml(Files.newInputStream(p));
				} catch (SAXException e1) {
						e1.printStackTrace();
				} catch (IOException e1) {
						e1.printStackTrace();
				} catch (ParserConfigurationException e1) {
						e1.printStackTrace();
				}		
			}
			
			view.clear();
			view.redraw();
			
		}
	}
}