/*
 * class electronics
 * holds data for electronic product
 * containts appropriate mutators and accessors and constructor
 */
public class Electronics
{
	private String id;
	private String price;
	private String year;
	private String name;
	private String maker;
	
	Electronics()
	{
		
	}
	Electronics(String id, String price, String year, String name, String maker)
	{
		this.id = id;
		this.price = price;
		this.year = year;
		this.name = name;
		this.maker = maker;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getPrice()
	{
		return this.price;
	}
	
	public void setPrice(String price)
	{
		this.price = price;
	}
	
	public String getYear()
	{
		return this.year;
	}
	
	public void setYear(String year)
	{
		this.year = year;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getMaker()
	{
		return this.maker;
	}
	
	public void setMaker(String maker)
	{
		this.maker = maker;
	}
	
}
