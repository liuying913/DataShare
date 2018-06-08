package com.highfd.common.FTP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Ftp {

    /** 
     * 从FTP服务器下载文件 
     */
    //file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
	public static final int timeout=2*60*1000;
	//public static int timeout=2;
    public static int downFile(String url,String username, String password, String remotePath,String fileName,String localPath) {
    	int ftpFlag = 4;
        FTPClient ftp = new FTPClient();
        
        ftp.setConnectTimeout(timeout);
        //ftpClient.setSoTimeout(timeout);
        ftp.setDefaultTimeout(timeout);
        ftp.setDataTimeout(timeout);
      
      
        try {  
            int reply;  
            ftp.connect(url.trim(), 21);  
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
            ftp.login(username, password);//登录  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {  
                //ftp.disconnect();
                return 5;
            }
            boolean changeWorkingDirectory = ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
            ftp.enterLocalPassiveMode();//解决假死问题
            FTPFile[] fs = ftp.listFiles();
            for(FTPFile ff:fs){
                if(ff.getName().equals(fileName)){  
                    File localFile = new File(localPath+"/"+ff.getName());  
                    OutputStream is = new FileOutputStream(localFile);  
                    ftp.enterLocalPassiveMode();//解决假死问题
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                    ftpFlag = 1;
                }
            }
            ftp.logout();  
        } catch (IOException e) { 
        	ftpFlag= 5;
            //e.printStackTrace();  
        } finally {
            if (ftp.isConnected()) { 
                try{  
                    ftp.disconnect();  
                }catch(IOException ioe) {  
                	
                }  
            } 
        }
        return ftpFlag;  
    }
    
    
    
    
    /** 
     * 从FTP服务器下载文件 
     */
    //file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
    public int moreThreaddownFile(String url,String username, String password, String remotePath,String fileName,String localPath) {
    	int ftpFlag = 4;
        FTPClient ftp = new FTPClient();
        
        ftp.setConnectTimeout(timeout);
        //ftpClient.setSoTimeout(timeout);
        ftp.setDefaultTimeout(timeout);
        ftp.setDataTimeout(timeout);
      
      
        try {  
            int reply;  
            ftp.connect(url.trim(), 21);  
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
            ftp.login(username, password);//登录  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {  
                //ftp.disconnect();
                return 5;
            }
            boolean changeWorkingDirectory = ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
            ftp.enterLocalPassiveMode();//解决假死问题
            FTPFile[] fs = ftp.listFiles();
            for(FTPFile ff:fs){
                if(ff.getName().equals(fileName)){  
                    File localFile = new File(localPath+"/"+ff.getName());  
                    OutputStream is = new FileOutputStream(localFile);  
                    ftp.enterLocalPassiveMode();//解决假死问题
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                    ftpFlag = 1;
                    break;
                }
            }
            ftp.logout();  
        } catch (IOException e) { 
        	ftpFlag= 5;
            e.printStackTrace();  
        } finally {
            if (ftp.isConnected()) { 
                try{  
                    ftp.disconnect();  
                }catch(IOException ioe) {  
                	
                }  
            } 
        }
        return ftpFlag;  
    }
    
    
    public static void main(String[] args) {
    	Ftp ftp = new Ftp();
    	List<String> remotePathList = new ArrayList<String>();
  	    remotePathList.add("/1/dZ");remotePathList.add("/2/dZ");remotePathList.add("/3/dZ");
  	    remotePathList.add("/tomcat/dZ");remotePathList.add("/4/dZ");remotePathList.add("/5/dZ");
  	    
  	    List<String> fileList = new ArrayList<String>();
	    fileList.add("GSMQ2160.17o".toLowerCase());fileList.add("HLWD090aD.T02".toLowerCase());fileList.add("SCGU013aD.17O".toLowerCase());
	   
  	    ftp.oneLogindownFile("172.128.16.49", "", "", remotePathList, fileList, "E:\\");
	}
    
    /** 
     * 筛选目录  筛选文件 ftp下载
     */
    //file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
    public int oneLogindownFile(String url,String username, String password, List<String> remotePathList,List<String> fileList,String localPath) {
    	int ftpFlag = 4;
        FTPClient ftp = new FTPClient();
        
        ftp.setConnectTimeout(timeout);
        //ftpClient.setSoTimeout(timeout);
        ftp.setDefaultTimeout(timeout);
        ftp.setDataTimeout(timeout);
       
        try {  
            int reply;  
            ftp.connect(url.trim(), 21);  
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
            ftp.login(username, password);//登录  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {  
                //ftp.disconnect();
                return 5;
            }
            
            for(int i=0;i<remotePathList.size();i++){
            	if(ftp.changeWorkingDirectory(remotePathList.get(i))){;//转移到FTP服务器目录
            	    System.out.println("文件目录是否找到："+remotePathList.get(i));
            		break;
            	}
            }
            ftp.enterLocalPassiveMode();//解决假死问题
            FTPFile[] fs = ftp.listFiles();
           
            for(FTPFile ff:fs){
            	if(fileList.contains(ff.getName().toLowerCase())){
            		
            		System.out.println("文件名称："+ff.getName().toLowerCase());
                //if(ff.getName().equals(fileName)){   
                    File localFile = new File(localPath+"/"+ff.getName());  
                    OutputStream is = new FileOutputStream(localFile);  
                    System.out.println("输出到本地文件："+localPath+"/"+ff.getName());
                    ftp.enterLocalPassiveMode();//解决假死问题
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                    ftpFlag = 1;
                }
            }
            ftp.logout();  
        } catch (IOException e) { 
        	ftpFlag= 5;
            e.printStackTrace();  
        } finally {
            if (ftp.isConnected()) { 
                try{  
                    ftp.disconnect();  
                }catch(IOException ioe) {  
                	
                }  
            } 
        }
        return ftpFlag;  
    }
}
