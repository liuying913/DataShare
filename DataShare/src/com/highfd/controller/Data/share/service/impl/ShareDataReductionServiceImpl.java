package com.highfd.controller.Data.share.service.impl;

import java.io.File;
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
import com.highfd.controller.Data.share.service.ShareDataReductionService;
import com.highfd.service.ApplyFileService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;
import com.linux.StaticUtil;

@Service
public class ShareDataReductionServiceImpl implements ShareDataReductionService {
	
	public static String share_temp_base_path = "/root/tmp_nas/gnssdata/Plus-GNSS";//共享文件  临时nas盘 o文件（人家的接收机存储文件）
	public static String share_final_base_path= "/root/nfs/Gnssdata/shareData";    //最终位置  共享文件最终存放位置
	
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
			String dzName        =siteNumber.toLowerCase()+yearDay+"0."+year2+"o"+"d.Z";
			
			//入关系库对象
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileYear(year4);
			fileInfo.setSiteNumber(siteNumber);
			fileInfo.setFileDayYear(yearDay);
			fileInfo.setFileName(dzName);
			fileInfo.setFilePath(share_final_base_path+"/"+year4+"/"+yearDay);
			fileInfo.setSystemStr(beforeDayStr);
			fileInfo.setFileFlag(5);
			fileInfo.setType("3");//连接不上
			fileInfo.setFileSize(0.00);
			File findNasFile = FileTool.findFiles(siteTempPath,o_file_Name);//从临时NAS盘中 找到目标文件
			if(null!=findNasFile){
				String o_file_NAS_Path = findNasFile.getAbsoluteFile().toString();//获得o文件路径（临时NAS盘上面的）
				String o_FinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+o_file_Name;
				String z_FinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+fileInfo.getFileName();
				//复制文件
				
				if(checkBigOrSmall(o_file_NAS_Path,z_FinalAllPath)){//判断新旧 谁的大  true则是新的大
					
				}
				
				if(CopyFileUtil.copyFile(o_file_NAS_Path, o_FinalAllPath, true)){
					fileInfo.setFileFlag(1);
					try{
						FileTypeTransform t = new FileTypeTransform();
						t.fileCompress(o_FinalAllPath,z_FinalAllPath);//压缩成d文件，并压缩成d.Z文件
					}catch(Exception e){
						System.out.println("转换错误：  来源"+o_file_NAS_Path+" 复制后文件："+o_FinalAllPath+"  目的地："+z_FinalAllPath);
					}
					fileInfo.setFileSize(new File(z_FinalAllPath).length()/1024.00);
					CopyFileUtil.deleteDirList(o_FinalAllPath);
					CopyFileUtil.deleteDirList(o_FinalAllPath.substring(0, o_FinalAllPath.length()-1)+"d");
				}else{
					System.out.println("共享台站    复制失败     临时文件路径："+o_file_NAS_Path);
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
	 * 判断两个文件的大小
	 */
	public static boolean checkBigOrSmall(String oldFile,String newFile){
		long oldLength = new File(oldFile).length();
		long newLength = new File(newFile).length();
		if(newLength>oldLength){
			return true;
		}else{
			return false;
		}
	}
	public static void main(String[] args) {
		//C:\\Users\\liuying\\Desktop\\test
		System.out.println(checkBigOrSmall("G:\\test\\kssc0070.17d.Z","G:\\test\\kstm0070.17d.Z"));
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
					String o_FinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+fileName;
					String z_FinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+fileInfo.getFileName();
					fileInfo.setFilePath(share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear());
					fileInfo.setFileSize(0.00);//文件大小
					fileInfo.setType("3"); 
					//复制文件
					if(CopyFileUtil.copyFile(o_file_NAS_Path, o_FinalAllPath, true)){
						fileInfo.setFileFlag(1);
						try{
							System.out.println(z_FinalAllPath);
							t.fileCompress(o_FinalAllPath,z_FinalAllPath);//压缩成d文件，并压缩成d.Z文件
						}catch(Exception e){
							System.out.println("转换错误：  来源"+o_file_NAS_Path+" 复制后文件："+o_FinalAllPath+"  目的地："+z_FinalAllPath);
						}
						
						fileInfo.setFileSize(new File(z_FinalAllPath).length()/1024.00);
						CopyFileUtil.deleteDirList(o_FinalAllPath);
						CopyFileUtil.deleteDirList(z_FinalAllPath.substring(0, z_FinalAllPath.length()-2));
						System.out.println(z_FinalAllPath.substring(0, z_FinalAllPath.length()-2));
						CopyFileUtil.deleteDirList(o_file_NAS_Path);
					}else{
						System.out.println("共享台站    复制失败     临时文件路径："+o_file_NAS_Path);
					}
					
					shareDataToDB( fileInfo.getFileYear(),  fileInfo,applyFileService);
				}
			}
		}
	}
	
	
	
	/**
	 * 从NAS盘扫描d.z文件获得数据
	 */
	public void analysisAll_dZ(final File dir,ApplyFileService applyFileService) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_dZ(fs[i], applyFileService);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("d.z")){//主要小写这里
					File dzFile = fs[i];
					String zPathAndName = dzFile.getPath();
					String year2 = zPathAndName.substring(zPathAndName.length()-5, zPathAndName.length()-3);
					String oName = dzFile.getName().toUpperCase().substring(0,7)+"0."+year2+"o";
					String dzName =dzFile.getName().toLowerCase().substring(0,7)+"0."+year2+"d.Z";
					
					FileInfo fileInfo = FileTool.getFileInfo(oName);
					fileInfo.setFileFlag(5);
					fileInfo.setType("3");
					String siteFinalAllPath =         share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear()+"/"+dzName;
					fileInfo.setFilePath(share_final_base_path+"/"+fileInfo.getFileYear()+"/"+fileInfo.getFileDayYear());
					fileInfo.setFileSize(0.00);//文件大小
					
					//复制文件
					if(CopyFileUtil.copyFile(dzFile.getAbsoluteFile().toString(), siteFinalAllPath, true)){
						fileInfo.setFileFlag(1);
						fileInfo.setFileSize(new File(siteFinalAllPath).length()/1024.00);
						System.out.println("共享整理的文件："+siteFinalAllPath);
						CopyFileUtil.deleteDirList(dzFile.getAbsoluteFile().toString());
					}else{
						System.out.println("共享台站    复制失败     临时文件路径：");
					}
					shareDataToDB( fileInfo.getFileYear(),  fileInfo,applyFileService);
				}
			}
		}
	}
	
	
	
	/**
	 * 删除剩余的文件
	 */
	public void deleteFile(final File dir) throws Exception{

		File[] fs = dir.listFiles();
		if(null==fs){return;}
		
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					deleteFile(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if( fileName.toLowerCase().endsWith("d.z") || fileName.toLowerCase().endsWith("o")){//主要小写这里
				
				}else{
					File dzFile = fs[i];
					CopyFileUtil.deleteDirList(dzFile.getAbsoluteFile().toString());
					System.out.println("执行删除："+dzFile.getAbsoluteFile().toString());
				}
			}
		}
	}
	

}
