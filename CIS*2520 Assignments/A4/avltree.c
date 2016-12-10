/*
Ahmed Mousa     0927129
CIS 2520        Assignment 4
November 20/2016
*/

#include "functions.c" // all functions here to keep main clean

int main()
{
    welcome();
    int x;
    int freq;
    char key[20];
    tree* root;
    tree* find;
    do
    {
        printf("\n\nChoose one of the follwing options: \n");
        printf("------------------------------------------\n");
        printf("1. Initialization\n");
        printf("2. Find\n");
        printf("3. Insert\n");
        printf("4. Remove\n");
        printf("5. Check Height and Size\n");
        printf("6. Find All\n");
        printf("7. Exit\navl/> ");

        scanf("%d", &x);

        switch(x)
        {
            case 1: //done
                root = initTree();
                break;
            case 2: //done
                printf("Find key: ");
                scanf("%s", key);
                find = findDuplicate(key, root);
                if(find == NULL)
                {
                    printf("No_such_key\n");
                }                
                else
                    printf("Key: %s, Frequency: %d\n",find->key, find->freq);
                break;
            case 3:  //done
                printf("Insert key: ");
                scanf("%s", key);
                find = findDuplicate(key, root);
                if(find == NULL)
                {
                    root = insert(1, key, root);
                    printf("\n----------\n");
                    printf("Added new key: %s\n", key);
                }  
                else 
                {  
                    find->freq += 1;
                    printf("\n----------\n");
                    printf("Found %s alredy\n", key);
                    printf("Key: %s, Frequency: %d", find->key, find->freq);
                }

                break;

            case 4: 
                printf("Remove key: ");
                scanf("%s", key);
                find = findDuplicate(key, root);
                
            
                if(find == NULL)
                {
                    printf("No_such_key\n");
                }  
                else
                {
                    int ctr = find->freq;
                    printf("Key: %s, Frequency: %d", find->key, --ctr);         
                    root = removeKey(root, key);
                }              
                break;
            case 5: //done
                printf("Height: %d, Size:%d\n", getHeight(root), getSize(root));
                break;
            case 6: //done
                printf("Enter frequency: ");
                scanf("%d", &freq);
                findAll(freq, root);
                break;
            case 7:
                break;
            default:
                printf("Error, enter number between 1-7\n");
                break;
        }

    }while(x != 7);

	return 0;
}
