package mousaa_a2;

import java.util.*;


//javac mousaa_a2/*.java
//java mousaa_a2.Main input.txt

/**
 *
 * @author AhmedMousa
 */
public class Main
{
    public static void main(String[] args)
    {     

        ArrayList<Product> productList = new ArrayList<>();
        EStoreSearch store = new EStoreSearch();
        
        //checks arg count 
        if(args.length != 1)
        {
        	System.out.println("Run program using: java Main <filename>: ");
        	System.exit(0);
        }
        else
        {
        	store.parseFile(productList,args);
        }
        System.out.println("=====Welcome To EStoreSearch====="); 
        

        //starts whole program
        store.startProgram(productList);
        
    }
}
 