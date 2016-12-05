package gui;

import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import core.NetworkListener;

public class GraphHandler implements NetworkListener{
	public Boolean active = true;
	
	private Graph graph;
	private Viewer viewer;
	private ViewPanel view;
	private Layout layout;
	
	public GraphHandler() {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		graph = new SingleGraph("Network");
		graph.setStrict(false);
		graph.setAutoCreate( true );
		
		graph.addAttribute("ui.stylesheet","url('file:Network.css')");
		viewer = new Viewer(graph,Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		layout = new LinLog();
		viewer.enableAutoLayout(layout);
	    view = viewer.addDefaultView(false);

	}
	
	public Component getComponent(){
		return view;
	}
	
	private String edgeId(String nodeId1, String nodeId2){
		String str1,str2;
		if(nodeId1.compareTo(nodeId2)>0){
			str1=nodeId1;
			str2=nodeId2;
		}else{
			str1=nodeId2;
			str2=nodeId1;
		}
		//http://stackoverflow.com/a/35566397
		return String.format("%s_%s_%s_%s", str1.length(), str2.length(), str1, str2);
	}
	
	@Override
	public void createNode(String id) {
		//adds the node iff it does not already exist
		if(graph.getNode(id) != null){
			return;
		}
		
		Node nodeVertex = graph.addNode(id);
		nodeVertex.addAttribute("ui.class", "Node");
		setNodeLabel(nodeVertex, Collections.emptyList());
				
	}

	private void setNodeLabel(Node node, List<Integer> queue){
		node.addAttribute("ui.label", node.getId()+" "+Arrays.toString(queue.toArray()));
	}
	
	@Override
	public void destroyNode(String id) {
		graph.removeNode(id);
	}
	
	@Override
	public void connectNodes(String id1, String id2) {
		graph.addEdge(edgeId(id1,id2), id1, id2);
	}

	@Override
	public void disconnectNodes(String id1, String id2) {
		graph.removeEdge(edgeId(id1,id2));
	}

	@Override
	public void updateMessages(Map<String, List<Integer>> status) {
		for(String nodeId : status.keySet()){
			Node n = graph.getNode(nodeId);
			if(n!=null){
				setNodeLabel(n,status.get(nodeId));
			}
		}
	}

	@Override
	public void clearAll() {
		for(Node n : graph.getNodeSet()){
			graph.removeNode(n);
		}
	}
	
	public void close() {
		if(graph != null){
			graph.clear();
			graph = null;
		}
	}
	
	public void finalize(){
		close();
	}
}
