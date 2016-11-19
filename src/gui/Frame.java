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
/**
 * This class creates the Gui for the netork
 * @author Benjamin Tobalt
**/
@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	protected static mxGraph graph = new mxGraph();
	protected static HashMap m = new HashMap();
	private mxGraphComponent graphComponent;
	private Object cell;
	
	public HashMap getM() {
		return m;
	}

	public static mxGraph getGraph() {
		return graph;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		
		JButton btnSetRate = new JButton("Set Rate");
		btnSetRate.setBounds(697, 96, 89, 23);
		contentPane.add(btnSetRate);
		
		textField = new JTextField();
		textField.setBounds(804, 97, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnNew = new JButton("New");
		btnNew.setBounds(5, 396, 89, 23);
		contentPane.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
            	
            	String name = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
            	
            	//input check for empty entry and all spaces
            	if(!name.isEmpty() && !name.trim().isEmpty()){
            		
            			@SuppressWarnings("unused")
						AddGraphic add = new AddGraphic(name);
            			
            			
            			
            	}else{
            		
            			name = JOptionPane.showInputDialog(null, "Node ID:", null, JOptionPane.PLAIN_MESSAGE);
            		
            	}
            }
        });
		
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(103, 396, 89, 23);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				Object v2 = getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
				
				
				graph.getModel().beginUpdate();
		        try {
				
		        	graph.removeCells(new Object[]{v2});
					
		        } finally {
		            graph.getModel().endUpdate();
		        }
			}
		});
		
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(202, 396, 89, 23);
		contentPane.add(btnConnect);
		btnConnect.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
               ConnectNodes link = new ConnectNodes();
            }
        });
		
		
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(301, 396, 100, 23);
		contentPane.add(btnDisconnect);
		btnDisconnect.addActionListener(new ActionListener(){
			
        	public void actionPerformed(ActionEvent e) {
                DisconnectNodes link = new DisconnectNodes();
             }
        });
		
		
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
}

