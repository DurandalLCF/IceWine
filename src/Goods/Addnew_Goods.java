package Goods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.border.LineBorder;

import Entity_Relationship.Product;

import java.awt.Color;

public class Addnew_Goods extends JFrame implements ActionListener{
	JButton add,close;
	JTextField name,price,number;
	JLabel lab;
	ButtonGroup bg;
	JPanel pancon,panmain;
	
	public Addnew_Goods() {
		this.setBounds(0, 0, 270, 270);
		this.setResizable(false);
		this.setTitle("��������ϵͳ");
		
		pancon = new JPanel(){
			//JPanel��ӱ���ͼƬ
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/check.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.53),(int)(icon.getIconHeight()*0.75),icon.getImageObserver());
			}
		};
		pancon.setLayout(null);
		getContentPane().add(pancon, BorderLayout.CENTER);
		
		//��ӱ���
		lab = new JLabel("\u6DFB\u52A0\u65B0\u8D27\u7269",JLabel.CENTER);
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
		lab = new JLabel("\u8D27\u7269\u540D\u5B57\uFF1A");
		lab.setFont(new java.awt.Font("����",1,15));
		name = new JTextField("",8);
		tmp.add(lab);
		tmp.add(name);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("\u8D27\u7269\u5355\u4EF7\uFF1A");
		lab.setFont(new java.awt.Font("����",1,15));
		price = new JTextField("",8);
		tmp.add(lab);
		tmp.add(price);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("\u8D27\u7269\u603B\u91CF\uFF1A");
		lab.setFont(new java.awt.Font("����",1,15));
		number = new JTextField("",8);
		number.addKeyListener(new KeyAdapter(){  
            public void keyTyped(KeyEvent e) {              
                if(!(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9))  
                    e.consume(); //�ؼ������ε��Ƿ�����  
            }  
        }); 
		tmp.add(lab);
		tmp.add(number);
		jp.add(tmp);
	}
	//��Ӱ�ť
	private void button(JPanel jp){
		jp.setBounds(35, 180, 200, 44);
		jp.setOpaque(false);
		
		add = new JButton("���");
		add.setFont(new Font("����", Font.BOLD, 18));
		add.addActionListener(this);
		close = new JButton("�ر�");
		close.setFont(new Font("����", Font.BOLD, 18));
		close.addActionListener(this);
		
		jp.add(add);
		jp.add(close);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == close){
			this.dispose();
		}
		else if (e.getSource() == add) {
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///"
						+ "E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "insert into Product values('"+name.getText()+"',"+
						price.getText()+","+number.getText()+")";
				statement.execute(sql);
			} catch (Exception e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			dispose();
		}
	}
}
