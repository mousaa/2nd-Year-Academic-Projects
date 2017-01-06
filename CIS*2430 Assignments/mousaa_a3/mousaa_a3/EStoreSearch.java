package mousaa_a3;
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

    private static String regex = "[0-9]+";
    private static ArrayList<Product> productList = new ArrayList<>();
    private static HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();

    protected void addProduct(String idField, String nameField, String priceField, String yearField, String authorsField, String publisherField, String makerField, int book_or_elec)
    {
        // just a huge if, else checking for all the field values.
        //id check. 
        if (foundDuplicate(productList, idField) == 1)
        {
            Gui.setAddText("Error - Found duplicate ID");
        }
        else if (!idField.matches(regex))
        {
            Gui.setAddText("Error - Please enter a valid product ID");
        }
        else if (idField.length() != 6)
        {
            Gui.setAddText("Error - Please enter a product ID of length six");
        }

        //name check
        else if (nameField.equals(""))
        {
            Gui.setAddText("You must enter a name");
        }
        else if (nameField.matches(regex))
        {
            Gui.setAddText("Error - Please enter valid String name");
        }

        //price 
        else if (((!priceField.equals("")) && (!priceField.matches(regex) && (!priceField.matches("[0-9.]+"))))) //second one to include decimal point numbers.)
        {
            Gui.setAddText("Error - Please enter valid price");
        }

        //year check
        else if (!yearField.matches(regex))
        {
            Gui.setAddText("Error - Please enter a year(numbers only) of length 4");
        }
        else if (yearField.length() != 4)
        {
            Gui.setAddText("Error - Please enter a year of length four");
        }
        else if ((Integer.parseInt(yearField) < 1000))
        {
            Gui.setAddText("Error - Please enter a year greater than 1000");
        }

        //book
        else if (book_or_elec == 1)
        {
            if (authorsField.matches(regex) || (publisherField.matches(regex)))
            {
                Gui.setAddText("Error - please enter valid " + (authorsField.matches(regex) ? "author" : "publisher"));
            }
            else if (authorsField.matches(regex) && (publisherField.matches(regex)))
            {
                Gui.setAddText("Error - please enter valid Authors & publisher");
            }
            else
            {
                Product book = new Book(authorsField, publisherField, idField, nameField, priceField, yearField);
                productList.add(book);
                addToMap(nameField.toLowerCase().split("[ ]+"), productList);
                Gui.setAddText("Book Added SUCCESSFULLY.");
                Gui.resetFields();

            }
        }
        //electronic
        else if (book_or_elec == 2)
        {
            if (makerField.matches(regex))
            {
                Gui.setAddText("Error - enter valid maker");
            }
            else
            {
                Product electronics = new Electronics(makerField, idField, nameField, priceField, yearField);
                productList.add(electronics);
                addToMap(nameField.toLowerCase().split("[ ]+"), productList);
                Gui.setAddText("Electronic Added SUCCESSFULLY.");
                Gui.resetFields();
            }
        }

    }

    //sorry repipitivve code. searches based on what field user entered. covers all possible combinations.
    //exhaustive way could be done much better i think.
    protected void searchProduct(String idSearchField, String keySearchField, String startYearSearchField, String endYearSearchField)
    {
        Product product;
        boolean isId = false;
        boolean isStartYear = false;
        boolean isEndYear = false;
        Gui.setSearchText("");

        if (idSearchField.equals("")) //empty string
        {
            isId = true;
        }
        else if (!idSearchField.matches(regex))
        {
            Gui.appendToSearchText("\nError - Please enter a product ID of length six: ");
        }
        else if (idSearchField.length() != 6)
        {
            Gui.appendToSearchText("\nError - Please enter a product ID of length six: ");
        }
        else
        {
            isId = true;
        }

        if (startYearSearchField.equals(""))
        {
            isStartYear = true;
        }
        else if (!startYearSearchField.matches(regex))
        {
            Gui.appendToSearchText("\nError - Enter valid Starting year");
        }
        else if (startYearSearchField.length() != 4)
        {
            Gui.appendToSearchText("\nError - Enter year length 4 (1996)");

        }
        else if ((Integer.parseInt(startYearSearchField) < 1000))
        {
            Gui.appendToSearchText("\nError - Year must be greater than 1000.");
        }
        else
        {
            isStartYear = true;
        }

        if (endYearSearchField.equals(""))
        {
            isEndYear = true;
        }
        else if (!endYearSearchField.matches(regex))
        {
            Gui.appendToSearchText("\nError - Enter valid Ending year");
        }
        else if (endYearSearchField.length() != 4)
        {
            Gui.appendToSearchText("\nError - Enter year length 4 (1996)");
        }
        else if ((Integer.parseInt(endYearSearchField) < 1000))
        {
            Gui.appendToSearchText("\nError - Year must be greater than 1000.");
        }
        else
        {
            isEndYear = true;
        }

        //valid input
        if (isEndYear && isId && isStartYear)
        {
            //all fields empty
            if (keySearchField.equals("") && startYearSearchField.equals("") && endYearSearchField.equals("") && idSearchField.equals(""))
            {
                Gui.setSearchText("You havent specified any search keywords for all the previous inputs");
                Gui.appendToSearchText("\nSo all contents will be printed: ");
                printLists(productList);
            }

            //search only by id.  
            else if (keySearchField.equals("") && startYearSearchField.equals("") && endYearSearchField.equals("") && !idSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (product.getId().equals(idSearchField))
                    {
                        Gui.setSearchText("Found given id: " + idSearchField + "\n");
                        Gui.appendToSearchText(product.toString());
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("ID not found");
                }
            }

            //search only by start year
            else if (keySearchField.equals("") && idSearchField.equals("") && endYearSearchField.equals("") && !startYearSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (Integer.parseInt(product.getYear()) >= Integer.parseInt(startYearSearchField))
                    {
                        Gui.appendToSearchText("\nFOUND: \n");
                        Gui.appendToSearchText(product.toString());
                        found = true;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("Given time period not found");
                }
            }

            //search only by end year
            else if (keySearchField.equals("") && idSearchField.equals("") && !endYearSearchField.equals("") && startYearSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (Integer.parseInt(product.getYear()) <= Integer.parseInt(endYearSearchField))
                    {
                        Gui.appendToSearchText("\nFOUND: \n");
                        Gui.appendToSearchText(product.toString());
                        found = true;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("Given time period not found");
                }
            }

            //search by start and end year
            else if (keySearchField.equals("") && idSearchField.equals("") && !endYearSearchField.equals("") && !startYearSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if ((Integer.parseInt(product.getYear()) >= Integer.parseInt(startYearSearchField))
                            && (Integer.parseInt(product.getYear()) <= Integer.parseInt(endYearSearchField)))
                    {
                        Gui.appendToSearchText("\nFOUND: \n");
                        Gui.appendToSearchText(product.toString());
                        found = true;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("Given time period not found");
                }
            }

            //search only by keywords
            else if (idSearchField.equals("") && startYearSearchField.equals("") && endYearSearchField.equals("") && !keySearchField.equals(""))
            {
                String[] keywords = keySearchField.toLowerCase().split("[ ]+");
                ArrayList<Integer> temp = new ArrayList<Integer>();
                ArrayList<Integer> intersection = new ArrayList<Integer>();
                Set<Integer> hs = new HashSet<>();
                int found = 0;

                //loop to get value for key and store it in temp list
                for (String key : keywords)
                {
                    if (map.containsKey(key))
                    {
                        found++;
                        temp.addAll(map.get(key));
                        if (intersection.size() == 0)
                        {
                            intersection.addAll(map.get(key));
                        }
                        intersection.retainAll(temp); //get intersection
                        temp.clear(); //resets for next iteration
                    }
                }

                if (found == 0)
                {
                    Gui.setSearchText("Given key not in list");
                }
                //avoid duplicate values. changes to set then back to arrayList
                hs.addAll(intersection);
                intersection.clear();
                intersection.addAll(hs);

                if (found == keywords.length) //make sure found all before printing
                {
                    for (Integer x : intersection)
                    {
                        Gui.appendToSearchText("\nFOUND: ");
                        Gui.appendToSearchText(productList.get(x).toString());
                    }
                }
                else
                {
                    Gui.setSearchText("Given key not in list");
                }
            }

            //search by id and start year
            else if (keySearchField.equals("") && endYearSearchField.equals("") && !idSearchField.equals("") && !startYearSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (Integer.parseInt(product.getYear()) >= Integer.parseInt(startYearSearchField)
                            && product.getId().equals(idSearchField))
                    {
                        Gui.setSearchText("\nFound:\n");
                        Gui.appendToSearchText(product.toString());
                        found = true;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("Given time period and ID not found");
                }
            }

            //search by id and end year
            else if (keySearchField.equals("") && !endYearSearchField.equals("") && !idSearchField.equals("") && startYearSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (Integer.parseInt(product.getYear()) <= Integer.parseInt(endYearSearchField)
                            && product.getId().equals(idSearchField))
                    {
                        Gui.setSearchText("\nFound:\n");
                        Gui.appendToSearchText(product.toString());
                        found = true;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("Given time period and ID not found");
                }
            }

            //search by id, start, and end year
            else if (keySearchField.equals("") && !endYearSearchField.equals("") && !idSearchField.equals("") && !startYearSearchField.equals(""))
            {
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if ((Integer.parseInt(product.getYear()) >= Integer.parseInt(startYearSearchField))
                            && (Integer.parseInt(product.getYear()) <= Integer.parseInt(endYearSearchField))
                            && product.getId().equals(idSearchField))
                    {
                        Gui.setSearchText("\nFound:\n");
                        Gui.appendToSearchText(product.toString());;
                        found = true;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("Given time period and ID not found");
                }
            }

            //search by name and id
            else if (!keySearchField.equals("") && !idSearchField.equals("") && startYearSearchField.equals("") && endYearSearchField.equals(""))
            {
                int index = -1; //stores index where it found the ID so it can find intersection with name later
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (product.getId().equals(idSearchField))
                    {
                        index = i;
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("ID not found - did not continue with name search");
                }
                else
                {

                    String[] keywords = keySearchField.toLowerCase().split("[ ]+");
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    ArrayList<Integer> intersection = new ArrayList<Integer>();
                    Set<Integer> hs = new HashSet<>();
                    int foundName = 0;

                    //loop to get value for key and store it in temp list
                    for (String key : keywords)
                    {
                        if (map.containsKey(key))
                        {
                            foundName++;
                            temp.addAll(map.get(key));
                            if (intersection.size() == 0)
                            {
                                intersection.addAll(map.get(key));
                            }
                            intersection.retainAll(temp);
                            temp.clear(); //resets for next iteration
                        }
                        else if (foundName == 0)
                        {
                            Gui.setSearchText("Couldnt find intersection between name and id");
                        }
                    }

                    //avoid duplicate values. changes to set then back to arrayList
                    hs.addAll(intersection);
                    intersection.clear();
                    intersection.addAll(hs);
                    boolean found_intersection = false;
                    for (Integer x : intersection)
                    {
                        if (x == index) //index for Id same as list for name -> MATCHs
                        {
                            Gui.setSearchText("Found\n");
                            Gui.appendToSearchText(productList.get(x).toString());
                            found_intersection = true;
                            break;
                        }
                    }
                    if (!found_intersection)
                    {
                        Gui.setSearchText("Couldnt find intersection between name and id");
                    }
                }
            }

            //search by name and start year
            else if (!keySearchField.equals("") && !startYearSearchField.equals("") && endYearSearchField.equals("") && idSearchField.equals(""))
            {
                //first searches by name to get the List indices
                String[] keywords = keySearchField.toLowerCase().split("[ ]+");
                ArrayList<Integer> temp = new ArrayList<Integer>();
                ArrayList<Integer> intersection = new ArrayList<Integer>();
                Set<Integer> hs = new HashSet<>();
                int foundKey = 0;

                //loop to get value for key and store it in temp list
                for (String key : keywords)
                {
                    if (map.containsKey(key))
                    {
                        foundKey++;
                        temp.addAll(map.get(key));
                        if (intersection.size() == 0)
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

                if (foundKey != keywords.length) //make sure found all before printing
                {
                    Gui.setSearchText("Didnt find names given - didnt continue with time period search");
                }
                else
                {
                    boolean found = false;
                    for (int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if (Integer.parseInt(product.getYear()) >= Integer.parseInt(startYearSearchField))
                        {
                            if (intersection.contains(i))
                            {
                                Gui.appendToSearchText("\nFound\n");
                                Gui.appendToSearchText(productList.get(i).toString());
                                found = true;
                            }
                        }
                    }
                    if (!found)
                    {
                        Gui.setSearchText("Didnt find name with given year");
                    }
                }
            }

            //search by name and end year .. repitive sorry.
            else if (!keySearchField.equals("") && startYearSearchField.equals("") && !endYearSearchField.equals("") && idSearchField.equals(""))
            {
                //first searches by name to get the List indices
                String[] keywords = keySearchField.toLowerCase().split("[ ]+");
                ArrayList<Integer> temp = new ArrayList<Integer>();
                ArrayList<Integer> intersection = new ArrayList<Integer>();
                Set<Integer> hs = new HashSet<>();
                int foundKey = 0;

                //loop to get value for key and store it in temp list
                for (String key : keywords)
                {
                    if (map.containsKey(key))
                    {
                        foundKey++;
                        temp.addAll(map.get(key));
                        if (intersection.size() == 0)
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

                if (foundKey != keywords.length) //make sure found all before printing
                {
                    Gui.setSearchText("Didnt find names given - didnt continue with time period search");
                }
                else
                {
                    boolean found = false;
                    for (int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if (Integer.parseInt(product.getYear()) <= Integer.parseInt(endYearSearchField))
                        {
                            if (intersection.contains(i))
                            {
                                Gui.appendToSearchText("\nFound\n");
                                Gui.appendToSearchText(productList.get(i).toString());
                                found = true;
                            }
                        }
                    }
                    if (!found)
                    {
                        Gui.setSearchText("Didnt find name with given year");
                    }
                }
            }

            //search by name and (Start and end)
            else if (!keySearchField.equals("") && !startYearSearchField.equals("") && !endYearSearchField.equals("") && idSearchField.equals(""))
            {
                //first searches by name to get the List indices
                String[] keywords = keySearchField.toLowerCase().split("[ ]+");
                ArrayList<Integer> temp = new ArrayList<Integer>();
                ArrayList<Integer> intersection = new ArrayList<Integer>();
                Set<Integer> hs = new HashSet<>();
                int foundKey = 0;

                //loop to get value for key and store it in temp list
                for (String key : keywords)
                {
                    if (map.containsKey(key))
                    {
                        foundKey++;
                        temp.addAll(map.get(key));
                        if (intersection.size() == 0)
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

                if (foundKey != keywords.length) //make sure found all before printing
                {
                    Gui.setSearchText("Didnt find names given - didnt continue with time period search");
                }
                else
                {
                    boolean found = false;
                    for (int i = 0; i < productList.size(); i++)
                    {
                        product = productList.get(i);
                        if (Integer.parseInt(product.getYear()) >= Integer.parseInt(startYearSearchField)
                                && (Integer.parseInt(product.getYear()) <= Integer.parseInt(endYearSearchField)))
                        {
                            if (intersection.contains(i))
                            {
                                Gui.appendToSearchText("\nFound\n");
                                Gui.appendToSearchText(productList.get(i).toString());
                                found = true;
                            }
                        }
                    }
                    if (!found)
                    {
                        Gui.setSearchText("Didnt find name with given year");
                    }
                }
            }

            //finally...all fields are full
            else
            {
                //copy paste function for finding id & name. 
                // then checks if its within the time period

                int index = -1; //stores index where it found the ID so it can find intersection with name later
                boolean found = false;
                for (int i = 0; i < productList.size(); i++)
                {
                    product = productList.get(i);
                    if (product.getId().equals(idSearchField))
                    {
                        index = i;
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    Gui.setSearchText("ID not found - did not continue with search");
                }
                else
                {

                    String[] keywords = keySearchField.toLowerCase().split("[ ]+");
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    ArrayList<Integer> intersection = new ArrayList<Integer>();
                    Set<Integer> hs = new HashSet<>();
                    int foundName = 0;

                    //loop to get value for key and store it in temp list
                    for (String key : keywords)
                    {
                        if (map.containsKey(key))
                        {
                            foundName++;
                            temp.addAll(map.get(key));
                            if (intersection.size() == 0)
                            {
                                intersection.addAll(map.get(key));
                            }
                            intersection.retainAll(temp);
                            temp.clear(); //resets for next iteration
                        }
                        else if (foundName == 0)
                        {
                            Gui.setSearchText("Couldnt find intersection between name and id");
                        }
                    }

                    //avoid duplicate values. changes to set then back to arrayList
                    hs.addAll(intersection);
                    intersection.clear();
                    intersection.addAll(hs);
                    boolean found_intersection = false;
                    for (Integer x : intersection)
                    {
                        if (x == index) //index for Id same as list for name -> MATCHs
                        {
                            if (Integer.parseInt(productList.get(x).getYear()) >= Integer.parseInt(startYearSearchField)
                                    && (Integer.parseInt(productList.get(x).getYear()) <= Integer.parseInt(endYearSearchField)))
                            {
                                Gui.setSearchText("Found\n");
                                Gui.appendToSearchText(productList.get(x).toString());
                                found_intersection = true;
                                break;
                            }
                        }
                    }
                    if (!found_intersection)
                    {
                        Gui.setSearchText("Couldnt find intersection between name, id and years.");
                    }
                }

            }

        }

    }
    
     /*
        printLists
        print the complete list 
        used by search function incase user skipped all input
    */
    private void printLists(ArrayList<Product> productList)
    {

        Product product;
        Gui.appendToSearchText("\n");
        for (int i = 0; i < productList.size(); i++)
        {
            product = productList.get(i);
            Gui.appendToSearchText(product.toString());
            Gui.appendToSearchText("\n");
        }
    }

    /*
        foundDuplicate
        checks for dupliacte ids in list
     */
    private int foundDuplicate(ArrayList<Product> productList, String id)
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
        parseFile
        basic parser uses regex for qoutations 
        assumes input file will follow format and same order
        key = "value"
        doesnt handle escape characters unfortunately
     */
    protected void parseFile(String[] args)
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

            while (scan.hasNextLine())
            {
                line = scan.nextLine();
                if (line != null && !line.isEmpty())
                {
                    information = line.split("[\"]+");
                    switch (ctr)
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
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                price = "";
                            }
                            break;
                        case 5:
                            year = information[1];
                            break;
                        case 6:
                            if (type.equals("book"))
                            {
                                try
                                {
                                    author = information[1];
                                }
                                catch (ArrayIndexOutOfBoundsException e)
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
                                catch (ArrayIndexOutOfBoundsException e)
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
                            catch (ArrayIndexOutOfBoundsException e)
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

    
    //adds to hashmap
    protected static void addToMap(String[] words, ArrayList<Product> productList)
    {
        for (String key : words)
        {
            if (!map.containsKey(key))
            {
                map.put(key, new ArrayList<Integer>());
            }
            map.get(key).add(productList.size() - 1); //index of product to refer to it later
        }
    }

    /*
        Update File
        Uses the populated product array list to overwrite file
        used at the start
        toString method defined in products classes to write
        to output file same way as in description based on object given
     */
    public static void updateFile()
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
}
