package gui;

/**
 * @author Benjamin Tobalt
 *This class adds the new 'node' the gui network
 */

public class AddGraphic extends Frame {	
	
	//used to randomize location
	int xLoc = (int)Math.floor(Math.random() * 300);
	int yLoc = (int)Math.floor(Math.random() * 300);
	int w = 80;
	int h = 60;
	
	
	public AddGraphic(String name){
		this.getGraph().getModel().beginUpdate();
		Object parent = this.getGraph().getDefaultParent(); 
		Object v1 = this.getGraph().insertVertex(parent, null,name, xLoc, yLoc, 80, 60,"shape=ellipse");
		
		
		this.getGraph().setCellsResizable(false);
		
		this.getM().put(name, v1);
		this.getGraph().getModel().endUpdate();
	}
	

}