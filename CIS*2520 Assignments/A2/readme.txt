****************************************************
Ahmed Mousa		0927129
CIS 2520		Assignment 2
October 17/2016
****************************************************

************
Compilation/Running
************
To compile this program execute one of the following command line arguments while in A1 directory:

for the 1st qs: make q1

for the 2nd qs: make q2 then ./q2 <postfixExpression>
Example: ./q2 56+ 

****************
Sample output
****************
1) All lists tested produce correct output. 
- if you wanted to transfer from one list to another a car which happens to be the last car on the list, program will successfully transfer it however it will not delete it from the list in order to keep atleast 1 car in each list(the head node). I did that in order to avoid a segfault next time I run the program. (When i parse the file next time, if the list was empty segfault occurs as parsing the file assumes that there is some data for each list).
- yymmdd assumes all years after 2000
- assumes plate number is 7 characters with no spaces



2) if q2 is run with the following command line: 56+ result will be 11.00
However, if run with additional operations such as 23++ result will be 5.00(additional + will be ignored) (limitation)
If program exceeds stack size seg fault will occur. Program doest not expect huge expressions (>100 characters) 

****************
References
****************
the following references were used to obtain different algorithms q1 and q2
1.https://www.tutorialspoint.com/data_structures_algorithms/stack_algorithm.htm
2.http://stackoverflow.com/questions/5526750/linked-list-sorting-in-c
3.http://www.geeksforgeeks.org/delete-a-given-node-in-linked-list-under-given-constraints/
 

