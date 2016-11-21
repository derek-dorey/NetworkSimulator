package core.routing;

import java.util.Set;

import core.Message;

public interface Router {
	public Set<String> route(Message toSend);
}
