package Account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import Entity_Relationship.Account;

import java.awt.Font;

public class Check_Account extends JFrame implements ActionListener{
	private JButton modify,delete;
	private JTextField user,pass;
	private JLabel lab;
	private JRadioButton admin,staff;
	private ButtonGroup bg;
	private JPanel pancon,panmain;
	private String account;
	private JButton sure;
	
//	public static void main(String[] arg){
//		javax.swing.SwingUtilities.invokeLater(new Runnable(){
//			public void run() {
//				Check_Account c = new Check_Account("");
//			}
//		});
//	}
	
	public Check_Account(String userstr) {
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///"
					+ "E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Account");
			
			boolean exist = false;
			while(rs.next())
				if(rs.getString("user").equals(userstr)){
					exist = true;
					Setall();
					
					user.setText(rs.getString("user"));
					pass.setText(rs.getString("password"));
					if(rs.getString("type").equals("admin"))	admin.setSelected(true);
					else 	staff.setSelected(true);
				}
			if(!exist){
				JOptionPane.showMessageDialog(null,"��ѯ�˺Ų����ڡ�",
						"����",JOptionPane.WARNING_MESSAGE);
				return ;
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	public Check_Account(String userstr,String passstr,String type) {
		// TODO �Զ����ɵĹ��캯�����
		Setall();
		user.setText(userstr);
		pass.setText(passstr);
		if(type.equals("admin"))	admin.setSelected(true);
		else 	staff.setSelected(true);
	}
	private void Setall(){
		this.setBounds(0, 0, 270, 270);
		this.setResizable(false);
		
		pancon = new JPanel(){
			//JPanel��ӱ���ͼƬ
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/check.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.53),(int)(icon.getIconHeight()*0.75),icon.getImageObserver());
			}
		};
		pancon.setLayout(null);
		
		//��ӱ���
		lab = new JLabel("�鿴�˺�����",JLabel.CENTER);
		lab.setOpaque(false);
		lab.setFont(new java.awt.Font("����",1,25));
		lab.setBounds(0, 5, 270, 30);
		pancon.add(lab);
		
		//�����д�Լ�ѡ������
		panmain = new JPanel();
		choose(panmain);
		pancon.add(panmain);
		
		//��Ӱ�ť
		panmain = new JPanel();
		button(panmain);
		pancon.add(panmain);
		
		this.getContentPane().add(pancon);
		this.setVisible(true);
		this.setLocationRelativeTo(null); 
	}
	//�����Ϣ���
	private void choose(JPanel jp){
		jp.setBounds(35, 40, 200, 130);
		jp.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		
		JPanel tmp = new JPanel();
		lab = new JLabel("�˺ţ�");
		lab.setFont(new java.awt.Font("����",1,15));
		user = new JTextField("�˺�",10);
		tmp.add(lab);
		tmp.add(user);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("���룺");
		lab.setFont(new java.awt.Font("����",1,15));
		pass = new JTextField("����",10);
		tmp.add(lab);
		tmp.add(pass);
		jp.add(tmp);
		
		lab = new JLabel("ʹ��Ȩ�ޣ�     ");
		lab.setFont(new java.awt.Font("����",1,15));
		jp.add(lab);
		
		tmp = new JPanel();
		admin = new JRadioButton("������");
		staff = new JRadioButton("Ա��");
		bg = new ButtonGroup();
		bg.add(admin);
		bg.add(staff);
		admin.setSelected(true);
		tmp.add(admin);
		tmp.add(staff);
		jp.add(tmp);
	}
	//��Ӱ�ť
	private void button(JPanel jp){
		jp.setBounds(0, 180, 270, 44);
		jp.setOpaque(false);
		
		modify = new JButton("�޸�");
		modify.setFont(new Font("����", Font.BOLD, 17));
		delete = new JButton("ɾ��");
		delete.setFont(new Font("����", Font.BOLD, 17));
		
		JPanel tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.add(modify);
		
		sure = new JButton("\u786E\u8BA4");
		sure.setFont(new Font("����", Font.BOLD, 17));
		tmp.add(sure);
		tmp.add(delete);
		jp.add(tmp);
		
		sure.addActionListener(this);
		modify.addActionListener(this);
		delete.addActionListener(this);
		sure.setEnabled(false);
		user.setEditable(false);
		pass.setEditable(false);
		admin.setEnabled(false);
		staff.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == modify){
			pass.setEditable(true);
			admin.setEnabled(true);
			staff.setEnabled(true);
			modify.setEnabled(false);
			sure.setEnabled(true);
		}
		else if (e.getSource() == delete) {
			int value=JOptionPane.showConfirmDialog(this, "ȷ��ɾ����", "��ʾ",JOptionPane.YES_NO_OPTION); 
			if(value==JOptionPane.YES_OPTION){
				/*
				 * ɾ���û�����
				 */
				try {
					Class.forName("com.hxtt.sql.access.AccessDriver");
					Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
					Statement statement = connection.createStatement();
					String sql = "delete from Account where user ='"+user.getText()+"'";
					statement.executeUpdate(sql);
				} catch (Exception e2) {
					// TODO �Զ����ɵ� catch ��
					e2.printStackTrace();
				}		
				dispose();
			}
		}
		else if(e.getSource() == sure){
			String type;
			if(admin.isSelected())	type = "admin";
			else type = "staff";
			/*
			 * �޸����ݿ�����
			 */
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "update Account set password ='"+pass.getText()+"',type ='"+type
						+"' where user ='"+user.getText()+"' ";
				statement.executeUpdate(sql);
			} catch (Exception e2) {
				// TODO �Զ����ɵ� catch ��
				e2.printStackTrace();
			}
			dispose();
		}
	}
}
