package com.highfd.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.jdbc.service.impl.AlarmJdbcServiceImpl;
public class Z_EarthHistoryController {

	/**
	 * 地震应急事件， 历史数据入库 main方法
	 */
	public static void main2(String[] args) throws Exception {
		
		 AlarmJdbcServiceImpl a = new AlarmJdbcServiceImpl();
		 List<EarthQuake> earthEventList = a.queryEarthEvent();
		 Thread.sleep(2000);
		 //showAllFiles(new File("/root/nfs/Earthquake/2011"),"2011",earthEventList);
		showAllFiles(new File("/root/nfs/Earthquake/2012"),"2012",earthEventList);
		showAllFiles(new File("/root/nfs/Earthquake/2013"),"2013",earthEventList);
		showAllFiles(new File("/root/nfs/Earthquake/2014"),"2014",earthEventList);
		showAllFiles(new File("/root/nfs/Earthquake/2015"),"2015",earthEventList);
		showAllFiles(new File("/root/nfs/Earthquake/2016"),"2016",earthEventList);
		
		//showAllFiles(new File("D:\\生活\\2012"),"2012");
	}
	
	 public static void showAllFiles(final File dir,String year,List<EarthQuake> earthEventList) throws Exception{
		  File[] fs = dir.listFiles();
		  if(null==fs){return;}
		  
		  AlarmJdbcServiceImpl a = new AlarmJdbcServiceImpl();
		 
		  for(int i=0; i<fs.length; i++){
			  //System.out.println(fs[i].getAbsolutePath());
			  if(fs[i].isDirectory()){
				  showAllFiles(fs[i],year,earthEventList);
			  }else{
				  String filePath = fs[i].getAbsolutePath();
				  if(filePath.endsWith(".T02")){
					  FileInfo fi = new FileInfo();
					  fi.setFileYear(year);
					  fi.setSiteNumber(fs[i].getName().substring(0, 4));
					  fi.setFileDayYear(fs[i].getName().substring(4, 7));
					  fi.setFilePath(fs[i].getParent());
					  fi.setFileName(fs[i].getName());
					  fi.setFileSize(fs[i].length()/1024.00);
					  fi.setType(4+"");
					  for(int u = 0;u<earthEventList.size(); u++){
						  EarthQuake earthQuake = earthEventList.get(u);
						  if(fi.getFilePath().indexOf(earthQuake.getName())>-1){
							  fi.setEarthQuakeId(earthQuake.getId());
						  }
					  }
					  System.out.println(fi.getFileYear()+" "+fi.getSiteNumber()+" "+fi.getFileDayYear()+" "+fi.getFilePath()+" "+fi.getFileName()+" "+fi.getFileSize());
					  a.insert_30SData_Files(year, fi);
					  Thread.sleep(20);
				  }
			  }
		  }
	 }

	 public static void main(String[] args) {
		 getText("D:\\soreOutData\\earthquake\\2017\\20170603-内蒙古阿拉善左旗地震\\20170603-内蒙古阿拉善左旗地震.txt");
	}
		
	public static EarthQuake getText(String filePath){
		try {
			 EarthQuake eq = new EarthQuake();
			 System.out.println(filePath);
             String encoding="utf-8";
             File file=new File(filePath);
             if(file.isFile() && file.exists()){ //判断文件是否存在
                 InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                 BufferedReader bufferedReader = new BufferedReader(read);
                 String lineTxt = null;
                 while((lineTxt = bufferedReader.readLine()) != null){
                     if(lineTxt.indexOf("深度")>-1){
                    	 if(lineTxt.indexOf(":")>-1){
                    		 eq.setHeight(lineTxt.split(":")[1].trim());
                    	 }else  if(lineTxt.indexOf("：")>-1){
                    		 eq.setHeight(lineTxt.split("：")[1].trim());
                    	 }
                     }else if(lineTxt.indexOf("地点")>-1){
                    	 if(lineTxt.indexOf(":")>-1){
                    		 eq.setAddress(lineTxt.split(":")[1].trim());
                    	 }else  if(lineTxt.indexOf("：")>-1){
                    		 eq.setAddress(lineTxt.split("：")[1].trim());
                    	 }
                     }else if(lineTxt.indexOf("震级")>-1){
                    	 if(lineTxt.indexOf(":")>-1){
                    		 eq.setGrade(lineTxt.split(":")[1].trim());
                    	 }else  if(lineTxt.indexOf("：")>-1){
                    		 eq.setGrade(lineTxt.split("：")[1].trim());
                    	 }
                     }else if(lineTxt.indexOf("经度")>-1){
                    	 if(lineTxt.indexOf(":")>-1){
                    		 eq.setSiteLat(lineTxt.split(":")[1].trim());
                    	 }else  if(lineTxt.indexOf("：")>-1){
                    		 eq.setSiteLat(lineTxt.split("：")[1].trim());
                    	 }
                     }else if(lineTxt.indexOf("纬度")>-1){
                    	 if(lineTxt.indexOf(":")>-1){
                    		 eq.setSiteLon(lineTxt.split(":")[1].trim());
                    	 }else  if(lineTxt.indexOf("：")>-1){
                    		 eq.setSiteLon(lineTxt.split("：")[1].trim());
                    	 }
                     }else if(lineTxt.indexOf("时间")>-1){
                    	 if(lineTxt.indexOf("：")>-1){
                    		 String time = lineTxt.split("：")[1].trim();
                        	 eq.setYear(time.substring(0, 4));
                        	 if(time.trim().length()>=9){
                        		 eq.setStrTime(time.substring(0, 4)+"-"+time.substring(4, 6)+"-"+time.substring(6, 8)+" "+time.substring(8, 10)+":00:00");
                        	 }else{
                        		 eq.setStrTime(time.substring(0, 4)+"-"+time.substring(4, 6)+"-"+time.substring(6, 8)+" 12:00:00");
                        	 }
                    	 }else if(lineTxt.indexOf(":")>-1){
                    		 String time = lineTxt.split(":")[1].trim();
                        	 eq.setYear(time.substring(0, 4));
                        	 eq.setStrTime(time.substring(0, 4)+"-"+time.substring(4, 6)+"-"+time.substring(6, 8)+" "+time.substring(8, 10)+":00:00");
                    	 }
                     }
                 }
                 read.close();
	     }else{
	         System.out.println("找不到指定的文件");
	     }
             System.out.println(eq.getStrTime()+" "+eq.getName());
         return eq;
	     } catch (Exception e) {
	         System.out.println("读取文件内容出错");
	         e.printStackTrace();
	     }
	     return null;
	}


}