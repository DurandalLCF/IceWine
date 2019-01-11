package Staff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import Entity_Relationship.Staff;

import javax.swing.JRadioButton;

public class Addnew_Staff extends JFrame implements ActionListener{
	private JTextField name;
	private JTextField phone;
	private JTextField address;
	private JTextField type;
	private JButton addnew;
	private JLabel label;
	private JPanel panel_main;
	private JTextField staffID;
	
	public static void main(String[] arg) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Addnew_Staff addnew_Staff = new Addnew_Staff();
			}
		});
	}
	public Addnew_Staff() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(330, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		panel_main = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/clicheck.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.45),(int)(icon.getIconHeight()*0.36),icon.getImageObserver());
			}
		};
		getContentPane().add(panel_main);
		panel_main.setLayout(null);
		
		label = new JLabel("\u6DFB\u52A0\u5458\u5DE5\u4FE1\u606F");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 25));
		label.setBounds(15, 15, 176, 30);
		panel_main.add(label);
				
		label = new JLabel("\u59D3\u540D\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(15, 58, 80, 26);
		panel_main.add(label);
		
		name = new JTextField();
		name.setBounds(92, 58, 209, 26);
		panel_main.add(name);
		name.setColumns(10);
		
		label = new JLabel("\u7535\u8BDD\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(15, 98, 80, 26);
		panel_main.add(label);

		phone = new JTextField();
		phone.setBounds(92, 98, 209, 26);
		panel_main.add(phone);
		phone.setColumns(10);
		
		label = new JLabel("\u5730\u5740\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(15, 137, 80, 26);
		panel_main.add(label);
		
		address = new JTextField();
		address.setBounds(92, 137, 209, 26);
		panel_main.add(address);
		address.setColumns(10);
		
		label = new JLabel("\u5458\u5DE5\u7C7B\u578B\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(15, 176, 110, 26);
		panel_main.add(label);
		
		type = new JTextField();
		type.setBounds(122, 176, 179, 26);
		panel_main.add(type);
		type.setColumns(10);
		
		addnew = new JButton("\u6DFB\u52A0");
		addnew.setFont(new Font("宋体", Font.BOLD, 20));
		addnew.setBounds(197, 264, 104, 40);
		panel_main.add(addnew);		
		
		label = new JLabel("\u5458\u5DE5\u5DE5\u53F7\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(15, 215, 110, 26);
		panel_main.add(label);
		
		staffID = new JTextField();
		staffID.setColumns(10);
		staffID.setBounds(122, 215, 179, 26);
		panel_main.add(staffID);
		
		addnew.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addnew){
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///"
						+ "E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "insert into Staff values('"+name.getText()+"','"+staffID.getText()
						+"','"+phone.getText()+"','"+address.getText()+"','"+type.getText()+"')";
				statement.execute(sql);
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			dispose();
		}
	}

}
