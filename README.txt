SYSC 3110 
Bits please

Members:Denis Atikpladza		100938122
	Benjamin Tobalt			100936576
	Derek Dorey			100956400
	Griffin Szymanski-Barrett	100978435

======================================================================================================

Introduction:

Network simulator that simulates the performance of the random routing algorithm with a 
graphic user interface(GUI) that will allow the user to create a network through the GUI. 

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

	Step

	New (valid input: "Name" *click create*, you can only create one node at a time)

	Delete (valid input: "Name" *click delete*, you can only delete one node at a time)

	Connect (valid input: "Name Name" *click connect*, must have a space between the two names)

	Disconnect (valid input: "Name Name" *click disconnect*, must have a space between the two names)

	Set Rate (valid input: "int" *click set*, Default rate is 1, ie message is created every time
		step is pressed.)


3) When completed simply click the X in the top right corner of the frame to end the simulation.

========================================================================================================