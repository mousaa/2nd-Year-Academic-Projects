
package mousaa_a2;


/**
 *
 * @author AhmedMousa
 */
public class Book extends Product
{
    private String author;
    private String publisher;

    public Book(String author, String publisher, String id, String name, String price, String year)
    {
        super(id, name, price, year);
        this.author = author;
        this.publisher = publisher;
    }

     
    //set and get
    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }
    
    public String toString()
    {
       return "type = \"book\"\nproductID = \"" + getId() + "\"\nname = \"" + getName() + 
        "\"\nprice = \"" + getPrice() + "\"\nyear = \"" + getYear() + "\"\nauthors = \"" + getAuthor()
        + "\"\npublisher = \"" + getPublisher() + "\"\n"; 
    }
    
}
