package com.highfd.common.ApplicationContextUtil;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware{
	
   public static ApplicationContext context;  
  
    public void setApplicationContext(ApplicationContext ctx)  throws BeansException {  
       System.out.println("容器初始化是否成功：" + (ctx!=null));  
       context = ctx;  
       /*SiteStationDAO userDao=(SiteStationDAO)context.getBean("SiteStationDAO");*/
	}  

}
