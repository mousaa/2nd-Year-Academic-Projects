/*
 * class EStoreSearch
 * adds/searches for data in both book and electronic lists
 * uses appropriate accessors (get()) to get data from book class or electronic class.
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EStoreSearch
{
	/*
	 * printMenu
	 * prints menu for the whole program add, search , quit
	 * returns 1 = add, 2 = search ,3= quit
	 */
	public int printMenu()
	{			
		//menu choice
		String choice;
		Scanner scan = new Scanner(System.in);
		
		boolean valid = false; //keep repeating until user enters valid input
		int retval = 5;
		
		while(!valid)
		{
			System.out.println("\nChoose one of the following options: ");
			System.out.println("Add\nSearch\nQuit");
			choice = scan.nextLine();			
			switch(choice)
			{
				//give user a variety of choices
				case "add":
				case "a":
				case "ad":
				case "A":
					valid = true;
					retval = 1;
					break;
					
				//give user a variety of choices
				case "search":
				case "s":
				case "S":
					valid = true;
					retval = 2;
					break;
					
				//give user a variety of choices
				case "quit":
				case "Quit":
				case "exit":
				case "Exit":
				case "q":
				case "Q":
					valid = true;
					retval = 3;	
					break;
				
				//unexpected input - asks  user to enter again
				default:		
					System.out.println("Error - Please enter either add, search or quit");
					continue;
			}	
		}
		return retval;
	}

	
	/*
	 * search()
	 * input is the array lists 
	 * asks user for data to search
	 * then searches for it in list
	 * started this too late = can only search for product id and (basic)year format :( 
	 */
	public void search(ArrayList<Book> booksArray, ArrayList<Electronics> electronicsArray)
	{
		Book book;
		Electronics electronic;
		Scanner scan = new Scanner(System.in);
		
		String id = "";
		String year= "";
		String keyword = "";
		String regex = "[0-9]+"; //storing data as string but treating as an integer, easier to handle exceptions
		
		//keep asking user untl proper input 
		boolean isId = false;
		boolean isYear = false;
		
		System.out.println("Enter product ID to search for or press enter if you dont want to search by id: ");
		while(!isId)
		{
			String temp;
			temp = scan.nextLine();
			if(temp.equals("")) //empty string
			{
				id = temp;
				isId = true; 
				break;
			}
			else if(!temp.matches(regex))
			{
				System.out.println("Error - Please enter a product ID(numbers only) of length six: ");
				continue;	
			}
			else if(temp.length() != 6)
			{
				System.out.println("Error - Please enter a product ID of length six");
				isId = false;
				continue;
			}
			else
			{
				id = temp;
				isId = true;
			}
		}
		
		System.out.println("Enter product year to search for or press enter if you dont want to search by year: ");
		while(!isYear)
		{
			String temp;
			temp = scan.nextLine();
			if(temp.equals(""))
			{
				year = temp;
				isYear = true; 
				break;
			}
			else if(!temp.matches(regex))
			{
				System.out.println("Error - Please enter a year(numbers only) of length 4: ");
				continue;	
			}
			else if(temp.length() != 4)
			{
				System.out.println("Error - Please enter a year of length four");
				isYear = false;
				continue;
			}
			else if((Integer.parseInt(temp) < 1000))
			{
				System.out.println("Error - Please enter a year greater than 1000");
				isYear = false;
				continue;
			}
			else
			{
				year = temp;
				isYear = true;
			}
		}
		
		//rip..
		System.out.println("Enter keywords to search for or press enter if you dont want to search by keywords: ");
		{
			keyword = scan.nextLine();			
		}
		
		//USE THIS ONE 10/10 works :D  
		if(keyword.equals("") && id.equals("") && year.equals(""))
		{
			System.out.println("You havent specified any search keywords for all the previous inputs");
			System.out.println("So all contents will be printed: ");
			printLists(booksArray, electronicsArray); 
		}
		else if(keyword.equals("") && year.equals("") && !id.equals(""))
		{
			//checks if the id does exist before traversing through list
			if (findDuplicate(id, booksArray, electronicsArray) == 2)
			{
				System.out.println("Given id does not exist");
			}
			else
			{
				boolean found = false;
				for(int i = 0; i < booksArray.size(); i++)
				{
					book = booksArray.get(i);
					if(book.getId().equals(id))
					{
						found = true;
						System.out.println("Found given id in books list: " + id);
						System.out.println("-----------");
						System.out.println("ID - " + book.getId());
						System.out.println("NAME - " +book.getName());
						System.out.println("PRICE - " +book.getPrice());
						System.out.println("YEAR - " +book.getYear());
						System.out.println("AUTHOR - " +book.getAuthor());
						System.out.println("PUBLISHER - " +book.getPublisher());
						System.out.println("-----------");
					}
				}
				
				if(!found) //check other list
				{
					for(int i = 0; i < electronicsArray.size(); i++)
					{
						electronic = electronicsArray.get(i);
						if(electronic.getId().equals(id))
						{
							System.out.println("Found given id in electronics list: " + id);
							System.out.println("-----------");
							System.out.println("ID - " +electronic.getId());
							System.out.println("NAME - " + electronic.getName());
							System.out.println("PRICE - " +electronic.getPrice());
							System.out.println("YEAR - " +electronic.getYear());
							System.out.println("MAKER - " +electronic.getMaker());
							System.out.println("-----------");
						}
					}
				}
			}
		}
		//finds jst the year inputted no fancy before -2008 or 2008-2009 or 2004+ etc
		else if(keyword.equals("") && !year.equals("") && id.equals(""))
		{
			for(int i = 0; i < booksArray.size(); i++)
			{
				book = booksArray.get(i);
				if(book.getYear().equals(year))
				{
					System.out.println("Found given year in books list: " + year);
					System.out.println("-----------");
					System.out.println("ID - " + book.getId());
					System.out.println("NAME - " +book.getName());
					System.out.println("PRICE - " +book.getPrice());
					System.out.println("YEAR - " +book.getYear());
					System.out.println("AUTHOR - " +book.getAuthor());
					System.out.println("PUBLISHER - " +book.getPublisher());
					System.out.println("-----------");
				}
			} 
			for(int i = 0; i < electronicsArray.size(); i++)
			{
				electronic = electronicsArray.get(i);
				if(electronic.getYear().equals(year))
				{
					System.out.println("Found given year in electronics list: " + year);
					System.out.println("-----------");
					System.out.println("ID - " +electronic.getId());
					System.out.println("NAME - " + electronic.getName());
					System.out.println("PRICE - " +electronic.getPrice());
					System.out.println("YEAR - " +electronic.getYear());
					System.out.println("MAKER - " +electronic.getMaker());
					System.out.println("-----------");
				}
			}
		}
	}
	
	/*
	 * add()
	 * input both the lists
	 * adds data to them
	 * error checks pretty good keeps asking user to reenter valid input!
	 */
	public void add(ArrayList<Book> booksArray, ArrayList<Electronics> electronicsArray)
	{
		
		Scanner scan = new Scanner(System.in);

				
		//will use set and get accessors/mutators
				
		Book book = new Book(); 
		Electronics electronics = new Electronics();
				
		String id;
		String price;
		String year;
		String name;
		String maker;
		String author;
		String publisher;
				
				
		String regex = "[0-9]+"; //to compare string has any digits from 0-9
		int add_answer; //hold either book or electronic (which to add)
		boolean valid = false;
		boolean isId = false;
		boolean isName = false;
		boolean isYear = false;
		boolean isPrice = false;
		boolean isAuthor = false;
		boolean isPublisher = false;
				
		System.out.println("Pick what you want to add: ");
		System.out.println("1.Book\n2.Electronic");
		
		while(!valid)
		{
			try
			{
				add_answer = scan.nextInt();
			}
			catch(InputMismatchException e) //if user enters random stuff
			{
				scan.nextLine(); //junk
				System.out.println("Error - Please enter either 1 to add a Book , 2 to add an electronic");
				continue;
			}
			
			switch(add_answer)
			{
				/*
				 * add book for case 1
				 * asks for data has while (false) flags for all of em
				 * until user finally enters valid data
				 * sets data once user enters the valid stuff
				 * before this case exits, adds data to array list
				 */
				case 1:
					scan.nextLine();
					System.out.println("Enter product ID: ");
					while(!isId)
					{
						id = scan.nextLine();
						if(findDuplicate(id , booksArray,  electronicsArray) == 1) //DUPLICATE FOUND , asks user to renter
						{
							System.out.println("Error- found duplicate");
							System.out.println("Enter different ID: ");
							continue;
						}
						if(!id.matches(regex))
						{
							System.out.println("Error - Please enter a product ID(numbers only) of length six: ");
							continue;	
						}
						else if(id.length() != 6)
						{
							System.out.println("Error - Please enter a product ID of length six");
							isId = false;
							continue;
						}
						book.setId(id);
						isId = true;
					}
					System.out.println("Enter product year: ");
					while(!isYear)
					{
						year = scan.nextLine();
						if(!year.matches(regex))
						{
							System.out.println("Error - Please enter a year(numbers only) of length 4: ");
							continue;	
						}
						else if(year.length() != 4)
						{
							System.out.println("Error - Please enter a year of length four");
							isYear = false;
							continue;
						}
						else if((Integer.parseInt(year) < 1000))
						{
							System.out.println("Error - Please enter a year greater than 1000");
							isYear = false;
							continue;
						}
						book.setYear(year);
						isYear = true;
					}
					
					System.out.println("Enter product name: ");
					
					while(!isName)
					{
						name = scan.nextLine();
						
						if(name.equals(""))
						{
							System.out.println("You must enter a name please: ");
							isName = false;
							continue;
						}
						if(name.matches(regex))
						{
							System.out.println("Error - Please enter valid name(string): ");
							isName = false;
							continue;	
						}
						book.setName(name);
						isName = true;
					}
					
					System.out.println("Enter product publisher(press enter to skip): ");
				
					while(!isPublisher)
					{
						publisher = scan.nextLine();
						if(publisher.equals(""))
						{
							book.setPublisher(publisher);
							break;
						}
						if(publisher.matches(regex))
						{
							System.out.println("Error - Please enter valid publisher(string) or enter to skip: ");
							isPublisher = false;
							continue;	
						}
						book.setPublisher(publisher);
						isPublisher = true;
					}
					
					System.out.println("Enter product author(press enter to skip): ");
					while(!isAuthor)
					{
						author = scan.nextLine();
						if(author.equals(""))
						{
							book.setAuthor(author);
							break;
						}
						if(author.matches(regex))
						{
							System.out.println("Error - Please enter valid author(string) or enter to skip: ");
							isAuthor = false;
							continue;	
						}
						book.setAuthor(author);
						isAuthor = true;
					}
					
					System.out.println("Enter product price(press enter to skip): ");
					while(!isPrice)
					{
						price = scan.nextLine();
						if(price.equals(""))
						{
							book.setPrice(price);
							break;
						}
						else if(!price.matches(regex)  && (!price.matches("[0-9.]+"))) //second one to include double ints
						{
							System.out.println("Error - Please enter valid price(integer/double) or enter to skip: ");
							isPrice = false;
							continue;	
						}
						
						book.setPrice(price);
						isPrice = true;
					}
					
					System.out.println("ADDING: ");
					System.out.println("ID: " + book.getId());
					System.out.println("YEAR: " + book.getYear());
					System.out.println("NAME: " + book.getName());
					System.out.println("PUB: " + book.getPublisher());
					System.out.println("AUTH: " + book.getAuthor());
					System.out.println("PRICE: " + book.getPrice());
					
					booksArray.add(book);
					System.out.println("--------------------");
					valid = true; //back to menu
					break;
					
				/*
				 * add electronic for case 1
				 * asks for data has while (false) flags for all of em
				 * until user finally enters valid data
				 * sets data once user enters the valid stuff
				 * before this case exits, adds data to array list	
				 */	
				case 2:
					valid = false;
					isId = false;
					isName = false;
					isYear = false;
					isPrice = false;
					isAuthor = false;
					isPublisher = false;
					
					scan.nextLine();
					System.out.println("Enter product ID: ");
					while(!isId)
					{
						id = scan.nextLine();
						if(findDuplicate(id , booksArray,  electronicsArray) == 1)//DUPLICATE FOUND , asks user to renter
						{
							System.out.println("Error- found duplicate");
							System.out.println("Enter different ID: ");
							continue;
						}
						if(!id.matches(regex))
						{
							System.out.println("Error - Please enter a product ID(numbers only) of length six: ");
							continue;	
						}
						else if(id.length() != 6)
						{
							System.out.println("Error - Please enter a product ID of length six");
							isId = false;
							continue;
						}
						electronics.setId(id);
						isId = true;
					}
					
					System.out.println("Enter product year: ");
					while(!isYear)
					{
						year = scan.nextLine();
						if(!year.matches(regex))
						{
							System.out.println("Error - Please enter a year(numbers only) of length 4: ");
							continue;	
						}
						else if(year.length() != 4)
						{
							System.out.println("Error - Please enter a year of length four");
							isYear = false;
							continue;
						}
						else if((Integer.parseInt(year) < 1000))
						{
							System.out.println("Error - Please enter a year greater than 1000");
							isYear = false;
							continue;
						}
						electronics.setYear(year);
						isYear = true;
					}
					
					System.out.println("Enter product name: ");
					
					while(!isName)
					{
						name = scan.nextLine();
						
						if(name.equals(""))
						{
							System.out.println("You must enter a name please: ");
							isName = false;
							continue;
						}
						if(name.matches(regex))
						{
							System.out.println("Error - Please enter valid name(string): ");
							isName = false;
							continue;	
						}
						electronics.setName(name);
						isName = true;
					}
					
					System.out.println("Enter product maker(press enter to skip): ");
					//isAuthor is just isMaker here
					while(!isAuthor)
					{
						maker = scan.nextLine();
						if(maker.equals(""))
						{
							electronics.setMaker(maker);
							break;
						}
						if(maker.matches(regex)) //only digits?
						{
							System.out.println("Error - Please enter valid maker(string) or enter to skip: ");
							isAuthor = false;
							continue;	
						}
						electronics.setMaker(maker);
						isAuthor = true;
					}
					
					System.out.println("Enter product price(press enter to skip): ");
					while(!isPrice)
					{
						price = scan.nextLine();
						if(price.equals(""))
						{
							electronics.setPrice(price);
							break;
						}
						else if(!price.matches(regex) && (!price.matches("[0-9.]+"))) //second one to include double values 
						{
							System.out.println("Error - Please enter valid price(integer/double) or enter to skip: ");
							valid = false;
							continue;	
						}
						
						electronics.setPrice(price);
						isPrice = true;
					}
					
					System.out.println("ADDING: ");
					System.out.println("ID: " + electronics.getId());
					System.out.println("YEAR: " + electronics.getYear());
					System.out.println("NAME: " + electronics.getName());
					System.out.println("MAKER: " + electronics.getMaker());
					System.out.println("PRICE: " + electronics.getPrice());
					System.out.println("--------------------");
					electronicsArray.add(electronics);
					valid = true; 
					break;
				default:
					System.out.println("Error - Please enter either 1 to add a Book , 2 to add an electronic\n");
					continue;
			}
		}	
		
	}
	
	/*
	 * findDuplicate()
	 * input: id to look for duplicate and both arraylists
	 * returns int reflecting what it found
	 * 1 for found duplicates so if when i call findDuplicate() == 1.. reject the given Id
	 * 2 just returned if duplicated not found
	 */
	public int findDuplicate(String id, ArrayList<Book> booksArray, ArrayList<Electronics> electronicsArray)
	{
		Book book;
		Electronics electronic;
		int retval = 2;
		
		for(int i = 0; i < booksArray.size(); i++)
		{
			book = booksArray.get(i);
			if(book.getId().equals(id))
				retval = 1;
		}
		// found one already , no need to check other list.
		if(retval == 1)
		{
			return 1;
		}
		else //check other list
		{
			for(int i = 0; i < electronicsArray.size(); i++)
			{
				electronic = electronicsArray.get(i);
				if(electronic.getId().equals(id))
					retval = 1;
			}
		}
		return retval;
	}
	
	/*
	 * prints all data in lists.
	 */
	public void printLists(ArrayList<Book> booksArray, ArrayList<Electronics> electronicsArray)
	{
		Book book;
		Electronics electronic;
		
		System.out.println("Books Array: ");
		for(int i = 0; i < booksArray.size(); i++)
		{
			book = booksArray.get(i);
			System.out.println("-----------");
			System.out.println("ID - " + book.getId());
			System.out.println("NAME - " +book.getName());
			System.out.println("PRICE - " +book.getPrice());
			System.out.println("YEAR - " +book.getYear());
			System.out.println("AUTHOR - " +book.getAuthor());
			System.out.println("PUBLISHER - " +book.getPublisher());
			System.out.println("-----------");
		}
		
		System.out.println("Electronics Array: ");

		for(int i = 0; i < electronicsArray.size(); i++)
		{
			electronic = electronicsArray.get(i);
			System.out.println("-----------");
			System.out.println("ID - " +electronic.getId());
			System.out.println("NAME - " + electronic.getName());
			System.out.println("PRICE - " +electronic.getPrice());
			System.out.println("YEAR - " +electronic.getYear());
			System.out.println("MAKER - " +electronic.getMaker());
			System.out.println("-----------");
		}
		
	}
}



