package Entity_Relationship;

import java.io.Serializable;

public class Order_Head implements Serializable {
	private String ordernumber;
	private String phonenumber;
	private String address;
	private String pickupdate;
	private String name;
	private boolean delivery;
	private double prices;
	private double tax;
	private Order_Detail[] details;
	
	public Order_Head(String name,String order,String phone,String addr,String date,
			boolean deli,double pric,double tax,Order_Detail[] details) {
		ordernumber = order;
		phonenumber = phone;
		address = addr;
		pickupdate = date;
		delivery = deli;
		prices = pric;
		this.tax = tax;
		this.name = name;
		this.details = details;
	}
	public String getname(){
		return name;
	}
	public String getorder(){
		return ordernumber;
	}
	public String getphone(){
		return phonenumber;
	}
	public String getaddress(){
		return address;
	}
	public String getdate() {
		return pickupdate;
	}
	public boolean deliveryornot(){
		return delivery;
	}
	public double getprices() {
		return prices;
	}
	public double gettax(){
		return tax;
	}
	public void setordernum(String num){
		ordernumber = num;
	}
	public Order_Detail[] getdetail(){
		return details;
	}
	public Object[] toTabel(){
		Object[] data = {new Boolean(false),ordernumber,name,phonenumber,address,prices,tax,(prices+tax)};
		return data;
	}
	public String toFile(){
		return ordernumber+"  "+name+"     "+phonenumber+"   "+prices+"  "+tax;
	}
}
