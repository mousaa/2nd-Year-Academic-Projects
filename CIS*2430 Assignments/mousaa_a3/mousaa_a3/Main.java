package mousaa_a3;
/**
 * @author AhmedMousa
 */
public class Main
{
    public static void main(String args[])
    { 
        if(args.length != 1)
        {
        	System.out.println("Run program using: java Main <filename>: (input.txt is one i used) ");
        	System.exit(0);
        }
        else
        {
            //populated arraylist and map from input file when program starts.
            EStoreSearch store = new EStoreSearch();
            System.out.println("Parsing input file...");
            store.parseFile(args);
            System.out.println("Done!");
            
            System.out.println("Creating GUI...");
            Gui frame = new Gui(); 
            System.out.println("Done!");
        }
    }
}
