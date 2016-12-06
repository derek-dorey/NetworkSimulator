package testing;

import core.Network;
import core.routing.RoutingAlgorithm;
import junit.framework.TestCase;

/**
 * Test cases for the cores network class using JUnit 3 library. 
 * @author Denis Atikpladza
 *
 */
public class NetworkTests extends TestCase 
{
	Network network = new Network(RoutingAlgorithm.RANDOM);
	
	
	public void testgetNodes()
	{
		boolean temp = false;
		network.createNode("nodeA");
		
		for (String s : network.getNodes())
		{
		    if(s.equals("nodeA"))
		    {
		    	temp = true;
		    }
		}
		assertEquals(temp, true);
		
	}
	
	public void testCreateNode(){
		boolean temp = false;
		
		network.createNode("nodeA");
		
		for (String s : network.getNodes())
		{
		    if(s.equals("nodeA"))
		    {
		    	temp = true;
		    }
		}
		
		assertEquals(temp, true);
	}
	
	public void testDestroyNode()
	{
		boolean temp = false;
		
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.destroyNode("nodeB");
		
		for (String s : network.getNodes())
		{
		    if(s.equals("nodeB"))
		    {
		    	temp = true;
		    }
		}
		assertEquals(temp, false);
	}
	
	public void testDestroyAll()
	{
		Network network2 = new Network(RoutingAlgorithm.RANDOM);
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.destroyAllNodes();
		
		assertEquals(network.getNodes(),network2.getNodes() );
	}
	
	public void testConnectNodes(){
		network.createNode("nodeA");
		network.createNode("nodeB");
		assertEquals(true,network.connectNodes("nodeA", "nodeB"));
	}
	
	public void testDisconnectNodes(){
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.connectNodes("nodeA", "nodeB");
		assertEquals(true,network.disconnectNodes("nodeA", "nodeB"));
	}
	
	public void testisAconnectedGraph()
	{
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.createNode("nodeC");
		network.connectNodes("nodeA", "nodeB");
		network.connectNodes("nodeC", "nodeB");
		assertEquals(true,network.isAConnectedGraph());
		
	}
	
	public void testDisconnectAllNodes(){
	
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.createNode("nodeC");
		network.connectNodes("nodeA", "nodeB");
		network.connectNodes("nodeC", "nodeB");
		network.disconnectAllNodes();
		
		
		assertFalse(network.isAConnectedGraph());
	}
	
	
	public void teststep()
	{
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.connectNodes("nodeA", "nodeB");
		
		network.step();
		
		assert(network.getMessageBufferFromNode("nodeA") != null);
		
		assert(network.getMessageBufferFromNode("nodeB") != null);
		
	}
	
	public void testclearNetworkBuffers()
	{
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.connectNodes("nodeA", "nodeB");
		
		network.step();
		
		if(network.getMessageBufferFromNode("nodeA") != null)
		{
			network.clearNetworkBuffers();
			assert(network.getMessageBufferFromNode("nodeA").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeB") != null)
		{
			network.clearNetworkBuffers();
			assert(network.getMessageBufferFromNode("nodeB").isEmpty());
		}
	}
	
}