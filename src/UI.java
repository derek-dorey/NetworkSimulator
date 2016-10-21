import java.util.Scanner;
import java.util.Arrays;

public class UI implements Runnable{
	
	private Network network;
	
	//Constructor
	public UI(Network network) {
		this.network = network;
	}

	public void run(){

		//system scanner
		Scanner reader = new Scanner(System.in);
		System.out.println("type 'help' for options");
		while(true){

			// prompt user to enter a command ... this is just to inform user that the program is running
			System.out.print(">");


			//remove white space and store into array s
			String[] s = reader.nextLine().split("\\s+");
			
			
			//System.out.println(Arrays.toString(s));

			if ("help".equalsIgnoreCase(s[0])) {

				if(s.length <= 2){
					help(Arrays.copyOfRange(s, 1, s.length));
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}

			} else if ("create".equalsIgnoreCase(s[0])){

				if(s.length > 1){
					create(Arrays.copyOfRange(s, 1, s.length));
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}
				
			} else if ("connect".equalsIgnoreCase(s[0])) {
			
				if(s.length == 3){
					connect(Arrays.copyOfRange(s, 1, s.length));
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}
				
			} else if ("disconnect".equalsIgnoreCase(s[0])) {

				if(s.length == 3){
					disconnect(Arrays.copyOfRange(s, 1, s.length));
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}
				
			} else if ("remove".equalsIgnoreCase(s[0])) {

				if(s.length > 1){
					remove(Arrays.copyOfRange(s, 1, s.length));
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}
				
			} else if ("send".equalsIgnoreCase(s[0])) {

				if(s.length == 3){
					send(Arrays.copyOfRange(s, 1, s.length));
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}
				
			} else if ("quit".equalsIgnoreCase(s[0])) {

				if(s.length == 1){
					break;
				}else{
					System.out.println("This is not valid syntax, please use:");
					help(new String[]{s[0]});
				}
				
				
			} else{
				System.out.println("Invalid command");
				help(new String[]{});
			}
			
			
		}
		reader.close();

	}

	private void create(String[] args){
		int count = 0;
		for(String id : args){
			if(network.create(id)){
				count++;
			}
		}
		System.out.println("Created "+count+" out of "+args.length);
	}
	
	private void remove(String[] args){
		int count = 0;
		for(String id : args){
			if(network.remove(id)){
				count++;
			}
		}
		System.out.println("Removed "+count+" out of "+args.length);
	}
	
	private void connect(String[] args){
		if(network.connect(args[0],args[1])){
			System.out.println("Connected successfully");
		}else{
			System.out.println("Failed to connect");
		}
	}
	
	private void disconnect(String[] args){
		if(network.disconnect(args[0],args[1])){
			System.out.println("Disconnected successfully");
		}else{
			System.out.println("Failed to disconnect");
		}
	}
	
	private void send(String[] args){
		int jumps = network.send(args[0], args[1]);
		if(jumps >= 0){
			System.out.println("Number of hops: " + jumps);
		}
	}
	
	private void help(String[] arg){
		
		if(arg.length == 0){
			System.out.println("The following commands are valid: \n");
			System.out.println("help [command]");
			System.out.println("create id1 [id2 [id 3 ...]]");
			System.out.println("connect id1 id2");
			System.out.println("disconnect id1 [id2 [id 3 ...]]");
			System.out.println("remove id1 id2");
			System.out.println("send id1 id2");
			System.out.println("quit\n");
		}else{
			//TODO
			if("help".equalsIgnoreCase(arg[0])){
				System.out.println("help [command]");
				System.out.println("If the command is not given, it lists all valid command syntax");
				System.out.println("If the command is given, it lists it's syntax and an explination of what it does");
			}else if("create".equalsIgnoreCase(arg[0])){
				System.out.println("create id1 [id2 [id 3 ...]]");
				System.out.println("This command creates a node for each identifier listed");
			}else if("remove".equalsIgnoreCase(arg[0])){
				System.out.println("remove id1 [id2 [id 3 ...]]");
				System.out.println("This command removes each node listed");
			}else if("connect".equalsIgnoreCase(arg[0])){
				System.out.println("connect id1 id2");
				System.out.println("This command connects the pair of nodes to each other");
			}else if("disconnect".equalsIgnoreCase(arg[0])){
				System.out.println("disconnect id1 id2");
				System.out.println("This command disconnects the pair of nodes from each other");
			}else if("quit".equalsIgnoreCase(arg[0])){
				System.out.println("quit");
				System.out.println("This ends the program");
			}else{
				System.out.println("That is not a valid command\n");
				help(new String[]{});
			}
		}
	}
}










