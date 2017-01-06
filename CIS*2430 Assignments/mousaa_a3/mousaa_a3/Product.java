package mousaa_a3;
/**
 *
 * @author AhmedMousa
 */
public abstract class Product
{
    //variables that repeat in both book and electronic class.
    private String id;
    private String name;
    private String price;
    private String year;

    public Product(String id, String name, String price, String year)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.year = year;
    }

    
    //set and get
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

}
