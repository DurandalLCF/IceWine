package Order;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import Client.Check_Client;
import Entity_Relationship.Order_Detail;
import Entity_Relationship.Order_Head;

import javax.swing.JTable;
import javax.swing.JButton;

public class Detail_Order extends JFrame implements ActionListener{
	JPanel panel_main;
	JLabel label;
	TableData data;
	private JLabel name,price,tax,address,time,ordernum,phone,allprice;
	private JTable table;
	private JButton delete;
	String ordernumstr;
	JScrollPane jScrollPane;
	
	public Detail_Order(String orderstr) {
		ordernumstr = orderstr;
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///"
					+ "E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Order_Head");
			
			boolean exist = false;
			while(rs.next())
				if(rs.getString("ordernumber").equals(orderstr)){
					exist = true;
					Setall();
					
					name.setText("顾客姓名："+rs.getString("name"));
					phone.setText("顾客电话："+rs.getString("phonenumber"));
					ordernum.setText("订单号："+rs.getString("ordernumber"));
					price.setText("总价："+rs.getDouble("prices"));
					tax.setText("税收："+rs.getDouble("tax"));
					
					if(rs.getString("delivery").equals("false"))	address.setText("送货地址：无需送货");
					else	address.setText("送货地址："+rs.getString("address"));
					time.setText("取货时间:"+rs.getString("pickupdate"));
					allprice.setText("税后总价："+(rs.getDouble("prices")+rs.getDouble("tax")));
					setTable();
				}
			if(!exist){
				JOptionPane.showMessageDialog(null,"订单不存在。",
						"警告",JOptionPane.WARNING_MESSAGE);
				return ;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public Detail_Order(Order_Head tmp){
		ordernumstr = tmp.getorder();
		Setall();
		
		name.setText("顾客姓名："+tmp.getname());
		phone.setText("顾客电话："+tmp.getphone());
		ordernum.setText("订单号："+tmp.getorder());
		price.setText("总价："+tmp.getprices());
		tax.setText("税收："+tmp.gettax());
		if(tmp.deliveryornot())	address.setText("送货地址："+tmp.getaddress());
		else	address.setText("送货地址：无需送货");
		time.setText("取货时间:"+tmp.getdate());
		allprice.setText("税后总价："+(tmp.getprices()+tmp.gettax()));
		setTable();
	}
	public Detail_Order(String order,String namestr,String phonestr,String addr,
			String date,boolean delivery,double pricestr,double taxstr) {
		// TODO 自动生成的构造函数存根
		ordernumstr = order;
		Setall();
		
		name.setText("顾客姓名："+namestr);
		phone.setText("顾客电话："+phonestr);
		ordernum.setText("订单号："+order);
		price.setText("总价："+pricestr);
		tax.setText("税收："+taxstr);
		if(delivery)	address.setText("送货地址："+addr);
		else	address.setText("送货地址：无需送货");
		time.setText("取货时间:"+date);
		allprice.setText("税后总价："+(pricestr+taxstr));
		setTable();
	}
	public void Setall() {
		/*
		 * 查询输入的订单号是否存在数据库中，如果没有直接输出提示并结束该窗口
		 */
		this.setTitle("冰酒销售系统");
		this.setVisible(true);
		this.setSize(490, 415);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		panel_main = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/ordercheck.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*1.47),
						(int)(icon.getIconHeight()*1.2),icon.getImageObserver());
			}
		};
		panel_main.setLayout(null);
		getContentPane().add(panel_main, BorderLayout.CENTER);
		
		label = new JLabel("\u8BA2\u5355\u7EC6\u8282");
		label.setFont(new Font("宋体", Font.BOLD, 25));
		label.setBounds(10, 10, 141, 30);
		panel_main.add(label);
		
		name = new JLabel("\u987E\u5BA2\u59D3\u540D\uFF1A");
		name.setFont(new Font("宋体", Font.BOLD, 18));
		name.setBounds(30, 79, 423, 20);
		panel_main.add(name);
		
		phone = new JLabel("\u987E\u5BA2\u7535\u8BDD\uFF1A");
		phone.setFont(new Font("宋体", Font.BOLD, 18));
		phone.setBounds(30, 101, 423, 20);
		panel_main.add(phone);
		
		ordernum = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		ordernum.setFont(new Font("宋体", Font.BOLD, 15));
		ordernum.setBounds(51, 36, 402, 30);
		panel_main.add(ordernum);
		
		price = new JLabel("\u603B\u4EF7\uFF1A");
		price.setFont(new Font("宋体", Font.BOLD, 18));
		price.setBounds(30, 247, 188, 20);
		panel_main.add(price);
		
		tax = new JLabel("\u7A0E\u6536\uFF1A");
		tax.setFont(new Font("宋体", Font.BOLD, 18));
		tax.setBounds(30, 280, 188, 20);
		panel_main.add(tax);
		
		address = new JLabel("\u9001\u8D27\u5730\u5740\uFF1A");
		address.setFont(new Font("宋体", Font.BOLD, 18));
		address.setBounds(258, 247, 226, 20);
		panel_main.add(address);
		
		time = new JLabel("\u53D6\u8D27\u65F6\u95F4\uFF1A");
		time.setFont(new Font("宋体", Font.BOLD, 18));
		time.setBounds(258, 280, 226, 20);
		panel_main.add(time);
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("宋体", Font.BOLD, 20));
		delete.setBounds(340, 339, 113, 30);
		panel_main.add(delete);
		
		allprice = new JLabel("\u7A0E\u540E\u603B\u4EF7\uFF1A");
		allprice.setFont(new Font("宋体", Font.BOLD, 18));
		allprice.setBounds(30, 310, 188, 20);
		panel_main.add(allprice);
		
		delete.addActionListener(this);
	}
	private void setTable(){
		data = new TableData(ordernumstr);
		table = new JTable(data);
		jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(30, 134, 430, 100);
		panel_main.add(jScrollPane);
	}
	
	class TableData extends AbstractTableModel{
		private final String[] columnNames = {"产品姓名", "单价（RMB）","数量", "总价（RMB）","税收（RMB）"};
		private Object[][] data = null;
		private String ordernum;

		public TableData(String order) {
			ordernum = order;
			/*
			 * 查询订单号为ordernum的所有订单细节
			 */
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from Order_Detail");
				
				int count = 0;
				int lenght = order.length();
				while(rs.next())
					if(rs.getString("ordernumber").substring(0,lenght).equals(order))
						count ++;
				Order_Detail[] details = new Order_Detail[count];
				Object[][] datatmp = new Object[count][];
				
				/*
				 * 返回货物信息
				 */
				rs = statement.executeQuery("select * from Order_Detail");

				int i=0;
				while(rs.next()){
					if(rs.getString("ordernumber").substring(0,lenght).equals(order)){
						details[i] = new Order_Detail(rs.getString("productname"),order, rs.getDouble("tax"),
								rs.getDouble("price"),rs.getDouble("totalprice"), rs.getInt("amount"));
						datatmp[i] = details[i].toTabelshow();
						i++;
					}
				}
				if(count == 0)	datatmp = null;
				data = datatmp;
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	
		// 获得列的数目
        public int getColumnCount() {
            return columnNames.length;
        }
        // 获得行的数目
        public int getRowCount() {
        	int row;
        	try{
        		row = data.length;
        	}catch (NullPointerException e) {
				row = 0;
			}
            return row;
        }
        // 获得某列的名字，而目前各列的名字保存在字符串数组columnNames中
        public String getColumnName(int col) {
            return columnNames[col];
        }
        // 获得某行某列的数据，而数据保存在对象数组data中
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        // 判断每个单元格的类型
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        // 将表格声明为可编辑的
        public boolean isCellEditable(int row, int col) {
            return false;
        }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete){
			/*
			 * 删除相应订单号的所有信息
			 */
			int value=JOptionPane.showConfirmDialog(this, "确认删除？", "提示",JOptionPane.YES_NO_OPTION); 
			if(value==JOptionPane.YES_OPTION){
				/*
				 * 删除用户操作
				 */
				try {
					Class.forName("com.hxtt.sql.access.AccessDriver");
					Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
					Statement statement = connection.createStatement();
					
					String sql = "delete from Order_Head where ordernumber ='"+ordernumstr+"'";
					statement.executeUpdate(sql);
					sql = "delete from Order_Detail where ordernumber ='"+ordernumstr+"'";
					statement.executeUpdate(sql);
					JOptionPane.showMessageDialog(null,"删除成功",
							"结果",JOptionPane.WARNING_MESSAGE);
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,"删除失败",
							"警告",JOptionPane.WARNING_MESSAGE);
					e2.printStackTrace();
				}		
			}
		}
	}
}
