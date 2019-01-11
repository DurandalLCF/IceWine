package Client;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Entity_Relationship.Customer;

public class Addnew_Client extends JFrame implements ActionListener{
	private JTextField nametext;
	private JTextField phonetext;
	private JTextField addrtext;
	private JRadioButton NOMchoose,VIPchoose;
	private JLabel label;
	private JPanel panel;
	private JButton button;
	
	public static void main(String[] arg){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Addnew_Client addnew = new Addnew_Client();
			}
		});
	}
	public Addnew_Client() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(310, 320);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		panel = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/clicheck.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.45),(int)(icon.getIconHeight()*0.36),icon.getImageObserver());
			}
		};
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("\u6DFB\u52A0\u65B0\u7528\u6237\u4FE1\u606F");
		label.setOpaque(false);
		label.setSize(186, 35);
		label.setLocation(10, 10);
		label.setFont(new Font("宋体", 1, 25));
		panel.add(label);
		
		label = new JLabel("\u59D3\u540D\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", 1, 20));
		label.setBounds(20, 58, 77, 25);
		panel.add(label);
		
		nametext = new JTextField();
		nametext.setBounds(81, 58, 200, 25);
		panel.add(nametext);
		nametext.setColumns(10);
		
		label = new JLabel("\u7535\u8BDD\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", 1, 20));
		label.setBounds(20, 99, 77, 25);
		panel.add(label);
		
		phonetext = new JTextField();
		phonetext.setColumns(10);
		phonetext.setBounds(81, 99, 200, 25);
		panel.add(phonetext);
		
		addrtext = new JTextField();
		addrtext.setColumns(10);
		addrtext.setBounds(81, 137, 200, 25);
		panel.add(addrtext);
		
		label = new JLabel("\u5730\u5740\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", 1, 20));
		label.setBounds(20, 137, 77, 25);
		panel.add(label);
		
		label = new JLabel("\u7528\u6237\u7C7B\u578B\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", 1, 20));
		label.setBounds(20, 186, 116, 25);
		panel.add(label);
		
		VIPchoose = new JRadioButton("VIP");
		VIPchoose.setFont(new Font("宋体", Font.BOLD, 17));
		VIPchoose.setOpaque(false);
		VIPchoose.setSelected(true);
		VIPchoose.setBounds(118, 187, 60, 27);
		NOMchoose = new JRadioButton("NOM");
		NOMchoose.setFont(new Font("宋体", Font.BOLD, 17));
		NOMchoose.setOpaque(false);
		NOMchoose.setBounds(184, 187, 66, 27);
		ButtonGroup bg = new ButtonGroup();
		bg.add(VIPchoose);
		bg.add(NOMchoose);
		panel.add(VIPchoose);
		panel.add(NOMchoose);
		
		button = new JButton("\u6DFB\u52A0");
		button.setFont(new Font("宋体", Font.BOLD, 15));
		button.setBounds(200, 240, 86, 27);
		panel.add(button);
		
		button.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button){
			String type;
			if(NOMchoose.isSelected())	type = "NOM";
			else type = "VIP";

			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///"
						+ "E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "insert into Customer values('"+nametext.getText()+"','"
						+phonetext.getText()+"','"+addrtext.getText()+"','"+type+"')";
				statement.execute(sql);
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			dispose();
		}
	}
}
