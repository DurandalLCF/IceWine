package Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import Entity_Relationship.Customer;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Check_Client extends JFrame implements ActionListener{
	private JTextField nametext;
	private JTextField phonetext;
	private JTextField addrtext;
	private JRadioButton NOMchoose,VIPchoose;
	private JLabel label;
	private JPanel panel;
	private JButton delete;	
	private JButton sure;
	private JButton modify;
	
	private String name;
	
	public static void main(String[] arg){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Check_Client check = new Check_Client("");
			}
		});
	}
	public Check_Client(String namestr){
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///"
					+ "E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Customer");
			
			boolean exist = false;
			while(rs.next())
				if(!(rs.getString("name").indexOf(namestr) == -1)){
					exist = true;
					Setall();
					
					nametext.setText(rs.getString("name"));
					phonetext.setText(rs.getString("phonenumber"));
					addrtext.setText(rs.getString("address"));
					if(rs.getString("type").equals("NOM"))	NOMchoose.setSelected(true);
					else VIPchoose.setSelected(true);
				}
			if(!exist){
				JOptionPane.showMessageDialog(null,"用户不存在。",
						"警告",JOptionPane.WARNING_MESSAGE);
				return ;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public Check_Client(String name,String phone,String address,String type){
		Setall();
		nametext.setText(name);
		phonetext.setText(phone);
		addrtext.setText(address);
		if(type.equals("VIP"))	VIPchoose.setSelected(true);
		else NOMchoose.setSelected(true);
	}
	private void Setall() {		
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
		
		label = new JLabel("\u67E5\u770B\u7528\u6237\u4FE1\u606F");
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
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("宋体", Font.BOLD, 15));
		delete.setBounds(215, 240, 66, 27);
		panel.add(delete);
		
		sure = new JButton("\u786E\u8BA4");
		sure.setFont(new Font("宋体", Font.BOLD, 15));
		sure.setBounds(135, 240, 66, 27);
		panel.add(sure);
		
		modify = new JButton("\u4FEE\u6539");
		modify.setFont(new Font("宋体", Font.BOLD, 15));
		modify.setBounds(55, 240, 66, 27);
		panel.add(modify);
		
		delete.addActionListener(this);
		modify.addActionListener(this);
		sure.addActionListener(this);
		sure.setEnabled(false);
		nametext.setEditable(false);
		statue(false);
	}
	
	private void statue(boolean can) {
		phonetext.setEditable(can);
		addrtext.setEditable(can);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete){
			int value=JOptionPane.showConfirmDialog(this, "确认删除？", "提示",JOptionPane.YES_NO_OPTION); 
			if(value==JOptionPane.YES_OPTION){
				/*
				 * 删除操作
				 */
				try {
					Class.forName("com.hxtt.sql.access.AccessDriver");
					Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
					Statement statement = connection.createStatement();
					String sql = "delete from Customer where name ='"+nametext.getText()+"'";
					statement.executeUpdate(sql);
					JOptionPane.showMessageDialog(null,"删除成功。",
							"提示",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,"删除失败。",
							"提示",JOptionPane.INFORMATION_MESSAGE);
				}	
			}
		}
		else if(e.getSource() == sure){
			String type;
			if(VIPchoose.isSelected())	type = "VIP";
			else type = "NOM";
			/*
			 * 修改数据库数据
			 */
			String name = nametext.getText();
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "update Customer set phonenumber='"+phonetext.getText()+"',address='"
						+addrtext.getText()+"',type='"+type+"' where name='"+name+"'";
				statement.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,"修改成功。",
						"提示",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} catch (Exception e2) {
				// TODO 自动生成的 catch 块
				JOptionPane.showMessageDialog(null,"修改失败。",
						"提示",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource() == modify){
			statue(true);
			sure.setEnabled(true);
			modify.setEnabled(false);
		}
	}
}
