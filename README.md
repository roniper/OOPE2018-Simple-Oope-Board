# OOPE2018-Simple-Oope-Board

Course work for the course Olio-ohjelmoinnin perusteet (OOPE) in 2018

Held in University of Tampere

(Due to course requirements the code and comments are mainly in Finnish)

## The program

The program is a clumsy example of how chat forum works. The user has practically a conversation with himself/herself.
All of the threads are individualized with an integer starting from number one and same thing for messages and replies.

The commands to run the program are following:

add <topic>
  - Creates new topic to the forum
  
catalog
  - Lists all the topics and shows the amount of messages under topic
  
select <number>
  - Sets already created thread active
  
new <message> <possible file>
  - Creates new message to a thread (not a reply)
  
reply <number> <message>
  - Creates a reply to chosen message
  
tree
  - Recursively lists all messages of the active thread
  
list
  - Prints out all messages of the active thread in the order of individualizing numbers
  
head <number>
  - Prints out the wanted amount of messages (given as parameter) from the beginning of active thread
  
tail <number>
  - Prints out the wanted amount of messages (given as parameter) from the end of active thread
  
empty <number>
  - Clears the message in given position of the thread
  
find <string>
  - Searches for similar strings as the string given as parameter from the active thread
  
exit
  - Prints out a goodbye message and exits the program
