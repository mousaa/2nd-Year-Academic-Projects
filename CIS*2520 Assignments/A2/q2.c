/*
Ahmed Mousa     0927129
CIS 2520        Assignment 1
September 25/2016
*/

#define SIZE 100    //stack size 
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

//global variables
double stack[SIZE]; //array implementation
int top=-1;  //pointer to top of stack

//adapted from https://www.tutorialspoint.com/data_structures_algorithms/stack_algorithm.htm


void push(double elem);
int pop();
   

/*
 *  main()
 *  input postfix expression as command line argument
 *  output value of expression
 */ 

int main(int argc, char* argv[])
{                
	if(argc != 2)
    {
		    printf("*Invalid # of arguments*\n");
        printf("run by inputting: ./q2 <postfixExpression>\n");
        printf("Program will exit now.\n");
        exit(1);
    } 
  
	char string[100] = "";
  strcpy(string, argv[1]); 

	char ch;

	int i=0;
	double num_1;
	double num_2;

  //error checking
  for(int j = 0; j < strlen(string); j++)
  {
    if(!isdigit(string[i]))
    {
      printf("Error - either expression does not contain any digits\n");
      printf("or given expression is invalid(Ex: starts with operator)\n");
      printf("Please re-run with appropriate expression\n");
      printf("Program is exiting\n");
      exit(1);
    }
  }
 	while( (ch=string[i]) != '\0') //end of string
 	{
  		if(isdigit(ch)) //push if its a digit
  			push(ch - '0');
  		else //pop if its not
  		{        
   			num_2 = pop();
   			num_1 = pop();
   			switch(ch)
  	 		{	
   				case '+':
   					push(num_1 + num_2);
   					break;
   				case '-':
   					push(num_1 - num_2);
   					break;
   				case '*':
   					push(num_1 * num_2);
   					break;
   				case '/':
   					push(num_1 / num_2);
   					break;
   				default: 
   					printf("Error could not read the following sign : [%c]\n" , ch);
   					printf("Only (\\,+,-,*) are accepted\n");
   					printf("Program is exiting\n");
   					exit(1);
   			}
  		}
  		i++;
	}

 	printf("\n Given Postfix Expn: %s\n",string);
 	printf("\n Result after Evaluation: %0.2f\n",stack[top]);

 	return 0;
}


/*
 *  push()
 *  input: elem to push on stack
 */ 

void push(double elem)
{         
	top++;              
	stack[top]=elem;
}

/*
 *  pop()
 *  input element to pop from stack
 */ 

int pop()
{                    
	return(stack[top--]);
}