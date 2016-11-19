package gui;

/**
 * This class removes the specified node connection
 * @author Benjamin Tobalt
 *
 */

import javax.swing.JOptionPane;


public class DisconnectNodes extends Frame {
	public DisconnectNodes(){
		
		
		Object v1 = this.getM().get(JOptionPane.showInputDialog(null,"Node 1:", null, JOptionPane.PLAIN_MESSAGE));
        Object v2 = this.getM().get(JOptionPane.showInputDialog(null,"Node 2:", null, JOptionPane.PLAIN_MESSAGE));
        
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
       

}