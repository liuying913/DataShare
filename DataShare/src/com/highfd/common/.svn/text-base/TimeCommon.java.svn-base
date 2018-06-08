package com.highfd.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeCommon{  
	
	
	
	//通过字符串截取里面的数值
	public static String getNumberByString(String str){
		 Pattern p=Pattern.compile("(\\d+)");   
		 Matcher m=p.matcher(str);       
		 if(m.find()){return m.group(1);  
		 }else{return null; }
	}
	
	//获得季度
	public static String getQuarterByString(String str){
		if(null==getNumberByString(str)){
			if(Pattern.compile("一").matcher(str).find()){ return "01-01";  
			}else if(Pattern.compile("二").matcher(str).find()){return "04-01";  
			}else if(Pattern.compile("三").matcher(str).find()){return "07-01";  
			}else if(Pattern.compile("四").matcher(str).find()){return "10-01";  
			}else{return null;}
		}else{
			String quarter = getNumberByString(str);
			if("1".equals(quarter)){quarter = "01-01";
			}else if("2".equals(quarter)){quarter = "04-01";
			}else if("3".equals(quarter)){quarter = "07-01";
			}else if("4".equals(quarter)){quarter = "10-01";}
			return quarter;
		}
	}
	//Sting--->sql date
    public static java.sql.Date getDate(String dates){

    	//周的形式
    	if(dates.indexOf("周")>-1){
    		String year = getNumberByString(dates.substring(0, dates.indexOf("年")));
    		String week = getNumberByString(dates.substring(dates.indexOf("年"), dates.indexOf("周")));
    		dates = ConvertDateTest.getStartDayOfWeekNo(Integer.parseInt(year),Integer.parseInt(week));
    	}else if(dates.indexOf("季")>-1){
    		String year = getNumberByString(dates.substring(0, dates.indexOf("年")));
    		String quarter = getQuarterByString(dates.substring(dates.indexOf("年"), dates.indexOf("季")));
    		dates = year+"-"+quarter;
    	}else{//其他形式
    		dates = dates.replaceAll(" ", "").replaceAll("上旬", "01").replaceAll("中旬", "11").replaceAll("下旬", "21").replaceAll("年", "-").replaceAll("月份", "-").replaceAll("月", "-").replaceAll("日", "").replaceAll("/", "-").replaceAll("_", "-").trim();
        	if(dates.endsWith("-")){
        		dates=dates.substring(0, dates.length()-1);
        	}
    	}
    	
    	//System.out.print(dates);
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	if(getYearMonth(dates)){                    //年月形式
    		sdf = new SimpleDateFormat("yyyy-MM");
    	}else if(getYearMonthCom(dates)){           //年月形式
    		sdf = new SimpleDateFormat("yyyy.MM");
    	}else if(getMonthYear(dates)){              //月年形式
    		sdf = new SimpleDateFormat("MM-yyyy");
    	}else if(getYearMonthDay(dates)){           //年月日形式
    		sdf = new SimpleDateFormat("yyyy-MM-dd");
    	}else{                                      //月日年形式
    		sdf = new SimpleDateFormat("MM-dd-yyyy");
    	}
    	
    	java.util.Date parse = new java.util.Date();
		try {
			parse = sdf.parse(dates);
			//System.out.println("   "+parse);
		} catch (ParseException e) {
			//System.err.println(dates +" 时间转换出现错误！！！");
			//e.printStackTrace();
			return null;
		}
		//System.out.println(parse.getTime());
    	return new java.sql.Date(parse.getTime());
    }
    
    
    //格式2015-12
	public static boolean getYearMonth(String date){
		String regex = "^\\d{4}\\-\\d{1,2}";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}
	//格式2015.12
	public static boolean getYearMonthCom(String date){
		String regex = "^\\d{4}\\.\\d{1,2}";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}
	//格式12-2015
	public static boolean getMonthYear(String date){
		String regex = "^\\d{1,2}\\D+\\d{4}";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}
	//格式2015-12-02
	public static boolean getYearMonthDay(String date){
		String regex = "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}[^>]*";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}

	
	
	/*	//格式12-02-2015
		public static boolean getDateRegex1(String date){
			String regex = "[0-9]{2}-[0-9]{2}-[0-9]{4}";  
			 Pattern pattern = Pattern.compile(regex);  
			 Matcher matcher = pattern.matcher("12-02-2015");  
			 if(matcher.matches()){  
				 return true;
	        }else{  
	        	return false;
	        }  
			
		}*/
	
	
	//日期  当天时间
	public static String getNowDate(){
	    String temp_str="";   
	    Date dt = new Date();   
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
	    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    temp_str=sdf.format(dt);   
	    return temp_str;   
	} 
	//日期 string——>Date
	public static Date getDates(String str){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");         
		Date date = null;                    
		try {    
		    date = format1.parse(str);   
		} catch (ParseException e) {    
		    e.printStackTrace();    
		}  
		return date;
	}
	//日期 string 转换成long型
	public static Long getDataTimeLong(String dates) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.parse(dates).getTime();
	}
	//日期 long型转换成 date型
	public static Date getTimeLongToDate(Long times) throws ParseException{
		Date dt = new Date(times); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		String str = format.format(dt);
		Date date = format.parse(str);
		return date;
	}
	//根据周期算秒
	public static long getCycleTimeMinutes(String fre){
		long longTime = 0;
		if("6".equals(fre)){
			longTime = 1;
		}else if("5".equals(fre)){
			longTime =  7;
		}else if("4".equals(fre)){
			longTime =  10;
		}else if("3".equals(fre)){
			longTime =  30;
		}else if("2".equals(fre)){
			longTime =  3*30;
		}else if("1".equals(fre)){
			longTime =  365;
		}else{
			longTime =  0;
		}
		return longTime*86400*1000;
	}
	
	//获得当天日期
	public static String getSysMonthDay(){
		Calendar cal=Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;//月
		String monthStr="";  String dayStr="";
		if(month<10){
			monthStr="0"+month;
		}else{
			monthStr=month+"";
		}
		int day   = cal.get(Calendar.DATE);//日
		if(day<10){
			dayStr = "0"+day;
		}else{
			dayStr = day+"";
		}
		return monthStr+"-"+dayStr;
	}
	//获得昨天日期
	public static String getYesMonthDay(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		int month = cal.get(Calendar.MONTH)+1;//月
		String monthStr="";  String dayStr="";
		if(month<10){
			monthStr="0"+month;
		}else{
			monthStr=month+"";
		}
		int day   = cal.get(Calendar.DATE);//日

		if(day<10){
			dayStr = "0"+day;
		}else{
			dayStr = day+"";
		}
		return monthStr+"-"+dayStr;
	}
	//获得前天日期
	public static String getBeforeMonthDay(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		int month = cal.get(Calendar.MONTH)+1;//月
		String monthStr="";  String dayStr="";
		if(month<10){
			monthStr="0"+month;
		}else{
			monthStr=month+"";
		}
		int day   = cal.get(Calendar.DATE);//日
		if(day<10){
			dayStr = "0"+day;
		}else{
			dayStr = day+"";
		}
		return monthStr+"-"+dayStr;
	}
	
	public static String getAllStrTime(String str){
		Calendar cal=Calendar.getInstance();
		return cal.get(Calendar.YEAR)+"-"+str;
	}
	
	public static boolean checkLastThreeDay(String todayTime){
		boolean flag=false;
		if (TimeCommon.getSysMonthDay().equals(todayTime)) {//当天
			flag=true;
		}/*else if (TimeCommon.getYesMonthDay().equals(todayTime)) {
			flag=true;
		}else if (TimeCommon.getBeforeMonthDay().equals(todayTime)) {
			flag=true;
		}*/
		return flag;
	}
	
	public static void main(String []abc) throws ParseException, InterruptedException{
		// 13  1  1  0  0  0.0000000  0 20G17G28G10G09G04G08R04G01R16R10R17R18
		String regex = "\\s+[0-9]{2}\\s+[0-9]{1,2}\\s+[1-9]{1,2}.*";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(" 13  1  1  0  0  0.0000000  0 20G17G28G10G09G04G08R04G01R16R10R17R18");  
		 if(matcher.matches()){	
			 System.out.println("111");
		 }else{
			 System.out.println("222");
		 }
		 
		 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//获取前一个月第一天
		    Calendar calendar1 = Calendar.getInstance();
		    calendar1.add(Calendar.MONTH, -4);
		    calendar1.set(Calendar.DAY_OF_MONTH,0);
		    String firstDay = sdf.format(calendar1.getTime());
		    //获取前一个月最后一天
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.set(Calendar.DAY_OF_MONTH, 0);
		    String lastDay = sdf.format(calendar2.getTime());
		      
		    System.out.println("每月二号 补数据  日期："+firstDay+" 到 "+lastDay);
		
			Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDay);
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(lastDay);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date0);
			while(cal.getTime().compareTo(date1)<0){
			    cal.add(Calendar.DAY_OF_MONTH,1);
			    Date dateStrings = cal.getTime();
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println("每月二号 补数据    开始执行："+dFormat.format(dateStrings));
				//t(dFormat.format(dateStrings));
			}
	}
	
	public static List<String> degreeList = new ArrayList<String>();
	static{
		degreeList.add("1");degreeList.add("3");degreeList.add("4");degreeList.add("5");degreeList.add("6");
	}
}