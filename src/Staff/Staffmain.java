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
		
		addnew = new JButton("添加新员工");
		addnew.setFont(new Font("宋体", Font.BOLD, 15));
		addnew.setBounds(30, 15, 150, 30);
		panel_top.add(addnew);
		
		textField = new JTextField();
		textField.setText("请输入需要查询的员工工号");
		textField.setFont(new Font("宋体", Font.BOLD, 15));
		textField.setBounds(250, 18, 300, 30);
		textField.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().equals("请输入需要查询的员工工号"))
					textField.setText("");
			}
			public void mouseExited(MouseEvent e) {
				if(textField.getText().equals(""))
					textField.setText("请输入需要查询的员工工号");
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
		panel_top.add(textField);
		textField.setColumns(10);
		
		check = new JButton("查询");
		check.setFont(new Font("宋体", Font.BOLD, 15));
		check.setBounds(567, 17, 100, 30);
		panel_top.add(check);
		
		panel_main = new JPanel();
		panel_main.setLayout(null);
		panel_main.setOpaque(false);
		panel_main.setBounds(0, 60, 680, 340);
		add(panel_main);
		
		setTable();
		
		checkmore = new JButton("\u67E5\u770B");
		checkmore.setFont(new Font("宋体", Font.BOLD, 18));
		checkmore.setBounds(368, 286, 100, 30);
		panel_main.add(checkmore);
		
		delete = new JButton("\u5220\u9664");
		delete.setFont(new Font("宋体", Font.BOLD, 18));
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
		private final String[] columnNames = { "选项", "员工号", "姓名","电话号码", "员工类型" };
		private Object[][] data = null;

		public TableData() {
			Statement statement = null;
			ResultSet rs ;
			try {
				Class.forName("com.hxtt.sql.access.AccessDriver");
				Connection connection = DriverManager.getConnection("jdbc:Access:///E:\\database\\IceWine.mdb");
				statement = connection.createStatement();
			} catch (Exception e2) {
				// TODO 自动生成的 catch 块
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	
		// 获得列的数目
        public int getColumnCount() {
            return columnNames.length;
        }
        // 获得行的数目
        public int getRowCount() {
        	int row;
        	try{
        		row = data.length;
        	}catch (NullPointerException e) {
				row = 0;
			}
            return row;
        }
        // 获得某列的名字，而目前各列的名字保存在字符串数组columnNames中
        public String getColumnName(int col) {
            return columnNames[col];
        }
        // 获得某行某列的数据，而数据保存在对象数组data中
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        // 判断每个单元格的类型
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        // 将表格声明为可编辑的
        public boolean isCellEditable(int row, int col) {
            if (col == 0) 	return true;
            return false;
        }
        // 改变某个数据的值
        public void setValueAt(Object value, int row, int col) { 
        	data[row][col] = value;
        	fireTableCellUpdated(row, col);
        }
        //查看选中的内容
        public void checkname() {
			for(int i = 0;i < getRowCount();i++)
				if(data[i][0].equals(new Boolean(true)))
					check = new Check_Staff(data[i][1].toString());
		}
        //删除选中的内容
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
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addnew)	{Addnew_Staff addnew = new Addnew_Staff();}
		else if (e.getSource() == check) {
			//判断是否输入名字
			if(textField.getText().equals("请输入需要查询的员工工号")
					|| textField.getText().equals(""))	return ;
			Check_Staff check = new Check_Staff(textField.getText());
			textField.setText("请输入需要查询的员工工号");
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
