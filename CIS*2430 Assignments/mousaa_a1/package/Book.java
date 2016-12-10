/*
 * class Book
 * holds data for book product
 * containts appropriate mutators and accessors and constructor
 */
public class Book 
{
	private String id;
	private String price;
	private String year;
	private String name;
	private String author;
	private String publisher;
	
	Book()
	{
		
	}
	
	Book(String id, String price, String year, String name, String author, String publisher)
	{
		this.id = id;
		this.price = price;
		this.year = year;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
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
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public String getPublisher()
	{
		return this.publisher;
	}
	
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

}