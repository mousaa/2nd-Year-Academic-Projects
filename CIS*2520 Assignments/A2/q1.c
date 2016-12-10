/*
Ahmed Mousa     0927129
CIS 2520        Assignment 2
October 16/2016
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//generic car with attributes
typedef struct Car
{
	char plateNum[10];
	int mile;
	int ret_date;
	struct Car* next;
} Car;

//head to linked lists
Car* available_head;
Car* rented_head;
Car* repair_head;

//all functions
void printMenu(FILE* file);
void parseFile(FILE* file);
void printAllLists(Car* available_head, Car* rented_head, Car* repair_head);
int searchRented(char plateNum[10] , int mile, Car* head);
void deleteNode(Car* node, Car* head);
double mileTracker(int x);
void updateFile();
int searchRepair(char plateNum[10], Car* head);
Car* addtoFront(Car* head, Car* toBeAdded);
Car* sortList(Car* available_head, Car* rented_head, char list);



/*
 * main()
 * opens file to initalize data then prints menuloop
 */
int main()
{
	FILE* file;
	file = fopen("data.txt", "r");
	if (file == NULL)
    {
        printf("Error opening file\n");
        exit(1);
    }
	parseFile(file); //and initializes data for linked lists

	fclose(file);
	available_head = sortList(available_head, rented_head, 'a');
	rented_head = sortList(available_head, rented_head, 'r');

	printMenu(file);

	return 0;
}

/*
 * parseFile()
 * input: file to parse
 * initializes the three lists based on whats in file
 * causes segfault is any of lists are empty and attempts to parseFile
 */
void parseFile(FILE* file)
{
	char line[30];
	char plateNum[10];
	int mile;
	int ret_date;

	Car* node;

	//initial data from file.
	while(fgets(line,sizeof(line), file) != NULL)
	{
		switch(line[0])
		{

			case 'a':
				{
					sscanf(line, "a %s %d", plateNum, &mile);

					node = malloc(sizeof(Car));
					if(node == NULL)
					{
						printf("malloc failed\n");
						exit(1);
					}
					strcpy(node->plateNum , plateNum);
					node->mile = mile;

					available_head = addtoFront(available_head, node);
					break;
				}
			case 'r':
				{
					sscanf(line, "r %s %d %6d", plateNum, &mile, &ret_date);

					node = malloc(sizeof(Car));
					if(node == NULL)
					{
						printf("malloc failed\n");
						exit(1);
					}
					strcpy(node->plateNum , plateNum);
					node->mile = mile;
					node->ret_date = ret_date;
					rented_head = addtoFront(rented_head, node);
					break;
				}
			case 'm':
				{
					sscanf(line, "m %s %d", plateNum, &mile);
					node = malloc(sizeof(Car));
					if(node == NULL)
					{
						printf("malloc failed\n");
						exit(1);
					}
					strcpy(node->plateNum,plateNum);
					node->mile = mile;
					repair_head = addtoFront(repair_head, node);
					break;
				}
		}
	}
}
/*
 * sortList()
 * input: available-for-rent list head, rented list head, character indicating which list to sort
 * returns head of whatever list it sorted
 */
Car* sortList(Car* available_head, Car* rented_head, char list)
{
	Car* retVal;
	switch(list)
	{
		//sorting list by mileage
		//http://stackoverflow.com/questions/5526750/linked-list-sorting-in-c
		case 'a':
		{

			Car* tempPtr = available_head;
			Car* tempNext = available_head->next;
			int temp;
			char temp2[10];
			while(tempNext != NULL)
			{
				while(tempNext != tempPtr)
				{
					if(tempNext->mile < tempPtr->mile)
					{
						temp = tempPtr->mile;
						strcpy(temp2, tempPtr->plateNum);
						strcpy(tempPtr->plateNum, tempNext->plateNum); 
						tempPtr->mile = tempNext->mile;
						strcpy(tempNext->plateNum, temp2);
						tempNext->mile = temp;
					}
					tempPtr = tempPtr->next;
				}
				tempPtr = available_head;
				tempNext = tempNext->next;
			}
			retVal = tempPtr;
			break;
		}
		//sorts by expected return date
		case 'r': //bit of an overkill can be simplified
		{
			Car* tempPtr2 = rented_head;
			Car* tempNext = rented_head->next;

			char temp2[10]; //to switch platenum
			int temp; //to switch expected return datea
			int temp3; //to switch mile
			int date1, date2;
			int year1, year2;
			int month1, month2;
			int day1, day2;
			while(tempNext != NULL)
			{
				while(tempNext != tempPtr2)
				{
					/*gets day, month and year from yymmdd format to compare values
					*runs this algorithm three times.
					*1. sorts year
					*2. sorts year, month
					*3. sorts year, month, day
					* sortof like a bubble sort
					*/

					date1 = tempNext->ret_date;
					date2 = tempPtr2->ret_date;
					day1 = date1 % 100;
					date1 /= 100;
					month1 = date1 %100;
					date1 /= 100;
					year1 = date1 % 100;
					date1 /= 100;
					day2 = date2 % 100;
					date2 /= 100;
					month2 = date2 %100;
					date2 /= 100;

					year2 = date2 % 100;
					date2 /= 100;
					if (year1 <= year2)
					{
						temp = tempPtr2->ret_date;
						strcpy(temp2, tempPtr2->plateNum);
						temp3 = tempPtr2->mile;
						

						tempPtr2->ret_date = tempNext->ret_date;
						strcpy(tempPtr2->plateNum, tempNext->plateNum);
						tempPtr2->mile = tempNext->mile;
						

						tempNext->ret_date = temp;
						strcpy(tempNext->plateNum, temp2);
						tempNext->mile = temp3;
					}
					tempPtr2 = tempPtr2->next;
				}	
				tempPtr2 = rented_head;
				tempNext = tempNext->next;
			}

			tempPtr2 = rented_head;
			tempNext = rented_head->next;
			while(tempNext != NULL)
			{
				while(tempNext != tempPtr2)
				{
					date1 = tempNext->ret_date;
					date2 = tempPtr2->ret_date;
					day1 = date1 % 100;
					date1 /= 100;
					month1 = date1 %100;
					date1 /= 100;
					year1 = date1 % 100;
					date1 /= 100;
					day2 = date2 % 100;
					date2 /= 100;
					month2 = date2 %100;
					date2 /= 100;

					year2 = date2 % 100;
					date2 /= 100;
					if ((year1 <= year2) && (month1 <= month2))
					{
						temp = tempPtr2->ret_date;
						strcpy(temp2, tempPtr2->plateNum);
						temp3 = tempPtr2->mile;
						

						tempPtr2->ret_date = tempNext->ret_date;
						strcpy(tempPtr2->plateNum, tempNext->plateNum);
						tempPtr2->mile = tempNext->mile;
						

						tempNext->ret_date = temp;
						strcpy(tempNext->plateNum, temp2);
						tempNext->mile = temp3;
					}
					tempPtr2 = tempPtr2->next;
				}	
				tempPtr2 = rented_head;
				tempNext = tempNext->next;
			}

			tempPtr2 = rented_head;
			tempNext = rented_head->next;
			while(tempNext != NULL)
			{
				while(tempNext != tempPtr2)
				{
					date1 = tempNext->ret_date;
					date2 = tempPtr2->ret_date;
					day1 = date1 % 100;
					date1 /= 100;
					month1 = date1 %100;
					date1 /= 100;
					year1 = date1 % 100;
					date1 /= 100;
					day2 = date2 % 100;
					date2 /= 100;
					month2 = date2 %100;
					date2 /= 100;

					year2 = date2 % 100;
					date2 /= 100;
					if ((year1 <= year2) && (month1 <= month2) && (day1 <= day2))
					{
						temp = tempPtr2->ret_date;
						strcpy(temp2, tempPtr2->plateNum);
						temp3 = tempPtr2->mile;
						

						tempPtr2->ret_date = tempNext->ret_date;
						strcpy(tempPtr2->plateNum, tempNext->plateNum);
						tempPtr2->mile = tempNext->mile;
						

						tempNext->ret_date = temp;
						strcpy(tempNext->plateNum, temp2);
						tempNext->mile = temp3;
					}
					tempPtr2 = tempPtr2->next;
				}	
				tempPtr2 = rented_head;
				tempNext = tempNext->next;
			}
			retVal = tempPtr2;
			break;
		}
	}
	return retVal;
}

/*
 * printMenu()
 * input: file that contains linked list
 * main loop for the whole program contains all functionality of the program
 * reasonable error checking mostly through verifying with user before doing anything to lists	
 * sorry code gets bit repititive! 
 */
void printMenu(FILE* file)
{
	double income = 0.0;
	double totalIncome = income;
	int x; //menu
	char c; //checks scanf return
	char plateNum[10];
	int mile; 
	int ret_date; //return date
	int quit = 0; //ends do while loop below
	int submit = 2; //used in most while loops to reask user to resubmit correct input
	do
	{

		printf("\n\n\nChoose one of the follwing options: \n");
		printf("------------------------------------------\n");
		printf("1. Add a new car to available-for-rent list\n");
		printf("2. Return car to available-for-rent list\n");
		printf("3. Return car to the repair list\n");
		printf("4. Transfer car from repair list to available-for-rent list\n");
		printf("5. Rent first avaliable car\n");
		printf("6. Print all lists\n");
		printf("7. Quit\n~");


		if(scanf("%d%c", &x, &c) != 2 || c != '\n') // c should hold '\n' if not then wrong input.
		{
			printf("ERROR (Input Mismatch) - Expecting an integer from 1-7\n");
			while (getchar()!='\n'); //flush buffer
			continue;
		}

		//all cases keeps asking user to re-enter until format is correct
		switch(x)
		{
			case 1:
				while(submit != 1)
				{	 
					printf("Enter plate number: (format: <plateNumber>)\n~");
					scanf("%s", plateNum);
					while(strlen(plateNum) != 7)
					{
						printf("Please enter valid 7character Plate number\n ~");
						scanf("%s", plateNum);
						while (getchar()!='\n'); //flushing buffer
					}
					printf("Enter mileage: (format: <mileage>)\n~"); 
					//checks tht input is just 1 number thats positive
					while(scanf("%d", &mile) != 1 || (mile < 0))
					{
						printf("Please enter valid mileage number\n ~");
						while (getchar()!='\n'); //flush buffer
						continue;
					}
					printf("\nPlate number: [%s], Mileage: [%d]\n", plateNum, mile);
					printf("\nPlease confirm this is what you want to add\n");
					printf("1.Yes\n2.No\n");
					scanf("%d", &submit);
					if(submit == 1)
					{
						Car* new = malloc(sizeof(Car));
						if (new == NULL)
						{
							printf("malloc failed\n");
							exit(1);
						}
						new->mile = mile;
						strcpy(new->plateNum,plateNum);
						available_head = addtoFront(available_head, new);
						available_head = sortList(available_head, rented_head, 'a');
						printf("Added [%s][%d] to available for rent list.\n", plateNum, mile);
						submit = 2;
					}
					break;
			    }
				break;

			case 2:
				while(submit != 1)
				{	
					printf("Enter plate number: (format: <plateNumber>)\n~");
					scanf("%s", plateNum);
					while(strlen(plateNum) != 7)
					{
						printf("Please enter valid 7character Plate number\n ~");
						while (getchar()!='\n'); //flushing buffer
						scanf("%s", plateNum);
					}
					printf("Enter mileage: (format: <mileage>)\n~");
					while(scanf("%d", &mile) != 1 || (mile < 0))
					{
						printf("Please enter valid mileage number\n ~");
						while (getchar()!='\n'); //flush buffer
						continue;
					}
					printf("\nPlate number: [%s], Mileage: [%d]\n", plateNum, mile);
					printf("\nPlease confirm this is what you want to return\n");
					printf("1.Yes\n2.No\n");
					scanf("%d", &submit);
					//looks for car first, gives error if not found
					if(searchRented(plateNum, mile, rented_head)) //deletes node in searchrented
					{

						Car* new = malloc(sizeof(Car));
						if (new == NULL)
						{
							printf("malloc failed\n");
							exit(1);
						}
						new->mile = mile;
						strcpy(new->plateNum,plateNum);
						available_head = addtoFront(available_head, new);
						available_head = sortList(available_head, rented_head, 'a');
						income = mileTracker(mile);
						totalIncome += income;
						printf("Returned [%s][%d] to available for rent list.\n", plateNum, mile);
						printf("You will be charged $%0.3f(CDN)\n", income);
						submit = 2;
						break;
					}
					else
					{
						int choice;
						printf("Error - attempted to return a non existent car\n");
						submit = 2;

						printf("Press 1 to  try again or 2 to go back to the menu\n~");
						while(scanf("%d", &choice) != 1 || (choice < 0))
						{
							printf("Please enter either 1(return another car) or 2(menu)\n ~");
							while (getchar()!='\n'); //flush buffer
							continue;
						}
						if(choice == 1)
							continue;

						else
						{
							while (getchar()!='\n'); //flush buffer
							break;
						}
					}
				}
				break;
			case 3:
				while(submit != 1)
				{	
					printf("Enter plate number: (format: <plateNumber>)\n~");
					scanf("%s", plateNum);
					while(strlen(plateNum) != 7)
					{
						printf("Please enter valid 7character Plate number\n ~");
						while (getchar()!='\n'); //flushing buffer
						scanf("%s", plateNum);
					}
					printf("Enter mileage: (format: <mileage>)\n~");
					while(scanf("%d", &mile) != 1 || (mile < 0))
					{
						printf("Please enter valid mileage number\n ~");
						while (getchar()!='\n'); //flush buffer
						continue;
					}
					printf("\nPlate number: [%s], Mileage: [%d]\n", plateNum, mile);
					printf("\nPlease confirm this is what you want to return\n");
					printf("1.Yes\n2.No\n");
					scanf("%d", &submit);
					if(searchRented(plateNum, mile,rented_head)) //deletes node in search
					{

						Car* new = malloc(sizeof(Car));
						if (new == NULL)
						{
							printf("malloc failed\n");
							exit(1);
						}
						new->mile = mile;
						strcpy(new->plateNum,plateNum);
						repair_head = addtoFront(repair_head, new);
						income = mileTracker(mile);
						totalIncome += income;
						printf("Returned [%s][%d] to repair list.\n", plateNum, mile);
						printf("You will be charged $%0.3f(CDN)\n", income);
						submit = 2;
						break;
					}
					else
					{
						int choice;
						printf("Error - attempted to return a non existent car\n");
						submit = 2;

						printf("Press 1 to  try again or 2 to go back to the menu\n~");
						while(scanf("%d", &choice) != 1 || (choice < 0))
						{
							printf("Please enter either 1(return another car) or 2(menu)\n ~");
							while (getchar()!='\n'); //flush buffer
							continue;
						}
						if(choice == 1)
							continue;

						else
						{
							while (getchar()!='\n'); //flush buffer
							break;
						}
					}
				}
				break;
			case 4: 
				while(submit != 1)
				{	
					int getMile;
					printf("Enter plate number: (format: <plateNumber>)\n~");
					scanf("%s", plateNum);
					while(strlen(plateNum) != 7)
					{
						printf("Please enter valid 7character Plate number\n ~");
						while (getchar()!='\n'); //flushing buffer
						scanf("%s", plateNum);
					}
					printf("\nPlate number: [%s]\n", plateNum);
					printf("\nPlease confirm this is what you want to transfer\n");
					printf("1.Yes\n2.No\n");
					scanf("%d", &submit);
					getMile = searchRepair(plateNum, repair_head);
					if(getMile != 0)
					{

						Car* new = malloc(sizeof(Car));
						if (new == NULL)
						{
							printf("malloc failed\n");
							exit(1);
						}
						new->mile = getMile;
						strcpy(new->plateNum,plateNum);
						available_head = addtoFront(available_head, new);
						available_head = sortList(available_head, rented_head , 'a');
						printf("Transfered [%s][%d] to available-for-rent list.\n", plateNum, getMile);
						submit = 2;
						break;
					}
					else
					{
						int choice;
						printf("Error - attempted to return a non existent car\n");
						submit = 2;

						printf("Press 1 to  try again or 2 to go back to the menu\n~");
						while(scanf("%d", &choice) != 1 || (choice < 0))
						{
							printf("Please enter either 1(return another car) or 2(menu)\n ~");
							while (getchar()!='\n'); //flush buffer
							continue;
						}
						if(choice == 1)
							continue;

						else
						{
							while (getchar()!='\n'); //flush buffer
							break;
						}
					}
				}
				break;
			case 5:
				while(submit != 1)
				{	
					printf("Enter expected return date: (format: <yymmdd>)\n~");
					while(scanf("%d", &ret_date) != 1 || (ret_date < 0))
					{
						printf("Please enter valid return date\n ~");
						while (getchar()!='\n'); //flush buffer
						continue;
					}
					printf("\nExpected return date: [%06d]\n", ret_date);
					printf("\nPlease confirm this to be accurate\n");
					printf("1.Yes\n2.No\n");
					scanf("%d", &submit);
				}
					Car* rent = malloc(sizeof(Car));
					if (rent == NULL)
					{	
						printf("malloc failed\n");
						exit(1);
					}
					rent->ret_date = ret_date;
					rent->mile = available_head->mile;
					strcpy(rent->plateNum,available_head->plateNum);
					printf("Rented Car - Plate number: %s, mileage: %d\n", available_head->plateNum, available_head->mile);
					deleteNode(available_head, available_head);
					rented_head = addtoFront(rented_head, rent);
					rented_head = sortList(available_head, rented_head, 'r');
					submit = 2;
				break;
			case 6: 
				printAllLists(available_head, rented_head, repair_head);
				break;
			case 7:
				printf("\nUpdating Data in file ...\n");
				updateFile();
				printf("Total Income from all cars: $%0.3f\n", totalIncome);
				printf("Program exiting...\n");
				quit = 1;
				break;
			default:
				printf("Enter a valid number between 1-7\n");
				break;
		}
	}while (quit == 0);
}

/*
 * searchRepair()
 * input: plate number, and head of repair list
 * searches by platenumber
 * returns mileage because it will be needed when u transfer it to available list
 * returns 0 if not found
 */

int searchRepair(char plateNum[10], Car* head)
{
	int mile;
	Car* temp = head;
	while(temp != NULL)
	{
		if(strcmp(temp->plateNum, plateNum) == 0)
		{
			mile = temp->mile;
			deleteNode(temp,head);
			return mile;
		}
		temp = temp->next;
	}
	return 0;
}

/*
 * searchRented()
 * input: plate number, mileage, and head of rended list
 * searches by platenumber and mile
 * returns 1 if car found
 * returns 0 if not found
 */
int searchRented(char plateNum[10] , int mile, Car* head)
{
	Car* temp = head;
	while(temp != NULL)
	{
		if((temp->mile == mile) && (strcmp(temp->plateNum, plateNum) == 0))
		{	
			deleteNode(temp, head);
			return 1;
		}
		temp = temp->next; 
	}
	return 0;
}

/*
 * deleteNode()
 * input: node to delete, head of list about to delete from
 * deletes node after a transfer code has been completed 
 * keeps only one node in list so if node to be deleted is very last, does not delete it.
 * however transfers are done 100% accurately.
 * adapted from http://www.geeksforgeeks.org/delete-a-given-node-in-linked-list-under-given-constraints/
 * cited in readme as well
 */
void deleteNode(Car* node, Car* head)
{
	// When node to be deleted is head node
    if(head == node)
    {
        if(head->next == NULL)
        {            
			printf("There is only one node. The list can't be made empty.\n");
            return;
        }
        /* Copy the data of next node to head */
        else if(head == rented_head)
        {
        	rented_head->mile = rented_head->next->mile;
        	rented_head->ret_date = rented_head->next->ret_date;
        	strcpy(rented_head->plateNum, rented_head->next->plateNum);
 
       		 // store address of next node
        	node = rented_head->next;
 
       		 // Remove the link of next node
        	rented_head->next = rented_head->next->next;
 
        	// free memory
        	free(node);
 
        	return;
        }
      	else if((head == repair_head) || (head == available_head))
      	{
      		head->mile = head->next->mile;
        	strcpy(head->plateNum, head->next->plateNum);
 
       		 // store address of next node
        	node = head->next;
 
       		 // Remove the link of next node
        	head->next = head->next->next;
 
        	// free memory
        	free(node);
 
        	return;
      	}
    }
 

    // When not first node, follow the normal deletion process
 
    // find the previous node
    Car *prev = rented_head;
    while(prev->next != NULL && prev->next != node)
        prev = prev->next;
 
    // Remove node from Linked List
    prev->next = prev->next->next;
 
    // Free memory
    free(node);
 
    return; 
}

/*
 * addtoFront()
 * input: head of whatever list, node to stuff to be added
 * returns head of whatever list it added
 */
Car* addtoFront(Car* head, Car* toBeAdded)
{
	if (head == NULL) //list is empty
	{
		head = toBeAdded;
		toBeAdded->next = NULL;
		return head;
	}
	else //adds to front and makes it new head
	{
		toBeAdded->next = head;
		head = toBeAdded;
		return head;
	}
}


/*
 * mileTracker()
 * input: mileage(km)
 * computes what to charge based on formula given in description
 * returns it as a double
 */
double mileTracker(int x)
{
	if(x < 100)
		return 40.00;
	else
		return 40.00 + 0.15*(x-100);
}


/*
 * updateFile()
 * traverses through all the linked lists and writes to the file
 * overwrites any old data in file 
 * writes to data using format i chose for simplicity:
 * available list: a %s 	   (string, mile)
 * rented list:    r %s %d %d (string,mile,return date)
 * repair list:    m %s %d    (string, mile)
 *	
 */
void updateFile()
{
	FILE* file;
	file = fopen("data.txt","w");
	if (file == NULL)
    {
        printf("Error opening file\n");
        exit(1);
    }
	Car* temp;

	temp = available_head;
	while(temp)
	{
		fprintf(file, "a %s %d\n", temp->plateNum, temp->mile);
		temp = temp->next;
	}

	temp = rented_head;
	while(temp)
	{
		fprintf(file, "r %s %d %d\n", temp->plateNum, temp->mile, temp->ret_date);
		temp = temp->next;
	}

	temp = repair_head;
	while(temp)
	{
		fprintf(file, "m %s %d\n", temp->plateNum, temp->mile);
		temp = temp->next;
	}
	fclose(file);
}

/*
 * printAllLists()
 * input: heads of all three lists
 * Traverses through all of them and prints them.
 */

void printAllLists(Car* available_head, Car* rented_head, Car* repair_head)
{
	Car* temp;
	temp = available_head;

	printf("---------------------------------------");

	printf("\nAvailable for rent list: \n\n");
	printf("Plate Num\tMileage\n");
	while(temp != NULL)
	{
		printf("%s\t\t%d\n", temp->plateNum, temp->mile);
		temp = temp->next;
	}
	printf("---------------------------------------");

	temp = rented_head;

	printf("\nRented list: \n\n");
	printf("Plate Num\tMileage\t\tExpected Return date\n");

	while(temp != NULL)
	{
		printf("%s\t\t%d\t\t%06d\n", temp->plateNum, temp->mile, temp->ret_date);
		temp = temp->next;
	}
	printf("---------------------------------------");

	temp = repair_head;

	printf("\nRepair list: \n\n");
	printf("Plate Num\tMileage\n");

	while(temp != NULL)
	{
		printf("%s\t\t%d\n", temp->plateNum, temp->mile);
		temp = temp->next;
	}
	printf("---------------------------------------\n");

}
