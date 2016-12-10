/*
Ahmed Mousa     0927129
CIS 2520        Assignment 1
September 25/2016
*/
int ackermann();
void mainAcker();

/*
 * mainAcker()
 * asks user for input then calls recursive function
 */
void mainAcker()
{
    printf("Program 2. Ackermanns method\n");

    int x;
    int y;
    int res;
    printf("Enter 1st number for ackermann:  \n~ ");
    scanf("%d", &x);
    printf("Enter 2nd number for ackermann:  \n~ ");
    scanf("%d", &y);

    res = ackermann(x,y);
    printf("Result is: %d\n", res);
}
/*
 * ackermann()
 * input: integers m and n to perform the 
 * 		  algorithm on
 * returns the ackermann of the values (m,n)
 * range so it doesnt exceed max int value:
 * m values can be 0,1,2,3 for
 * n values that are 0,1,2,3,4,5 without exceeding.
 * if m >= 4 then only (4,0) & (4,1) & (5,0) can be used 
 * without exceeding integer value.
 */
int ackermann(int m, int n)
{
    if(m == 0)
	return n+1;

    if(n == 0)
	return ackermann(m-1, 1);

    return ackermann(m-1, ackermann(m,n-1));
}
