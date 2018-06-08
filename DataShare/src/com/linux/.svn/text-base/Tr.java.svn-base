package com.linux;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class Tr {
	
	public static void main(String[] args) {
		
		//方法一：重新加载容器
		ApplicationContext ac = new FileSystemXmlApplicationContext("D:/springmvc-servlet.xml");
		System.out.println();
		Object bean = ac.getBean("UserDAO"); 
		
		
		//方法二：（获取当前的spring容器，任何Java类中适用）
        //ServletContext application = ServletActionContext.getServletContext();
        //ApplicationContext act = ContextLoader.getCurrentWebApplicationContext();
        //UserService userService = (UserService) act.getBean("userService");


		//方法三：（通过request或session加载spring容器）
		//ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
	}

}
