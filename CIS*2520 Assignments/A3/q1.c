/*
Ahmed Mousa     0927129
CIS 2520        Assignment 3
October 25/2016
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>


typedef struct tree
{
    char data[5];
    struct tree* left;
    struct tree* right;
}tree;



int top = -1;
tree* stack[150];
tree* constructTree(char x[100], tree* root);
tree* newNode(tree* curr);
tree* pop();
int getHeight(tree* root);
void push(tree* elem);
void inOrder(tree* root);
void postOrder(tree* root);
void preOrder(tree* root);
void printMenu(tree* root);
void update(tree* root);
void searchTree(tree* root, char x[4], char num[4]);
void displayTree(tree* root, int indent);
float calculate(tree* root);
bool isOperator(char x);


int main(int argc, char* argv[])
{     
    if(argc != 2)
    {
        printf("*Invalid # of arguments*\n");
        printf("run by inputting: ./q1 <expression>\n");
        printf("Program will exit now.\n");
        exit(1);
    }

    tree* root = malloc(sizeof(tree)); //create empty tree and push to stack
    push(root);
    root = constructTree(argv[1], root);
    printMenu(root);
}

void printMenu(tree* root)
{
    int x = 0;
    float result;
    do
    {
        printf("\n\n\nChoose one of the follwing options: \n");
        printf("------------------------------------------\n");
        printf("1. Display\n");
        printf("2. Preorder\n");
        printf("3. Inorder\n");
        printf("4. Postorder\n");
        printf("5. Update\n");
        printf("6. Calculate\n");
        printf("7. Exit\n~");

        scanf("%d", &x);

        switch(x)
        {
            case 1:
                displayTree(root, 0); 
                break;
            case 2:
                printf("Preorder: ");
                preOrder(root);
                printf("\n");
                break;
            case 3:
                printf("Inorder: ");
                inOrder(root);
                printf("\n");
                break;
            case 4:
                printf("Postorder: ");
                postOrder(root);
                printf("\n");
                break;
            case 5:
                update(root);
                break;
            case 6:
                result = calculate(root);
                printf("Result after evaluation: %.2f", result);
                break;
            case 7: 
                //exit
                break;
            default:
                printf("Error, enter number between 1-7\n");
                break;
        }

    } while(x!=7);

}

/* algorithm referenced in readme
 * display binanry tree 'sideways' 
 * to be read from bottom right to top right
 */
void displayTree(tree* root, int indent)
{
    int i;
   if(root != NULL)
   {
        if(root->right) displayTree(root->right, indent+4);
        if(indent)
        {
           for(i = 0; i < indent; i++)
            printf(" ");
        }
        if(root->right)
        {
            printf(" /\n");
            for(i = 0; i < indent; i++)
                printf(" ");
        }
        printf("%s\n", root->data);
        if(root->left)
        {
           for(i = 0; i < indent; i++)
                printf(" ");
                printf("\\\n");
           displayTree(root->left, indent+4);
        }
   }
}


/*recursively calculate left subtree and right subtree
 * algorithm referenced in readme
 */
float calculate(tree* root)
{
    if(!root)
    {
        return 0;
    }

    if(!root->left && !root->right)
    {
        return atof(root->data);
    }

    float left_tree = calculate(root->left);
    float right_tree = calculate(root->right);

    switch(root->data[0])
    {
        case '+':
            return left_tree+right_tree;
            break;
        case '-':
            return left_tree-right_tree;
            break;
        case '*':
            return left_tree*right_tree;
            break;
        case '/':
            return left_tree/right_tree;
            break;
    }
    return 0;
}

/*updates variable with new value
 *does nothing if variable doesnt exist
 * in tree. 
 */
void update(tree* root)
{
    char x[4];
    char num[5];
    printf("Enter variable: \n");
    scanf("%s", x);

    printf("enter value\n");
    scanf("%s", num);

    searchTree(root, x, num);
}


/* searches tree for given variable */
void searchTree(tree* root, char x[4], char num[4])
{
   if(root != NULL)
   {
        if(strcmp(x,root->data) == 0)
        {
            strcpy(root->data, num);
        }
        searchTree(root->left, x,num);
        searchTree(root->right,x,num);
   }
}

/* inOrder traversal */
void inOrder(tree* root)
{
    if(root == NULL)
    {
        return ;
    }
    else
    {
        if(isOperator(root->data[0]))
        {
            printf("(");
        }
        inOrder(root->left);
        printf("%s",root->data);
        inOrder(root->right);
        if(isOperator(root->data[0]))
        {
            printf(")");
        }
    }
}

//postOrder traversal

void postOrder(tree* root)
{
    if(root == NULL)
        return;
    else
    {
        postOrder(root->left);
        postOrder(root->right);
        printf("%s", root->data);
    }
}


//preOrder traversal
void preOrder(tree* root)
{
    if(root == NULL)
        return;
    else
    {
        printf("%s", root->data);
        postOrder(root->left);
        postOrder(root->right);
    }
}


/*builds tree from command line argument
 * assumes FULLY paranthesized expression as per description
 * algorithm referenced in readme
 */
tree* constructTree(char x[100], tree* root)
{   
    tree* curr = root;
    tree* parent;
    int i = 0;
    for(i = 0; i <strlen(x); i++)
    {
        if(x[i] == '(')
        {
            curr->left = newNode(curr);
            push(curr);
            curr = curr->left;
        }
        else if (x[i] == ')')
        {
            curr = pop();
        }
        else if(isOperator(x[i]))
        {
            strncpy(curr->data,&x[i],1);
            curr->right = newNode(curr);
            push(curr);
            curr = curr->right;

        }
        else
        {
            if(x[i] == 'x') //i.e x1
            {
                strncpy(curr->data, &x[i], 2);
                parent = pop();
                curr = parent;
                i++;
                continue;
            }
            else //float number i.e 1.68
            {
                strncpy(curr->data, &x[i], 4);
                i+=3;
                parent = pop();
                curr = parent;
                continue;
            }
        }
    }
    return curr;
}

//create new node 
tree* newNode(tree* curr)
{
    tree* temp;
    temp = malloc(sizeof(tree));
    temp->left = NULL;
    temp->right = NULL;
    return temp;
}

//check if character is an operator
bool isOperator(char x)
{
    switch(x)
    {
        case '+':
        case '-':
        case '*':
        case '/':
        return true;

        default:
        return false;
    }
}


// stack functions for tree
tree* pop()
{
    return(stack[top--]);
}

void push(tree* elem)
{
    top++;
    stack[top] =  elem;
}