package gui;

/**
 * @author Benjamin Tobalt
 *This class adds the new 'node' the gui network
 */

public class AddGraphic extends Frame {	
	
	public AddGraphic(String name){
		this.getGraph().getModel().beginUpdate();
		Object parent = this.getGraph().getDefaultParent(); 
		Object v1 = this.getGraph().insertVertex(parent, null,name, 30, 56, 80, 60,"shape=ellipse");
		
		this.getM().put(name, v1);
		this.getGraph().getModel().endUpdate();
	}
	

}