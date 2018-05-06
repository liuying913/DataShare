package com.highfd.common;

import java.util.Calendar;
public class ConvertDateTest {
    
    public static void main(String[] args) {
        System.out.println("开始时间: " + ConvertDateTest.getStartDayOfWeekNo(2014,5) );
        System.out.println("结束时间:" + ConvertDateTest.getEndDayOfWeekNo(2014,5) );    
    }

    //根据年、周  获得   本周开始时间
    public static String getStartDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
        
    }
    
    //根据年、周  获得   本周结束时间
    public static String getEndDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
               cal.get(Calendar.DAY_OF_MONTH);    
    }

    public static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        return cal;
    }
    
    /*
    public String getFirstDayOfMonth(int year,int month){
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
        return year + "-"+monthStr+"-" +"01";
    }

    public String getLastDayOfMonth(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR , year);
        calendar.set(Calendar.MONTH , month - 1);
        calendar.set(Calendar.DATE , 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR , -1);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
               calendar.get(Calendar.DAY_OF_MONTH);
    }
    */
}