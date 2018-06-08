package com.linux;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Description: 静态方法类
 * @author wj
 * @version 1.0
 */
public class StaticUtil {
	
	private static final long serialVersionUID = 203849594023450455L;
	private static Logger logger = Logger.getLogger(StaticUtil.class);
	
	/**
	 * 方法描述：得到当前日期向前推beforeDays天后的日期
	 * @param beforeDays 向前推的天数
	 * @return 向前推beforeDays天后的日期
	 */
	public static String getStrDateByBeforeDays(int beforeDays) {
		int days = 0;
		days = 0-beforeDays;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		Date dateStrings = calendar.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dFormat.format(dateStrings);
	}
	/**
	 * 方法描述：判断字符串是否是空
	 * @param s 要验证的字符串
	 * @return 向前推beforeDays天后的日期
	 */
	public static boolean ifnull(String s){
		if(s == null || s.equals("")){
			return true;
		}
		return false;
	}
	
	//ab;bb|cc;dd -->aa;bb;cc;dd
	public static String getStr(String str){
		
		if(str != null && (!str.equals(""))){
			String [] arrayStr = str.split("\\|");
			if(arrayStr.length == 1){
				return arrayStr[0];
			}
			else if(arrayStr.length == 2){
				return arrayStr[0]+";"+arrayStr[1];
			}
		}
		return null;
	}
	
	/**
	 * 方法描述：获取年积日
	 * @param time 时间字符串 (例：2012-12-12)
	 * @return 年纪日
	 */
	public static int getCountDays(String time) {

		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getCountDays():"+e.getMessage());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int days = c.get(Calendar.DAY_OF_YEAR);
		return days;
	}
	
	/**
	 * 方法描述：获取年积日
	 * @param time 时间字符串 (例：2012-12-12)
	 * @return 年纪日
	 */
	public static int getCountDaysByNYR(String time) {

		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getCountDaysByNYR():"+e.getMessage());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int days = c.get(Calendar.DAY_OF_YEAR);
		return days;
	}
	/**
	 * 方法描述：根据年纪日返回三个长度的年纪日字符串
	 * @param int 年即日 (例：12或者是111或者是4)
	 * @return 年纪日
	 */
	public static String getThreeLengthCountDays(int doy) {
		if(doy <=0){
			return null;
		}else if(doy <10){
			return "00"+doy;
		}else if(doy <100){
			return "0"+doy;
		}
		return doy+"";
	}
	
	/**
	 * 方法描述：获取年（yyyy）
	 * @param time 时间字符串 (例：2012-12-12 12:12:12)
	 * @return 年的四位字符串
	 */
	public static int getYear(String time) {

		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getYear():"+e.getMessage());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 方法描述：获取年（yyyy）
	 * @param time 时间字符串 (例：2012-12-12)
	 * @return 年的四位字符串
	 */
	public static int getYearByNYR(String time) {

		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getYearByNYR():"+e.getMessage());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 方法描述：获取后两位年字符串（yy），2012----->12
	 * @param time 时间字符串 (例：2012-12-12)
	 * @return 年的两位字符串
	 */
	public static String getYearTwoLength(String time) {
		String year = ""+getYearByNYR(time);// "2012"
		year = year.substring(2,year.length());
		return year;
	}
	
	/**
	 * 方法描述：
	 * @return 当前系统时间的字符串（yyyy-MM-dd）
	 */
	public static String getNowTime() {
	
		String time = null;
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		time = f.format(d);
		return time;	
	}
	
	/**
	 * 方法描述：将2012-12-12格式的时间串转换成20121212格式
	 * @return 当前系统时间的字符串（yyyy-MM-dd）
	 */
	public static String changeYearStyle(String yearMonthDay){
		
		DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat f2 = new SimpleDateFormat("yyyyMMdd");
		
		try {
			return f2.format(f1.parse(yearMonthDay));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("changeYearStyle():"+e.getMessage());
		}
		
		return null;
		
	}
	
	/**
	 * 方法描述：获取小时数
	 * @param time 时间字符串 (例：2012-12-12)
	 * @return 年的四位字符串
	 */
	public static int getHour(String time) {

		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getHour():"+e.getMessage());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	/**
	 * 方法描述：两位月获取月
	 * @param time 时间字符串 (例：2012-12-12)
	 * @return 年的四位字符串
	 */
	public static String getMonth(String time) {

		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = f.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("getMonth():"+e.getMessage());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return getTwoLengthMonth(c.get(Calendar.MONTH)+1);
	}
	
	public static String getTwoLengthMonth(int month) {
		if(month<=0){
			return null;
		}
		if(month<10){
			return "0"+month;
		}
		return month+"";
		
	}
	
}
