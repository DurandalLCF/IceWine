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
		this.setTitle("��������ϵͳ");
		
		pancon = new JPanel(){
			//JPanel��ӱ���ͼƬ
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/check.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.53),
						(int)(icon.getIconHeight()*0.75),icon.getImageObserver());
			}
		};
		pancon.setLayout(null);
		
		//��ӱ���
		lab = new JLabel("\u6DFB\u52A0\u65B0\u8D26\u53F7",JLabel.CENTER);
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
		user = new JTextField("",10);
		tmp.add(lab);
		tmp.add(user);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("���룺");
		lab.setFont(new java.awt.Font("����",1,15));
		pass = new JTextField("",10);
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
		jp.setBounds(35, 180, 200, 44);
		jp.setOpaque(false);
		
		add = new JButton("���");
		add.addActionListener(this);
		close = new JButton("�ر�");
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
			 * �������ݲ���
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
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			dispose();
		}
	}
}
