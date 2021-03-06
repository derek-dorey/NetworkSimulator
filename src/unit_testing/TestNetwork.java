package unit_testing;

import core.Network;
import junit.framework.TestCase;

/**
 * Test cases for the cores network class using JUnit 3 library. 
 * @author Benjamin Tobalt and Denis Atikpladza
 *
 */
public class TestNetwork extends TestCase {

	Network network = new Network();
	
	
	public void testCreate(){
		network.createNode("nodeA");
		network.createNode("nodeB");
		assertEquals(network.hasNode("nodeB"),network.hasNode("nodeA"));
	}
	
	
	public void testRemove()
	{
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.removeNode("nodeB");
		assertNotSame(true,network.hasNode("nodeB"));
	}
	
	
	public void testSetRate(){
		network.setRate(0);
		assertEquals(0,network.getRate());
		assertNotSame(1,network.getRate());
	}
	
	
	public void testConnect(){
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.connect("nodeA", "nodeB");
		assertEquals(true, network.hasConnection("nodeA", "nodeB"));
			
	}
	
	public void testDisconnect(){
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.disconnect("nodeA", "nodeB");
		assertEquals(false, network.hasConnection("nodeA", "nodeB"));
	}
	
	/*since all completed transfers are placed in the set result and result is not empty 
	 *the transfer completed and the step functioned correctly
	*/
	public void testStep(){
		
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.connect("nodeA", "nodeB");
		network.step();
		assertEquals(true, !network.emptyResult());
		
	}

	public static void main(String[] args) {
		 junit.textui.TestRunner.run(TestNetwork.class);

	}
	
}
