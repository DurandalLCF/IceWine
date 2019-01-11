package Goods;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import Entity_Relationship.Product;


public class Goodsmain extends JPanel implements ActionListener{
	private TableData data ;
	private JTable table;
	private JButton addnew,modify,delete;
	JScrollPane scrollPane;
	
	public Goodsmain() {
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(680, 400);
		
		Settable();
		
		addnew = new JButton("\u6DFB\u52A0");
		addnew.setFont(new Font("����", Font.BOLD, 20));
		addnew.setBounds(276, 320, 90, 40);
		addnew.addActionListener(this);
		add(addnew);
		
		modify = new JButton("\u67E5\u770B/\u4FEE\u6539");
		modify.setFont(new Font("����", Font.BOLD, 20));
		modify.setBounds(384, 320, 136, 40);
		modify.addActionListener(this);
		add(modify);
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("����", Font.BOLD, 20));
		delete.setBounds(540, 320, 90, 40);
		delete.addActionListener(this);
		add(delete);
		
	}
	private void Settable(){
		data = new TableData();
		table = new JTable(data);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(64, 35, 550, 230);
		add(scrollPane);
	}
	
	class TableData extends AbstractTableModel{
		private Check_Goods check;
		private final String[] columnNames = { "ѡ��", "��Ʒ����", "��Ʒ����","��Ʒ����"};
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
				rs = statement.executeQuery("select * from Product");
				int count = 0;
				while(rs.next())	count ++;
				Product[] products = new Product[count];
				
				rs = statement.executeQuery("select * from Product");
				for(int i=0;i<count;i++){
					rs.next();
					products[i] = new Product(rs.getString("productname"), 
							rs.getDouble("price"), rs.getInt("amount"));
				}
				
				Object[][] datatmp = new Object[count][];
				for(int i=0;i<count;i++)
					datatmp[i] = products[i].toTabelchoose();
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
        //ִ���޸Ĺ��ܣ���ʾѡ�е���������
        public void checkname() {
			for(int i = 0;i < getRowCount();i++)
				if(data[i][0].equals(new Boolean(true)))
					check = new Check_Goods(data[i][1].toString(),Double.parseDouble
							(data[i][2].toString()),Integer.parseInt(data[i][3].toString()));
		}
        //ɾ��ѡ�ж�������
        public void delete(){
        	try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "delete from Product where productname ='";
				
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
			Addnew_Goods addnew = new Addnew_Goods();
		}
		else if (e.getSource() == modify) {
			data.checkname();
		}
		else if (e.getSource() == delete) {
			data.delete();
			this.remove(scrollPane);
			Settable();
		}
	}
}
