SYSC 3110 
Bits please

Members:Denis Atikpladza		100938122
	Benjamin Tobalt			100936576
	Derek Dorey			100956400
	Griffin Szymanski-Barrett	100978435

======================================================================================================

Introduction:

Network simulator that simulates the performance of the random routing algorithm. 

Currently we have implemented the random algorithum; when a router receives a message, it forwards it 
to a random neighbour. 

After this opperation is complete the program will print where the message was sent from, were it
arrived and what the average number of hops was.


======================================================================================================

How to run:

1)build the project and run

2)You will be propted to enter a command. The valid commands are listed below:
	
	create 		(ie create A B C)
	connect 	(ie connect A B)
	disconnect 	(ie disconnect A B)
	remove 		(ie remove B [This will remove the node and it's connections])
	simulate 	(ie simulate 5 2 will send 2 mesages at time intervals seperated by 5 simulation cycles)
	
	quit 
	help 

	If you forget the commands the help command will list the possible commands and show the
	proper syntax for those commands. 
	
	Typing 'help [command]' will give a brief description of
	the commands function and also provide the syntax for that command. 

	An example of how to implement a very simple network design is shown below:

			
				
				
				e
			       / \
			      a---b
			     /     \
			    c-------d	


	create a b c d
	connect a b
	connect a c
	connect c d
	connect d b
	create e
	connect e a
	connect e b	
	simulate 5 2 

3)When completed simply type 'quit' to exit the simulation.

========================================================================================================