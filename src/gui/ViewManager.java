package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Network;
import core.NetworkManager;
import core.routing.RoutingAlgorithm;

public class ViewManager {
	
	private final NetworkManager networkManager;
	private ViewFrame viewFrame;
	
	public ViewManager(NetworkManager nm) {
		
		networkManager = nm;
		GraphHandler gh = new GraphHandler();
		viewFrame = new ViewFrame(gh);
		networkManager.registerNetworkListener(gh);
		networkManager.registerNetwork(new Network(RoutingAlgorithm.RANDOM));
		
		viewFrame.getBtnCreate().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String createName = new String();
				createName = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
				
				if(createName==null) {
					return;
				}
	
				if(!networkManager.createNode(createName)) {
					JOptionPane.showMessageDialog(viewFrame, "Can't create node!");
				}
			}	
		});
		
		viewFrame.getBtnDestroy().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String destroyName = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
				
				if(!networkManager.destroyNode(destroyName)) {
					JOptionPane.showMessageDialog(viewFrame, "Can't destroy node!");
				}
			}
		});
		
		viewFrame.getBtnConnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstNode = JOptionPane.showInputDialog(null, "Node ID 1:", null, JOptionPane.PLAIN_MESSAGE);
				String secondNode = JOptionPane.showInputDialog(null, "Node ID 2:", null, JOptionPane.PLAIN_MESSAGE);
				
				if(firstNode == null || secondNode == null || !networkManager.connectNodes(firstNode, secondNode)) {
					JOptionPane.showMessageDialog(viewFrame, "Connect failed.");
				}
			}
		});
		
		viewFrame.getBtnDisconnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String firstNode = JOptionPane.showInputDialog(null, "Node ID 1:", null, JOptionPane.PLAIN_MESSAGE);
				String secondNode = JOptionPane.showInputDialog(null, "Node ID 2:", null, JOptionPane.PLAIN_MESSAGE);
				
				if(firstNode == null || secondNode == null || !networkManager.disconnectNodes(firstNode, secondNode)) {
					JOptionPane.showMessageDialog(viewFrame, "Disconnect failed.");
				}
				
			}
		});
		
		viewFrame.getBtnStep().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!networkManager.step()) {
					JOptionPane.showMessageDialog(viewFrame, "Step failed.");
				}
			}
		});
		
		viewFrame.getBtnUndo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(!networkManager.undo()) {
					JOptionPane.showMessageDialog(viewFrame, "Undo failed");
				}
			}
		});
		
		viewFrame.getBtnSave().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean isSuccessful = false;
				String filePath = ".\\SavedFiles\\";
				
				JFileChooser fileSaver = new JFileChooser();
				fileSaver.setCurrentDirectory(new File(filePath));
				FileNameExtensionFilter saveFilter = new FileNameExtensionFilter(".xml files","xml");
				fileSaver.setFileFilter(saveFilter);
				
				//open save menu
				int userSelection = fileSaver.showSaveDialog(viewFrame);
				
				//check if user saved a file
				if(userSelection == JFileChooser.APPROVE_OPTION) {
					
					File destination = fileSaver.getSelectedFile();
					Path p = destination.toPath();
					
					try {
						isSuccessful = networkManager.save(Files.newOutputStream(p));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				if(!isSuccessful) {
					JOptionPane.showMessageDialog(viewFrame, "Error: save unsuccessful.");
				}
			}
		});
		
		viewFrame.getBtnLoad().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean isSuccessful = false;
				String filePath = System.getProperty("user.dir") + "/SavedFiles/";
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(filePath));
				FileNameExtensionFilter chooseFilter = new FileNameExtensionFilter(".xml files","xml");
				fileChooser.setFileFilter(chooseFilter);
				
				//open selection menu
				int userSelection = fileChooser.showOpenDialog(viewFrame);
				
				if(userSelection == JFileChooser.APPROVE_OPTION) {
					
					File selection = fileChooser.getSelectedFile();
					Path p = selection.toPath();
					
					try {
						isSuccessful = networkManager.load(new BufferedReader(new InputStreamReader(Files.newInputStream(p)))
								  .lines().collect(Collectors.joining("\n")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}	
				
				if(!isSuccessful) {
					JOptionPane.showMessageDialog(viewFrame, "Error: load unsuccessful.");
				}
				
			}
		});
		
		viewFrame.getBtnRate().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String input = JOptionPane.showInputDialog(null, "New Rate: ", null, JOptionPane.PLAIN_MESSAGE);
				try {
					int rate = Integer.parseInt(input);
					
					if(rate>0) {
						networkManager.setRate(rate);
					}
				} catch (NumberFormatException n) {								
					JOptionPane.showMessageDialog(viewFrame,"Invalid input, please enter a positive integer value");
				}
			}	
		});
		
		viewFrame.getBtnAlgorithm().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoutingAlgorithm algorithm = (RoutingAlgorithm)JOptionPane.showInputDialog(viewFrame, "Select Routing Algorithm: ",
	                    "Routing Algorithm",JOptionPane.PLAIN_MESSAGE,null,RoutingAlgorithm.values(),null);
				networkManager.setAlgorithm(algorithm);
			}
		});
		
		viewFrame.getBtnMetrics().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double avgHops;
				double avgTransmissions;
				
				String source = JOptionPane.showInputDialog(null, "Node ID 1:", null, JOptionPane.PLAIN_MESSAGE);
				if(source == null){
					return;
				}
				String destination = JOptionPane.showInputDialog(null, "Node ID 2:", null, JOptionPane.PLAIN_MESSAGE);
			
				if(destination != null) {
					avgHops = networkManager.getHops(source, destination);
					avgTransmissions = networkManager.getTransmissions(source, destination);

					String hops = (avgHops < 0)?("unavailable"):(Double.valueOf(avgHops).toString());
					String tran = (avgTransmissions < 0)?("unavailable"):(Double.valueOf(avgTransmissions).toString());
					
					JOptionPane.showMessageDialog(viewFrame, "Average hops is " + hops + "\nAverage transmissions is " + tran);
				}
			}
		});

	}
	
	public static void main(String[] args) {
		
		new ViewManager(new NetworkManager());
	}
	
	
	
	
	
	
	
}
