
package mousaa_a2;

/**
 *
 * @author AhmedMousa
 */
public class Electronics extends Product
{
    private String maker;

    public Electronics(String maker, String id, String name, String price, String year)
    {
        super(id, name, price, year);
        this.maker = maker;
    }
    //set and get
    public String getMaker()
    {
        return maker;
    }

    public void setMaker(String maker)
    {
        this.maker = maker;
    }
    
    public String toString()
    {
        return "type = \"electronics\"\nproductID = \"" + getId() + "\"\nname = \"" + getName() + 
        "\"\nprice = \"" + getPrice() + "\"\nyear = \"" + getYear() + "\"\nauthors = \"" + getMaker() + "\"\n";    
    }
    
}
