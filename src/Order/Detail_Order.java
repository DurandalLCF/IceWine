package Order;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import Client.Check_Client;
import Entity_Relationship.Order_Detail;
import Entity_Relationship.Order_Head;

import javax.swing.JTable;
import javax.swing.JButton;

public class Detail_Order extends JFrame implements ActionListener{
	JPanel panel_main;
	JLabel label;
	TableData data;
	private JLabel name,price,tax,address,time,ordernum,phone,allprice;
	private JTable table;
	private JButton delete;
	String ordernumstr;
	JScrollPane jScrollPane;
	
	public Detail_Order(String orderstr) {
		ordernumstr = orderstr;
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			Connection connection = DriverManager.getConnection("jdbc:Access:///"
					+ "E:\\database\\IceWine.mdb");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Order_Head");
			
			boolean exist = false;
			while(rs.next())
				if(rs.getString("ordernumber").equals(orderstr)){
					exist = true;
					Setall();
					
					name.setText("�˿�������"+rs.getString("name"));
					phone.setText("�˿͵绰��"+rs.getString("phonenumber"));
					ordernum.setText("�����ţ�"+rs.getString("ordernumber"));
					price.setText("�ܼۣ�"+rs.getDouble("prices"));
					tax.setText("˰�գ�"+rs.getDouble("tax"));
					
					if(rs.getString("delivery").equals("false"))	address.setText("�ͻ���ַ�������ͻ�");
					else	address.setText("�ͻ���ַ��"+rs.getString("address"));
					time.setText("ȡ��ʱ��:"+rs.getString("pickupdate"));
					allprice.setText("˰���ܼۣ�"+(rs.getDouble("prices")+rs.getDouble("tax")));
					setTable();
				}
			if(!exist){
				JOptionPane.showMessageDialog(null,"���������ڡ�",
						"����",JOptionPane.WARNING_MESSAGE);
				return ;
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public Detail_Order(Order_Head tmp){
		ordernumstr = tmp.getorder();
		Setall();
		
		name.setText("�˿�������"+tmp.getname());
		phone.setText("�˿͵绰��"+tmp.getphone());
		ordernum.setText("�����ţ�"+tmp.getorder());
		price.setText("�ܼۣ�"+tmp.getprices());
		tax.setText("˰�գ�"+tmp.gettax());
		if(tmp.deliveryornot())	address.setText("�ͻ���ַ��"+tmp.getaddress());
		else	address.setText("�ͻ���ַ�������ͻ�");
		time.setText("ȡ��ʱ��:"+tmp.getdate());
		allprice.setText("˰���ܼۣ�"+(tmp.getprices()+tmp.gettax()));
		setTable();
	}
	public Detail_Order(String order,String namestr,String phonestr,String addr,
			String date,boolean delivery,double pricestr,double taxstr) {
		// TODO �Զ����ɵĹ��캯�����
		ordernumstr = order;
		Setall();
		
		name.setText("�˿�������"+namestr);
		phone.setText("�˿͵绰��"+phonestr);
		ordernum.setText("�����ţ�"+order);
		price.setText("�ܼۣ�"+pricestr);
		tax.setText("˰�գ�"+taxstr);
		if(delivery)	address.setText("�ͻ���ַ��"+addr);
		else	address.setText("�ͻ���ַ�������ͻ�");
		time.setText("ȡ��ʱ��:"+date);
		allprice.setText("˰���ܼۣ�"+(pricestr+taxstr));
		setTable();
	}
	public void Setall() {
		/*
		 * ��ѯ����Ķ������Ƿ�������ݿ��У����û��ֱ�������ʾ�������ô���
		 */
		this.setTitle("��������ϵͳ");
		this.setVisible(true);
		this.setSize(490, 415);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		panel_main = new JPanel(){
			//JPanel��ӱ���ͼƬ
			protected void paintComponent(Graphics g){
				ImageIcon icon = new ImageIcon("./image/ordercheck.jpg");
				g.drawImage(icon.getImage(), 0, 0, (int)(icon.getIconWidth()*1.47),
						(int)(icon.getIconHeight()*1.2),icon.getImageObserver());
			}
		};
		panel_main.setLayout(null);
		getContentPane().add(panel_main, BorderLayout.CENTER);
		
		label = new JLabel("\u8BA2\u5355\u7EC6\u8282");
		label.setFont(new Font("����", Font.BOLD, 25));
		label.setBounds(10, 10, 141, 30);
		panel_main.add(label);
		
		name = new JLabel("\u987E\u5BA2\u59D3\u540D\uFF1A");
		name.setFont(new Font("����", Font.BOLD, 18));
		name.setBounds(30, 79, 423, 20);
		panel_main.add(name);
		
		phone = new JLabel("\u987E\u5BA2\u7535\u8BDD\uFF1A");
		phone.setFont(new Font("����", Font.BOLD, 18));
		phone.setBounds(30, 101, 423, 20);
		panel_main.add(phone);
		
		ordernum = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		ordernum.setFont(new Font("����", Font.BOLD, 15));
		ordernum.setBounds(51, 36, 402, 30);
		panel_main.add(ordernum);
		
		price = new JLabel("\u603B\u4EF7\uFF1A");
		price.setFont(new Font("����", Font.BOLD, 18));
		price.setBounds(30, 247, 188, 20);
		panel_main.add(price);
		
		tax = new JLabel("\u7A0E\u6536\uFF1A");
		tax.setFont(new Font("����", Font.BOLD, 18));
		tax.setBounds(30, 280, 188, 20);
		panel_main.add(tax);
		
		address = new JLabel("\u9001\u8D27\u5730\u5740\uFF1A");
		address.setFont(new Font("����", Font.BOLD, 18));
		address.setBounds(258, 247, 226, 20);
		panel_main.add(address);
		
		time = new JLabel("\u53D6\u8D27\u65F6\u95F4\uFF1A");
		time.setFont(new Font("����", Font.BOLD, 18));
		time.setBounds(258, 280, 226, 20);
		panel_main.add(time);
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("����", Font.BOLD, 20));
		delete.setBounds(340, 339, 113, 30);
		panel_main.add(delete);
		
		allprice = new JLabel("\u7A0E\u540E\u603B\u4EF7\uFF1A");
		allprice.setFont(new Font("����", Font.BOLD, 18));
		allprice.setBounds(30, 310, 188, 20);
		panel_main.add(allprice);
		
		delete.addActionListener(this);
	}
	private void setTable(){
		data = new TableData(ordernumstr);
		table = new JTable(data);
		jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(30, 134, 430, 100);
		panel_main.add(jScrollPane);
	}
	
	class TableData extends AbstractTableModel{
		private final String[] columnNames = {"��Ʒ����", "���ۣ�RMB��","����", "�ܼۣ�RMB��","˰�գ�RMB��"};
		private Object[][] data = null;
		private String ordernum;

		public TableData(String order) {
			ordernum = order;
			/*
			 * ��ѯ������Ϊordernum�����ж���ϸ��
			 */
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from Order_Detail");
				
				int count = 0;
				int lenght = order.length();
				while(rs.next())
					if(rs.getString("ordernumber").substring(0,lenght).equals(order))
						count ++;
				Order_Detail[] details = new Order_Detail[count];
				Object[][] datatmp = new Object[count][];
				
				/*
				 * ���ػ�����Ϣ
				 */
				rs = statement.executeQuery("select * from Order_Detail");

				int i=0;
				while(rs.next()){
					if(rs.getString("ordernumber").substring(0,lenght).equals(order)){
						details[i] = new Order_Detail(rs.getString("productname"),order, rs.getDouble("tax"),
								rs.getDouble("price"),rs.getDouble("totalprice"), rs.getInt("amount"));
						datatmp[i] = details[i].toTabelshow();
						i++;
					}
				}
				if(count == 0)	datatmp = null;
				data = datatmp;
			} catch (Exception e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	
		// ����е���Ŀ
        public int getColumnCount() {
            return columnNames.length;
        }
        // ����е���Ŀ
        public int getRowCount() {
        	int row;
        	try{
        		row = data.length;
        	}catch (NullPointerException e) {
				row = 0;
			}
            return row;
        }
        // ���ĳ�е����֣���Ŀǰ���е����ֱ������ַ�������columnNames��
        public String getColumnName(int col) {
            return columnNames[col];
        }
        // ���ĳ��ĳ�е����ݣ������ݱ����ڶ�������data��
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        // �ж�ÿ����Ԫ�������
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        // ���������Ϊ�ɱ༭��
        public boolean isCellEditable(int row, int col) {
            return false;
        }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete){
			/*
			 * ɾ����Ӧ�����ŵ�������Ϣ
			 */
			int value=JOptionPane.showConfirmDialog(this, "ȷ��ɾ����", "��ʾ",JOptionPane.YES_NO_OPTION); 
			if(value==JOptionPane.YES_OPTION){
				/*
				 * ɾ���û�����
				 */
				try {
					Class.forName("com.hxtt.sql.access.AccessDriver");
					Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
					Statement statement = connection.createStatement();
					
					String sql = "delete from Order_Head where ordernumber ='"+ordernumstr+"'";
					statement.executeUpdate(sql);
					sql = "delete from Order_Detail where ordernumber ='"+ordernumstr+"'";
					statement.executeUpdate(sql);
					JOptionPane.showMessageDialog(null,"ɾ���ɹ�",
							"���",JOptionPane.WARNING_MESSAGE);
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,"ɾ��ʧ��",
							"����",JOptionPane.WARNING_MESSAGE);
					e2.printStackTrace();
				}		
			}
		}
	}
}
