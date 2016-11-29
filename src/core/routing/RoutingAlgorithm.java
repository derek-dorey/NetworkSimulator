package core.routing;

import core.NetworkNode;


/**
 * This enum acts as a factory class
 * 
 * It does use reflection to instantiate the router
 * I am doing this instead of having a long if tree in a factory class
 * 
 * @author Griffin
 *
 */
public enum RoutingAlgorithm {
	RANDOM(RandomRouter.class), 
	SHORTEST_PATH(ShortestPathRouter.class), 
	FLOOD(FloodRouter.class), 
	ADAPTIVE(AdaptiveRouter.class);
	
	private Class<? extends Router> routerClass;
	
	private RoutingAlgorithm(Class<? extends Router> r){
		routerClass = r;
	}
	
	public Router getRouter(NetworkNode n){
		try {
			return routerClass.getConstructor(NetworkNode.class).newInstance(n);
		} catch (Throwable t) {
			throw new IllegalArgumentException("The enum is broken");
		}
	}
}
