package gui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.Message;
import core.Network;
import core.Node;

public class NetworkView extends JPanel {
	private static final long serialVersionUID = -6977255976388251135L;
	private static final Comparator<Node> COMP = new Comparator<Node>(){
		@Override
		public int compare(Node o1, Node o2) {
			return o1.getID().compareTo(o2.getID());
		}
	};
	private String[] TABLE_HEADERS = {"Node", "Neighbors", "Buffer"};
	private final Network network;
	private JTable table;
	
	public NetworkView(Network network) {
		this.network = network;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[0][0],
			TABLE_HEADERS));
		scrollPane.setViewportView(table);
	}
	
	
	
	public void update(){
		List<Node> nodes = new ArrayList<>(network.getNodes());
		Collections.sort(nodes, COMP);
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
			table.setModel(new DefaultTableModel(
					rows.toArray(new String[0][0]),
					TABLE_HEADERS));
		}
	}
}
