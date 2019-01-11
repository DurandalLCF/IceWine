package Entity_Relationship;

import java.io.Serializable;

public class Product implements Serializable {
	String productname;
	double price;
	int amount;
	
	public Product(String name,double price,int amount) {
		productname = name;
		this.price = price;
		this.amount = amount;
	}
	
	public String pro_name() {
		return productname;
	}
	public double getprice() {
		return price;
	}
	public int getamount() {
		return amount;
	}
	public Object[] toTabelchoose() {
		Object[] data = {new Boolean(false),productname,price,amount};
		return data;
	}
	public Object[] toTabelshow() {
		Object[] data = {productname,price,amount};
		return data;
	}
}
