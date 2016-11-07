package unit_testing;

import junit.framework.TestCase;
import core.Message;
import core.Network;
import core.Node;

/**
 * Test cases for the cores network class using JUnit 3 library. 
 * @author Benjamin Tobalt and Denis Atikpladza
 *
 */
public class TestNode extends TestCase
{
	Network network = new Network();
	Node nodeA = new Node(network, "NodeA");
	Node nodeB = new Node(network, "NodeB");
	Node nodeC = new Node(network, "NodeC");
	
	public void testReceiveAndSend()
	{
		Message msg = new Message("NodeA", "NodeB");
		nodeA.addNeighbour(nodeB);
		
		nodeA.send();
		for(Message m :nodeB.getBufferContents())
			{
			assertEquals(msg, m);
			}
	}
	
	
	public void testAddNeighbour()
	{
		nodeA.addNeighbour(nodeB);
		
		for(Node c : nodeA.getNeighbours())
		{
			assertEquals(c,nodeB);
		}
		
	}
	
	public void testRemoveNeighbour()
	{
		nodeA.addNeighbour(nodeB);
		nodeA.addNeighbour(nodeC);
		nodeA.removeNeighbour(nodeB);
		
		for(Node c : nodeA.getNeighbours())
		{
			assertNotSame(c,nodeB);
		}
	}
	
	public static void main(String[] args) 
	{
		 junit.textui.TestRunner.run(TestNode.class);
	}
	 
	
}
