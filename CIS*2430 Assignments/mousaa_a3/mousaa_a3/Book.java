package mousaa_a3;

import java.util.Objects;
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



    public String getAuthor()
    {
        return author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.author, other.author))
        {
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher))
        {
            return false;
        }
        return true;
    }
    
    
    public String toString()
    {
       return "type = \"book\"\nproductID = \"" + getId() + "\"\nname = \"" + getName() + 
        "\"\nprice = \"" + getPrice() + "\"\nyear = \"" + getYear() + "\"\nauthors = \"" + getAuthor()
        + "\"\npublisher = \"" + getPublisher() + "\"\n"; 
    }   
}
