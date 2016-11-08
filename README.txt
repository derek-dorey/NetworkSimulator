SYSC 3110 
Bits please

Members:Denis Atikpladza		100938122
	Benjamin Tobalt			100936576
	Derek Dorey			100956400
	Griffin Szymanski-Barrett	100978435

======================================================================================================

Introduction:

Network simulator that simulates the performance of a random routing algorithm with a 
graphic user interface (GUI) that will allow the user to create a network. 

Currently we have implemented the random algorithm; when a router receives a message, it forwards it 
to a random neighbour. 

In a later development we will implement the flooding, shortest path, and another local method 
that we will decide on in a later development. 

After this operation is complete the program will 

For this iteration our design decisions are:

======================================================================================================

How to run:

1)build the project and run from Network class

2)A GUI interface will pop up with the following options:

	Step: Press the button to step through simulation. The messages will no longer appear in a buffer once they have reached their  
	destination. Furthermore, only the first message in each populated buffer will "step" with each step.

	New (valid input: "Name" *click create*, you can only create one node at a time) 

	Delete (valid input: "Name" *click delete*, you can only delete one node at a time)

	Connect (valid input: "Name Name" *click connect*, must have a space between two or more names, e.g. "a b c d")

	Disconnect (valid input: "Name Name" *click disconnect*, must have a space between two or more names, e.g. "a b")

	Set Rate (valid input: "5" *click set*, Default rate is 1, ie a new message is created every time
		step is pressed.)
		
	Exit any popup during the application by clicking the "x" icon in the upper right-hand corner.

3) example set up: 
	1.Create nodes a b c d (separetely) using new
	2.Provide desired connections or use *all connect feature*
	3.Provide a desired rate, if not the default rate is 1
	4.Press step to step through

*All connect feature works if you click connect then type in a b c d, a will be connected to b
								      b will be connected to a and c
								      c will be connected to b and d
								      d will be connected to c
similar connections will happen for larger networks								   

4) When completed simply click the X in the top right corner of the frame to end the simulation.

========================================================================================================
