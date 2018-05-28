package com.highfd.controller.Data.share.service;

import java.io.File;

import com.highfd.bean.FileInfo;
import com.highfd.service.ApplyFileService;

public interface ShareDataReductionService {
	
	public void shareDataReductionMain(String beforeDayStr,String siteNumberParam) throws InterruptedException;
	
	/**
	 * 共享数据入库操作
	 */
	public void shareDataToDB(String year4, FileInfo fileInfo,ApplyFileService applyFileService);
	
	/**
	 * 从NAS盘扫描o文件获得数据
	 */
	public void analysisAll_o(final File dir,ApplyFileService applyFileService) throws Exception;
	
}
