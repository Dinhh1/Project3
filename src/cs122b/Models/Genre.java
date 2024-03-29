package cs122b.Models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Genre extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7770155209044294491L;
	private int id;
	private String name;
	
	public Genre() {
		super();
	}
	
	public Genre(int i, String n) {
		super();
		this.id = i;
		this.name = n;
	}

	public Genre(int i, String n, int count) {
		super();
		this.id = i;
		this.name = n;
		this.count = count;
	}
	
	public int getId() { return this.id;}
	public String getName() { return this.name;}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String n) {
		this.name = n;
	}

	// adding this property to hold the duplicate
	private int count = 1;
	public void setCount(int c) {
		this.count = c;
	}

	public int getCount() {
		return this.count;
	}
}
