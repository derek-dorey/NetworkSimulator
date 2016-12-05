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
when a message is received, it forwards it to a random neighbor.

Flooding algorithm:
when a a message is received it forwards the message to all of its neighbors except the one
it came from. 

Shortest path algorithm:
the routing tables are configured globally to deliver messages using the least
amount of intermediate nodes (a.k.a hops).

Better random algorithm:
This algorithm will "learn" about the network it is in and gets better over time by
recording where it has been before and will not go back there unless it absolutely 
has to get to it's target.

Finally we have implemented the xml export and import to resort a previous network and to save 
a network which one wishes to save. We have done this through the gui using a new button called "save".
  

==========================================================================================================================

For this iteration our design decisions were:



To filter through all comments made on previous iterations and ensure that we
address anything that was an outstanding issue. Also refactoring the code and removing
all "code smells" was very important as well so our implementation of this iteration was strongly
influenced by this.

More test cases were provided to test the network class and all the algorithms more accurately.

===========================================================================================================================

How to run:

1) Import the project from .jar file or the provided source code.

Detailed instructions on how to run the program can be found in the Manual provided.
===========================================================================================================================
