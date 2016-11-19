package gui;

/**
 * 
 *This class adds the connection between the two nodes
 *@author Benjamin Tobalt
 *
 */

import java.util.HashMap;
import javax.swing.JOptionPane;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;


public class ConnectNodes extends Frame {
	
	public ConnectNodes(){
		
		Object parent = this.getGraph().getDefaultParent();
		Object v1 = this.getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
        Object v2 = this.getM().get(JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE));
        String name = "";
        this.getGraph().insertEdge(parent, name, null, v1, v2,"endArrow=none");
     
	}
}