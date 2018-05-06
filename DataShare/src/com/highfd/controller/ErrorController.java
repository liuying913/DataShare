package com.highfd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Component
@Controller
public class ErrorController {
	
	//跳转到正在建设中 界面
	@RequestMapping(value = "errorBuildIng")
	@ResponseBody
	public ModelAndView userInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("error/errorBuildIng");
	}

}
