****************************************************
Ahmed Mousa		0927129
CIS 2520		Assignment 1
September 25/2016
****************************************************

************
Compilation/Running
************
To compile this program execute one of the following command line arguments while in A1 directory:

for the 1st qs(carbon) : make carbon

for the 2nd qs(ackerman): make ackermann

for the 3rd qs(newton): make newton

or if you compile with gcc:
./a.out 1 (for carbon)
./a.out 2 (for ackermann)
./a.out 3 (for newton)


****************
Sample output
****************
1) carbon program if run with the string “abc” will output: 
abc, acb , bac, bca, cab, cba. (3! = 6 therefore this is correct). 

2) ackermann program if run with the numbers (3,3) will output:
61. A wolfram program was used to verify this to be correct.
if m >= 4 then only (4,0) & (4,1) & (5,0) can be used without exceeding integer value.

3) newton program if run with the numbers 100 and 0.001 will output:
10.00004636 for both the regular and recursive function. 
recursive will take : 15 ms while regular will take 13 ms. 
time difference will be displayed: 2 ms. (this might differ on different machines.)
NOTE: in the recursive and regular program a for loop is used to run to 5000000 as a means to increase execution time to find the difference between the 2 programs.  

****************
References
****************
the following references were used to obtain the algorithm for the permutation(#1) question:
1. http://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/

