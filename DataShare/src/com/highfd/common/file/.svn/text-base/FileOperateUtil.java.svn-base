/** 
 * 
 * @author geloin 
 * @date 2012-5-5 下午12:05:57 
 */
package com.highfd.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传功能
 */
public class FileOperateUtil {
	private static final String REALNAME = "realName";
	private static final String SIZE = "size";
	private static final String CONTENTTYPE = "contentType";
	private static final String CREATETIME = "createTime";
	public static final String UPLOADDIR = "uploads";

	/**
	 * 将上传的文件进行重命名
	 */
	private static String rename(String name) {

		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		Long random = (long) (Math.random() * now);
		String fileName = now + "" + random;

		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}

	/**
	 * 压缩后的文件名
	 */
	private static String zipName(String name) {
		String prefix = "";
		if (name.indexOf(".") != -1) {
			prefix = name.substring(0, name.lastIndexOf("."));
		} else {
			prefix = name;
		}
		return prefix + ".zip";
	}

	/**
	 * 上传文件
	 */
	public static String upload(HttpServletRequest request) throws Exception {
		String noZipName  = "";
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();

		String uploadDir = request.getSession().getServletContext().getRealPath("/")+ FileOperateUtil.UPLOADDIR;
		//String uploadDir = "E:\\";
		
		File file = new File(uploadDir);
		

		if (!file.exists()) {
			file.mkdir();
		}

		String fileName = null;
		String storeName = null;
		int i = 0;
		for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();

			fileName = mFile.getOriginalFilename();
			if(null==fileName || "".equals(fileName)){
				return null;
			}
			storeName = rename(fileName);

			noZipName = uploadDir +"/"+ storeName;

			FileOutputStream fout  = new FileOutputStream(noZipName);  
			
			FileCopyUtils.copy(mFile.getInputStream(), fout);

			Map<String, Object> map = new HashMap<String, Object>();
			// 固定参数值对
			map.put(FileOperateUtil.REALNAME, zipName(fileName));
			//map.put(FileOperateUtil.STORENAME, zipName(storeName));
			map.put(FileOperateUtil.SIZE, new File(noZipName).length());
			//map.put(FileOperateUtil.SUFFIX, "zip");
			map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
			map.put(FileOperateUtil.CREATETIME, new Date());
		}
		return storeName;
	}

	/**
	 * 下载
	 */
	public static int download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName){
		
		response.setContentType("text/html;charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;

			String downLoadPath = request.getSession().getServletContext().getRealPath("/") + FileOperateUtil.UPLOADDIR +"/"+ storeName;
			//String downLoadPath = storeName;
			long fileLength = new File(downLoadPath).length();

			response.setContentType(contentType);
			realName=new File(downLoadPath).getName();
			response.setHeader("Content-disposition", "attachment; filename=" + new String(realName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			bos.close();
			return 1;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return -1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return -1;
		}
	}
}