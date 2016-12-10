/*
Ahmed Mousa     0927129
CIS 2520        Assignment 4
November 20/2016
*/

#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <ctype.h>


//tree structure.
typedef struct tree
{
    char key[20];
    int freq;
    struct tree* left;
    struct tree* right;
}tree;

#define max( a, b ) ( ((a) > (b)) ? (a) : (b)) //returns max of two ints.
void welcome();
tree* initTree();
tree* findDuplicate(char key[], tree* root);
tree* insert(int freq, char key[], tree* root);
tree* balanced(tree* root);
tree* rightRotate(tree* root);
tree* leftRotate(tree* root);
tree* newNode(int freq, char key[]);
void findAll(int freq, tree* root);
int getHeight(tree* root);
int getSize(tree* root);
int getBalance(tree* root);
tree* minNode(tree* root);
tree* removeKey(tree* root, char key[]);



//prints welcome screen when program starts
void welcome()
{
	printf("------------------------------------------\n");
	printf("\n0927129\n");
	printf("\n\t\t\t\t*************\n");
	printf("\n\t\tWelcome to Ahmed Mousa's AVL TREE implementation\n");
	printf("\n\t\t\t\t*************\n");
}

//asks for filename to initialze the tree
tree* initTree()
{   
	tree* root = NULL;
	tree* check; 
	FILE* f;
    char file[20];
    char line[150];
    char str[20];


    printf("Enter file name: \navl/> ");
    scanf("%s", file);
	f = fopen(file, "r");
    if(f == NULL)
    {
        printf("[FILE: %s] does not exist.\nProgram exiting . . .\n", file);
        exit(0);
    }

    //reads line by line, seperataes each token by space
    while (fgets(line, sizeof(line) , f) != NULL)
    {
    	char* token;
    	token = strtok(line, " ");

    	while(token != NULL)
    	{
    		sscanf(token, "%s", str);
    		
    		check = findDuplicate(str, root);

    		if(check == NULL) //found nothing
    		{
    			root = insert(1, str, root);
    		}
    		else //already found one-> increase frequency
    		{
    			check->freq  += 1;
    		}

    		token = strtok(NULL, " ");
    	}

    }
    fclose(f);
    return root;
}

/*  looks if key already exists to avoid repitition.
 *  returns the ptr to the found key
 */
tree* findDuplicate(char key[], tree* root)
{
	if(root == NULL)
		return NULL;

	int compare = strcmp(key, root->key);

	if(compare < 0)
		return findDuplicate(key, root->left);
	else if(compare > 0)
		return findDuplicate(key, root->right);
	else if(compare == 0)
		return root;

	return root;
}

/*
 * adds key then balances as it traverses back too root.
 */
tree* insert(int freq, char key[], tree* root)
{
	if(root == NULL) return newNode(freq, key);		
	int compare = strcmp(key, root->key);

	if(compare < 0)
	{
		root->left = insert(freq, key, root->left);
	}		
	else if(compare > 0)
	{
		root->right = insert(freq, key, root->right);
	}


	return balanced(root); 	//update tree
}

// balances tree
tree* balanced(tree* root)
{
	if(getHeight(root->left) - getHeight(root->right) == 2)
	{
		if(getHeight(root->left->right) > getHeight(root->left->left))
			root->left = leftRotate(root->left); //left right
		return rightRotate(root);//left left
	}
	else if(getHeight(root->right) - getHeight(root->left) == 2)
	{
		if(getHeight(root->right->left) > getHeight(root->right->right))
			root->right = rightRotate(root->right); //right left
		return leftRotate(root); //right right
	}

	return root;
}

tree* rightRotate(tree* root)
{
	tree* newRoot = root->left;

	root->left = newRoot->right;
	newRoot->right = root;
	return newRoot;
}

tree* leftRotate(tree* root)
{
	tree* newRoot = root->right;

	root->right = newRoot->left;
	newRoot->left = root;
	return newRoot;
}

//creates a newNode to be insertedd
tree* newNode(int freq, char key[])
{
	tree* temp = malloc(sizeof(tree));
	temp->freq = freq;
	strcpy(temp->key, key);
	temp->right = temp->left = NULL;
	return temp;
}

//searches for keys above freq
void findAll(int freq, tree* root)
{

    if(root == NULL)
    {
        return ;
    }

    findAll(freq, root->left);
    if(root->freq >= freq)
    	printf("Key: %s, Frequency: %d\n",root->key, root->freq);
    findAll(freq,root->right);
}

//height of tree
int getHeight(tree* root)
{
	if(root == NULL)
		return -1;
	return 1 + max(getHeight(root->left), getHeight(root->right));
}

//size of tree
int getSize(tree* root)
{
	if(root == NULL)
		return 0;
	else
		return (getSize(root->left) + 1 + getSize(root->right));
}

//balance factor
int getBalance(tree* root)
{
	if(root == NULL)
		return 0;
	return (getHeight(root->left) - getHeight(root->right));
}


tree* minNode(tree* root)
{
    tree* current = root;
 
    /* loop down to find the leftmost leaf */
    while (current->left != NULL)
        current = current->left;
 
    return current;
}

/* removes key then balances as it traverses up tree 
 *  referenced in readme
 */
tree* removeKey(tree* root, char key[])
{
    // STEP 1: PERFORM STANDARD BST DELETE
 
    if (root == NULL)
        return root;
 
    // If the key to be deleted is smaller than the
    // root's key, then it lies in left subtree
    if (strcmp(key,root->key) < 0)
        root->left = removeKey(root->left, key);
 
    // If the key to be deleted is greater than the
    // root's key, then it lies in right subtree
    else if(strcmp(key,root->key) > 0)
        root->right = removeKey(root->right, key);
 
    // if key is same as root's key, then This is
    // the node to be deleted
    else
    {
    	//just decrement key if its not the only one
    	if(root->freq > 1)
    	{
    		root->freq -= 1;
    		return root;
    	}
    	else
    	{
    		root->freq-=1;
    		 // node with only one child or no child
	        if( (root->left == NULL) || (root->right == NULL) )
	        {
	            tree* temp = root->left ? root->left :
	                                             root->right;
	 
	            // No child case
	            if (temp == NULL)
	            {
	                temp = root;
	                root = NULL;
	            }
	            else // One child case
	             *root = *temp; // Copy the contents of
	                            // the non-empty child
	            free(temp);
	        }
	        else
	        {
	            // node with two children: Get the inorder
	            // successor (smallest in the right subtree)
	            tree* temp = minNode(root->right);
	 
	            // Copy the inorder successor's data to this node
	            strcpy(root->key,temp->key);
	 
	            // Delete the inorder successor
	            root->right = removeKey(root->right, temp->key);
	        }
    	}
		       
    }
 
    // If the tree had only one node then return
    if (root == NULL)
      return root;
 

 	//check if tree is unbalanced
    int balance = getBalance(root);
 
    // If this node becomes unbalanced, then there are 4 cases
 
    // Left Left Case
    if (balance > 1 && getBalance(root->left) >= 0)
        return rightRotate(root);
 
    // Left Right Case
    if (balance > 1 && getBalance(root->left) < 0)
    {
        root->left =  leftRotate(root->left);
        return rightRotate(root);
    }
 
    // Right Right Case
    if (balance < -1 && getBalance(root->right) <= 0)
        return leftRotate(root);
 
    // Right Left Case
    if (balance < -1 && getBalance(root->right) > 0)
    {
        root->right = rightRotate(root->right);
        return leftRotate(root);
    }
 
    return root;
}
