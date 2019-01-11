package Other;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import javax.swing.JLabel;

public class Time extends TimerTask{
	JLabel timelab;
	
	public Time(JLabel lab){
		timelab = lab;
	}
	public Time(){	}
	public void run() {
		timelab.setText(Gettime());
	}
	//���ص�ǰʱ��
	private String Gettime() {
        String[] weekDays = {"�������գ�", "������һ��", "�����ڶ���", "����������", "�������ģ�", "�������壩", "����������"};
        Calendar cal = Calendar.getInstance();
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)	w = 0;
        return cal.get(Calendar.YEAR)+"��"
        		+((cal.get(Calendar.MONTH)+1)<10?"0"+(cal.get(Calendar.MONTH)+1):cal.get(Calendar.MONTH)+1)+"��"
        		+(cal.get(Calendar.DAY_OF_MONTH)<10?"0"+cal.get(Calendar.DAY_OF_MONTH):
        			cal.get(Calendar.DAY_OF_MONTH))+"��"+weekDays[w]+"   "
        	  +(cal.get(Calendar.HOUR_OF_DAY)<10?"0"+cal.get(Calendar.HOUR_OF_DAY):cal.get(Calendar.HOUR_OF_DAY))
        	  +":"+(cal.get(Calendar.MINUTE)<10?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE))
        	  +":"+(cal.get(Calendar.SECOND)<10?"0"+cal.get(Calendar.SECOND):cal.get(Calendar.SECOND)) ;
    }
	//������ʱ��Ϊ�ַ����Ķ�����
	public String Getordernum(){
        Calendar cal = Calendar.getInstance();
		return ""+cal.get(Calendar.YEAR)+cal.get(Calendar.MONTH)+cal.get(Calendar.DAY_OF_MONTH)
		+cal.get(Calendar.HOUR_OF_DAY)+cal.get(Calendar.MINUTE);
	}
}
