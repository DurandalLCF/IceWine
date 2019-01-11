package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.omg.PortableServer.ImplicitActivationPolicyOperations;

import Account.Check_Account;
import Entity_Relationship.Customer;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Clientmain extends JPanel implements ActionListener{
	private JTextField textField;
	private JTable table;
	private JButton addnew,check,checkmore,delete;
	private TableData data ;
	private JPanel panel;
	JScrollPane scrollPane;
	JPanel panel_main;
	
	public Clientmain() {
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(680, 400);
		
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setOpaque(false);
		panel_top.setBounds(0, 0, 680, 60);
		add(panel_top);
		
		addnew = new JButton("\u6DFB\u52A0\u65B0\u7528\u6237");
		addnew.setFont(new Font("����", Font.BOLD, 15));
		addnew.setBounds(30, 15, 150, 30);
		panel_top.add(addnew);
		
		textField = new JTextField();
		textField.setText("��������Ҫ��ѯ�Ĺ˿�����");
		textField.setFont(new Font("����", Font.BOLD, 15));
		textField.setBounds(250, 18, 300, 30);
		textField.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().equals("��������Ҫ��ѯ�Ĺ˿�����"))
					textField.setText("");
			}
			public void mouseExited(MouseEvent e) {
				if(textField.getText().equals(""))
					textField.setText("��������Ҫ��ѯ�Ĺ˿�����");
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
		
		settable();
		
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
		
		addnew.addActionListener(this);
		check.addActionListener(this);
		checkmore.addActionListener(this);
		delete.addActionListener(this);
	}
	void settable(){
		data = new TableData();
		table = new JTable(data);
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(41, 13, 600, 250);
		panel_main.add(scrollPane);
	}
	
	class TableData extends AbstractTableModel{
		private Check_Client check;
		private final String[] columnNames = { "ѡ��", "����", "�绰����","��ַ", "�˿�����" };
		private Object[][] data = null;

		public TableData() {
			Statement statement = null;
			ResultSet rs ;
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				statement = connection.createStatement();
			} catch (Exception e2) {
				// TODO �Զ����ɵ� catch ��
				e2.printStackTrace();
			}
			
			try {
				rs = statement.executeQuery("select * from Customer");
				int count = 0;
				while(rs.next())	count ++;
				Customer[] customers = new Customer[count];
				
				rs = statement.executeQuery("select * from Customer");

				for(int i=0;i<count;i++){
					rs.next();
					customers[i] = new Customer(rs.getString("name"), rs.getString("phonenumber"), 
							rs.getString("address"), rs.getString("type"));
				}
				
				Object[][] datatmp = new Object[count][];
				for(int i=0;i<count;i++)
					datatmp[i] = customers[i].toTabel();
				data = datatmp;
			} catch (SQLException e) {
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
        
        public void checkname() {
        	for(int i = 0;i < getRowCount();i++)
				if(data[i][0].equals(new Boolean(true)))
					check = new Check_Client(data[i][1].toString(),data[i][2].
							toString(),data[i][3].toString(),data[i][4].toString());
		}
        
        public void delete(){
        	/*
        	 * ɾ������
        	 */
        	try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "delete from Customer where name='";
				
				for(int i = 0;i < getRowCount();i++)
					if(data[i][0].equals(new Boolean(true))){
						sql = sql+data[i][1]+"'";
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
			Addnew_Client addnew = new Addnew_Client();
		}
		else if (e.getSource() == check) {
			//�ж��Ƿ���������
			if(textField.getText().equals("��������Ҫ��ѯ�Ĺ˿�����") || textField.getText().equals(""))
				return ;
			Check_Client check = new Check_Client(textField.getText());
			textField.setText("��������Ҫ��ѯ�Ĺ˿�����");
		}
		else if (e.getSource() == checkmore) {
			data.checkname();
		}
		else if (e.getSource() == delete) {
			data.delete();
			panel_main.remove(scrollPane);
			settable();
		}
	}
}
