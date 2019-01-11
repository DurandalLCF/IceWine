package MainWin;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Account.Accountmain;
import Client.Clientmain;
import Entity_Relationship.Order_Head;
import Goods.Goodsmain;
import Order.Detail_Order;
import Order.Ordermain;
import Other.Time;
import Staff.Staffmain;

public class Menuwin extends JFrame implements ActionListener{
	JLabel lab;
	JPanel pancon,panmain,panone;
	JButton client_button,order_button,goods_button,staff_button,account_button,file_button;
	JButton[] buttons = {client_button,order_button,goods_button,staff_button,account_button,file_button};
	String[] choose = {"客户信息","订单信息","仓库信息","员工信息","账号管理","订单文本"}; 
	
	Clientmain client;
	Accountmain accountmain;
	Staffmain staffmain;
	Goodsmain goodsmain;
	Ordermain ordermain;
	private final String IP = "127.0.0.1"; 
	private final static int port_Busi = 9093;
	private final static ArrayList<String> LIST = new ArrayList<>();
	private int number = 0;
	
	public static void main(String[] arg){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				Menuwin menuwin = new Menuwin();
			}
		});
	}
	Menuwin(){
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 850, 580);
		this.setResizable(false);
		this.setTitle("冰酒销售系统");
		this.addWindowListener(new WindowAdapter() {// 注册窗口监听器
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
		
		//设置窗体背景以及内容容器
		pancon = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/mainwin.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.78)
						,(int)(icon.getIconHeight()*0.78),icon.getImageObserver());
			}
		};
		pancon.setLayout(null);
		
		//设置标题内容
		panone = new JPanel();
		setTitle(panone);
		pancon.add(panone);
		
		//设置左侧按钮边框		
		panone = new JPanel();
		panone.setOpaque(false);
		panone.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));
		panone.setBounds(30, 70, 105, 400);
		pancon.add(panone);
		//设置左侧按钮内容
		panone = new JPanel();
		setButton(panone);
		pancon.add(panone);
		
		//设置中间的内容
		panmain = new JPanel();
		panmain.setOpaque(false);
		panmain.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));
		panmain.setBounds(145, 70, 680, 400);
		panmain.setLayout(new BoxLayout(panmain,BoxLayout.Y_AXIS));
		addNull(panmain,3);
		lab = new JLabel("        请在左侧选项中");
		lab.setFont(new java.awt.Font("宋体",1,40));
		lab.setForeground(new Color(155,100,50));
		panmain.add(lab);
		addNull(panmain);
		lab = new JLabel("        选择相应的操作。");
		lab.setFont(new java.awt.Font("宋体",1,40));
		lab.setForeground(new Color(155,100,50));
		panmain.add(lab);
		addNull(panmain,3);
		pancon.add(panmain);
		
		//设置下端的时间以及信息通知
		panone = new JPanel();
		setBottom(panone);
		pancon.add(panone);
		
		this.getContentPane().add(pancon);
		refresh();
	}
	private void refresh(){
		this.setVisible(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	private void setTitle(JPanel jp){
		jp.setOpaque(false);
		lab = new JLabel("     冰酒销售系统",JLabel.CENTER);
		lab.setFont(new java.awt.Font("宋体",1,30));
		jp.add(lab);
		
		lab = new JLabel("",JLabel.CENTER);
		lab.setFont(new java.awt.Font("宋体",1,13));
		if(Loadwin.use)	
			lab.setText("          欢迎您：admin");
		else			
			lab.setText("          欢迎您：staff");
		jp.add(lab);
		jp.setBounds(0, 5, 850, 50);
	}
	private void setButton(JPanel jp){
		jp.setBounds(35, 90, 100, 280);
		jp.setOpaque(false);
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		
		for(int i = 0;i < 6;i++){
			buttons[i] = new JButton(choose[i]);
			buttons[i].addActionListener(this);
			jp.add(buttons[i]);
			addNull(jp);
		}
		
		if(!Loadwin.use){
			for(int i=3;i<6;i++)
				buttons[i].setEnabled(false);
		}
	}
	private void setBottom(JPanel jp){
		jp.setLayout(new BoxLayout(jp,BoxLayout.X_AXIS));
		jp.setOpaque(false);
		jp.setBounds(0, 510, 780, 25);
		
		lab = new JLabel("",JLabel.LEFT);
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new Time(lab),new Date(), 1000);
		lab.setFont(new java.awt.Font("宋体",1,20));
		
		JButton buttmp = new JButton("退出");
		buttmp.addActionListener(this);
		buttmp.setPreferredSize(new Dimension(130,0));
		buttmp.setFont(new java.awt.Font("宋体",1,18));
		
		addNull(jp);
		jp.add(lab);
		addNull(jp);
		addNull(jp,3);
		jp.add(buttmp);
	}
	private void addNull(JPanel jp){
		JPanel tmp = new JPanel();
		tmp.setOpaque(false);
		jp.add(tmp);
	}
	private void addNull(JPanel jp,int num){
		JPanel tmp;
		if(num<=0) return;
		while(num > 0){
			num--;
			tmp = new JPanel();
			tmp.setOpaque(false);
			jp.add(tmp);
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		String order = e.getActionCommand();
		if(order.equals("退出"))
			System.exit(0);
		else if (order.equals(choose[0])) {
			panmain.removeAll();
			client = new Clientmain();
			client.setSize(panmain.getSize());
			panmain.add(client);
			statue_but(0);
		}
		else if (order.equals(choose[1])) {
			panmain.removeAll();
			ordermain = new Ordermain();
			ordermain.setSize(panmain.getSize());
			panmain.add(ordermain);
			statue_but(1);
		}
		else if (order.equals(choose[2])) {
			panmain.removeAll();
			goodsmain = new Goodsmain();
			goodsmain.setSize(panmain.getSize());
			panmain.add(goodsmain);
			statue_but(2);
		}
		else if (order.equals(choose[3])) {
			panmain.removeAll();
			staffmain = new Staffmain();
			staffmain.setSize(panmain.getSize());
			panmain.add(staffmain);
			statue_but(3);
		}
		else if (order.equals(choose[4])) {
			panmain.removeAll();
			accountmain = new Accountmain();
			accountmain.setSize(panmain.getSize());
			panmain.add(accountmain);
			statue_but(4);
		}
		else if(order.equals(choose[5])){
			statue_but(5);
			SaveFile();
			
			panmain.removeAll();
			JPanel tmporder = new JPanel();
			tmporder.setOpaque(false);
			tmporder.setLayout(new BoxLayout(tmporder,BoxLayout.Y_AXIS));
			addNull(tmporder,3);
			lab = new JLabel("        请在左侧选项中");
			lab.setFont(new java.awt.Font("宋体",1,40));
			lab.setForeground(new Color(155,100,50));
			tmporder.add(lab);
			addNull(tmporder);
			lab = new JLabel("        选择相应的操作。");
			lab.setFont(new java.awt.Font("宋体",1,40));
			lab.setForeground(new Color(155,100,50));
			tmporder.add(lab);
			addNull(tmporder,3);
			panmain.add(tmporder);
			
			statue_but(-1);
		}
//		else if (e.getSource() == news) {
//			int number = Integer.parseInt(news.getText().substring(news.getText().
//					indexOf("(")+1,news.getText().indexOf(")")));
//			if(number == 0)	return;
//
////			Detail_Order detail = new Detail_Order(tmp)
//			number -- ;
//			news.setText("新消息"+number);
//		}
	}
	
	private void statue_but(int index){
		for(int i=0;i<6;i++){
			if(i==index) continue;
			buttons[i].setEnabled(true);
		}
		if(index == -1) return;
		buttons[index].setEnabled(false);
		
		if(!Loadwin.use){
			for(int i=3;i<6;i++)
				buttons[i].setEnabled(false);
		}
	}

	//保存文件方法
	private void SaveFile(){
		JFileChooser chooser = new JFileChooser("c:\\");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件(*.txt)", ".txt");  
	    chooser.setFileFilter(filter); 
	    chooser.setDialogTitle("保存财务文件");
	    Calendar cal = Calendar.getInstance();
	    chooser.setSelectedFile(new File("财务文件("+(cal.get(Calendar.MONTH)+1)+"月)"));
		
		int option = chooser.showSaveDialog(null);  
		if(option == JFileChooser.APPROVE_OPTION){    //用户选择了保存  
	        File file = chooser.getSelectedFile();  		          
	        String filename = chooser.getName(file);   //从文件名输入框中获取文件名  

	        //用户填写的文件名不带我们制定的后缀名，添上后缀  
	        if(filename.indexOf(".txt")==-1)
	        	file=new File(chooser.getCurrentDirectory(),filename+".txt");
	        /*
	         * 保存文件操作
	         */
	        try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from Order_Head");
				int count = 0;
				while(rs.next())	count ++;
				Order_Head[] head = new Order_Head[count];
				
				rs = statement.executeQuery("select * from Order_Head");
				for(int i=0;i<count;i++){
					rs.next();
					boolean bool;
					if(rs.getString("delivery").equals("false"))	bool = false;
					else	bool = true;
					
					head[i] = new Order_Head(rs.getString("name"), rs.getString("ordernumber"), rs.
							getString("phonenumber"),rs.getString("address"),rs.getString("pickupdate")
							,bool,rs.getDouble("prices"),rs.getDouble("tax"), null);
				}
				
				BufferedWriter out =  new BufferedWriter(new OutputStreamWriter
						(new FileOutputStream(file)));
				out.write("订单号---顾客姓名---电话号码---总价---税率"+"\r\n");
				for(int i=0 ;i<count;i++)
					out.write(head[i].toFile()+"\r\n");
				out.flush();
				out.close();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}
}
