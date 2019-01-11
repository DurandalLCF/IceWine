package Entity_Relationship;

import java.io.Serializable;

public class Account implements Serializable {
	private String user;
	private String password;
	private String type;
	
	public Account(String user,String password,String type) {
		this.user = user;
		this.password = password;
		this.type = type;
	}

	public String getuser() {
		return user;
	}
	public String getpassword() {
		return password;
	}
	public String gettype(){
		return type;
	}
	public Object[] toTabel(){
		Object[] data = {new Boolean(false),user,password,type};
		return data;
	}
}
