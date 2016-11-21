SYSC 3110 
Bits please

Members:Denis Atikpladza		100938122
	Benjamin Tobalt				100936576
	Derek Dorey					100956400
	Griffin Szymanski-Barrett	100978435

===========================================================================================================================

Introduction:

Network simulator that simulates the performance of a random routing algorithm with a 
graphic user interface (GUI) that will allow the user to create a network. 

Currently we have implemented the random algorithm, flooding algorithm, shortest path algorithm and our local algorithm.
A brief description of the the algorithms are provided below.

Random algorithm:
when a message is recieved, it forwards it to a random neighbor.

Flooding algorithm:
when a a message is recieved it forwards the message to all of its neighbors except the one
it came from. 

Shortest path algorithm:
the routing tables are configured globally to deliver messages using the least
amount of intermediate nodes (a.k.a hops).

Local algorithm:
???


In a later development we will implement the xml export and import to resort a previous network and to save a network
which one wishes to save. We plan to implement this using in the gui using two new Jbuttons or utilize a drop down menu.
Next iteration we will also be implementing the step back functionality to the simulator.  

==========================================================================================================================

For this iteration our design decisions were:

A new gui was implemented using the Jgraph library and the network was heavily refactored from last iteration to better 
conform with MVC (Model View Controller). The gui now shows the network graph and the a more detailed output as requested from 
last iteration. The gui is update with respect to the networks model. Error messages have been added to the error handling
and more error cases have been implemented.

The flooding algorithm messages have a living time (number of hops) which when exceeded the message is removed. This
algorithm avoids infinite loops by not allowing a message to re-enter the same path twice.

The random algorithm has stayed the same since the last iterations it still uses a randomly generated number to determine which 
neighbor the message will be sent to next.

More test cases were provided to test the added added code. The 

===========================================================================================================================

How to run:

1) Import the project from .jar file or the provided source code.

Detailed instructions on how to run the program can be found in the Manual provided.
===========================================================================================================================
