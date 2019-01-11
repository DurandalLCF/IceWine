package Linking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import Entity_Relationship.Order_Detail;
import Entity_Relationship.Order_Head;
import Entity_Relationship.Product;
import Other.Time;

public class server_Cust {
	private final static int port_reciver = 9091;
	private final static int port_send = 9092;
	
	public static void main(String[] arg) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				server_Cust server_Cust = new server_Cust();
				server_Cust.server_Cust_receive();
			}
		});
		thread.start();
		
		thread = new Thread(new Runnable() {
			public void run() {
				server_Cust server_Cust = new server_Cust();
				server_Cust.server_Cust_send();
			}
		});
		thread.start();
	}
	private void server_Cust_receive (){
		try {
			ServerSocket serversocket = new ServerSocket(port_reciver);
			System.out.println("接收货物信息服务端开启.");
			while(true){
				Socket socket = serversocket.accept();
				Thread thread = new Thread(new Reciver(socket));
				thread.start();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	private void server_Cust_send (){
		try {
			ServerSocket serversocket = new ServerSocket(port_send);
			System.out.println("发送货物信息服务端开启.");
			while(true){
				Socket socket = serversocket.accept();
				Thread thread = new Thread(new Send(socket));
				thread.start();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
 	class Reciver implements Runnable {
		Socket mySocket;
		
		public Reciver(Socket socket){
			mySocket = socket;
		}
		private int amount(String name) throws Exception{
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Product");
			
			int num = name.length();
        	while(rs.next()){
        		String pname=rs.getString("productname");
        		if(pname.length() == name.length())
	        		if(pname.substring(0, num).equals(name))
	        			return rs.getInt("amount");
        	}
			return 0;
		}
		public void run() {
			try {
				ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
				Order_Head order = (Order_Head) in.readObject();
				Order_Detail[] details = order.getdetail();
				
				/*
				 * 向数据库下单
				 */
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				//添加订单头的操作
				String insert = "insert into Order_Head values('"+order.getorder()+"','"+order.getphone()
					+"','"+order.getname()+"','"+order.getaddress()+"','"+order.getdate()+"','"+
						order.deliveryornot()+"',"+order.getprices()+","+order.gettax()+")";
				statement.execute(insert);
				
				//添加订单细节的操作
				for(int i=0;i<details.length;i++){
					Order_Detail tmp = details[i];
					insert = "insert into Order_Detail values('"+(tmp.getmainorder()+i)+"','"+tmp.getname()+
							"','"+tmp.getmainorder()+"',"+tmp.gettax()+","+tmp.getprice()+","+
							tmp.getallprice()+","+tmp.getamount()+")";
					statement.executeUpdate(insert);
					
					int num = amount(tmp.getname())-tmp.getamount();
					String name = tmp.getname();
					for(int j=name.length();j<6;j++)
						name+=" ";
					insert = "update Product set amount="+num+" where productname='"+name+"'";
					statement.executeUpdate(insert);
				}
				
				DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
				out.write('Y');
				out.flush();
				out.close();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				try {
					DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
					out.write('N');
					out.flush();
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	class Send implements Runnable {
		Socket mySocket;
		
		public Send(Socket socket){
			mySocket = socket;
		}
		public void run() {
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from Product");
				int count = 0;
				while(rs.next())	count ++;
				Product[] products = new Product[count];
				
				rs = statement.executeQuery("select * from Product");

				for(int i=0;i<count;i++){
					rs.next();
					products[i] = new Product(rs.getString("productname"), 
							rs.getDouble("price"), rs.getInt("amount"));
				}
				
				ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
				out.writeObject(products);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
