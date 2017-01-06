
package mousaa_a3;

import java.util.Objects;

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

    public String getMaker()
    {
        return maker;
    }

    public void setMaker(String maker)
    {
        this.maker = maker;
    }

    @Override
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
        final Electronics other = (Electronics) obj;
        if (!Objects.equals(this.maker, other.maker))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "type = \"electronics\"\nproductID = \"" + getId() + "\"\nname = \"" + getName() + 
        "\"\nprice = \"" + getPrice() + "\"\nyear = \"" + getYear() + "\"\nauthors = \"" + getMaker() + "\"\n";    
    }
    
    
    
    
}
