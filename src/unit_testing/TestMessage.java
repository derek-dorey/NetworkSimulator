package unit_testing;

/**
 * Test cases for the cores Message class using JUnit 3 library. 
 * @author Benjamin Tobalt 
 *
 */

import core.Message;
import core.Network;
import core.Node;
import junit.framework.TestCase;

public class TestMessage extends TestCase{

	Network network = new Network();
	Node nodeA = new Node(network, "NodeA");

	public void testHops(){
		Message msg = new Message("NodeA", "NodeB"); 
		int hop = msg.getHops();
		msg.incHops();
		int incrementedHop = msg.getHops();
		assertEquals(hop+1, incrementedHop);
	}
	
	public void testToString(){
		Message msg = new Message("NodeA", "NodeB"); 
		assertEquals("Message from:'NodeA' to:'NodeB' with:0 hops", msg.toString());
	}
	
	
	
	public static void main(String[] args) {
		 junit.textui.TestRunner.run(TestNetwork.class);
	}
}