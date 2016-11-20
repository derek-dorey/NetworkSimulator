package core;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	public int getId(){
		return 0;
	}
	
	public List<String> getHistory(){
		return new ArrayList<>();
	}
	
	public boolean received(){
		return false;
	}

	public int hops() {
		// TODO Auto-generated method stub
		return 0;
	}
}
