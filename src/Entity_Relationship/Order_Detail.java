package Entity_Relationship;

import java.io.Serializable;

public class Order_Detail implements Serializable {
	String pro_name;
	String ordernumber;
	double tax;
	double price;
	double totalprice;
	int amount;
	
	public Order_Detail(String name,String order,double tax,double price,double total,int amount) {
		pro_name = name;
		ordernumber = order;
		this.tax = tax;
		this.price = price;
		totalprice = total;
		this.amount = amount;
	}
	
	public String getname() {
		return pro_name;
	}
	public String getmainorder(){
		return ordernumber;
	}
	public double gettax(){
		return tax;
	}
	public double getprice(){
		return price;
	}
	public double getallprice() {
		return totalprice;
	}
	public int getamount(){
		return amount;
	}
	public void settax(double tax) {
		this.tax = tax;
	}
	public Object[] toTabel(){
		Object[] data = {new Boolean(false),pro_name,price,amount,totalprice,tax};
		return data;
	}
	public Object[] toTabelshow(){
		Object[] data = {pro_name,price,amount,totalprice,tax};
		return data;
	}
}
