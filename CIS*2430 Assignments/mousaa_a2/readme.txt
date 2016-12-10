Ahmed Mousa
CIS 2430 Assignment 2


*************
Running
	to run the program go to the assignment directory(mousaa_a2):
	1)javac mousaa_a2/*.java
	2)java mousaa_a2.Main <filename> (file name used is input.txt)


*************
General problem program tries to solve:
- protecting data using valid mutators and accessors in classes and setting attributes to private
- prevents anyone from accessing these private methods outside a class
- dynamically grow an array during runtime instead of having one statically 
- Uses arrayLists in order to implement this
- implements hashmap to quickly search for given name keywords
- Reduces code by taking advantage of java's extends functionality


**************
Limitations/assumptions of program
- Program tries to use as much hints as possible as to what the user is allowed to enter
- Limitations: 
	1)if user enters to add function for example by accident there is no way to go back so must complete the whole function input first
	2)Assumes file format is the same as given in description(same order as well)
	3)Unfortunately my parsing doesnt take into account escape characters did not have time to fix it..so program will just split input by qoutations


************
User testing/building
- Any user can run the program and follow the prompts on what to input
- to throughouly test the program, users should input unexpected stuff in order to discover where some improvements should be made etc
- Program tries as much as possible to error check input and prompt user again to input instead of exiting from the method/function



*************
Test Plan
- In order to make sure input is correct before proceeding with anything program uses booleans in while loops with flags that once set to true(input verified) then the data gets stored
Basic test plan this program follows:

- menu loop:
valid inputs for add: add, a, ad, A. for search: search, s, S. for quit: quit, Quit, exit, Exit, q, Q.
ANYTHING else other than the folllowing would result program to print an error message and prompt user to enter a valid input.

- add function:
- accepts input: 1 for book 2 for electronic.
		anything else other than that will result in an error and the program prompts user again for either 1 or 2.
		this is done using try catch (exception handling) to catch possible inpput mismatch (user decides to enter string instead of integer)

After that prompts user first for either book add or electronic add.
and then asks for mandatory information and depending on wether user picked to add electronic or books, asks for more information(i.e maker for electronic)

- Search functionality
	accepts input: product array list
	first asks user for what they would like to search then exhaustively searches based on whats given: All possible scenarios taken into account perfectly
	1. name, time period, id not given = print all list function is called
	2. name, time period not given = searches just for id (same function)
	3. name, id not given = searches for time period
	4. name not given = searches for id and time period
	5. id not given = searches for name and time period
	6. time not given = searches for name and id
	(finds intersection between them)

- Parsing the file at the start
	Very basic parser does not take into account escape sequences then adds to arraylist

- Updating the file before quitting
	Uses the populated arrayList to overwrite the old file in order to make things efficient and easier to code

for all the above, i tested with all weird input to ensure program does not break but instead prompts user to re enter value.


**************
Possible improvements to be done for future assignment:
- include Testing files to illustrate the weird input the program can handle
- provide user with an example string/int of expected input if enters it wrong
- improve search method to avoid code repitition (sorry last min horrible coding) 
- allows user to traverse back and forth between choices incase user enters something accidently
- when user enters data, confirms with user first(shows all data about to be added to arrayList) before adding it incase user wants to make some final changes
- Possibly use a Set instead of an arrayList in the hashmap in order to avoid duplicate values in scenarios like: name = " java java" now key(java) will have value[0,0]. With sets i think this would not repeat.
