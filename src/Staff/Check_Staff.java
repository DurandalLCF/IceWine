package Staff;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entity_Relationship.Staff;

import javax.swing.GroupLayout.Alignment;

public class Check_Staff extends JFrame implements ActionListener{
	private JTextField name;
	private JTextField phone;
	private JTextField address;
	private JTextField type;
	private JTextField staffID;
	private JLabel label;
	private JPanel panel_main;
	private JButton delete;
	private JButton modify;
	private String namestr;
	private JButton sure;
	
	public Check_Staff(String ID){
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///"
					+ "E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Staff");
			
			boolean exist = false;
			while(rs.next())
				if(rs.getString("staffID").equals(ID)){
					exist = true;
					Setall();
					
					name.setText(rs.getString("name"));
					phone.setText(rs.getString("phone"));
					address.setText(rs.getString("address"));
					type.setText(rs.getString("type"));
					staffID.setText(rs.getString("staffID"));
				}
			if(!exist){
				JOptionPane.showMessageDialog(null,"员工不存在。",
						"警告",JOptionPane.WARNING_MESSAGE);
				return ;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public Check_Staff(String namestr,String phonestr,String addr,String typestr,String ID){
		Setall();
		name.setText(namestr);
		phone.setText(phonestr);
		address.setText(addr);
		type.setText(typestr);
		staffID.setText(ID);
	}
	
	public void Setall() {		
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
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("宋体", Font.BOLD, 18));
		delete.setBounds(221, 255, 80, 40);
		panel_main.add(delete);
		
		modify = new JButton("\u4FEE\u6539");
		modify.setFont(new Font("宋体", Font.BOLD, 18));
		modify.setBounds(36, 255, 80, 40);
		panel_main.add(modify);
		
		label = new JLabel("\u5458\u5DE5\u5DE5\u53F7\uFF1A");
		label.setOpaque(false);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(15, 216, 110, 26);
		panel_main.add(label);
		
		staffID = new JTextField();
		staffID.setColumns(10);
		staffID.setBounds(122, 216, 179, 26);
		panel_main.add(staffID);
		
		sure = new JButton("\u786E\u8BA4");
		sure.setFont(new Font("宋体", Font.BOLD, 18));
		sure.setBounds(132, 255, 80, 40);
		panel_main.add(sure);
		
		input(false);
		staffID.setEditable(false);
		sure.setEnabled(false);
		sure.addActionListener(this);
		delete.addActionListener(this);
		modify.addActionListener(this);
	}

	private void input(boolean can){
		name.setEditable(can);
		phone.setEditable(can);
		address.setEditable(can);
		type.setEditable(can);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete){
			int value=JOptionPane.showConfirmDialog(this, "确认删除？", "提示",JOptionPane.YES_NO_OPTION); 
			if(value==JOptionPane.YES_OPTION){
				/*
				 * 删除操作（namestr）
				 */
				try {
					Class.forName("com.hxtt.sql.access.AccessDriver");
					Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
					Statement statement = connection.createStatement();
					String sql = "delete from Staff where staffID ='"+staffID.getText()+"'";
					statement.executeUpdate(sql);
				} catch (Exception e2) {
					// TODO 自动生成的 catch 块
					e2.printStackTrace();
				}
				dispose();
			}
		}
		else if(e.getSource() == sure){
			/*
			 * 提交新员工信息
			 */
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "update Staff set name ='"+name.getText()+"',phone ='"+
						phone.getText()+"',address ='"+ address.getText()+"',type ='"+
						type.getText()+"' where staffID ='"+staffID.getText()+"' ";
				statement.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,"更新成功。",
						"提示",JOptionPane.WARNING_MESSAGE);
				dispose();
			} catch (Exception e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			}  
			
		}
		else if (e.getSource() == modify) {
			input(true);
			sure.setEnabled(true);
			modify.setEnabled(false);
		}
	}
}