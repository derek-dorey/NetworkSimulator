package gui;

/**
 * @author Benjamin Tobalt
 *
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;

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


import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import core.Network;
import core.Node;
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
	protected Object v1;
	protected Object v2;
	protected Object parent;
	
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
		
		super("JGraph - Network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 500);
		setResizable(false);
	
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnFlooding = new JRadioButton("Flooding");
		rdbtnFlooding.setBounds(697, 26, 109, 23);
		
		JRadioButton rdbtnRandom = new JRadioButton("Random",true);
		rdbtnRandom.setBounds(808, 26, 109, 23);
		
		JRadioButton rdbtnFastest = new JRadioButton("Fastest Route");
		rdbtnFastest.setBounds(697, 52, 109, 23);
		
		JRadioButton rdbtnLocal = new JRadioButton("Local");
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
		
		JButton btnStep = new JButton("Step");
		btnStep.setBounds(411, 396, 89, 23);
		contentPane.add(btnStep);
		
		/*
		 * Iteration 4: Components
		 *
		JButton btnStepBack = new JButton("Back Step");
		btnStepBack.setBounds(511, 396, 100, 23);
		contentPane.add(btnStepBack);
		
		JButton btnSave = new JButton("Save Network as XML");
		btnSave.setBounds(301, 430, 311, 23);
		contentPane.add(btnSave);
		
		JButton btnImport = new JButton("Import XML File");
		btnImport.setBounds(5, 430, 286, 23);
		contentPane.add(btnImport);
		*/
		
		
		JTextArea txt = new JTextArea();
		//test purposes
		// outputs and keep the original text do (current string + new string) then setText
		//txt.setText("a\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\na\nb\nc\nd\n");
		txt.setEditable(false); 
		
		JScrollPane sp = new JScrollPane(txt);
		sp.setBounds(697, 163, 277, 290);
		contentPane.add(sp);
        		
		JLabel lblNetworksBufferOutput = new JLabel("Networks Buffer Output:");
		lblNetworksBufferOutput.setBounds(697, 140, 141, 14);
		contentPane.add(lblNetworksBufferOutput);	
	}
	
	/***
	 * 
	 * Add the NetworkController reference to the view, and add it as an actionlistener for handling user inputs
	 */
	
	public void addNetworkController(NetworkController c) {
		this.controller = c;
		btnNew.addActionListener(controller);
		btnDelete.addActionListener(controller);
		btnConnect.addActionListener(controller);
		btnDisconnect.addActionListener(controller);
		btnSetRate.addActionListener(controller);
		textField.setEditable(true);
		textField.addActionListener(controller);
	}
	
	public void addNetworkModel(Network n) {
		this.model = n;
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
			AddGraphic add = new AddGraphic(model.getNodes().get(nodeIndex));	//add the node to the view
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
		            Object[] edges = graph.getEdgesBetween( v1, v2);

		            for( Object edge: edges) {
		                graph.getModel().remove(edge);
		            }

		        } finally {
		            graph.getModel().endUpdate();
		     }
		}
		
		else if(e.getActionCommand().equals("Set Rate")) {
			
			textField.setText(null);		//clear the input field to reflect the new rate
		}
	}
}
