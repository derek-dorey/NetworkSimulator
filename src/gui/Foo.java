package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphModel;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;

public class Foo extends JPanel{	
	
	public Foo(){
		ListenableGraph<String,DefaultEdge> g = new ListenableUndirectedGraph<>( DefaultEdge.class );
		
		g.addVertex( "v1" );
        g.addVertex( "v2" );
        g.addVertex( "v3" );
        g.addVertex( "v4" );

        g.addEdge( "v1", "v2" );
        g.addEdge( "v2", "v3" );
        g.addEdge( "v3", "v1" );
        g.addEdge( "v4", "v3" );
		
		
		JGraph graph = new JGraph( new JGraphModelAdapter<>( g ) );
		
		graph.setEditClickCount(-1);
		graph.setMoveable(true);
		graph.setCloneable(false);
		graph.setSizeable(false);
		graph.setBendable(false);
		graph.setConnectable(false);
		graph.setDisconnectable(false);
		graph.setEdgeLabelsMovable(false);
		
		
		this.add(graph);
		
	}
	
	public static void main(String ...args){
		JFrame jf = new JFrame("asdf");
		Foo f = new Foo();
		jf.getContentPane().add(f);
		jf.setSize(500, 500);
		jf.setVisible(true);
	}
}