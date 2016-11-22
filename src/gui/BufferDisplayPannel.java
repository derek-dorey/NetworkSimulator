package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.Network;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BufferDisplayPannel extends JPanel {
	private static final long serialVersionUID = -3456118943243874717L;
	
	private final Network net;
	private final JList<String> list;
	/**
	 * Create the panel.
	 */
	public BufferDisplayPannel(Network net) {
		this.net = net;
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		list = new JList<>();
		list.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(list);

	}
	
	public void update(){
		List<String> nodes = net.getNodes();
		list.setVisibleRowCount(nodes.size());
		if(nodes.isEmpty()){
			list.setModel(new DefaultListModel<String>());
			return;
		}
		
		Collections.sort(nodes);
		int maxLength = nodes.get(0).length();
		for(String s : nodes){
			if(maxLength < s.length()){
				maxLength = s.length();
			}
		}
		
		DefaultListModel<String> mod = new DefaultListModel<String>();
		for(String id : nodes){
			String messages = Arrays.toString(net.getMessageBufferFromNode(id).toArray());
			StringBuilder sb = new StringBuilder();
			sb.append(id);
			//right pad the string
			for(int i = 0; i+id.length()<maxLength; i++){
				sb.append(" ");
			}
			sb.append(":");
			sb.append(messages);
			mod.addElement(sb.toString());
		}
		list.setModel(mod);
	}
	
	
}
