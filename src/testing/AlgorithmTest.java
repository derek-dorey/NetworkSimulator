package testing;

import core.Network;
import core.routing.RoutingAlgorithm;
import junit.framework.TestCase;

/**
 * Test cases for the core algorithms class using JUnit 3 library. 
 * @author Denis Atikpladza
 *
 */
public class AlgorithmTest extends TestCase
{
	
	public void testShortestPath()
	{
		Network network = new Network(RoutingAlgorithm.SHORTEST_PATH);
		
		boolean test = false;
		
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.createNode("nodeC");
		network.createNode("nodeD");
		
		network.connectNodes("nodeA", "nodeB");
		network.connectNodes("nodeA", "nodeC");
		network.connectNodes("nodeC", "nodeD");
		
		network.setMessageCreationPeriod(10000);
		
		network.step();
		 
		if(network.getMessageBufferFromNode("nodeA") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeA").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeB") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeB").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeC") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeC").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeD") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeD").isEmpty());
		}
		
		else{
			assert(test);
		}
		 
	}
	public void testFlooding()
	{
		boolean test = false;
		
		Network network = new Network(RoutingAlgorithm.FLOOD);
		
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.createNode("nodeC");
		network.createNode("nodeD");
		
		network.connectNodes("nodeA", "nodeB");
		network.connectNodes("nodeA", "nodeC");
		network.connectNodes("nodeC", "nodeD");
		network.connectNodes("nodeB", "nodeD");
		
		network.setMessageCreationPeriod(10000);
		
		network.step();
		 
		if(network.getMessageBufferFromNode("nodeA") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeA").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeB") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeB").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeC") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeC").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeD") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeD").isEmpty());
		}
		
		else{
			assert(test);
		}
	}
	
	public void testRandom()
	{
		boolean test = false;
		
		Network network = new Network(RoutingAlgorithm.RANDOM);
		
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.createNode("nodeC");
		network.createNode("nodeD");
		
		network.connectNodes("nodeA", "nodeB");
		network.connectNodes("nodeA", "nodeC");
		network.connectNodes("nodeC", "nodeD");
		
		network.setMessageCreationPeriod(10000);
		
		network.step();
		 
		if(network.getMessageBufferFromNode("nodeA") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeA").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeB") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeB").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeC") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeC").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeD") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeD").isEmpty());
		}
		
		else{
			assert(test);
		}
	}
	
	public void testAdaptive()
	{
		boolean test = false;
		
		Network network = new Network(RoutingAlgorithm.BETTER_RANDOM);
		
		network.createNode("nodeA");
		network.createNode("nodeB");
		network.createNode("nodeC");
		network.createNode("nodeD");
		
		network.connectNodes("nodeA", "nodeB");
		network.connectNodes("nodeA", "nodeC");
		network.connectNodes("nodeC", "nodeD");
		
		network.setMessageCreationPeriod(10000);
		
		network.step();
		 
		if(network.getMessageBufferFromNode("nodeA") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeA").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeB") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeB").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeC") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeC").isEmpty());
		}
		
		if(network.getMessageBufferFromNode("nodeD") != null)
		{
			network.step();
			assert(network.getMessageBufferFromNode("nodeD").isEmpty());
		}
		
		else{
			assert(test);
		}
	
	}
}
