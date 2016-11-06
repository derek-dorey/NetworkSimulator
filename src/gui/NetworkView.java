package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JEditorPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.Message;
import core.Node;

public class NetworkView extends JFrame {
	private static final Comparator<Node> COMP = new Comparator<Node>(){
		@Override
		public int compare(Node o1, Node o2) {
			return o1.getID().compareTo(o2.getID());
		}
	};
	
	private List<Node> nodes = new ArrayList<>();
	private static final long serialVersionUID = -6977255976388251135L;
	private JTable table;
	
	public NetworkView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[0][0],
			new String[] {"Node", "Neighbors", "Buffer"}
		));
		scrollPane.setViewportView(table);
	}
	
	public void setNodes(Collection<Node> nodes){
		this.nodes = new ArrayList<>(nodes);
		Collections.sort(this.nodes, COMP);
		update();
	}
	
	public void update(){
		List<String[]> rows = new ArrayList<>(nodes.size());
		for(Node n : nodes){
			String name = n.getID();
			List<String> neighbors = new ArrayList<>();
			for(Node neighbor : n.getNeighbours()){
				neighbors.add(neighbor.getID());
			}
			Collections.sort(neighbors);
			
			List<String> messages = new ArrayList<>();
			for(Message m : n.getBufferContents()){
				messages.add(m.toString());
			}
			rows.add(new String[]{name, Arrays.toString(neighbors.toArray(new String[]{})),Arrays.toString(messages.toArray(new String[]{}))});
			
		}
	}
	
	
}
