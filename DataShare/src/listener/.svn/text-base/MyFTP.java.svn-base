package listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import com.highfd.controller.CrawTaskAnnotation;

public class MyFTP {
	public static void main(String[] args) throws FtpException, InterruptedException {
		
		FtpServerFactory serverFactory = new FtpServerFactory();
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		
		userManagerFactory.setFile(new File("C:\\users.properties"));
		UserManager userManager = userManagerFactory.createUserManager();
		MyFTP.addTestUser(userManager, "testtest", "testtest", "D:/soreOutData");
		//setEnableFlag(userManager,"20170416145744_139",false);
		serverFactory.setUserManager(userManager);
		
		FtpServer server = serverFactory.createServer();
		
		//添加服务监听
		ServerListener t = new ServerListener();
		serverFactory.addListener("t", t);
		
		//添加事件监听
		Ftplet f = new EventListener();
		Map<String, Ftplet> ftplets = new HashMap<String, Ftplet>();
		ftplets.put("f", f);
		serverFactory.setFtplets(ftplets);
		
		server.start();
		
		//Thread.sleep(30*1000);
		System.out.println("11111111111111");
		//UserManager userManager2 = userManagerFactory.createUserManager();
		//MyFTP.addTestUser(userManager2, "admin123", "admin123456", "D:/soreOutData");
		//serverFactory.setUserManager(userManager2);
		
		//******删除用户测试
		userManager.delete("xixi");
	    serverFactory.setUserManager(userManager);
	    //**************
	}
	
	public static void addTestUser(UserManager userManager,String userName,String password,String homdir) throws FtpException{
	    BaseUser user = new BaseUser();  
	    user.setHomeDirectory(homdir);  
	    user.setName(userName);  
	    user.setPassword(password);  
	    user.setEnabled(true);
	    user.setMaxIdleTime(0);
	    //int maxLogins = askForInt(in, "Maximum number of concurrent logins (0 for no restriction)");//最大登陆数
        //int maxLoginsPerIp = askForInt(in, "Maximum number of concurrent logins per IP (0 for no restriction)");//同一ip最大登陆数
        //int downloadRate = askForInt(in, "Maximum download rate (0 for no restriction)");//下载速率限制为48字节每秒
        //int uploadRate = askForInt(in, "Maximum upload rate (0 for no restriction)");//上传速率限制为48字节每秒
	    List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());//是否包括写入权限
        authorities.add(new ConcurrentLoginPermission(1, 1));
        authorities.add(new TransferRatePermission(1024*1024,1024*1024));
        user.setAuthorities(authorities);
	    userManager.save(user);//user 必须是实现User接口的实体类  
	}  
	
	//添加FTP用户
	public static void addUser(String userName,String password,String homdir) throws FtpException{
		UserManager userManager = CrawTaskAnnotation.userManagerFactory.createUserManager();
	    BaseUser user = new BaseUser();  
	    user.setHomeDirectory(homdir);  
	    user.setName(userName);  
	    user.setPassword(password);  
	    user.setEnabled(true);
	    user.setMaxIdleTime(0);
	    List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());//是否包括写入权限
        authorities.add(new ConcurrentLoginPermission(0, 0));
        authorities.add(new TransferRatePermission(1024*1024, 1024*1024));
        user.setAuthorities(authorities);
	    userManager.save(user);//user 必须是实现User接口的实体类  
	    CrawTaskAnnotation.serverFactory.setUserManager(userManager);
	}  
	
	//删掉FTP用户
	public static void deleteUser(String userName) throws FtpException{
		UserManager userManager = CrawTaskAnnotation.userManagerFactory.createUserManager();
		userManager.delete(userName);
	    CrawTaskAnnotation.serverFactory.setUserManager(userManager);
	}  
	
	
	public static void setEnableFlag(String userName,boolean flag) throws FtpException{
		UserManager userManager = CrawTaskAnnotation.userManagerFactory.createUserManager();
		BaseUser userFtp = (BaseUser)userManager.getUserByName(userName);
		userFtp.setEnabled(flag);
		userManager.save(userFtp);
		CrawTaskAnnotation.serverFactory.setUserManager(userManager);
	    
	}  
	
	public static void setPwd(String userName,String pwd) throws FtpException{
		UserManager userManager = CrawTaskAnnotation.userManagerFactory.createUserManager();
		BaseUser userFtp = (BaseUser)userManager.getUserByName(userName);
		userFtp.setPassword(pwd);
		userManager.save(userFtp);
		CrawTaskAnnotation.serverFactory.setUserManager(userManager);
	    
	}  
	  
}

// !userManager.doesExist( username )