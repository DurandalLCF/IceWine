package Customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.ServerCloneException;

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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import org.omg.PortableInterceptor.ServerIdHelper;

import Client.Check_Client;
import Entity_Relationship.Order_Detail;
import Entity_Relationship.Order_Head;
import Entity_Relationship.Product;
import Order.Ordermain;
import Other.Time;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Customerwin extends JFrame implements ActionListener{
	private JPanel panel_main;
	private JTextField name,phone,time,address,goodsnum;
	private JRadioButton sendgoods,notsendgoods;
	private JTable goodsshow;
	private JComboBox goodslist;
	private JButton goodssure,sure,clear;
	private JTextArea goodschoose;
	private JLabel allprice,tax,label;
	private TableData data;
	
	private final String IP = "127.0.0.1"; 
	private final static int port_reciver = 9092;
	private final static int port_send = 9091;
	private JLabel allpay;

	public static void main(String[] arg){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Customerwin customerwin = new Customerwin();
			}
		});
	}
	public Customerwin() {
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 465, 640);
		this.setResizable(false);
		this.setTitle("冰酒选购");
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {// 注册窗口监听器
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
		
		panel_main = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/clicheck.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.64)
						,(int)(icon.getIconHeight()*0.63),icon.getImageObserver());
			}
		};
		panel_main.setLayout(null);
		getContentPane().add(panel_main, BorderLayout.CENTER);
		
		label = new JLabel("\u5BA2\u6237\u9009\u8D2D\uFF08\u6B22\u8FCE\u5149\u4E34\u672C\u5E97\uFF09");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(14, 13, 252, 31);
		panel_main.add(label);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBounds(14, 57, 210, 130);
		panel_main.add(panel);
		
		JPanel paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		panel.add(paneltmp);
		label = new JLabel("\u987E\u5BA2\u59D3\u540D\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 17));
		paneltmp.add(label);
		name = new JTextField();
		paneltmp.add(name);
		name.setColumns(8);
		
		paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		panel.add(paneltmp);
		label = new JLabel("\u8054\u7CFB\u7535\u8BDD\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 17));
		paneltmp.add(label);
		phone = new JTextField();
		phone.setColumns(8);
		paneltmp.add(phone);
		
		paneltmp = new JPanel();
		paneltmp.setOpaque(false);
		panel.add(paneltmp);
		label = new JLabel("\u53D6\u8D27\u65F6\u95F4\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 17));
		paneltmp.add(label);
		time = new JTextField();
		time.setColumns(8);
		paneltmp.add(time);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setOpaque(false);
		panel.setBounds(235, 57, 210, 130);
		panel_main.add(panel);
		
		sendgoods = new JRadioButton("\u9001\u8D27");
		sendgoods.setOpaque(false);
		sendgoods.setFont(new Font("宋体", Font.BOLD, 17));
		sendgoods.setBounds(10, 20, 69, 27);
		panel.add(sendgoods);
		
		notsendgoods = new JRadioButton("\u4E0D\u9001\u8D27");
		notsendgoods.setOpaque(false);
		notsendgoods.setFont(new Font("宋体", Font.BOLD, 17));
		notsendgoods.setBounds(107, 21, 93, 27);
		panel.add(notsendgoods);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(sendgoods);
		bGroup.add(notsendgoods);
		sendgoods.setSelected(true);
		
		address = new JTextField();
		address.setColumns(8);
		address.setBounds(96, 69, 104, 24);
		panel.add(address);
		
		label = new JLabel("\u9001\u8D27\u5730\u5740\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 17));
		label.setBounds(10, 71, 190, 20);
		panel.add(label);
		
		label= new JLabel("\u5546\u54C1\u603B\u89C8\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 19));
		label.setBounds(14, 194, 146, 31);
		panel_main.add(label);
		
		label = new JLabel("\u9009\u62E9\u4E0B\u5355\u8D27\u7269\uFF08\u53EF\u591A\u6B21\u9009\u62E9\uFF09\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(14, 335, 300, 32);
		panel_main.add(label);
		
		goodslist = new JComboBox();
		goodslist.setBounds(24, 367, 153, 24);
		panel_main.add(goodslist);
		
		label = new JLabel("\u6570\u91CF\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(214, 369, 70, 24);
		panel_main.add(label);
		
		goodsnum = new JTextField("0", 6);
		goodsnum.setBounds(277, 369, 37, 24);
		panel_main.add(goodsnum);
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
		goodsnum.addKeyListener(new KeyAdapter(){  
            public void keyTyped(KeyEvent e) {              
                if(!(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9))  
                    e.consume(); //关键，屏蔽掉非法输入  
            }  
        }); 
		
		goodssure = new JButton("\u786E\u5B9A");
		goodssure.setFont(new Font("宋体", Font.BOLD, 16));
		goodssure.setBounds(328, 367, 70, 27);
		panel_main.add(goodssure);
		
		label = new JLabel("\u5DF2\u9009\u4E2D\u8D27\u7269\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(14, 394, 126, 24);
		panel_main.add(label);
		
		data = new TableData();
		goodsshow = new JTable(data);
		JScrollPane scrollPane = new JScrollPane(goodsshow);
		scrollPane.setBounds(24, 231, 393, 94);
		panel_main.add(scrollPane);
		
		goodschoose = new JTextArea("");
		goodschoose.setEditable(false);
		scrollPane = new JScrollPane(goodschoose);
		scrollPane.setBounds(26, 420, 300, 65);
		panel_main.add(scrollPane);
		
		allprice = new JLabel("\u603B\u4EF7\uFF1A0.0RMB");
		allprice.setFont(new Font("宋体", Font.BOLD, 18));
		allprice.setBounds(14, 498, 180, 24);
		panel_main.add(allprice);
		
		tax = new JLabel("\u7A0E\u6536\uFF1A0.0RMB");
		tax.setFont(new Font("宋体", Font.BOLD, 18));
		tax.setBounds(214, 498, 231, 24);
		panel_main.add(tax);
		
		sure = new JButton("\u786E\u8BA4\u4E0B\u5355");
		sure.setFont(new Font("宋体", Font.BOLD, 18));
		sure.setBounds(304, 555, 113, 39);
		panel_main.add(sure);
		
		clear = new JButton("\u6E05\u7A7A\u9009\u9879");
		clear.setFont(new Font("宋体", Font.BOLD, 16));
		clear.setBounds(340, 461, 105, 24);
		panel_main.add(clear);
		
		allpay = new JLabel("\u52A0\u7A0E\u603B\u4EF7\uFF1A0.0RMB");
		allpay.setFont(new Font("宋体", Font.BOLD, 18));
		allpay.setBounds(24, 535, 200, 31);
		panel_main.add(allpay);
		
		sendgoods.addActionListener(this);
		notsendgoods.addActionListener(this);
		goodssure.addActionListener(this);
		sure.addActionListener(this);
		clear.addActionListener(this);
	}
	
	//设置货物展示
	class TableData extends AbstractTableModel{
		private final String[] columnNames = {"商品名字", "售价", "库存量"};
		private Object[][] data = null;
		Product[] products = null;

		public TableData() {
			try {
				Socket server = new Socket(IP,port_reciver);
				ObjectInputStream object = new ObjectInputStream(server.getInputStream());
				products = (Product[])object.readObject();
				object.close();
				server.close();
				
				//需展示的货物内容
				Object[][] datatmp = new Object[products.length][];
				for(int i=0;i<products.length;i++){
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
            return false;
        }
        // 改变某个数据的值
        public void setValueAt(Object value, int row, int col) { 
        	data[row][col] = value;
        	fireTableCellUpdated(row, col);
        }
        //返回对应名字货品的价格
        public double getprice(String name){
        	int number = name.length();
        	for(int i=0;i<data.length;i++)
        		if(((String)data[i][0]).length() == name.length())
	        		if(((String)data[i][0]).substring(0, number).equals(name))
	        			return (double)data[i][1];	
        	return -1;
        }
        //返回存货是否足够
        public boolean enable(String name,int number){
        	int num = name.length();
    		for(int i=0;i<data.length;i++)
    			if(((String)data[i][0]).length() == num)
	    			if(((String)data[i][0]).substring(0, num).equals(name) 
	    				&& (number <= (int)data[i][2]))
	    				return true;	
    		return false;
        }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendgoods)
			address.setEditable(true);
		else if(e.getSource() == notsendgoods)
			address.setEditable(false);
		else if(e.getSource() == goodssure){
			if(goodsnum.getText().equals("0") || goodsnum.getText().equals(""))	return;
			
			if(!data.enable((String)goodslist.getSelectedItem(), Integer.parseInt(goodsnum.getText()))){
				JOptionPane.showMessageDialog(null,goodslist.getSelectedItem()+" 库存不足",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				goodsnum.setText("0");
				return;
			}
			goodschoose.append(goodslist.getSelectedItem()+"   数量："
					+Integer.parseInt(goodsnum.getText())+"\r\n");
			goodsnum.setText("0");
			goodslist.removeItem(goodslist.getSelectedItem());
			
			double price = 0.0;
			String[] message = goodschoose.getText().split("\r\n");
			//计算总价和税率
			for(int i = 0 ;i<message.length;i++){
				String tmp = message[i];
				price += data.getprice(tmp.substring(0, tmp.indexOf(" ")))*Float.
						parseFloat(tmp.substring(tmp.indexOf("：")+1,tmp.length()));
			}
			
			allprice.setText("总价："+price+"RMB");
			tax.setText("税收："+price*Ordermain.tax_*0.01+"RMB");
			allpay.setText("加税总价："+(price+price*Ordermain.tax_*0.01)+"RMB");
		}
		else if(e.getSource() == clear){
			goodschoose.setText("");
			goodsnum.setText("");
			allprice.setText("总价：0.0 RMB");
			tax.setText("税收：0.0 RMB");
			data.additem();
		}
		else if(e.getSource() == sure){
			if (name.getText().equals("") || phone.getText().equals("")
					|| time.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"请补全所有信息",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(sendgoods.isSelected() && address.getText().equals("")){
				JOptionPane.showMessageDialog(null,"请补全所有信息",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				return ;
			}
			if(goodschoose.getText().equals("")){
				JOptionPane.showMessageDialog(null,"请选择商品！",
						"警告",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			
			double price = 0.0;
			String[] message = goodschoose.getText().split("\r\n");
			//计算总价和税率
			for(int i = 0 ;i<message.length;i++){
				String tmp = message[i];
				price += data.getprice(tmp.substring(0, tmp.indexOf(" ")))*Float.
						parseFloat(tmp.substring(tmp.indexOf("：")+1,tmp.length()));
			}		
			int value=JOptionPane.showConfirmDialog(this, "总价为："+price+"RMB\r\n税收为："+
					price*Ordermain.tax_*0.01+"RMB\r\n加税总价："+(price+price*Ordermain.tax_*0.01)+"RMB"
					, "提示",JOptionPane.YES_NO_OPTION);
			
			//下单操作
			if(value == JOptionPane.YES_OPTION){
				Order_Detail[] order_Detail = new Order_Detail[message.length];
				for(int i=0;i<message.length;i++){
					String tmp = message[i];
					int num = Integer.parseInt(tmp.substring(tmp.indexOf("：")+1,tmp.length()));
					order_Detail[i] = new Order_Detail(tmp.substring(0, tmp.indexOf(" ")),  new Time
							(new JLabel()).Getordernum(),num*Ordermain.tax_, data.getprice
							(tmp.substring(0, tmp.indexOf(" "))),data.getprice
							(tmp.substring(0, tmp.indexOf(" ")))*num, num);
				}
				Order_Head order_Head = new Order_Head(name.getText(), new Time(new JLabel()).
						Getordernum(),phone.getText(),address.getText(),time.getText(), 
						sendgoods.isSelected(),price, price*Ordermain.tax_*0.01,order_Detail);
				
				//把数据发送至服务器
				try {
					Socket socket = new Socket(IP, port_send);
					ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
					outputStream.writeObject(order_Head);
					
					DataInputStream in = new DataInputStream(socket.getInputStream());
					if((char)in.read() == 'N'){
						JOptionPane.showMessageDialog(null,"下单失败，请重新操作。",
								"提示",JOptionPane.WARNING_MESSAGE);
						socket.close();
						return ;
					}

					socket.close();
					JOptionPane.showMessageDialog(null,"下单成功。",
							"提示",JOptionPane.WARNING_MESSAGE);
					dispose();
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} 
			}
			else return;
		}
	}
}
