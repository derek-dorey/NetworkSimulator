package core.routing;

import java.util.Set;

import core.Message;
/**
 * This class is a parent class to the algorithms
 *
 */
public interface Router {
	public Set<String> route(Message toSend);
}
