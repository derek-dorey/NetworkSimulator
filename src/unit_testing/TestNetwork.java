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
		network.create("nodeA");
		network.create("nodeB");
		assertEquals(network.hasNode("nodeB"),network.hasNode("nodeA"));
	}
	
	
	public void testRemove()
	{
		network.create("nodeA");
		network.create("nodeB");
		network.remove("nodeB");
		assertNotSame(true,network.hasNode("nodeB"));
	}
	
	
	public void testSetRate(){
		network.setRate(0);
		assertEquals(0,network.getRate());
		assertNotSame(1,network.getRate());
	}
	
	
	public void testConnect(){
		network.create("nodeA");
		network.create("nodeB");
		network.connect("nodeA", "nodeB");
		assertEquals(true, network.hasConnection("nodeA", "nodeB"));
			
	}
	
	public void testDisconnect(){
		network.create("nodeA");
		network.create("nodeB");
		network.disconnect("nodeA", "nodeB");
		assertEquals(false, network.hasConnection("nodeA", "nodeB"));
	}

	public static void main(String[] args) {
		 junit.textui.TestRunner.run(TestNetwork.class);

	}
	
}
