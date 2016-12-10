/*
 * class EStoreSearch
 * adds/searches for data and really controls whole program
 * File input/output 
 */
package mousaa_a2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * @author AhmedMousa
 */
public class EStoreSearch
{
    //'global' hashmap
    private static HashMap<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();


    /*
     *  startProgram
     *  called from main once then keeps going till user hits quit
     */
    public void startProgram(ArrayList<Product> productList)
    {
        Scanner scan = new Scanner(System.in);
        String choice;
        boolean isValid = false;

        while (!isValid)
        {
            System.out.println("\nChoose one of the following options: ");
            System.out.println("Add\nSearch\nQuit");
            choice = scan.nextLine();
            switch (choice)
            {
                //give user a variety of choices
                case "add":
                case "a":
                case "ad":
                case "A":
                    newProduct(productList);
                    break;

                case "search":
                case "s":
                case "S":
                    searchProduct(productList);
                    break;

                case "quit":
                case "Quit":
                case "exit":
                case "Exit":
                case "q":
                case "Q":
                    //just to be interactive
                    System.out.println("Saving Data to file . . .");
                    updateFile(productList);
                    System.out.println("\nData successfully saved");
                    System.out.println("Program Exiting . . .");
                    isValid = true; // to end program.
                    break;

                default:
                    System.out.println("Error - Please enter either add, search or quit");
                    continue;
            }
        }
    }


    /*
        Update File
        Uses the populated product array list to overwrite file
        used at the start
        toString method defined in products classes to write
        to output file same way as in description based on object given
     */
    public void updateFile(ArrayList<Product> productList)
    {
        PrintWriter writer = null;
        Product product;
        try
        {
            writer = new PrintWriter("input.txt", "UTF-8");
            for (int i = 0; i < productList.size(); i++)
            {
                product = productList.get(i);
                writer.println(product.toString());
            }
        }
        catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            System.out.println("Error opening file to update");
            System.exit(0);
        }
        finally
        {
            writer.close();
        }
    }

    /*
        parseFile
        basic parser uses regex for qoutations 
        assumes input file will follow format and same order
        key = "value"
        doesnt handle escape characters unfortunately
    */
    public void parseFile(ArrayList<Product> productList, String[] args)
    {
        String information[];
        String type = "";
        String line = "";
        String id = "";
        String name = "";
        String price = "";
        String year = "";
        String author = "";
        String publisher = "";
        String maker = "";
        int ctr = 1;

        try
        {
            File f = new File(args[0]);
            Scanner scan = new Scanner(f);

            while(scan.hasNextLine())
            {                    
                line = scan.nextLine();
                if(line != null && !line.isEmpty())
                {
                    information = line.split("[\"]+");   
                    switch(ctr)
                    {
                        /*
                         * ASSUMES format of file 
                         * to be accurate as described in 
                         * assignment description.
                         */
                        case 1:
                            type = information[1];
                            break;
                        case 2:
                            id = information[1];
                            break; 
                        case 3:
                            name = information[1]; //fix escape character                                                        
                            break;         
                        case 4:
                            try
                            {
                                price = information[1];
                            }
                            catch(ArrayIndexOutOfBoundsException e)
                            {
                                price = "";
                            }
                            break;
                        case 5:
                            year = information[1];
                            break;
                        case 6:
                            if(type.equals("book"))
                            {
                                try
                                {
                                    author = information[1];
                                }
                                catch(ArrayIndexOutOfBoundsException e)
                                {
                                    author = "";
                                }
                            }
                            else
                            {
                                try
                                {
                                    maker = information[1];
                                }
                                catch(ArrayIndexOutOfBoundsException e)
                                {
                                    maker = "";
                                }                            
                                Product electronics = new Electronics(maker, id, name, price, year);
                                productList.add(electronics);
                                //adds to hashmap
                                addToMap(name.toLowerCase().split("[ ]+"), productList);
                            } 
                        break;
                        case 7: 
                            try
                            {
                               publisher = information[1];
                            }
                            catch(ArrayIndexOutOfBoundsException e)
                            {
                                publisher = "";
                            }                    
                            Product book = new Book(author, publisher, id, name, price, year);
                            productList.add(book);
                            addToMap(name.toLowerCase().split("[ ]+"), productList);
                            break;
                    }
                    ctr++;
                } 
                else
                {
                    ctr = 1; //keeps track of line number for parser to know which item ..again very basic parser
                }

            }
        }   
        catch (FileNotFoundException ex)
        {
            System.out.println("FATAL ERROR - file not found");
            System.out.println("Please run program and enter a different file");
            System.exit(0);
        }

    }

    /*
        newProduct
        adds new products to the arraylist
        asks first if its a book/electronic then asks for relevant info
        adds to hashmap too right after the object is added to arraylist
    */
    public void newProduct(ArrayList<Product> productList)
    {
        Scanner scan = new Scanner(System.in);
        int book_or_electronic = -2;
        boolean isValid = false;
        System.out.println("Choose what you would like to add: ");
        System.out.println("1.Book\n2.Electronic");


        while (!isValid)
        {
            try
            {
                book_or_electronic = scan.nextInt();
                isValid = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Error - either:\n1.Book\n2.Electronic");
                scan.nextLine();
                continue;
            }

            switch (book_or_electronic)
            {
                case 1:
                case 2:
                    String regex = "[0-9]+";
                    String id = "";
                    String name = "";
                    String price = "";
                    String year = "";

                    boolean isId = false;
                    boolean isName = false;
                    boolean isPrice = false;
                    boolean isYear = false;

                    scan.nextLine();
                    System.out.println("Enter ID: ");
                    while (!isId)
                    {
                        id = scan.nextLine();

                        if (foundDuplicate(productList, id) == 1)
                        {
                            System.out.println("Error - Found duplicate ID");
                            System.out.println("Enter different ID: ");
                            isId = false;
                            continue;
                        }
                        if (!id.matches(regex))
                        {
                            System.out.println("Error - Please enter a product ID of length six: ");
                            isId = false;
                            continue;
                        }
                        else if (id.length() != 6)
                        {
                            System.out.println("Error - Please enter a product ID of length six: ");
                            isId = false;
                            continue;
                        }
                        else
                        {
                            isId = true;
                        }
                    }

                    System.out.println("Enter Year: ");
                    while (!isYear)
                    {
                        year = scan.nextLine();
                        if (!year.matches(regex))
                        {
                            System.out.println("Error - Please enter a year(numbers only) of length 4: ");
                            continue;
                        }
                        else if (year.length() != 4)
                        {
                            System.out.println("Error - Please enter a year of length four: ");
                            isYear = false;
                            continue;
                        }
                        else if ((Integer.parseInt(year) < 1000))
                        {
                            System.out.println("Error - Please enter a year greater than 1000: ");
                            isYear = false;
                            continue;
                        }
                        else
                        {
                            isYear = true;
                        }
                    }

                    System.out.println("Enter product name: ");
                    while (!isName)
                    {
                        name = scan.nextLine();
                        if (name.equals(""))
                        {
                            System.out.println("You must enter a name: ");
                            isName = false;
                            continue;
                        }
                        if (name.matches(regex))
                        {
                            System.out.println("Error - You entered just a number. Please enter valid String name: ");
                            isName = false;
                            continue;
                        }
                        else
                        {
                            isName = true;
                        }

                    }

                    System.out.println("Enter product price OR press enter to skip: ");
                    while (!isPrice)
                    {
                        price = scan.nextLine();
                        if (price.equals(""))
                        {
                            isPrice = true;
                            break;
                        }
                        else if (!price.matches(regex) && (!price.matches("[0-9.]+"))) //second one to include decimal point numbers.
                        {
                            System.out.println("Error - Please enter valid price(integer/double) or enter to skip: ");
                            isPrice = false;
                            continue;
                        }
                        else
                        {
                            isPrice = true;
                        }
                    }

                    //for books -> Asks for author and pub.
                    if (book_or_electronic == 1)
                    {
                        boolean isAuthor = false;
                        boolean isPublisher = false;
                        String author = "";
                        String publisher = "";

                        System.out.println("Enter product author OR press enter to skip: ");
                        while (!isAuthor)
                        {
                            author = scan.nextLine();
                            if (author.equals(""))
                            {
                                isAuthor = true;
                                break;
                            }
                            if (author.matches(regex))
                            {
                                System.out.println("Error - Please enter valid author(string) OR enter to skip: ");
                                isAuthor = false;
                            }
                            else
                            {
                                isAuthor = true;
                            }
                        }

                        System.out.println("Enter product publisher OR press enter to skip: ");
                        while (!isPublisher)
                        {
                            publisher = scan.nextLine();
                            if (publisher.equals(""))
                            {
                                isPublisher = true;
                                break;
                            }
                            if (publisher.matches(regex))
                            {
                                System.out.println("Error - Please enter valid publisher(string) OR enter to skip: ");
                                isPublisher = false;
                            }
                            else
                            {
                                isPublisher = true;
                            }
                        }
                        Product book = new Book(author, publisher, id, name, price, year);
                        productList.add(book);
                        addToMap(name.toLowerCase().split("[ ]+"), productList);

                    }

                    //for electronics -> Asks for maker.
                    else if (book_or_electronic == 2)
                    {
                        String maker = "";
                        boolean isMaker = false;

                        System.out.println("Enter product maker OR press enter to skip: ");
                        while (!isMaker)
                        {
                            maker = scan.nextLine();
                            if (maker.equals(""))
                            {
                                isMaker = true;
                                break;
                            }
                            if (maker.matches(regex)) //only digits?
                            {
                                System.out.println("Error - Please enter valid maker(string) or enter to skip: ");
                                isMaker = false;
                                continue;
                            }
                            else
                            {
                                isMaker = true;
                            }
                        }
                        Product electronics = new Electronics(maker, id, name, price, year);
                        productList.add(electronics);
                        addToMap(name.toLowerCase().split("[ ]+"), productList);

                    }
                    break;

                default:
                    isValid = false;
                    System.out.println("Error - either:\n1.Book\n2.Electronic");
                    break;
            }

        }
    }

    /*
        foundDuplicate
        checks for dupliacte ids in list
    */
    public int foundDuplicate(ArrayList<Product> productList, String id)
    {
        Product product;
        int retVal = 2;
        for (int i = 0; i < productList.size(); i++)
        {
            product = productList.get(i);
            if (product.getId().equals(id))
            {
                retVal = 1; //found duplicate.
                break;
            }
        }
        return retVal;
    }


    /*
     * asks for ID, keywords and time period
     * then uses every possible scenario to search
     * i.e: all empty -> search list
            id & year empty -> search name
            id & name empty -> search year
            name & year empty -> search id
            search name & id only given
            search name & year only given
            search id & year
     *        
     * sorry code gets very repititive
     */

    public void searchProduct(ArrayList<Product> productList)
    {       
        Scanner scan = new Scanner(System.in);
        Product product; 
        String id = "";
        String year = "";
        String keyword = "";
        String regex = "[0-9]+"; //storing data as string but treating as an integer, easier to handle exceptions
        boolean isId = false;
        boolean isYear = false;
        int option = 0;

        System.out.println("Enter product ID to search for or press enter if you dont want to search by id: ");
        while(!isId)
        {
            id = scan.nextLine();
            if(id.equals("")) //empty string
            {
                break;
            }
            if (!id.matches(regex))
            {
                System.out.println("Error - Please enter a product ID of length six: ");
                continue;
            }
            else if (id.length() != 6)
            {
                System.out.println("Error - Please enter a product ID of length six: ");
                continue;
            }
            else
            {
                isId = true;
            }
        }

        System.out.println("Enter product year to search for or press enter if you dont want to search by year: ");
        while(!isYear)
        {
            year = scan.nextLine();
            if(year.equals(""))
            {
                break; //empty
            }
            //WILL REFER TO OPTIOON #'s later on 
            //throughouly checks all options and asks user to reenter if error 
            else if(year.length() >= 4)
            {
                if(year.length() == 5)
                {
                    //-2008 everything before OPTION 1 
                    if(year.charAt(0) == '-')
                    {
                        if(!year.substring(1,5).matches(regex))
                        {
                            System.out.println("Error - Please enter a valid time period: ");
                            continue;
                        }
                        else
                        {
                            isYear = true;
                            option = 1; //will refer to it later for search
                        }
                    }
                    //2008- after OPTION 2
                    else if(year.charAt(4) == '-')
                    {

                        if(!year.substring(0,4).matches(regex))
                        {
                            System.out.println("Error - Please enter a valid time period: ");
                            continue;
                        }
                        else
                        {
                            isYear = true;
                            option = 2; //will refer to it later for search
                        }
                    }
                    else
                    {
                        System.out.println("Error - You entered a weird symbol");
                        System.out.println("Enter a valid time period i.e 2008-, -2008, 2008-2009, 2008: ");
                        continue;
                    }
                }
                //2008 OPTION 3
                else if(year.length() == 4)
                {
                    if(!year.matches(regex))
                    {
                        System.out.println("Error - Please enter a valid time period: ");
                        continue;
                    }
                    else
                    {
                        isYear = true;
                        option = 3; //will refer to it later for search
                    }
                }
                //2008-2009 OPTION 4
                else if(year.length() == 9)
                {
                    if(year.charAt(4) == '-')
                    {
                        
                        if(year.substring(0,4).matches(regex) && (year.substring(5,9).matches(regex)))
                        {
                           isYear = true;
                           option = 4; //will refer to it later for search
                           break;
                        }
                        else
                        {
                            System.out.println("Error - Please enter a valid time period: ");
                            continue;
                        }
                    }
                    else
                    {
                        System.out.println("Error - You entered a weird symbol");
                        System.out.println("Enter a valid time period i.e 2008-, -2008, 2008-2009, 2008: ");
                        continue;
                    }

                }
                else
                {
                    System.out.println("Error - Please enter a valid time period: ");
                    continue; 
                }
            }
            else
            {
               System.out.println("Error - Please enter a valid time period: ");
               continue; 
            }
        }

        System.out.println("Enter keywords to search for or press enter if you dont want to search by keywords: ");
        {
            keyword = scan.nextLine();          
        }

        System.out.println("SEARCHING.. ");

        //all empty -> prints entire 
        if(keyword.equals("") && year.equals("") && id.equals(""))
        {
            System.out.println("You havent specified any search keywords for all the previous inputs");
            System.out.println("So all contents will be printed: ");
            printLists(productList);
        }


        //search only by id
        else if(keyword.equals("") && year.equals("") && !id.equals(""))
        {
            boolean found = false;
            for(int i = 0; i < productList.size(); i++)
            {
                product = productList.get(i);
                if(product.getId().equals(id))
                {
                   System.out.println("Found given id: " + id);
                   System.out.println("-----------");
                   System.out.println("FOUND: ");
                   System.out.println(product.toString()); 
                   found = true;
                   break;
                }
            }
            if(!found)
                System.out.println("ID not found");
        }

        //search only by year
        else if(keyword.equals("") && id.equals("") && !year.equals(""))
        {
            boolean found = false;
            switch(option)
            {
                case 1:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if(Integer.parseInt(product.getYear()) <= Integer.parseInt(year.substring(1,5)))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period not found");
                    break;
                case 2:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if(Integer.parseInt(product.getYear()) >= Integer.parseInt(year.substring(0,4)))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period not found");
                    break;
                case 3:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if(Integer.parseInt(product.getYear()) == Integer.parseInt(year.substring(0,4)))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period not found");
                    break;
                case 4:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if((Integer.parseInt(product.getYear()) >= Integer.parseInt(year.substring(0,4)))
                            && (Integer.parseInt(product.getYear()) <= Integer.parseInt(year.substring(5,9))))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period not found");
                    break;

                default:            
                    break;
            }
        }

        //search only by keywords
        else if(id.equals("") && year.equals("") && !keyword.equals(""))
        {    
            String[] keywords = keyword.toLowerCase().split("[ ]+");
            ArrayList<Integer> temp = new ArrayList<Integer>();
            ArrayList<Integer> intersection = new ArrayList<Integer>();
            Set<Integer> hs = new HashSet<>();
            int found = 0;


            //loop to get value for key and store it in temp list
            for(String key: keywords)
            {
                if(map.containsKey(key))
                {
                    found++;
                    temp.addAll(map.get(key));           
                if(intersection.size() == 0)
                {
                    intersection.addAll(map.get(key));
                }
                    intersection.retainAll(temp); //get intersection
                    temp.clear(); //resets for next iteration
                }
            }


            //avoid duplicate values. changes to set then back to arrayList
            hs.addAll(intersection);
            intersection.clear();
            intersection.addAll(hs); 

            if(found == keywords.length) //make sure found all before printing
            {
                for(Integer x: intersection)
                {
                    System.out.println("-----------");
                    System.out.println("FOUND: ");
                    System.out.println(productList.get(x).toString());
                }                
            }
        }

        //searches by id & year
        else if(keyword.equals("") && !id.equals("") && !year.equals(""))
        {
            boolean found = false;
            switch(option)
            {
                case 1:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if(Integer.parseInt(product.getYear()) <= Integer.parseInt(year.substring(1,5)) 
                            && product.getId().equals(id))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period and ID not found");
                    break;
                case 2:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if(Integer.parseInt(product.getYear()) >= Integer.parseInt(year.substring(0,4))
                            && product.getId().equals(id))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period and ID not found");
                    break;
                case 3:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if(Integer.parseInt(product.getYear()) == Integer.parseInt(year.substring(0,4))
                            && product.getId().equals(id))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period and ID not found");
                    break;
                case 4:
                    for(int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if((Integer.parseInt(product.getYear()) >= Integer.parseInt(year.substring(0,4)))
                            && (Integer.parseInt(product.getYear()) <= Integer.parseInt(year.substring(5,9)))
                            && product.getId().equals(id))
                        {
                            System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(product.toString()); 
                            found = true;                            
                        }
                    }
                    if(!found)
                    System.out.println("Given time period and ID not found");
                    break;

                default:            
                    break;
            }
        }

        //searches by name and id
        else if(!keyword.equals("") && !id.equals("") && year.equals(""))
        {
            int index = -1; //stores index where it found the ID so it can find intersection with name later
            boolean found = false;
            for(int i = 0; i < productList.size(); i++)
            {
                product = productList.get(i);
                if(product.getId().equals(id))
                {
                   index = i;
                   found = true;
                   break;
                }
            }
            if(!found)
            {
                System.out.println("ID not found - did not continue with name search");
            }
            else
            {
                String[] keywords = keyword.toLowerCase().split("[ ]+");
                ArrayList<Integer> temp = new ArrayList<Integer>();
                ArrayList<Integer> intersection = new ArrayList<Integer>();
                Set<Integer> hs = new HashSet<>();
                int foundName = 0;


                //loop to get value for key and store it in temp list
                for(String key: keywords)
                {
                    if(map.containsKey(key))
                    {
                        foundName++;
                        temp.addAll(map.get(key));           
                        if(intersection.size() == 0)
                        {
                            intersection.addAll(map.get(key));
                        }
                        intersection.retainAll(temp);
                        temp.clear(); //resets for next iteration
                    }
                    else
                    System.out.println("Couldnt find intersection between name and id");                
                }


                //avoid duplicate values. changes to set then back to arrayList
                hs.addAll(intersection);
                intersection.clear();
                intersection.addAll(hs); 

                if(foundName == keywords.length) //make sure found all before printing
                {
                    for(Integer x: intersection)
                    {
                        if(x == index) //index for Id same as list for name -> MATCHs
                        {
                           System.out.println("-----------");
                            System.out.println("FOUND: ");
                            System.out.println(productList.get(x).toString()); 
                        }
                        else
                        {
                            System.out.println("Couldnt find intersection between name and id");
                        }
                    }                
                }
            }
        }


        //finally... searches by name & time period 
        else
        {  
            //first searches by name to get the List indices
            String[] keywords = keyword.toLowerCase().split("[ ]+");
            ArrayList<Integer> temp = new ArrayList<Integer>();
            ArrayList<Integer> intersection = new ArrayList<Integer>();
            Set<Integer> hs = new HashSet<>();
            int foundKey = 0;


            //loop to get value for key and store it in temp list
            for(String key: keywords)
            {
                if(map.containsKey(key))
                {
                    foundKey++;
                    temp.addAll(map.get(key));           
                if(intersection.size() == 0)
                {
                    intersection.addAll(map.get(key));
                }
                    intersection.retainAll(temp);
                    temp.clear(); //resets for next iteration
                }
            }


            //avoid duplicate values. changes to set then back to arrayList
            hs.addAll(intersection);
            intersection.clear();
            intersection.addAll(hs); 

            if(foundKey != keywords.length) //make sure found all before printing
            {
                System.out.println("Didnt find names given - didnt continue with time period search");   
            }
            else
            {
                boolean found = false;
                switch(option)
                {
                    case 1:
                        for(int i = 0; i < productList.size(); i++)
                        {
                            product = productList.get(i);
                            if(Integer.parseInt(product.getYear()) <= Integer.parseInt(year.substring(1,5)))
                            {
                                if(intersection.contains(i))
                                {
                                    System.out.println("-----------");
                                    System.out.println("FOUND: ");
                                    System.out.println(productList.get(i).toString()); 
                                    found = true;
                                }                          
                            }
                        }
                        break;
                    case 2:
                        for(int i = 0; i < productList.size(); i++)
                        {
                            product = productList.get(i);
                            if(Integer.parseInt(product.getYear()) >= Integer.parseInt(year.substring(0,4)))
                            {
                                if(intersection.contains(i))
                                {
                                    System.out.println("-----------");
                                    System.out.println("FOUND: ");
                                    System.out.println(productList.get(i).toString()); 
                                    found = true;
                                }                          
                            }
                        }

                        break;
                    case 3:
                        for(int i = 0; i < productList.size(); i++)
                        {
                            product = productList.get(i);
                            if(Integer.parseInt(product.getYear()) == Integer.parseInt(year.substring(0,4)))
                            {
                                if(intersection.contains(i))
                                {
                                    System.out.println("-----------");
                                    System.out.println("FOUND: ");
                                    System.out.println(productList.get(i).toString()); 
                                    found = true;
                                }                           
                            }
                        }
                        break;
                    case 4:
                        for(int i = 0; i < productList.size(); i++)
                        {
                            product = productList.get(i);
                            if((Integer.parseInt(product.getYear()) >= Integer.parseInt(year.substring(0,4)))
                                && (Integer.parseInt(product.getYear()) <= Integer.parseInt(year.substring(5,9))))
                            {
                                if(intersection.contains(i))
                                {
                                    System.out.println("-----------");
                                    System.out.println("FOUND: ");
                                    System.out.println(productList.get(i).toString()); 
                                    found = true;
                                }                            
                            }
                        }
                        break;
                    default:            
                        break;          
                }
            }        
        }     
    }

    /*
        addToMap
        adds to the hashmap 
        called once objects are already added to the product list
    */
    private void addToMap(String[] words, ArrayList<Product> productList)
    {
        for(String key: words)
        {
            if(!map.containsKey(key))
            {
                map.put(key,new ArrayList<Integer>());
            }
            map.get(key).add(productList.size() - 1 ); //index of product to refer to it later
        }
    }

    /*
        printLists
        print the complete list 
        used by search function incase user skipped all input
    */
    public void printLists(ArrayList<Product> productList)
    {
         Product product;  
          for (int i = 0; i < productList.size(); i++)
            {
                product = productList.get(i);
                System.out.println(product.toString());

            }
    }
}
