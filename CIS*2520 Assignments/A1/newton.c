/*
Ahmed Mousa     0927129
CIS 2520        Assignment 1
September 25/2016
*/
double newtonRec(double x,double e,double a);
void newtonRecMain();
int newtonReg();


/* 
 *  newtonRecMain()
 *  asks for the numbers to perform the newton method on first
 *  then calls both the recursive and non recursive functions
 *   uses ftime() to calculate the time difference between both
 */
void newtonRecMain()
{
    struct timeb start, end; //for ftime
    printf("Program 3. Newtons method(RECURSIVE)\n");
    double x;
    double e;
    double a;
    double answer;
    int recTime;
    int regTime;

    printf("Enter a (big) number to calculate root of: \n~ ");
    scanf("%lf", &x);

    printf("Enter a (small) number to calculate accuracy: \n~ ");
    scanf("%lf", &e);
    a = x/2;

    //start time for recursive 
    ftime(&start);
    for(int i =0; i<5000000; i++); //for loop to extend the recursive functions execution time
    answer = newtonRec(x,e,a); //recursive function
    ftime(&end); 
    recTime = (int) (1000.0 * (end.time - start.time)
        + (end.millitm - start.millitm)); //changes time to milliseconds

    printf("(RECURSIVE)Square root of %.8f using Newton's method is %.8f\n", x ,answer);
    printf("(RECURSIVE) took %u milliseconds\n\n", recTime);
    //end time for recursive

    //regular function
    regTime = (int) newtonReg(x,e,a);


    printf("Time difference: %u milliseconds(ABS VALUE)\n", abs((recTime-regTime)));
}

/*
 *	newtonReg()
 *  finds the square root of x non recursively
 *  input: x = big number to square root
 *         e = small number for accuracy
 *         a = to find the square root of x (as per assignment instructions)
 *   returns time it took to execute the algorithm
 */
int newtonReg(double x, double e, double a)
{
    //for ftime
    struct timeb first, last;
    int time;


    int flag = 1; 

    ftime(&first);
    for(int i =0; i<5000000; i++);
    while(flag)
    {
        if(fabs(a*a - x) <= e)
	flag = 0;

	else
	{
	    a = (a+x/a) / 2;
	    continue;
	}
    }

    ftime(&last);
    time = (int) (1000.0 * (last.time - first.time)
        + (last.millitm - first.millitm));

    printf("(REGULAR)Square root of %.8f using Newton's method is %.8f\n", x ,a);
    printf("(REGULAR) took %u milliseconds\n\n", time);

    return time;
}

/*
 *	newtonReg()
 *  finds the square root of x recursively
 *  input: x = big number to square root
 *         e = small number for accuracy
 *         a = to find the square root of x (as per assignment instructions)
 *   returns the square root found
 */

double newtonRec(double x, double e, double a)
{	
    if(fabs(a*a - x) <= e)
	return a;
		
    else
    {
	a = (a+x/a) / 2;
	return newtonRec(x,e,a);
    }
    return a;
}

