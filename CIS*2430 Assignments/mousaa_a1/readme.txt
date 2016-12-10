Ahmed Mousa
CIS 2430 Assignment 1

*************
General problem program tries to solve:
- protecting data using valid mutators and accessors in classes and setting attributes to private
- prevents anyone from accessing these private methods outside a class
- dynamically grow an array during runtime instead of having one statically 
- Uses arrayLists in order to implement this


**************
Limitations/assumptions of program
- Program tries to use as much hints as possible as to what the user is allowed to enter
- Limitations: if user enters to add function for example by accident there is no way to go back so must complete the whole function input first


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

Next prompts user first for either book add or electronic add.
- accepts input: id, price, year, name, maker, author, publisher.
1. id: input MUST be of length 6 and ONLY numbers anything else disregarded
2. price: can be either with decimal or without decimal. if theres any characters, input disregarded
3.year: accepted only if valid 4 length and greater than 1000
4. name, maker(optional)/author(optional)/publisher(optional): must input name cant skip it. if name contains only numbers will reject it. Must contain a mix of digits and strings.

for all the above, i tested with all weird input to ensure program does not break but instead prompts user to re enter value.


**************
Possible improvements to be done for future assignment:
- javadoc? Was not able to do it through eclipse for some reason so next assignment i will be using netbeans instead.
- include Testing files to illustrate the weird input the program can handle
- provide user with an example string/int of expected input if enters it wrong
- improve search method since it does not meet the expectations(started this assignment late unfortunately)
- allows user to traverse back and forth between choices incase user enters something accidently
- when user enters data, confirms with user first(shows all data about to be added to arrayList) before adding it incase user wants to make some final changes
