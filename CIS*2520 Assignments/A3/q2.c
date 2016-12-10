/*
Ahmed Mousa     0927129
CIS 2520        Assignment 3
October 25/2016
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void parseFile(FILE* fp, int arr[20][12]);
void initKey(int arr[20][12]);
void heap(int arr[20][12]);
void swap(int arr[12], int arr2[12]);

int main(int argc, char* argv[])
{
	//check argument count
	if(argc != 2)
    {
		printf("*Invalid # of arguments*\n");
        printf("run by inputting: ./q2 f.dat\n");
        printf("Program will exit now.\n");
        exit(1);
    }
    FILE* fp;
    int i,j = 0;
	fp = fopen(argv[1], "r");
	int arr[20][12];
	parseFile(fp, arr);
	initKey(arr);
   	heap(arr);

   	printf("Sorted heap: \n");

   	for(i = 0; i < 20; i++)
   	{
   		/*
   		   starts at 2 because first element is key and next one is not used
			to make heap sorting easier
		*/  
		for(j = 2; j < 12; j++)
   		{
   			printf("%d ", arr[i][j]);
   		}
   		printf("\n");
   	}

   	return 0;
}

void heap(int arr[20][12])
{	
	int parent = 0;
	int child = 0;
	int ctr;

	for(parent = 0; parent < 20; parent++)
	{
		ctr = 0;
		child = (parent*2) + 1;

		if(child >= 20)
		{
			return;
		}

		if(arr[parent][1] > arr[child][1])
		{
			swap(arr[parent], arr[child]);
			ctr = 1;
		}

		if(child+1 >= 20)
		{
			return;
		}

		if(arr[parent][1] > arr[child+1][1])
		{
			swap(arr[parent],arr[child+1]);
			ctr = 1;
		}
		
		//swap was done -> restart whole thing
		if(ctr == 1)
		{
			parent = -1;
		}
	}
}

void swap(int arr[12], int arr2[12])
{
	int i = 0;
	int temp = 0;

	for(i = 0; i  < 12; i++)
	{
		temp = arr[i];
		arr[i] = arr2[i];
		arr2[i] = temp;
	}
}
//adds key to beginning of array and resizes array
void initKey(int arr[20][12])
{
	int i = 0;
	for(i = 0; i < 20; i++)
	{
		arr[i][0] = i;
		arr[i][1] = arr[i][2] + arr[i][3] + arr[i][4];
	}
}

//initialises contents of 2d array from file
void parseFile(FILE* fp, int arr[20][12])
{	
	int i = 0;
	int j = 0;

	//scan into 2d Array
	for(i = 0; i < 20; i++)
	{
		for(j = 2; j < 12; j++)
		{
			fscanf(fp, "%d", &arr[i][j]);
		}
	}
}