package com.linux;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * <p>Title: GetFileName</P>
 * <p>Description: 计算gps周，下载igs数据时用到</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lmj
 * @version 1.0
 * @since 2010-09-07
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 修改版本号：
 */
public class GpsWeek {
	public static Calendar calendar = Calendar.getInstance(); 
	//计算输入年份到Gps初始年之间的闰年的年数，因为每个闰年需要多加一天
	public static Integer leap(Integer ye) {
		Integer k = 0;
		for (int i = ye; i>1979; i--) {
			if(((GregorianCalendar)calendar).isLeapYear(i)){
				k = k+1;
			}
		}
		return k;
	}
	
	//计算输入的日期到gps初始年之间的总天数
	public static Integer tianShu(Integer y,Integer m,Integer d){
		Integer total = 0;
		total = (y-1980)*365;
		total = total+d-6;
		if (m>=3){
			total = total + leap(y);
		}else if(((GregorianCalendar)calendar).isLeapYear(y)){
			total = total+leap(y)-1;
		}else {
			total = total+leap(y);
		}
		m = m-1;
		while (m>=1) {
		
			if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10){
				total = total+31;
			}else if (m==4 || m==6 || m==9 || m==11) {
				total = total+30;
			}else {
				total = total+28;
			}
		
			m = m-1;
		} 
		return total;
	}
	
	//得到gps周及周天数
	public static List<String> getGpsWeek(Integer y,Integer m,Integer d) {
		List<String> list = new ArrayList<String>();
		Integer gpsWeek = tianShu(y, m, d)/7;
		Integer gpsWeekNumber = tianShu(y, m, d)%7;
		//String gpsWN = gpsWeek+""+gpsWeekNumber;
		list.add(gpsWeek.toString());
		list.add(gpsWeekNumber+"");
		return list;
	}
	
	public static int getGPSWeek(String date){
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		//DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = f.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d); 
		List<String> gpsList=getGpsWeek(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
		//第0为是周字符串  第1位是周几的字符串
		return Integer.parseInt(gpsList.get(0));
	}
	
	public static int gpsWeekNumber(String date){
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = f.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d); 
		List<String> gpsList=getGpsWeek(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
		//第0为是周字符串  第1位是周几的字符串
		return Integer.parseInt(gpsList.get(1));
	}
	
	public static void main(String[] args) {
	//	System.out.println(gpsWeekNumber("2000-1-1 1:30:12"));
		
		String targetPath = "/g/bjsh3470.04d.Z";
		String name = targetPath.substring(targetPath.lastIndexOf("/"+1,targetPath.length()));
	}
	
}
