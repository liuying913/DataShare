package com.highfd.common.latLon;

import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;

public class LocationUtils {  
    private static double EARTH_RADIUS = 6378.137;  
  
    private static double rad(double d) {  
        return d * Math.PI / 180.0;  
    }  
  
    /** 
     * 通过经纬度获取距离(单位：米) 
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2 
     * @return 
     */  
    public static double getDistance(double lat1, double lng1, double lat2,  
                                     double lng2) {  
        double radLat1 = rad(lat1);  
        double radLat2 = rad(lat2);  
        double a = radLat1 - radLat2;  
        double b = rad(lng1) - rad(lng2);  
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
                + Math.cos(radLat1) * Math.cos(radLat2)  
                * Math.pow(Math.sin(b / 2), 2)));  
        s = s * EARTH_RADIUS;  
        s = Math.round(s * 10000d) / 10000d;  
        s = s*1000;  
        return s;  
    }  
    

    public static double getDistianceByGradessss(String grade){
    	if(Double.valueOf(grade)>=5 && Double.valueOf(grade)<6){
    		return 200*1000;//200公里
    	}else if(Double.valueOf(grade)>=6 && Double.valueOf(grade)<7){
    		return 300*1000;//300公里
    	}else if(Double.valueOf(grade)>=7 && Double.valueOf(grade)<8){
    		return 400*1000;//400公里
    	}else if(Double.valueOf(grade)>=8){
    		return 600*1000;//600公里
    	}else{
    		return 0;
    	}
    }
    
    
    public static double getDistianceByGrade(EarthQuake earthQuake,EarthQuakeConfig config){
    	if(null!=earthQuake.getCrawType() && !"".equals(earthQuake.getCrawType()) && earthQuake.getCrawType().equals("1")){
    		return Double.valueOf(earthQuake.getCrawRange());
    	}else{
    		String grade = earthQuake.getGrade();
    		if(Double.valueOf(grade)>=5 && Double.valueOf(grade)<6){
        		return Double.valueOf(config.getEarthquakefirst());//200公里
        	}else if(Double.valueOf(grade)>=6 && Double.valueOf(grade)<7){
        		return Double.valueOf(config.getEarthquakesecond());//300公里
        	}else if(Double.valueOf(grade)>=7 && Double.valueOf(grade)<8){
        		return Double.valueOf(config.getEarthquakethird());//400公里
        	}else if(Double.valueOf(grade)>=8){
        		return Double.valueOf(config.getEarthquakeforth());//600公里
        	}else{
        		return 0;
        	}
    	}
    	
    }
    
    public static boolean rangeByDistance(double siteBetween,double distianceByGrade){
    	if(siteBetween<=distianceByGrade*1000){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    
    public static void printEnglish(){    
        for(int i = (int)'A'; i <= (int)'Z'; ++i){    
            char lowercase = (char)(i + 32);    
            System.out.print("" + lowercase);  
        }    
        System.out.println();    
    }    
    
    public static void main(String[] args) {
		printEnglish();
	}
}  