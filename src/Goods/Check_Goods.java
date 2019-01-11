package Goods;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entity_Relationship.Product;

import java.awt.Font;

public class Check_Goods extends JFrame implements ActionListener {
	JButton modify,delete,sure;
	JTextField name,price,number;
	JLabel lab;
	ButtonGroup bg;
	JPanel pancon,panmain;
	private String namestr;
	
	public Check_Goods(String namestr){
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///"
					+ "E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Product");
			
			boolean exist = false;
			while(rs.next())
				if(rs.getString("productname").equals(namestr)){
					exist = true;
					Setall();
					
					name.setText(rs.getString("productname"));
					price.setText(rs.getString("price"));
					number.setText(rs.getString("amount"));
				}
			if(!exist){
				JOptionPane.showMessageDialog(null,"货物不存在。",
						"警告",JOptionPane.WARNING_MESSAGE);
				return ;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public Check_Goods(String proname,double pricestr,int amountstr){
		Setall();
		name.setText(proname);
		price.setText(""+pricestr);
		number.setText(""+amountstr);
	}
	public void Setall() {
		this.namestr = namestr;
		this.setBounds(0, 0, 270, 270);
		this.setResizable(false);
		this.setTitle("冰酒销售系统");
		
		pancon = new JPanel(){
			//JPanel添加背景图片
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/check.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*0.53),(int)(icon.getIconHeight()*0.75),icon.getImageObserver());
			}
		};
		pancon.setLayout(null);
		getContentPane().add(pancon, BorderLayout.CENTER);
		
		//添加标题
		lab = new JLabel("\u67E5\u770B\u8D27\u7269\u4FE1\u606F",JLabel.CENTER);
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
		jp.setBounds(35, 48, 200, 119);
		jp.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		
		JPanel tmp = new JPanel();
		lab = new JLabel("\u8D27\u7269\u540D\u5B57\uFF1A");
		lab.setFont(new java.awt.Font("宋体",1,15));
		name = new JTextField("",8);
		tmp.add(lab);
		tmp.add(name);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("\u8D27\u7269\u5355\u4EF7\uFF1A");
		lab.setFont(new java.awt.Font("宋体",1,15));
		price = new JTextField("",8);
		tmp.add(lab);
		tmp.add(price);
		jp.add(tmp);
		
		tmp = new JPanel();
		lab = new JLabel("\u8D27\u7269\u603B\u91CF\uFF1A");
		lab.setFont(new java.awt.Font("宋体",1,15));
		number = new JTextField("",8);
		number.addKeyListener(new KeyAdapter(){  
            public void keyTyped(KeyEvent e) {              
                if(!(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9))  
                    e.consume(); //关键，屏蔽掉非法输入  
            }  
        }); 
		tmp.add(lab);
		tmp.add(number);
		jp.add(tmp);
		
		name.setEditable(false);
		price.setEditable(false);
		number.setEditable(false);
	}
	//添加按钮
	private void button(JPanel jp){
		jp.setBounds(0, 180, 264, 44);
		jp.setOpaque(false);
		
		modify = new JButton("修改");
		modify.setFont(new Font("宋体", Font.BOLD, 18));
		modify.addActionListener(this);
		sure = new JButton("确认");
		sure.setFont(new Font("宋体", Font.BOLD, 18));
		sure.addActionListener(this);
		delete = new JButton("删除");
		delete.setFont(new Font("宋体", Font.BOLD, 18));
		delete.addActionListener(this);
		
		sure.setEnabled(false);
		jp.add(modify);
		jp.add(sure);
		jp.add(delete);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == modify){
			price.setEditable(true);
			number.setEditable(true);
			sure.setEnabled(true);
			modify.setEnabled(false);
		}
		else if(e.getSource() == sure){
			Product product = new Product(name.getText(), Double.parseDouble
					(price.getText()),Integer.parseInt(number.getText()));
			/*
			 * 提交订单操作
			 */
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "update Product set price ="+price.getText()+",amount ="+
						number.getText()+" where productname='"+name.getText()+"' ";
				statement.executeUpdate(sql);
				JOptionPane.showMessageDialog(null,"修改成功。",
						"提示",JOptionPane.WARNING_MESSAGE);
				dispose();
			} catch (Exception e2) {
				// TODO 自动生成的 catch 块
				JOptionPane.showMessageDialog(null,"修改失败。",
						"提示",JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource() == delete){
			int value=JOptionPane.showConfirmDialog(this, "确认删除？", "提示",JOptionPane.YES_NO_OPTION); 
			if(value==JOptionPane.YES_OPTION){
				/*
				 * 删除订单操作
				 */
				try {
					Class.forName("com.hxtt.sql.access.AccessDriver");
					Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
					Statement statement = connection.createStatement();
					String sql = "delete from Product where productname ='"+name.getText()+"'";
					statement.executeUpdate(sql);
					JOptionPane.showMessageDialog(null,"删除成功。",
							"提示",JOptionPane.WARNING_MESSAGE);
					dispose();
				} catch (Exception e2) {
					// TODO 自动生成的 catch 块
					JOptionPane.showMessageDialog(null,"删除失败。",
							"提示",JOptionPane.WARNING_MESSAGE);
					e2.printStackTrace();
				}	
			}
		}
	}
}
