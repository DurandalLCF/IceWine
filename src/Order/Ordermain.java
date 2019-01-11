package Order;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import Entity_Relationship.Order_Head;
import Entity_Relationship.Product;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Ordermain extends JPanel implements ActionListener{
	private JTextField textField;
	private JTable table;
	private JButton addnew,check,checkmore,delete;
	private TableData data ;
	private JPanel panel;
	private JButton tax;
	private JLabel taxlabel;
	public static float tax_ = 10f;
	JPanel panel_main;
	JScrollPane scrollPane;
	
	public Ordermain() {
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(680, 400);
		
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setOpaque(false);
		panel_top.setBounds(0, 0, 680, 60);
		add(panel_top);
		
		addnew = new JButton("\u6DFB\u52A0\u65B0\u8BA2\u5355");
		addnew.setFont(new Font("����", Font.BOLD, 15));
		addnew.setBounds(30, 15, 150, 30);
		panel_top.add(addnew);
		
		textField = new JTextField();
		textField.setText("��������Ҫ��ѯ�Ķ�����");
		textField.setFont(new Font("����", Font.BOLD, 15));
		textField.setBounds(250, 18, 300, 30);
		textField.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().equals("��������Ҫ��ѯ�Ķ�����"))
					textField.setText("");
			}
			public void mouseExited(MouseEvent e) {
				if(textField.getText().equals(""))
					textField.setText("��������Ҫ��ѯ�Ķ�����");
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
		panel_top.add(textField);
		textField.setColumns(10);
		
		check = new JButton("\u67E5\u8BE2");
		check.setFont(new Font("����", Font.BOLD, 15));
		check.setBounds(567, 17, 100, 30);
		panel_top.add(check);
		
		panel_main = new JPanel();
		panel_main.setLayout(null);
		panel_main.setOpaque(false);
		panel_main.setBounds(0, 59, 680, 341);
		add(panel_main);
		
		setTable();
		
		checkmore = new JButton("\u67E5\u770B");
		checkmore.setFont(new Font("����", Font.BOLD, 18));
		checkmore.setBounds(392, 286, 100, 30);
		panel_main.add(checkmore);
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("����", Font.BOLD, 18));
		delete.setBounds(531, 286, 100, 30);
		panel_main.add(delete);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(0, 0, 680, 1);
		panel_main.add(panel);
		
		tax = new JButton("\u4FEE\u6539\u7A0E\u7387");
		tax.setFont(new Font("����", Font.BOLD, 15));
		tax.setBounds(41, 307, 100, 20);
		panel_main.add(tax);
		
		taxlabel = new JLabel("˰�ʣ�"+tax_+"%");
		taxlabel.setFont(new Font("����", Font.BOLD, 18));
		taxlabel.setBounds(41, 276, 126, 18);
		panel_main.add(taxlabel);
		
		addnew.addActionListener(this);
		check.addActionListener(this);
		checkmore.addActionListener(this);
		delete.addActionListener(this);
		tax.addActionListener(this);
	}
	void setTable(){
		data = new TableData();
		table = new JTable(data);
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(41, 13, 600, 250);
		panel_main.add(scrollPane);
	}
	
	class TableData extends AbstractTableModel{
		private Detail_Order check;
		private final String[] columnNames = { "ѡ��", "������", "�˿�����","�绰����", "��ַ" ,"�ܼ�" ,"˰��" ,"˰���ܼ�"};
		private Object[][] data = null;
		Order_Head[] head;

		public TableData() {
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from Order_Head");
				int count = 0;
				while(rs.next())	count ++;
				head = new Order_Head[count];
				Object[][] datatmp = new Object[count][];
				
				/*
				 * ���ػ�����Ϣ
				 */
				rs = statement.executeQuery("select * from Order_Head");

				for(int i=0;i<count;i++){
					rs.next();
					boolean bool;
					if(rs.getString("delivery").equals("false"))	bool = false;
					else	bool = true;
					
					head[i] = new Order_Head(rs.getString("name"), rs.getString("ordernumber"), rs.
							getString("phonenumber"),rs.getString("address"),rs.getString("pickupdate")
							,bool,rs.getDouble("prices"),rs.getDouble("tax"), null);
					datatmp[i] = head[i].toTabel();
				}
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
            if (col == 0) 	return true;
            return false;
        }
        // �ı�ĳ�����ݵ�ֵ
        public void setValueAt(Object value, int row, int col) { 
        	data[row][col] = value;
        	fireTableCellUpdated(row, col);
        }
        //�鿴����
        public void checkname() {
        	int i ;
			for(i = 0;i < getRowCount();i++)
				if(data[i][0].equals(new Boolean(true)))
					check = new Detail_Order(head[i]);
		}
        public void deletename(){
        	/*
        	 * ɾ����������
        	 */
        	try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				
				for(int i = 0;i < getRowCount();i++)
					if(data[i][0].equals(new Boolean(true))){	
						String sql  = "delete from Order_Head where ordernumber ="+data[i][1];
						statement.executeUpdate(sql);
						sql = "delete from Order_Detail where ordernumber ="+data[i][1];;
						statement.executeUpdate(sql);
					}
			} catch (Exception e2) {
				// TODO �Զ����ɵ� catch ��
				e2.printStackTrace();
			}
        }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addnew)	{
			Addnew_Order addnew = new Addnew_Order();
		}
		else if (e.getSource() == check) {
			//�ж��Ƿ���������
			if(textField.getText().equals("��������Ҫ��ѯ�Ķ�����") || textField.getText().equals(""))
				return ;
			Detail_Order detail_Order = new Detail_Order(textField.getText());
			textField.setText("��������Ҫ��ѯ�Ķ�����");
		}
		else if (e.getSource() == checkmore) {
			data.checkname();
		}
		else if (e.getSource() == delete) {
			data.deletename();
			panel_main.remove(scrollPane);
			setTable();
		}
		else if	(e.getSource() == tax) {
			boolean judge = true;
			String inputValue = "";
			while(judge){
				judge = false;
				inputValue = JOptionPane.showInputDialog(null,"�����뵱ǰ˰��:"
						,"˰��",JOptionPane.PLAIN_MESSAGE);
				if(inputValue == null || inputValue.length() == 0) return;
				for(int i=0;i<inputValue.length();i++)
					if(!(Character.isDigit(inputValue.charAt(i)) || inputValue.charAt(i) == '.')){
						judge = true;
						JOptionPane.showMessageDialog(null,"�������ݽ��������ֺ�С����",
								"����",JOptionPane.INFORMATION_MESSAGE);
						inputValue = "";
						break;
					}
			}
				
			taxlabel.setText("˰�ʣ�"+inputValue+"%");
			tax_ = Float.parseFloat(inputValue);
		}
	}
}
