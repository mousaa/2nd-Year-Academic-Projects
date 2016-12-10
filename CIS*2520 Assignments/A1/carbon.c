/*
Ahmed Mousa     0927129
CIS 2520        Assignment 1
September 25/2016
*/


void switchChars(char *a, char *b);
void permute(char *string, int start, int end);
void carbon();

/*
 *   carbon()
 *   sets the string to carbon and
 *   calls the recursive function
 *   for the permutation.
 */

void carbon()
{
    printf("Program 1. Permutations\n");
    char string[] = "carbon";
    permute(string, 0, strlen(string) - 1);
}

/*
 *  switchChars()
 *  swaps character a with character b
 */
void switchChars(char *a, char *b) 
{
    char temp;
    temp = *a;
    *a = *b;
    *b = temp;
}


/*
 * permute()
 * input:
 * string = string to find permutation (carbon)
 * int start = index @ start of string (0) 
 * int end = last letter of the string(5) 
 * 
 * Function uses backtracking algorithm to find 
 * permutation of the given string (carbon)
 *
 * Algorithm adapted from http://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
 * mentioned in readme as well
 */
void permute(char *string, int start, int end) 
{
    int j;
    if(start == end)
    {
        printf("%s\n", string);
    } 
    else 
    {
        for(j = start; j <= end; j++)
         {
            switchChars(string + start, string + j); //swap charc to permute
            permute(string, start + 1, end);
            switchChars(string + start, string + j);//backtrack to original string
        }
    }
}
