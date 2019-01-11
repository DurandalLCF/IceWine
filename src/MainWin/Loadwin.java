package MainWin;

import Other.Time;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Loadwin extends JFrame implements ActionListener {
	JLabel lab,timelab;
	JTextField user;
	JPasswordField password;
	JButton sure,exit;
	JPanel pancon,pan,panone,pantwo,panthree;
	private int ONE_SECOND = 1000;
	public static boolean use = true;//false Staff；true Manager
	
	public static void main(String[] arg){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Loadwin loadwin = new Loadwin();
			}    
		});
	}
	//设置控件透明setOpaque(false);
	Loadwin(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 550, 310);
		this.setResizable(false);
		this.setTitle("冰酒销售系统");
		
		//设置背景
		pancon = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/load.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.45),(int)(icon.getIconHeight()*0.28),icon.getImageObserver());
			}
		};
		pancon.setLayout(new BorderLayout(5,20));
		
		
		//添加标题(NORTH位置添加）
		panone = new JPanel();
		panone.setOpaque(false);//设置控件透明
		lab = new JLabel("冰酒销售系统登录确认",JLabel.CENTER);
		lab.setFont(new java.awt.Font("宋体",1,25));
		lab.setOpaque(false);
		panone.add(lab);
		pancon.add(lab,BorderLayout.NORTH);
		
		//pan（JPanel）用于添加中间（centre）
		pan = new JPanel();
		pan.setOpaque(false);
		pan.setLayout(new BoxLayout(pan,BoxLayout.Y_AXIS));
		//添加登录信息框
		panone = new JPanel();
		panone.setOpaque(false);
		pantwo = new JPanel();
		pantwo.setLayout(new BoxLayout(pantwo,BoxLayout.Y_AXIS));
		pantwo.setOpaque(false);
		pantwo.setPreferredSize(new Dimension(380, 125));
		//设置边框字体
		pantwo.setBorder(BorderFactory.createTitledBorder(null,"登录信息",0,0,new java.awt.Font("宋体",1,18), Color.BLACK));
		addNull(pantwo);
		//添加用户输入框
		panthree = new JPanel();
		panthree.setOpaque(false);
		lab = new JLabel("用户：");
		user = new JTextField("请输入用户名",20);
		user.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(user.getText().equals("请输入用户名"))
					user.setText("");
			}
			public void mouseExited(MouseEvent e) {
				if(user.getText().equals(""))
					user.setText("请输入用户名");
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
		panthree.add(lab);
		panthree.add(user);
		pantwo.add(panthree);
		//添加密码输入框
		panthree = new JPanel();
		panthree.setOpaque(false);
		lab = new JLabel("密码：");
		password = new JPasswordField("",20);
		panthree.add(lab);
		panthree.add(password);
		pantwo.add(panthree);
		//添加登录框
		panone.add(pantwo);
		pan.add(panone);
		
		//添加登录和退出按钮
		sure = new JButton("登录");
		exit = new JButton("退出");
		sure.addActionListener(this);
		exit.addActionListener(this);
		panone = new JPanel();
		panone.setOpaque(false);
		panone.setLayout(new BoxLayout(panone,BoxLayout.X_AXIS));
		
		//调节控件位置(start)
		for(int i=1;i<=6;i++){
			pantwo = new JPanel();
			pantwo.setOpaque(false);
			panone.add(pantwo);
		}	
		panone.add(sure);
		pantwo = new JPanel();
		pantwo.setOpaque(false);
		panone.add(pantwo);
		panone.add(exit);
		for(int i=1;i<=2;i++){
			pantwo = new JPanel();
			pantwo.setOpaque(false);
			panone.add(pantwo);
		}
		pan.add(panone);
		//调节控件位置(end)
		//添加内容（centre）
		pancon.add(pan,BorderLayout.CENTER);
		
		//添加时间（South）
		timelab = new JLabel("",JLabel.CENTER);
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new Time(timelab),new Date(), ONE_SECOND);
		timelab.setFont(new java.awt.Font("宋体",1,15));
		pancon.add(timelab, BorderLayout.SOUTH);
		//添加内容到窗口中
		this.getContentPane().add(pancon);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null); 
	}

	//添加一个透明JPanel（用于调节面板）
	private void addNull(JPanel jp){
		JPanel tmp = new JPanel();
		tmp.setOpaque(false);
		jp.add(tmp);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sure){
			Statement statement = null;
			ResultSet rs ;
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				statement = connection.createStatement();
			} catch (Exception e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			}
			
			String namestr = user.getText();
			String passstr = new String(password.getPassword());
			
			String query = "select * from Account";
			try {
				rs =  statement.executeQuery(query);
				while(rs.next()){
					if(namestr.equals(rs.getString("user")) && 
							passstr.equals(rs.getString("password"))){
						if(rs.getString("type").equals("admin"))	use = true;
						else 	use = false;
						Menuwin menuwin = new Menuwin();
						this.dispose();
						return;
					}
				}
				JOptionPane.showMessageDialog(null,"账号或密码错误，请重新输入。",
						"警告",JOptionPane.WARNING_MESSAGE);
				user.setText("");
				password.setText("");
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == exit){
			System.exit(0);
		}
	}
}
