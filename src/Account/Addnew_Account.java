package Account;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.*;

import Entity_Relationship.Account;

public class Addnew_Account extends JFrame implements ActionListener{
	JButton add,close;
	JTextField user,pass;
	JLabel lab;
	JRadioButton admin,staff;
	ButtonGroup bg;
	JPanel pancon,panmain;
	
	Addnew_Account(){
		this.setBounds(0, 0, 270, 270);
		this.setResizable(false);
		this.setTitle("冰酒销售系统");
		
		pancon = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/check.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.53),
						(int)(icon.getIconHeight()*0.75),icon.getImageObserver());
			}
		};
		pancon.setLayout(null);
		
		//添加标题
		lab = new JLabel("\u6DFB\u52A0\u65B0\u8D26\u53F7",JLabel.CENTER);
		lab.setOpaque(false);
		lab.setFont(new java.awt.Font("宋体",1,25));
		lab.setBounds(0, 5, 270, 30);
		pancon.add(lab);
		
		//添加填写以及选项内容
		panmain = new JPanel();
		choose(panmain);
		pancon.add(panmain);
		
		//添加按钮
		panmain = new JPanel();
		button(panmain);
		pancon.add(panmain);
		
		this.getContentPane().add(pancon);
		this.setVisible(true);
		this.setLocationRelativeTo(null); 
	}
	//添加信息面板
	private void choose(JPanel jp){
		jp.setBounds(35, 40, 200, 130);
		jp.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		
		JPanel tmp = new JPanel();
		lab = new JLabel("账号：");
		lab.setFont(new java.awt.Font("宋体",1,15));
		user = new JTextField("",10);
		tmp.add(lab);
		tmp.add(user);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("密码：");
		lab.setFont(new java.awt.Font("宋体",1,15));
		pass = new JTextField("",10);
		tmp.add(lab);
		tmp.add(pass);
		jp.add(tmp);
		
		lab = new JLabel("使用权限：     ");
		lab.setFont(new java.awt.Font("宋体",1,15));
		jp.add(lab);
		
		tmp = new JPanel();
		admin = new JRadioButton("管理者");
		staff = new JRadioButton("员工");
		bg = new ButtonGroup();
		bg.add(admin);
		bg.add(staff);
		admin.setSelected(true);
		tmp.add(admin);
		tmp.add(staff);
		jp.add(tmp);
	}
	//添加按钮
	private void button(JPanel jp){
		jp.setBounds(35, 180, 200, 44);
		jp.setOpaque(false);
		
		add = new JButton("添加");
		add.addActionListener(this);
		close = new JButton("关闭");
		close.addActionListener(this);
		
		jp.add(add);
		jp.add(close);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == close){
			this.dispose();
		}
		else if (e.getSource() == add) {
			String type;
			if(admin.isSelected())	type = "admin";
			else type = "staff";
			/*
			 * 保存数据操作
			 */
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///"
						+ "E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "insert into Account values('"+user.getText()+"','"
						+pass.getText()+"','"+type+"')";
				statement.execute(sql);
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			dispose();
		}
	}
}
