package com.highfd.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

public class PingUtil {

	public static boolean ping(String server, int timeout) {
		try {
			boolean status=InetAddress.getByName(server).isReachable(timeout);
			return status;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	
	public static boolean HttpCheck(String url, int timeout) {
	    //url = url.replaceFirst("https", "http"); 
		url="http://"+url;
	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        return (100 <= responseCode && responseCode <= 599);
	    } catch (IOException exception) {
	        return false;
	    }
	}
	
	
	public static boolean pingServer(String url, int timeout) {
		boolean res = false;
		int iter = timeout / 5000 +1;
		while(!res && iter>0)
		{
			res = HttpCheck(url, 5000);
			iter--;
		}
		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("Strart！！！");
		Date date = new Date();
		long start_time = date.getTime();
		for(int i=0;i<100;i++){
			boolean flag = PingUtil.pingServer("172.128.16.49/CACHEDIR2957015411/xml/dynamic/merge.xml?svData=&agcData=&sv=&glonass=&galileo=&options=", 10000);
			if(!flag){
				System.out.println("问题："+i);
			}
		}
		
		long end_time = new Date().getTime();
		System.out.println("结束:"+(end_time-start_time));
	}
}
