import java.util.ArrayList;

public class Main
{
	public static void main(String[] args)
	{
		int choice;
		System.out.println("----Welcome To EStoreSearch----");
		
		//to send it through functions
		ArrayList<Electronics> electronicsArray = new ArrayList<Electronics>();
		ArrayList<Book> booksArray = new ArrayList<Book>();
		
		EStoreSearch store = new EStoreSearch();
		
		
		do
		{
			choice = store.printMenu();
			if (choice == 1) //add
			{
				store.add(booksArray, electronicsArray);
			} 
			else if (choice == 2) //store
			{
				store.search(booksArray, electronicsArray);
			} 
		}while(choice != 3); //quit
	
		System.out.println("Thank you, come again!");
		System.exit(0);
	}	
} 
