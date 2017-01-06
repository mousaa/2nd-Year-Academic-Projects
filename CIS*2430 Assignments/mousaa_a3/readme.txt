Ahmed Mousa
CIS 2430 Assignment 3


*************
Running
	to run the program go to the assignment directory(mousaa_a3):
	1)javac mousaa_a3/*.java
	2)java mousaa_a3.Main <filename> (file name used is input.txt)


*************
General problem program tries to solve:
**************
- creating an inventory list that is quickly searchable for electronic/book products
- implementing a robust program that is easily usable by any user.
- protecting data using valid mutators and accessors in classes and setting attributes to private
- prevents anyone from accessing these private methods outside a class
- dynamically grow an array during runtime instead of having one statically 
- Uses arrayLists in order to implement this
- implements hashmap to quickly search for given name keywords
- Reduces code by taking advantage of java's extends functionality and polymor


**************
Limitations/assumptions of program
**************
- Assumes user know what/where to input for specific fields. Assumes user knows already what theyre "adding"/"searching" etc.
- Limitations: 
	1)Assumes file format is the same as given in description(same order as well)
	2)Unfortunately my parsing doesnt take into account escape characters, did not have time to fix it..so program will just split input by qoutations
	3) user cant pick which file to save too. I just pick to save to a file called input.txt
	4) might not be a limitation but program could have been implemented better definitely. Some parts are messy especially search function. 
	5) very little checking when creating constructor. much/all checking for valid input is done before creating the constructor so if anyone else is using the program they would have to implement their own methods to check input before creating a product object which is definitely not logical. Sorry didnt have time to do most checking in constructor. 
	6) program doesnt reset fields if input is wrong. I thought that would be better since the user can see what they did wrong and fix it faster.
	7) Made the JFrame unresizable as if the user had the option to it would make the sizes of some component pretty weird and awkward looking.
	8) cant delete any product once entered.


************
User testing/building
- I believe the UI makes it easy for the user to follow along on what to do. 
- to throughouly test the program, users should input unexpected stuff in order to discover where some improvements should be made. 
- Program tries as much as possible to error check input and prompt user again to input instead of exiting from the method/function
- tested program on each step of the way to make sure its perfected before moving on to the next so it should handle any input correctly.


*************
Test Plan
**************
- Program makes sure user cant escape with entering any bad input maybe thats why some of the code is very repitive. 
	
- for reset button
	- successfully resets all TEXT fields.

- for Adding: 
	- any invalid input will be typed in Text area for user to see for example:
		- Entering product id that is not digits only, less than 6 digits -> prints out ERROR ENTER VALID ID.
		- entering duplicate id -> ERROR DUPLICATE ID.
		- no name entered -> ERROR MUST ENTER NAME
		- Price is string -> ERROR INVALID
		- Year is less than 1000/greater than 4 digits, has strings? -> ERROR INVALID.
		- maker/author/publisher -> if its only numbers -> ERROR.(i thought it would be logical if these fields were strings not numbers.. why would user enter number for author? hence the error.)
- for searching
	- implemented pretty poorly and very repitive as i wanted to narrow down every case/combination user enters.
	- for example possible scenarios accounted for:
		- id alone, id with key, id with start year, id with endYear.. and so on for all until last scenario is user entered all id, key, start year and end year.
	- if user enters start year > end year -> ERROR NOT FOUND.
	- invalid product ID/start/endYear will automatically result in ERROR being displayed before actually searching.  

for all the above, i tested with all weird input to ensure program does not break but instead displays whats wrong to the user so they can try again.


**************
Possible improvements to be done for future assignment:
**************
- include Testing files to illustrate the weird input the program can handle
- provide user with an example string/int of expected input if enters it wrong
- improve search method to avoid code repitition (sorry last min horrible coding) 
- when user enters data, confirms with user first(shows all data about to be added to arrayList) before adding it incase user wants to make some final changes
- Possibly use a Set instead of an arrayList in the hashmap in order to avoid duplicate values in scenarios like: name = " java java" now key(java) will have value[0,0]. With sets i think this would not repeat.
- Confirmation window upon exiting program/ hitting quit button. Confirmation window before adding products.
- option to delete products instead of manually going through text file.


**************
Resources used
**************
1. TA Irenaeus' code for lab 5 example. Thank you Irenaeus,  helped out a lot in getting this assignment done without much trouble. 