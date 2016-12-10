/*
Ahmed Mousa     0927129
CIS 2520        Assignment 1
September 25/2016
*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <sys/timeb.h> //for ftime

#include "carbon.c"
#include "ackermann.c"
#include "newton.c"

/*
 *  main()
 *  uses command line arguments to detect which program to run
 *  (make carbon, make ackermann, make newton)
 */
int main(int argc, char* argv[])
{
    //check argument count
    if(argc != 2)
    {
	printf("*Invalid # of arguments*\n");
        printf("Input the program name followed by one of the following:\n");
        printf("1. \"carbon\"\n2.\"ackerman\"\n3.\"newton\"\n");
        printf("Program will exit now.\n");
        exit(1);
    }


    /*
     *atoi used to switch first argument string to integer
     * corresponding to which program to run
     */

    switch(atoi(argv[1]))
    {
	case 1:
	    carbon();
	    break;
	case 2:	
	    mainAcker();
	    break;
	case 3:
	    newtonRecMain();
            break;
	default: //incase u compile urself and input wrong numbers.
	    printf("Enter number between 1-3:\n");
	    printf("1. \"carbon\"\n2.\"ackerman\"\n3.\"newton\"\n");
	    printf("Program will exit now.\n");
	    break;
    }

    return 0;
}