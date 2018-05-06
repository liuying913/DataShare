package com.highfd.common.number;

import java.text.DecimalFormat;
import java.text.ParseException;

import com.highfd.common.time.DayNumberOfOneYear;
import com.highfd.common.time.TimeUtils;

public class NumberChange {

	public static String getOnlyNumber(String num){
		if(null==num || "".equals(num)){
			num="0";
		}else{
			if(num.startsWith("0")){num = num.substring(1, num.length());}
			if(num.startsWith("0")){num = num.substring(1, num.length());}
		}
		
		return num;
	}
	public static String getThreeNumber(int num){
		String resultNum = "";
		if(num<=9){
			resultNum="00"+num;
		}else if(num<=99){
			resultNum = "0"+num;
		}else{
			resultNum = num+"";
		}
		return resultNum;
	}
	
	public static String getThreeNumber(String num){
		String resultNum = "";
		if(num.length()==1){
			resultNum="00"+num;
		}else if(num.length()==2){
			resultNum = "0"+num;
		}else{
			resultNum = num+"";
		}
		return resultNum;
	}
	
	public static String getFourPoint(double numStr){
		 DecimalFormat df = new DecimalFormat("#.00");
		 String rnum = df.format(numStr);
		 if(rnum.startsWith(".")){
			 rnum="0"+rnum;
		 }
		 return rnum;
	}
	
	public static String getRealFourPoint(double numStr){
		DecimalFormat df = new DecimalFormat("#.0000");
		String rnum = df.format(numStr);
		if(rnum.startsWith(".")){
			rnum="0"+rnum;
		}
		return rnum;
	}
	
	public static String getFiveNumber(double yearWzl,String year){
		int dayNumber = getYearDay(year);
		String realFourPoint = getRealFourPoint(yearWzl/dayNumber);
		double d = Double.valueOf(realFourPoint)*100;
		String str = d+"";
		if(str.length()>=5){
			str=str.substring(0, 5);
		}
		return str;
	}
	
	
	public static int getYearDay(String year){
		String nowYear = TimeUtils.getSysYear();//系统年
		if(year.indexOf(nowYear)>-1){//当年
			String today = TimeUtils.getSysYearMonthDay2();
			Integer yesterdayNumber = 0;
			try {
				yesterdayNumber = DayNumberOfOneYear.getDayNumberOfOneYear(today)-1;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//今年   昨天是第几天
			return yesterdayNumber;
		}else{
			int intYear = Integer.valueOf(year);
			if( ((intYear%4==0 && intYear%100!=0) || intYear%400==0)){
				return 366;
			}else{
				return 365;
			}
		}
	}
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		System.out.println(getYearDay("2016"));
		String nowYear = TimeUtils.getSysYear();//系统年
		String year = "2017";//参数年
		if(year.indexOf(nowYear)>-1){//当年
			String today = TimeUtils.getSysYearMonthDay2();
			Integer yesterdayNumber = DayNumberOfOneYear.getDayNumberOfOneYear(today)-1;//今年   昨天是第几天
			System.out.println(yesterdayNumber);
		}else{
			int intYear = Integer.valueOf(year);
			if( ((intYear%4==0 && intYear%100!=0) || intYear%400==0)){
				System.out.println(366+"  runyue");
			}else{
				System.out.println(365+"  bus");
			}
		}
	}
}
