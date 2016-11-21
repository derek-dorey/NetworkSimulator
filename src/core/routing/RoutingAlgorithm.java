package core.routing;

import core.Node;


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
	SHORTEST_PATH(RandomRouter.class), 
	FLOOD(RandomRouter.class), 
	ADAPTIVE(RandomRouter.class);
	
	private Class<? extends Router> routerClass;
	
	private RoutingAlgorithm(Class<? extends Router> r){
		routerClass = r;
	}
	
	public Router getRouter(Node n){
		try {
			return routerClass.getConstructor(Node.class).newInstance(n);
		} catch (Throwable t) {
			throw new IllegalArgumentException();
		}
	}
}
