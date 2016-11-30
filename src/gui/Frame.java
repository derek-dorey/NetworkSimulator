package gui;

/**
 * @author Benjamin Tobalt
 *
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import core.Network;
import core.NetworkNode;
import core.routing.RoutingAlgorithm;
/**
 * This class creates the Gui for the network
 * @author Benjamin Tobalt
**/



@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	private Network model;

	protected JPanel contentPane;
	protected JTextField textField;
	protected JTextArea txt;
	protected static mxGraph graph = new mxGraph();
	protected static HashMap m = new HashMap();
	private mxGraphComponent graphComponent;

	private Object cell;
	private NetworkController controller;
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnSetRate;
	private JButton btnSave;
	private JButton btnRestore;
	private JRadioButton rdbtnFlooding;
	private JRadioButton rdbtnRandom;
	private JRadioButton rdbtnFastest;
	private JRadioButton rdbtnLocal;
	
	protected Object v1;
	protected Object v2;
	protected Object parent;
	private BufferDisplayPannel bufferDisplay;
	private JButton btnStep;
	
	public HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}

	/**
	 * Create the frame.
	 * 
	 * @author Benjamin Tobalt
	 *
	 */
	public Frame() {
		super("BitsPlease Network Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 500);
		setResizable(false);
	
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rdbtnFlooding = new JRadioButton("Flooding");
		rdbtnFlooding.setBounds(697, 26, 109, 23);
		
		rdbtnRandom = new JRadioButton("Random",true);
		rdbtnRandom.setBounds(808, 26, 109, 23);
		
		//random routing algorithm is the default configuration
		rdbtnRandom.setSelected(true);
		
		rdbtnFastest = new JRadioButton("Fastest Route");
		rdbtnFastest.setBounds(697, 52, 109, 23);
		
		rdbtnLocal = new JRadioButton("Adaptive");
		rdbtnLocal.setBounds(808, 52, 109, 23);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnFlooding);
		bg.add(rdbtnRandom);
		bg.add(rdbtnFastest);
		bg.add(rdbtnLocal);
		
		contentPane.add(rdbtnFlooding);
		contentPane.add(rdbtnRandom);
		contentPane.add(rdbtnFastest);
		contentPane.add(rdbtnLocal);
		
		//graph component
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setBounds(5, 5, 670, 380);
		graphComponent.setConnectable(false);
	
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		contentPane.add(graphComponent);
		
		mxIGraphLayout graphLayout = new mxFastOrganicLayout(graph);
		graph.getModel().beginUpdate();
		graphLayout.execute(graph.getDefaultParent());
		graph.getModel().endUpdate();
		
		JLabel lblAl = new JLabel("Routing Algorithms:");
		lblAl.setBounds(695, 5, 111, 14);
		contentPane.add(lblAl);
		
		btnSetRate = new JButton("Set Rate");
		btnSetRate.setBounds(697, 96, 89, 23);
		contentPane.add(btnSetRate);
		
		textField = new JTextField();
		textField.setBounds(804, 97, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnNew = new JButton("New");
		btnNew.setBounds(5, 396, 89, 23);
		contentPane.add(btnNew);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(103, 396, 89, 23);
		contentPane.add(btnDelete);   
	
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(202, 396, 89, 23);
		contentPane.add(btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(301, 396, 100, 23);
		contentPane.add(btnDisconnect);
		
		btnStep = new JButton("Step");
		btnStep.setBounds(411, 396, 89, 23);
		contentPane.add(btnStep);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(550, 396, 89, 21);
		contentPane.add(btnSave);
		
		btnRestore = new JButton("Restore");
		btnRestore.setBounds(680, 396, 89, 21);
		contentPane.add(btnRestore);
        		
		JLabel lblNetworksBufferOutput = new JLabel("Networks Buffer Output:");
		lblNetworksBufferOutput.setBounds(697, 140, 141, 14);
		contentPane.add(lblNetworksBufferOutput);	
	}
	
	/***
	 * 
	 * Add the NetworkController reference to the view, and add it as an actionlistener for handling user inputs
	 * Add the radioButtonListener to the view's radio buttons
	 */
	
	public void addNetworkController(NetworkController c) {
		this.controller = c;
		btnNew.addActionListener(controller);
		btnDelete.addActionListener(controller);
		btnConnect.addActionListener(controller);
		btnDisconnect.addActionListener(controller);
		btnSetRate.addActionListener(controller);
		btnStep.addActionListener(controller);
		btnSave.addActionListener(controller);
		btnRestore.addActionListener(controller);
		textField.setEditable(true);
		textField.addActionListener(controller);
		rdbtnFlooding.addActionListener(controller.radioButtonListener);
		rdbtnRandom.addActionListener(controller.radioButtonListener);
		rdbtnFastest.addActionListener(controller.radioButtonListener);
		rdbtnLocal.addActionListener(controller.radioButtonListener);
	}
	
	
	
	public void addNetworkModel(Network n) {
		this.model = n;
		bufferDisplay = new BufferDisplayPannel(model);
		bufferDisplay.setBounds(697, 163, 277, 290);
		contentPane.add(bufferDisplay);
	}
	
	/***
	 * 
	 * If valid changes are made, update the view to reflect the new state of the model
	 * 
	 */
	
	public void update(ActionEvent e, String node) {
		
		if(e.getActionCommand().equals("New")) {
			int nodeIndex = model.getNodes().indexOf(node);
			@SuppressWarnings("unused")
			String name = model.getNodes().get(nodeIndex);
			
			/*
			String message = "";
			for(Integer s: model.getMessageBufferFromNode(name)){
				message = message + "s\n";
			}
			*/
			
			AddGraphic add = new AddGraphic(name);	//add the node to the view
		}
		
		else if(e.getActionCommand().equals("Delete")) {
			
			Object v2 = getM().get(node);	//locate the deleted node in the view
			
			graph.getModel().beginUpdate();
	        try {
	        	graph.removeCells(new Object[]{v2});
				
	        } finally {
	            graph.getModel().endUpdate();
	        }
		}
		
		else if(e.getActionCommand().equals("Connect")) {
			String name = "";
	        getGraph().insertEdge(parent, name, null, v1, v2,"endArrow=none");
		}
		
		else if(e.getActionCommand().equals("Disconnect")) {
		
			 graph.getModel().beginUpdate();
			 
		     try {
		            Object[] edges = graph.getEdgesBetween(v1, v2);

		            for( Object edge: edges) {
		                graph.getModel().remove(edge);
		            }

		        } finally {
		            graph.getModel().endUpdate();
		     }
		}
		
		else if(e.getActionCommand().equals("Set Rate")) {
			
			textField.setText(null);		//clear the input field to reflect the new rate
		} else if (e.getActionCommand().equals("Step")) {
			getBufferDisplay().update();
		}
		
	}
	
	public void clear() {
		
		graph.getModel().beginUpdate();
		
		graph = new mxGraph();
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setBounds(5, 5, 670, 380);
		graphComponent.setConnectable(false);
	
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		contentPane.add(graphComponent);
		
		mxIGraphLayout graphLayout = new mxFastOrganicLayout(graph);
		graphLayout.execute(graph.getDefaultParent());
	
		setContentPane(graphComponent);
		
		graph.getModel().endUpdate();
		
	}
	
	public void redraw() {
		
		ArrayList<String> nodes = model.getNodes();
		int numberOfNodes;
		Set<String> nodeNeighbours;
		
		if(nodes.isEmpty()) {
			return;
		} else {
			numberOfNodes = nodes.size();
		}
		for(String node : nodes) {
			graph.getModel().beginUpdate();
			AddGraphic add = new AddGraphic(node);
			graph.getModel().endUpdate();
		}
		
		for(String node : nodes) {
			if(model.getNetworkNodes().get(node).hasNeighbours()) {
				nodeNeighbours = model.getNetworkNodes().get(node).getNeighbourIds();
				
				for(String neighbour : nodeNeighbours) {
					String name = "";
					graph.getModel().beginUpdate();
			        getGraph().insertEdge(parent, name, null, getM().get(node), getM().get(neighbour),"endArrow=none");
			    	graph.getModel().endUpdate();
				}
			}
		}	
	}
	
	
	public BufferDisplayPannel getBufferDisplay() {
		return bufferDisplay;
	}
	public JButton getBtnStep() {
		return btnStep;
	}
}
