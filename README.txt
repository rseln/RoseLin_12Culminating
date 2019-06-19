READ ME:

While the inital purpose of the game was to have a user draw an image from a word prompt and have another 
user guess the word on a seperate platform, the program I wrote can only send over the prompt. I have not 
yet been able to figure out how to send an image to the client, however, there has been a successful connection
established between the server (drawingGUI) and the client (guessingGUI). 

Currently, I have attempted to incorporate several elements of course material into my program. My current program 
runs at a time of O(n) and also implements the Stack ADT in the undo button found in the guessingGUI.

USING THE PROGRAM:

Seeing as there is no main menu as of right now, to run to program, the user must first run the DrawingGUI and then 
run the GuessingGUI. In the textfield that is indicated by the label called "Server Name" (On the guessingGUI), 
the user would input "localhost" to form a connection between the server and the client. Clicking the connect
button will allow the information from the server to transfer over to the client. 

TO IMPLEMENT IN THE FUTURE:

There are several improvements to be made to the project. Of course the most glaring problem is the fact that 
the image cannot send over to the client, defeating the entire purpose of the application. In addition to that, 
I also plan to improve user experiance by creating a main menu that would allow the user(s) to choose between being 
the server or the client. Other improvements/modifications may be adding a timer and a points system. 