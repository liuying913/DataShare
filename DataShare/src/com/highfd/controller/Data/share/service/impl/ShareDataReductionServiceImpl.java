package com.highfd.controller.Data.share.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.common.linux.FileTypeTransform;
import com.highfd.common.time.DayNumberOfOneYear;
import com.highfd.controller.Z_DataSupplyController;
import com.highfd.controller.Data.share.service.ShareDataReductionService;
import com.highfd.service.ApplyFileService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;
import com.linux.StaticUtil;

@Service
public class ShareDataReductionServiceImpl implements ShareDataReductionService {
	
	public static String share_temp_base_path = "/root/tmp_nas/gnssdata/Plus-GNSS";//共享文件  临时nas盘 o文件（人家的接收机存储文件）
	public static String share_final_base_path= "/root/nfs/Gnssdata/shareData";//最终位置  共享文件最终存放位置
	
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	HighFDService highFDService;
	
	public void shareDataReductionMain(String beforeDayStr,String siteNumberParam) throws InterruptedException{
		
		//清空整理文件的空间
		//CopyFileUtil.deleteDirList("/java");
		//CopyFileUtil.deleteDirList("/javaFtp");
		
		//共享数据启动整理
		//System.out.println(beforeDayStr);
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String year2 =StaticUtil.getYearTwoLength(beforeDayStr);//年
		
		int doy= StaticUtil.getCountDaysByNYR(beforeDayStr);
		// 三位数年纪日 001,065,123
		String yearDay =StaticUtil.getThreeLengthCountDays(doy);
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoListByIn("3", siteNumberParam);//3   共享台站
		for(int i=0;i<siteInfoList.size();i++){
			
			SiteInfo siteInfo = siteInfoList.get(i);
			String siteNumber = siteInfo.getSiteNumber(); 
			
			//判断是否整理好了
			boolean historyFlag = applyFileService.queryHistoryFileInfo(year4, yearDay, siteNumber, "3", "", " and t.fileflag in (1,2) ");
			if(historyFlag){System.out.println(siteNumber+"不需要再跑了");continue;}
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.println("开始执行下一个台站："+beforeDayStr+" "+ siteNumber+"  "+time);
			
			
			//设置路径 
			String siteTempPath = share_temp_base_path+"/"+siteNumber;
			String o_file_Name = siteNumber+yearDay+"0."+year2+"o";
			String siteFinalAllPath =         share_final_base_path+"/"+year4+"/"+yearDay+"/"+o_file_Name;
			
			//入关系库对象
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileYear(year4);
			fileInfo.setSiteNumber(siteNumber);
			fileInfo.setFileDayYear(yearDay);
			fileInfo.setFileName(o_file_Name);
			fileInfo.setFilePath(share_final_base_path+"/"+year4+"/"+yearDay);
			fileInfo.setSystemStr(beforeDayStr);
			fileInfo.setFileFlag(5);
			fileInfo.setType("3");//连接不上
			
			File findNasFile = FileTool.findFiles(siteTempPath,o_file_Name);//从临时NAS盘中 找到目标文件
			if(null!=findNasFile){
				String o_file_NAS_Path = findNasFile.getAbsoluteFile().toString();//获得o文件路径（临时NAS盘上面的）
				//复制文件
				if(CopyFileUtil.copyFile(o_file_NAS_Path, siteFinalAllPath, true)){
					fileInfo.setFileFlag(1);
					FileTypeTransform t = new FileTypeTransform();
					t.fileCompress(siteFinalAllPath,siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z");//压缩成d文件，并压缩成d.Z文件
					fileInfo.setFileSize(new File(siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z").length()/1024.00);
					CopyFileUtil.deleteDirList(siteFinalAllPath);
					CopyFileUtil.deleteDirList(siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d");
				}else{
					System.out.println("共享台站    复制失败     临时文件路径："+share_final_base_path+"/"+year4+"/"+yearDay+"/"+fileInfo.getFileName());
				}
			}else{
				System.out.println("共享台站    未找到文件     临时文件路径："+share_final_base_path+"/"+year4+"/"+yearDay+"/"+fileInfo.getFileName());
			}
			//入库操作
			shareDataToDB( year4,  fileInfo,applyFileService);
			System.out.println("共享台站跑完站跑完！"+siteInfo.getSiteName()+"   "+siteInfo.getSiteNumber());//启动下一个台站
		}
	}

	
	
	
	/**
	 * 共享数据入库操作
	 */
	public void shareDataToDB(String year4, FileInfo fileInfo,ApplyFileService applyFileService){
		
		FileInfo shareFile = applyFileService.select_30SData_Files(year4, fileInfo);
		if(shareFile==null){
			List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);applyFileService.insert_30SData_Files(year4, list);//入 关系库
		}else{
			applyFileService.update_30S_apply(year4, fileInfo);
		}
	}
	
	
	/**
	 * 从NAS盘扫描o文件获得数据
	 */
	public void analysisAll_o(final File dir,ApplyFileService applyFileService) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		FileTypeTransform t = new FileTypeTransform();
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_o(fs[i], applyFileService);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("o")){//主要小写这里
					
					FileInfo fileInfo = FileTool.getFileInfo(fileName);
					fileInfo.setFileFlag(5);
					String o_file_NAS_Path = fs[i].getAbsoluteFile().toString();//获得o文件路径（临时NAS盘上面的）
					String siteFinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+fileName;
					fileInfo.setFilePath(share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear());
					fileInfo.setFileSize(0.00);//文件大小
					fileInfo.setType("3");
					//复制文件
					if(CopyFileUtil.copyFile(o_file_NAS_Path, siteFinalAllPath, true)){
						fileInfo.setFileFlag(1);
						t.fileCompress(siteFinalAllPath,siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z");//压缩成d文件，并压缩成d.Z文件
						fileInfo.setFileSize(new File(siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z").length()/1024.00);
						System.out.println("共享整理的文件："+siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z");
						CopyFileUtil.deleteDirList(siteFinalAllPath);
						CopyFileUtil.deleteDirList(siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d");
						CopyFileUtil.deleteDirList(o_file_NAS_Path);
					}else{
						System.out.println("共享台站    复制失败     临时文件路径：");
					}
					
					shareDataToDB( fileInfo.getFileYear(),  fileInfo,applyFileService);
				}
			}
		}
	}
	
	
	
	
	/**
	 * 从NAS盘扫描d.z文件获得数据
	 */
	public static void analysisAll_dZ(final File dir) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_dZ(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("d.z")){//主要小写这里
					File dzFile = fs[i];
					String zPathAndName = dzFile.getPath();
					String year2 = zPathAndName.substring(zPathAndName.length()-5, zPathAndName.length()-3);
					String oName = dzFile.getName().toUpperCase().substring(0,7)+"0."+year2+"o";
					
					FileInfo fileInfo = FileTool.getFileInfo(oName);
					fileInfo.setFileFlag(5);
					fileInfo.setType("3");
					String siteFinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+oName;
					fileInfo.setFilePath(share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear());
					fileInfo.setFileSize(0.00);//文件大小
					
					//复制文件
					if(CopyFileUtil.copyFile(dzFile.getAbsoluteFile().toString(), siteFinalAllPath, true)){
						fileInfo.setFileFlag(1);
						FileTypeTransform t = new FileTypeTransform();
						t.fileCompress(siteFinalAllPath,siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z");//压缩成d文件，并压缩成d.Z文件
						fileInfo.setFileSize(new File(siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z").length()/1024.00);
						System.out.println("共享整理的文件："+siteFinalAllPath.substring(0, siteFinalAllPath.length()-1)+"d.Z");
						CopyFileUtil.deleteDirList(dzFile.getAbsoluteFile().toString());
					}else{
						System.out.println("共享台站    复制失败     临时文件路径：");
					}
					
				}
			}
		}
	}
	
}
