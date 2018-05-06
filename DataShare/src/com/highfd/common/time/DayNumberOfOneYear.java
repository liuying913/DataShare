package com.highfd.common.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 年积日转化类
 * @author liulin
 *
 */
public class DayNumberOfOneYear {
	
	public static String getDayNumberOfOneYear(Timestamp dateStr){
		Calendar ca = Calendar.getInstance();//创建一个日期实例
	    Date myDate1;
		myDate1 = dateStr;
		ca.setTime(myDate1);//实例化一个日期
		return ca.get(Calendar.DAY_OF_YEAR)+"";
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getDayNumberOfOneYear("2016-03-01"));
		yearDayToDate(62,"2016");
		     
	}

	/**
      * 指定日期加上天数后的日期
      * @param num 为增加的天数
      * @param newDate 创建时间
    */
	public static String yearDayToDate(int num,String year) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date  currdate = format.parse(year+"-01-01");
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);

        ca.add(Calendar.DATE, num-1);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }
	
	public static Integer getDayNumberOfOneYear(String dataStr) throws ParseException{
		return Integer.valueOf(getDayNumberOfOneYear(TimeUtils.getTempTime(dataStr)));
	}
	
	public static java.sql.Timestamp getDateAfter(String dateStr,String dayStr){
	   DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); 
	   Date d;
		try {
			d = dateFormat1.parse(dateStr+"-01-01");
			Calendar now =Calendar.getInstance();
			now.setTime(d);
			int day = Integer.valueOf(dayStr);
			now.set(Calendar.DATE,now.get(Calendar.DATE)+day-1);
			Date time = now.getTime();
			java.sql.Timestamp stp=new java.sql.Timestamp(time.getTime());
			//System.out.println(stp);
			return stp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}

	

}
