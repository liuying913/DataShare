package com.highfd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.highfd.bean.UserInfo;
import com.highfd.service.UserService;


/**
 * BackInterceptor
 */
public class LoginInterceptor implements HandlerInterceptor {

	//方法执行前调用
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		String requestURI = request.getRequestURI();
		//
		
		if(requestURI.indexOf("earthQuakeDataReduction")>-1){
			return true;
		}
		if(requestURI.indexOf("foot")>-1){
			return true;
		}
		if(requestURI.indexOf("head")>-1){
			return true;
		}
		if(requestURI.indexOf("queryDocUseInfoList")>-1){//消息数量
			return true;
		}
		if(requestURI.indexOf("index.action")>-1){//首页
			return true;
		}
		if(requestURI.indexOf("loginFlag.action")>-1){//登录校验
			return true;
		}
		if(requestURI.indexOf("login2.action")>-1){//登录系统
			return true;
		}
		if(requestURI.indexOf("/news/")>-1){//新闻
			return true;
		}
		
		if(requestURI.indexOf("/message/")>-1){//留言板
			return true;
		}
		
		if(requestURI.indexOf("downRuanJian")>-1){//软件下载
			return true;
		}
		
		if(requestURI.indexOf("supply")>-1){//数据互补拦截
			return true;
		}
		//System.out.println(requestURI);
		HttpSession session = request.getSession(false);
		if(session != null){
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			if(null!=userInfo){
				return true;
			}
		}
		request.getRequestDispatcher("index.action").forward(request, response); 
		return false;
		//return true;
	}

	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
	}

	@Autowired
	UserService userService;

	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		
	}
}
