package com.highfd.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

import com.highfd.bean.FileInfo;
public class O_FileTool {
	
	 public static void main(final String[] args) throws Exception {
		 //递归显示C盘下所有文件夹及其中文件
		 File root = new File("E:\\new");
		 showAllFiles(root);
		 //getYearByFileName("BJFS0010.16d.Z");
	 }
	 
	 final static void showAllFiles(final File dir) throws Exception{
		  File[] fs = dir.listFiles();
		  if(null==fs){return;}
		  
		  for(int i=0; i<fs.length; i++){
			  //System.out.println(fs[i].getAbsolutePath());
			  if(fs[i].isDirectory()){
				  try{
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
					  System.out.println("else "+filePath);
				  }
				  //System.out.println(fs[i].getAbsolutePath()+"  文件名称："+fs[i].getName()+"  "+fs[i].getParent());
			  }
		  }
	 }
	 
	 public static long forChannel(final File f1,final File f2) throws Exception{
		System.out.println(f1.getName());
        long time=new Date().getTime();
        int length=2097152;
        FileInputStream in=new FileInputStream(f1);
        FileOutputStream out=new FileOutputStream(f2);
        FileChannel inC=in.getChannel();
        FileChannel outC=out.getChannel();
        ByteBuffer b=null;
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.close();
                return new Date().getTime()-time;
            }
            if((inC.size()-inC.position())<length){
                length=(int)(inC.size()-inC.position());
            }else
                length=2097152;
            b=ByteBuffer.allocateDirect(length);
            inC.read(b);
            b.flip();
            outC.write(b);
            outC.force(false);
        }
    }
	 
   public static String getYearByFileName(String fileName){
	   //String fileName = "BJFS0010.16d.Z";
	   if(fileName.endsWith("d.Z")){
	   }else if(fileName.endsWith("D.Z")){
	   }else if(fileName.endsWith("D.z")){
	   }else if(fileName.endsWith("d.z")){
	   }else{
		   return null;
	   }
	   fileName = fileName.substring(9, 11);
	   String year = "20"+fileName;
	   System.out.println(year);
	   return year;
   }

}