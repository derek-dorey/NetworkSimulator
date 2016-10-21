import java.util.Map;

public class Network {
	
	private Map<String,Node> nodeNetwork;
	
	public Network() {}
	
	public boolean create(String id) {
		return true;
	}
	
	public boolean remove(String id) {
		return true;
	}
	
	public boolean connect(String a, String b) {
		return true;
	}
	
	public boolean disconnect(String a, String b) {
		return true;
	}
	
	public int send(String from, String to) {
		return 0;
	}
}
