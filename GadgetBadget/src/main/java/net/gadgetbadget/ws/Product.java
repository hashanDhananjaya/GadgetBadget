package net.gadgetbadget.ws;

public class Product {
	
	private int product_Id;
	private String product_Name;
	private String product_Description;
	private String manufactured_date;
	private float price;
	
	public Product() {
				
	}

	public Product(int product_Id, String product_Name, String product_Description,String manufactured_date , 
			float price) {
		this.product_Id = product_Id;
		this.product_Name = product_Name;
		this.product_Description = product_Description;
		this.manufactured_date= manufactured_date;
		this.price = price;
	}

	public int getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(int product_Id) {
		this.product_Id = product_Id;
	}

	public String getProduct_Name() {
		return product_Name;
	}

	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}

	public String getProduct_Description() {
		return product_Description;
	}

	public void setProduct_Description(String product_Description) {
		this.product_Description = product_Description;
	}

	public String getManufactured_date() {
		return manufactured_date;
	}

	public void setManufactured_date(String manufactured_date) {
		this.manufactured_date = manufactured_date;
	}
//
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + product_Id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (product_Id != other.product_Id)
			return false;
		return true;
	}
	
}

   



    
  



	
	
	
	
	



