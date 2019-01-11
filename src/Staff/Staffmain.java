package Staff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import Client.Addnew_Client;
import Client.Check_Client;
import Entity_Relationship.Product;
import Entity_Relationship.Staff;

import java.awt.Color;
import java.awt.Font;

public class Staffmain extends JPanel implements ActionListener{
	private JTextField textField;
	private JTable table;
	private JButton addnew,check,checkmore,delete;
	private TableData data ;
	JPanel panel_main;
	JScrollPane scrollPane;
	
	public Staffmain() {
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(680, 400);
		
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setOpaque(false);
		panel_top.setBounds(0, 0, 680, 60);
		add(panel_top);
		
		addnew = new JButton("�����Ա��");
		addnew.setFont(new Font("����", Font.BOLD, 15));
		addnew.setBounds(30, 15, 150, 30);
		panel_top.add(addnew);
		
		textField = new JTextField();
		textField.setText("��������Ҫ��ѯ��Ա������");
		textField.setFont(new Font("����", Font.BOLD, 15));
		textField.setBounds(250, 18, 300, 30);
		textField.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().equals("��������Ҫ��ѯ��Ա������"))
					textField.setText("");
			}
			public void mouseExited(MouseEvent e) {
				if(textField.getText().equals(""))
					textField.setText("��������Ҫ��ѯ��Ա������");
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
		panel_top.add(textField);
		textField.setColumns(10);
		
		check = new JButton("��ѯ");
		check.setFont(new Font("����", Font.BOLD, 15));
		check.setBounds(567, 17, 100, 30);
		panel_top.add(check);
		
		panel_main = new JPanel();
		panel_main.setLayout(null);
		panel_main.setOpaque(false);
		panel_main.setBounds(0, 60, 680, 340);
		add(panel_main);
		
		setTable();
		
		checkmore = new JButton("\u67E5\u770B");
		checkmore.setFont(new Font("����", Font.BOLD, 18));
		checkmore.setBounds(368, 286, 100, 30);
		panel_main.add(checkmore);
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("����", Font.BOLD, 18));
		delete.setBounds(500, 286, 100, 30);
		panel_main.add(delete);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(0, 0, 680, 1);
		panel_main.add(panel);
		
		addnew.addActionListener(this);
		check.addActionListener(this);
		checkmore.addActionListener(this);
		delete.addActionListener(this);
	}
	
	void setTable(){
		data = new TableData();
		table = new JTable(data);
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(80, 30, 520, 220);
		panel_main.add(scrollPane);
	}
	
	class TableData extends AbstractTableModel{
		private Check_Staff check;
		private final String[] columnNames = { "ѡ��", "Ա����", "����","�绰����", "Ա������" };
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
				rs = statement.executeQuery("select * from Staff");
				int count = 0;
				while(rs.next())	count ++;
				Staff[] staffs = new Staff[count];
				
				rs = statement.executeQuery("select * from Staff");
				for(int i=0;i<count;i++){
					rs.next();
					staffs[i] = new Staff(rs.getString("name"),rs.getString("phone"),
							rs.getString("address"),rs.getString("type"),rs.getString("staffID"));
				}
				
				Object[][] datatmp = new Object[count][];
				for(int i=0;i<count;i++)
					datatmp[i] = staffs[i].toTabel();
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
        //�鿴ѡ�е�����
        public void checkname() {
			for(int i = 0;i < getRowCount();i++)
				if(data[i][0].equals(new Boolean(true)))
					check = new Check_Staff(data[i][1].toString());
		}
        //ɾ��ѡ�е�����
        public void delete() {
        	try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				Statement statement = connection.createStatement();
				String sql = "delete from Staff where staffID='";
				
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
		if(e.getSource() == addnew)	{Addnew_Staff addnew = new Addnew_Staff();}
		else if (e.getSource() == check) {
			//�ж��Ƿ���������
			if(textField.getText().equals("��������Ҫ��ѯ��Ա������")
					|| textField.getText().equals(""))	return ;
			Check_Staff check = new Check_Staff(textField.getText());
			textField.setText("��������Ҫ��ѯ��Ա������");
		}
		else if (e.getSource() == checkmore) {
			data.checkname();
		}
		else if (e.getSource() == delete) {
			data.delete();
			panel_main.remove(scrollPane);
			setTable();
		}
	}
}
