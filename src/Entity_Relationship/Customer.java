package Entity_Relationship;

import java.io.Serializable;

public class Customer implements Serializable {
	private String name;
	private String phonenumber;
	private String address;
	private String type;
	
	public Customer(){	}
	public Customer(String namestr,String phonestr,String addressstr,String typestr) {
		name = namestr;
		phonenumber = phonestr;
		address=addressstr;
		type=typestr;
	}
	public void putname(String name){
		this.name = name;
		/*
		 * 从数据库获取客户所有信息
		 */
	}
	public String getname(){
		return name;
	}
	public String getphone(){
		return phonenumber;
	}
	public String getaddr() {
		return address;
	}
	public String gettype(){
		return type;
	}
	public Object[] toTabel(){
		Object[] data = {new Boolean(false),name,phonenumber,address,type};
		return data;
	}
}
