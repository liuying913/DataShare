/** 
 * 
 * @author geloin 
 * @date 2012-5-5 上午11:56:35 
 */
package com.highfd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.ApplyUser;
import com.highfd.common.file.FileOperateUtil;
import com.highfd.common.map.MapAll;
import com.highfd.service.ApplyFileService;

/**
 * 文件上传下载Controller
 */
@Controller
public class FileOperateController {
	
	@Autowired
	ApplyFileService applyFileService;
	
	/**
	 * 到上传文件的位置
	 */
	@RequestMapping(value = "to_upload")
	public ModelAndView toUpload() {
		return new ModelAndView("upload");
	}

	/**
	 * 上传文件
	 */
	@RequestMapping(value = "upload")
	public ModelAndView upload(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 别名
		FileOperateUtil.upload(request);
		return new ModelAndView("list", map);
	}

	/**
	 * 下载数据申请模板
	 */
	@RequestMapping(value = "download")
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String storeName = ServletRequestUtils.getStringParameter(request,"storeName");
		String realName = ServletRequestUtils.getStringParameter(request,"realName");

		String contentType = "application/octet-stream";
		//storeName="D:\\LTWL\\2014.xlsx";
		//storeName="\\docPath\\applyData.docx";
		//storeName = request.getSession().getServletContext().getRealPath("/")+ FileOperateUtil.UPLOADDIR+"/"+FileOperateUtil.applyWord;
		storeName = MapAll.applyTempletPath;
		FileOperateUtil.download(request, response, storeName, contentType,realName);
		
		String applyId = request.getParameter("applyId");
		ApplyUser au = new ApplyUser();
		au.setId(Integer.valueOf(applyId));
		applyFileService.updateApplyUser(3, au);

		return null;
	}
	
	
	/**
	 * 下载 盖章的文档
	 * @param attachment
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "downApplyWord")
	public ModelAndView downApplyWord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String storeName = ServletRequestUtils.getStringParameter(request,"storeName");
		String realName = ServletRequestUtils.getStringParameter(request,"realName");
		String applyId = request.getParameter("applyId");
		
		ApplyUser af = new ApplyUser();
		af.setAllOrNo("no");
		af.setId(Integer.valueOf(applyId));
		List<ApplyUser> queryApplyFile = applyFileService.queryApplyUser(-1, af);
		ApplyUser applyUser = queryApplyFile.get(0);
		String applyWorldPath = applyUser.getApplyWorldPath();
		
		String contentType = "application/octet-stream";
		//storeName="D:\\LTWL\\2014.xlsx";
		//storeName="\\docPath\\applyData.docx";
		//storeName = request.getSession().getServletContext().getRealPath("/")+ FileOperateUtil.UPLOADDIR+"/"+applyWorldPath;
		storeName = applyWorldPath;
		FileOperateUtil.download(request, response, storeName, contentType,realName);

		return null;
	}
	
}