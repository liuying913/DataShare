package com.highfd.common.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.highfd.bean.FileInfo;
import com.highfd.bean.LinuxFileBean;

public class FileErgodic {
	/**
	 * @param args
	 */
	 public static void main(final String[] args) throws Exception {
		 //递归显示C盘下所有文件夹及其中文件
		 //File root = new File("D:/生活/2011/20110311_日本本州岛附近地震/30s");
		 //showAllFiles(root);
		 //getYearByFileName("BJFS0010.16d.Z");
		 getParentFiles(new LinuxFileBean("D:/生活/2011/20110311_日本本州岛附近地震/30s","",true));
	 }
	 
	 //获得子类的文件列表
	 public static void getNextFiles(LinuxFileBean fileBean){
		 List<LinuxFileBean> list = new ArrayList<LinuxFileBean>();
		 File dir = new File(fileBean.getParentPath()+"/"+fileBean.getFileName());
		 File[] fs = dir.listFiles();
		 if(null==fs){return;}
		 for(int i=0; i<fs.length; i++){
			 LinuxFileBean thisFile = new LinuxFileBean();
			 if(fs[i].isDirectory()){
				 thisFile.setDirectory(true);
			 }else{
				 thisFile.setDirectory(false);
			 }
			 thisFile.setFileName(fs[i].getName());
			 thisFile.setParentPath(fs[i].getParent());
			 list.add(thisFile);
		 }
		 
		 for(int i=0;i<list.size();i++){
			 LinuxFileBean linuxFileBean = list.get(i);
			 System.out.println(linuxFileBean.isDirectory+"  "+linuxFileBean.getParentPath()+"  "+linuxFileBean.getFileName());
		 }
	 }
	 
	 //获得父类的文件列表
	 public static void getParentFiles(LinuxFileBean fileBean){
		 List<LinuxFileBean> list = new ArrayList<LinuxFileBean>();
		 String parentPath = fileBean.getParentPath().substring(0, fileBean.getParentPath().lastIndexOf("/"));
		 File dir = new File(parentPath);
		 File[] fs = dir.listFiles();
		 if(null==fs){return;}
		 for(int i=0; i<fs.length; i++){
			 LinuxFileBean thisFile = new LinuxFileBean();
			 if(fs[i].isDirectory()){
				 thisFile.setDirectory(true);
			 }else{
				 thisFile.setDirectory(false);
			 }
			 thisFile.setFileName(fs[i].getName());
			 thisFile.setParentPath(fs[i].getParent());
			 list.add(thisFile);
		 }
		 
		 for(int i=0;i<list.size();i++){
			 LinuxFileBean linuxFileBean = list.get(i);
			 System.out.println(linuxFileBean.isDirectory+"  "+linuxFileBean.getParentPath()+"  "+linuxFileBean.getFileName());
		 }
	 }
	 public static void showAllFiles(final File dir) throws Exception{
		  File[] fs = dir.listFiles();
		  if(null==fs){return;}
		  
		  for(int i=0; i<fs.length; i++){
			  //System.out.println(fs[i].getAbsolutePath());
			  if(fs[i].isDirectory()){
				  try{
					 /* System.out.println(fs[i].getAbsolutePath()+"   "+fs[i].getName());
					  System.out.println(fs[i].getParent());
					  System.out.println(fs[i].getCanonicalPath()+"    "+fs[i].getPath());*/
					  showAllFiles(fs[i]);
				  }catch(Exception e){}
			  }else{
				  String filePath = fs[i].getAbsolutePath();
				  if(filePath.indexOf("sd")>-1){
					  FileInfo info = new FileInfo();
					  String[] split = filePath.split("\\\\");
					  info.setFileName(split[split.length-1]);//文件名称
					  info.setFileYear(split[split.length-3]);//年
					  info.setFileDayYear(split[split.length-2]);//年积日
					  info.setFilePath(fs[i].getParent());//路径
					  info.setFileSize(fs[i].length()/1024.00);//大小
					  info.setSiteNumber(fs[i].getName().substring(0, 4));
					  System.out.println(info.getFileName()+" 年" +info.getFileYear()+" 年积日"+info.getFileDayYear()+"  路径"+info.getFilePath()+" 文件大小"+info.getFileSize()+" 台站名称"+info.getSiteNumber());
					  
					  info.setFileYear(fs[i].getParent());
				  }else{
					  //System.out.println("else "+filePath);
				  }
				  //System.out.println(fs[i].getAbsolutePath()+"  文件名称："+fs[i].getName()+"  "+fs[i].getParent());
				  
				  System.out.println(fs[i].getAbsolutePath()+"   "+fs[i].getName());
				  System.out.println(fs[i].getParent());
				  System.out.println(fs[i].getCanonicalPath()+"    "+fs[i].getPath());
				  System.out.println();
			  }
		  }
	 }

}
