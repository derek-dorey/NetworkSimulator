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
				String createName = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
				
				if(!createName.isEmpty()) {
					networkManager.createNode(createName);
				}
			}	
		});
		
		viewFrame.getBtnDestroy().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String destroyName = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
				networkManager.destroyNode(destroyName);
			}
		});
		
		viewFrame.getBtnConnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstNode = JOptionPane.showInputDialog(null, "Node ID 1:", null, JOptionPane.PLAIN_MESSAGE);
				String secondNode = JOptionPane.showInputDialog(null, "Node ID 2:", null, JOptionPane.PLAIN_MESSAGE);
				networkManager.connectNodes(firstNode, secondNode);
			}
		});
		
		viewFrame.getBtnDisconnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstNode = JOptionPane.showInputDialog(null, "Node ID 1:", null, JOptionPane.PLAIN_MESSAGE);
				String secondNode = JOptionPane.showInputDialog(null, "Node ID 2:", null, JOptionPane.PLAIN_MESSAGE);
				networkManager.disconnectNodes(firstNode, secondNode);
			}
		});
		
		viewFrame.getBtnStep().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				networkManager.step();
			}
		});
		
		viewFrame.getBtnUndo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				networkManager.undo();
			}
		});
		
		viewFrame.getBtnSave().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
						networkManager.save(Files.newOutputStream(p));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		viewFrame.getBtnLoad().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
						networkManager.load(new BufferedReader(new InputStreamReader(Files.newInputStream(p)))
								  .lines().collect(Collectors.joining("\n")));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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

	}
	
	public static void main(String[] args) {
		
		new ViewManager(new NetworkManager());
	}
	
	
	
	
	
	
	
}
