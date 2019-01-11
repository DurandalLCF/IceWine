package Entity_Relationship;

import java.io.Serializable;

public class Staff implements Serializable {
	public String name;
	public String phone;
	public String address;
	public String type;
	public String staffID;
	
	public Staff(String name,String phone,String addr,String type,String ID){
		this.name = name;
		this.phone = phone;
		address = addr;
		this.type = type;
		staffID = ID;
	}
	
	public String getname(){
		return name;
	}
	public String getphone(){
		return phone;
	}
	public String getaddr(){
		return address;
	}
	public String gettype(){
		return type;
	}
	public String getID(){
		return staffID;
	}
	public Object[] toTabel() {
		Object[] data = {new Boolean(false),staffID,name,phone,type};
		return data;
	}
}
