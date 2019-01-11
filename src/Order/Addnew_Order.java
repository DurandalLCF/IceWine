package Order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import Client.Check_Client;
import Entity_Relationship.Order_Detail;
import Entity_Relationship.Order_Head;
import Entity_Relationship.Product;
import Other.Time;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTable;

public class Addnew_Order extends JFrame implements ActionListener{
	private JPanel panel_main;
	private JLabel label;
	private JTextField name,phone,time,addr,goodsnum;
	private JTextArea goodschoose;
	private JRadioButton sendradio,notsendradio;
	private ButtonGroup group;
	private JButton goodssure,addorder,clean;
	private JComboBox goodslist;
	private JTable table;
	private TableData data;
	
	public static void main(String[] arg) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Addnew_Order addnew_Order =  new Addnew_Order();				
			}
		});
	}
	public Addnew_Order(){
		this.setTitle("冰酒销售系统");
		this.setVisible(true);
		this.setSize(370, 561);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		panel_main = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/ordernew.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.8),
						(int)(icon.getIconHeight()*1.68),icon.getImageObserver());
			}
		};
		panel_main.setLayout(null);
		getContentPane().add(panel_main, BorderLayout.CENTER);
		
		label = new JLabel("\u6DFB\u52A0\u65B0\u8BA2\u5355");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 25));
		label.setBounds(15, 12, 151, 38);
		panel_main.add(label);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		panel.setBounds(35, 53, 300, 120);
		panel_main.add(panel);
		
		JPanel paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		label = new JLabel("\u987E\u5BA2\u540D\u5B57\uFF1A");
		paneltmp.add(label);
		label.setFont(new Font("宋体", Font.BOLD, 18));
		name = new JTextField(12);
		paneltmp.add(name);
		panel.add(paneltmp);
		
		paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		label = new JLabel("\u8054\u7CFB\u7535\u8BDD\uFF1A");
		paneltmp.add(label);
		label.setFont(new Font("宋体", Font.BOLD, 18));
		phone = new JTextField(12);
		paneltmp.add(phone);
		panel.add(paneltmp);
		
		paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		label = new JLabel("\u53D6\u8D27\u65F6\u95F4\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 18));
		paneltmp.add(label);
		time = new JTextField(12);
		paneltmp.add(time);
		panel.add(paneltmp);
		
		paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		paneltmp.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		paneltmp.setLocation(35, 180);
		paneltmp.setLayout(null);
		paneltmp.setSize(300, 74);
		panel_main.add(paneltmp);
		
		sendradio = new JRadioButton("\u9001\u8D27");
		sendradio.setFont(new Font("宋体", Font.BOLD, 17));
		sendradio.setOpaque(false);
		sendradio.setBounds(54, 9, 72, 27);
		notsendradio = new JRadioButton("\u4E0D\u9001\u8D27");
		notsendradio.setFont(new Font("宋体", Font.BOLD, 17));
		notsendradio.setOpaque(false);
		notsendradio.setBounds(162, 9, 92, 27);
		paneltmp.add(sendradio);
		paneltmp.add(notsendradio);
		
		group = new ButtonGroup();
		group.add(sendradio);
		group.add(notsendradio);
		sendradio.setSelected(true);
		
		label = new JLabel("\u9001\u8D27\u5730\u5740\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 18));
		label.setBounds(52, 43, 95, 21);
		paneltmp.add(label);
		
		addr = new JTextField(12);
		addr.setBounds(152, 43, 102, 24);
		paneltmp.add(addr);
		
		label = new JLabel("\u9009\u62E9\u4E0B\u5355\u8D27\u7269\uFF08"
				+ "\u53EF\u591A\u6B21\u9009\u62E9\uFF09\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(35, 332, 300, 32);
		panel_main.add(label);
		
		goodslist = new JComboBox();
		goodslist.setFont(new Font("宋体", Font.BOLD, 15));
		goodslist.setBounds(35, 365, 114, 24);
		panel_main.add(goodslist);
		
		label = new JLabel("\u6570\u91CF\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(170, 365, 63, 24);
		panel_main.add(label);
		
		goodsnum = new JTextField("0",6);
		goodsnum.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(goodsnum.getText().equals("0"))
					goodsnum.setText("");
			}
			public void mouseExited(MouseEvent e) {
				if(goodsnum.getText().equals(""))
					goodsnum.setText("0");
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
		//设置只能输入数字
		goodsnum.addKeyListener(new KeyAdapter(){  
            public void keyTyped(KeyEvent e) {              
                if(!(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9))  
                    e.consume(); //关键，屏蔽掉非法输入  
            }  
        }); 
		goodsnum.setBounds(223, 365, 37, 24);
		panel_main.add(goodsnum);
		
		goodssure = new JButton("\u786E\u5B9A");
		goodssure.setFont(new Font("宋体", Font.BOLD, 16));
		goodssure.setBounds(265, 365, 70, 27);
		panel_main.add(goodssure);
		
		label = new JLabel("\u5DF2\u9009\u4E2D\u8D27\u7269\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(35, 397, 126, 24);
		panel_main.add(label);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 423, 300, 52);
		panel_main.add(scrollPane);
		
		goodschoose = new JTextArea("");
		scrollPane.setViewportView(goodschoose);
		goodschoose.setEditable(false);
		
		addorder = new JButton("\u786E\u8BA4\u6DFB\u52A0\u8BA2\u5355");
		addorder.setFont(new Font("宋体", Font.BOLD, 17));
		addorder.setBounds(193, 488, 142, 30);
		panel_main.add(addorder);
		
		clean = new JButton("\u6E05\u7A7A\u9009\u62E9");
		clean.setFont(new Font("宋体", Font.BOLD, 15));
		clean.setBounds(35, 491, 105, 27);
		panel_main.add(clean);
		
		data = new TableData();
		table = new JTable(data);
		scrollPane = new JScrollPane(table); 
		scrollPane.setBounds(35, 267, 299, 63);
		panel_main.add(scrollPane);
		
		goodssure.addActionListener(this);
		addorder.addActionListener(this);
		sendradio.addActionListener(this);
		notsendradio.addActionListener(this);
		clean.addActionListener(this);
	}
	//设置货物展示
	class TableData extends AbstractTableModel{
		private final String[] columnNames = {"商品名字", "售价", "库存量"};
		private Object[][] data = null;

		public TableData() {
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from Product");
				int count = 0;
				while(rs.next())	count ++;
				Product[] products = new Product[count];
				Object[][] datatmp = new Object[count][];
				
				/*
				 * 返回货物信息
				 */
				rs = statement.executeQuery("select * from Product");

				for(int i=0;i<count;i++){
					rs.next();
					products[i] = new Product(rs.getString("productname"), rs.getDouble("price"), rs.getInt("amount"));
					datatmp[i] = products[i].toTabelshow();
					goodslist.addItem(datatmp[i][0]);
				}
				
				data = datatmp;
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		public void additem() {
			goodslist.removeAllItems();
			for(int i=0;i<data.length;i++)
				goodslist.addItem(data[i][0]);
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
            if (col == 0) 	return true;
            return false;
        }
        // 改变某个数据的值
        public void setValueAt(Object value, int row, int col) { 
        	data[row][col] = value;
        	fireTableCellUpdated(row, col);
        }
        //返回对应名字货品的价格
        public double getprice(String name){
        	int num = name.length();
        	for(int i=0;i<data.length;i++)
        		if(((String)data[i][0]).length() == name.length())
	        		if(((String)data[i][0]).substring(0, num).equals(name))
	        			return Double.parseDouble(""+data[i][1]);
        	return 0;
        }
        //返回存货是否足够
        public boolean enable(String name,int number){
        	int num = name.length();
        	for(int i=0;i<data.length;i++)
        		if(((String)data[i][0]).length() == name.length())
	        		if(((String)data[i][0]).substring(0, num).
	        				equals(name) && number <= (int)data[i][2])
	        			return true;
        	return false;	
        }
        //返回存货量
        public int amount(String name){
        	int num = name.length();
        	for(int i=0;i<data.length;i++)
        		if(((String)data[i][0]).length() == name.length())
	        		if(((String)data[i][0]).substring(0, num).equals(name))
	        			return (int)data[i][2];
        	return 0;	
        }
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == notsendradio)
			addr.setEditable(false);
		else if(e.getSource() == sendradio)
			addr.setEditable(true);
		else if(e.getSource() == goodssure){
			if(goodsnum.getText().equals("0"))	return;
			int number = Integer.parseInt(goodsnum.getText());
			if(!data.enable((String)goodslist.getSelectedItem(),number)){
				JOptionPane.showMessageDialog(null,goodslist.getSelectedItem()+" 库存不足",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				goodsnum.setText("0");
				return;
			}
			goodschoose.append(goodslist.getSelectedItem().toString()
					+ "  数量：" + number +"\r\n");
			goodsnum.setText("0");
			goodslist.removeItem(goodslist.getSelectedItem());
		}
		else if(e.getSource() == addorder){
			if(name.getText().equals("") || phone.getText().equals("")
					|| time.getText().equals("")){
				JOptionPane.showMessageDialog(null,"请补全所有信息",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(sendradio.isSelected() && addr.getText().equals("")){
				JOptionPane.showMessageDialog(null,"请补全所有信息",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			if(goodschoose.getText().equals("")){
				JOptionPane.showMessageDialog(null,"请选择商品！",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			float price = 0.0f;
			String[] message = goodschoose.getText().split("\r\n");
			for(int i = 0 ;i<message.length;i++){
				String tmp = message[i];
				price += data.getprice(tmp.substring(0, tmp.indexOf(" ")))*Float.
						parseFloat(tmp.substring(tmp.indexOf("：")+1,tmp.length()));
			} 

			//生成订单头和订单细节
			Time timer = new Time();
			String ordernumber = timer.Getordernum();
			Order_Detail[] details = new Order_Detail[message.length];
			
			for(int i=0;i<message.length;i++){
				String tmp = message[i];
				int num = Integer.parseInt(tmp.substring(tmp.indexOf("：")+1,tmp.length()));
				double pridou = data.getprice(tmp.substring(0, tmp.indexOf(" ")));
				
				details[i] = new Order_Detail(tmp.substring(0, tmp.indexOf(" ")),
						ordernumber,num*Ordermain.tax_, pridou,pridou*num, num);
			}
			
			Order_Head order = new Order_Head(name.getText(), ordernumber, phone.getText(),addr.getText(), 
					time.getText(), sendradio.isSelected(), price, price*Ordermain.tax_*0.01,details);
			
			/*
			 * 向数据库下单
			 */
			try{
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
					insert = "insert into Order_Detail values('"+(tmp.getmainorder()+i)+"','"+
							tmp.getname()+"','"+tmp.getmainorder()+"',"+tmp.gettax()+","+tmp.getprice()
							+","+tmp.getallprice()+","+tmp.getamount()+")";
					statement.executeUpdate(insert);
					
					int num = data.amount(tmp.getname()) - tmp.getamount();
					String name = tmp.getname();
					for(int j=name.length();j<6;j++)
						name+=" ";
					insert = "update Product set amount="+num+" where productname='"+name+"'";
					statement.executeUpdate(insert);
				}
				JOptionPane.showMessageDialog(null,"下单成功",
						"结果",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}	catch (Exception e1) {
				JOptionPane.showMessageDialog(null,"下单失败",
						"警告",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == clean){
			goodschoose.setText("");
			data.additem();
			goodsnum.setText("");
		}
	}
}
