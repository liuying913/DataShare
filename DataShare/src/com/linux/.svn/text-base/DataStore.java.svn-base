package com.linux;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class DataStore {
	
	private static final long serialVersionUID = 203849594028350455L;
	private static Logger logger = Logger.getLogger(DataStore.class);
	
	private Map<String,String> templatemap = new HashMap<String,String>();
	protected static final int BUFFER_SIZE = 16 * 1024;
	private Connection con = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;
	
	private String naspath = null;
	
	private boolean defaultCommit;
	public DataStore(){
	}
	public int initConn(){
		try {
			con = ConnectionSource.getConnection();
			//设置成不自动提交，为了可能多次访问数据库
			try {
				defaultCommit = con.getAutoCommit();
				con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			templatemap = getTemplate();
			naspath = StaticConstant.getInstance().getNasPath();
			System.out.println(naspath);
			if(con!=null && templatemap!=null){
				logger.info("Initail process successed!");
				return 1;
			}
			
		} catch (DataServiceSQLException e) {
			e.printStackTrace();
		}
		logger.info("Initail process failed!");
		return 0;
	}
	
	private Map getTemplate(){
		String sql = "select WORKTYPE,FILENAMETEMPLATE from WORKTYPETABLE";
		//放置总表的WORKTYPE和FILENAMETEMPLATE组成的Map
		Map<String,String> templatemap = new HashMap<String,String>();
		try {
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()){
				templatemap.put(rs.getString("WORKTYPE"), rs.getString("FILENAMETEMPLATE"));
			}
			return templatemap;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}
	public int closeConn(){
	
		//先提交，关闭此次连接
		try {
			con.commit();
			con.setAutoCommit(defaultCommit);
			ConnectionSource.closeResultSet(rs);
			ConnectionSource.closeStatement(st);
			ConnectionSource.closeConnection(con);
			logger.info("Close process successed!");
			return 1;
		} catch (DataServiceSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		logger.info("Close process failed!");
		return 0;
	}
	
	/**
	 * 
	 * @param time 年月日
	 * @return
	 */
	public Map dateConvert(String time){
		
		if(time==null || "".equals(time)){
			return null;
		}
		
		Map<String,String> timeMap = new HashMap<String,String>();
		try{
			// 四位年
			String fouryear = StaticUtil.getYear(time)+"";
			//两位年
			String twoyear = fouryear.substring(2,fouryear.length());
			int doy= StaticUtil.getCountDays(time);
			//三位年纪日
			String doyThreelength = StaticUtil.getThreeLengthCountDays(doy);
			
			//小时
			int hour = StaticUtil.getHour(time);
			
			String twoMonth = StaticUtil.getMonth(time);
			 char c = 'a';
			 c+=hour;
			 String houStr = String.valueOf(c);
			//年纪周
			int wwww = GpsWeek.getGPSWeek(time);
			
			int d = GpsWeek.gpsWeekNumber(time);
			
			timeMap.put("yyyy", fouryear);
			timeMap.put("yy", twoyear);
			timeMap.put("h", houStr);
			timeMap.put("wwww", wwww+"");
			timeMap.put("doy", doyThreelength);
			timeMap.put("d", d+"");
			timeMap.put("mm", twoMonth);
			
		return timeMap;
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 文件拷贝
	 * @param sourcePath
	 * @param targetFile
	 * @return
	 */
	private int fileCopy(String sourcePath,String targetPath){
		
		if(sourcePath==null || targetPath==null || "".equals(sourcePath) || "".equals(targetPath)){
			logger.error("fileCopy() sourcePath or targetPath is null");
			return 0;
		}
		
		sourcePath = sourcePath.replaceAll("\\\\","/");
		targetPath = targetPath.replaceAll("\\\\","/");
		File sourceFile = new File(sourcePath);
		
		File targetFile = new File(targetPath);
		if(!sourceFile.exists()){
			logger.error("fileCopy() sourcePath is not existed!");
			return 0;
		}
		
		//目标文件夹目录，不存在的时候要创建一下
		File targetDirFilie = new File(targetPath.substring(0,targetPath.lastIndexOf("/")));
		if(!targetDirFilie.exists()){
			targetDirFilie.mkdirs();
		}
		if(targetFile.exists()){
			targetFile.delete();
		}
		
		boolean copyResult = copy(sourceFile, targetFile);
		
		//拷贝成功时候返回1
		if(copyResult){
			return 1;
		}
		//拷贝失败返回0
		return 0;
	}
	
	
	/**
	 * 文件拷贝
	 * @param src
	 * @param dst
	 * @return
	 */
	private boolean copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src),
					BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int site = 0;
			while ((site = in.read(buffer)) > 0) {
				out.write(buffer, 0, site);
			}
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("copy() copy file failed");
		}finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("copy() in close failed");
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("copy() out close failed");
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 根据拼接的JSON的map，和初始化的Map，以及获得的时间map，最终返回存放在nas中的全路径
	 * @param jsonMap
	 * @return
	 */
	private String createFilePath(Map<String,String> jsonMap){
		String nasFilePath = null;
		
		if(jsonMap.get("WORKDATE") == null){
			logger.error("createFilePath() Map [WORKDATE] is null!");
			return null;
		}
		String workdate = jsonMap.get("WORKDATE");
		String filetype = jsonMap.get("FILETYPE");
		
		Map<String,String> dateMap  = dateConvert(workdate);
		nasFilePath = templatemap.get(filetype);
		if(nasFilePath==null || "".equals(nasFilePath)){
			logger.error("createFilePath() path template is null!");
			return null;
		}
		
		if(nasFilePath.contains("$(yyyy)")){
			nasFilePath = nasFilePath.replace("$(yyyy)", dateMap.get("yyyy"));
		}
		
		if(nasFilePath.contains("$(yy)")){
			
			nasFilePath = nasFilePath.replace("$(yy)", dateMap.get("yy"));
		}
		
		if(nasFilePath.contains("$(doy)")){
			nasFilePath = nasFilePath.replace("$(doy)", dateMap.get("doy"));
		}
		
		if(nasFilePath.contains("$(h)")){
			nasFilePath = nasFilePath.replace("$(h)", dateMap.get("h"));
		}
		
		if(nasFilePath.contains("$(d)")){
			nasFilePath = nasFilePath.replace("$(d)", dateMap.get("d"));
		}
		
		if(nasFilePath.contains("$(wwww)")){
			nasFilePath = nasFilePath.replace("$(wwww)", dateMap.get("wwww"));
		}
		
		if(nasFilePath.contains("$(ssss)")){
			nasFilePath = nasFilePath.replace("$(ssss)", jsonMap.get("SSSS").toLowerCase());
		}
		
		if(nasFilePath.contains("$(null)")){
			nasFilePath = nasFilePath.replace("$(null)", jsonMap.get("default"));
		}
		
		if(nasFilePath.contains("$(mm)")){
			nasFilePath = nasFilePath.replace("$(mm)", dateMap.get("mm"));
		}
		
		return nasFilePath;
	}
	
	/**
	 * 根据文件类型（数据库表名）和文件在表中的记录的id号获取文件的全路径
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	private String getFilePath(String fileType,int fileId){
		
		String findSql = "SELECT FILEPATH FROM "+fileType +" WHERE ID = '"+fileId+"'";
		try {
			st = con.prepareStatement(findSql);
			rs = st.executeQuery();
			
			if(rs.next()){
				return rs.getString("FILEPATH");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	/**
	 * get file path(not real) by filetype and fileid
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	private String getFileState(String fileType,int fileId){
		
		String findSql = "SELECT FILESTATE FROM "+fileType +" WHERE ID = '"+fileId+"'";
		try {
			st = con.prepareStatement(findSql);
			rs = st.executeQuery();
			
			if(rs.next()){
				return rs.getString("FILESTATE");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	/**
	 * 根据总表中WORKTYPE获取ALLCOL字段值
	 * @param fileType
	 * @return
	 */
	private String getFileProperty(String fileType){
		
		String findSql = "SELECT ALLCOL FROM WORKTYPETABLE WHERE WORKTYPE = '"+fileType+"'";
		try {
			st = con.prepareStatement(findSql);
			rs = st.executeQuery();
			
			if(rs.next()){
				return rs.getString("ALLCOL");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
	/**通过json字符串返回一个Map集合
	 * 
	 * @param json
	 * @return
	 */
	private Map<String,String> getMapByJsonStr(String json){
		
		try{
			if(json==null || json.equals("")){
				return null;
			}
			
			Type type = new TypeToken<Map<String,String>>(){}.getType();
			
			Gson gs = new Gson();
			Map<String,String> map = gs.fromJson(json, type);
			return map;
		}catch (Exception e) {
			logger.error("getMapByJsonStr() json string parser error!");
		}
		return null;
	}
	/**
	 * remove cycle to file
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	public int recycleFile(String fileType,int fileId){
		
		//file full path
		String fileFullPath = null;
		//table record path
		String filepath = getFilePath(fileType, fileId);
		String filestate = getFileState(fileType, fileId);
		
		if(filestate.equals("0")){
			fileFullPath = naspath +filepath;
		}else{
			fileFullPath = naspath +filepath+"."+filestate;
		}
		
		File self = new File(fileFullPath);
		if(!self.exists()){
			logger.error("recycleFile() recycle file is not exist!");
			return 0;
		}
		//table record state
		// 
		fileFullPath = naspath + filepath;
		//isExist new upload file
		String findSql = "SELECT * FROM "+fileType+" WHERE FILEPATH= '"+filepath+"' AND FILESTATE = '0'";
		try {
			st = con.prepareStatement(findSql);
			rs = st.executeQuery();
			
			if(rs.next()){
//				File newUploadFile = new File(fileFullPath);
//				if(newUploadFile.exists()){
//					boolean newUploadFileDelRes = newUploadFile.delete();
//					if(!newUploadFileDelRes){
//						logger.error("recycleFile() deleteing new upload file is failed!");
//						return 0;
//					}
//				}
//				
				int id = Integer.parseInt(rs.getString("ID"));
				int deleteRes = deleteFile(fileType, id);
				
				if(deleteRes==0){
					logger.error("recycleFile() delete new upload file is failed!");
					return 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		boolean renameRes = self.renameTo(new File(naspath +filepath));
		if(!renameRes){
			logger.error("recycleFile() "+self.getAbsolutePath()+" file rename to ["+naspath +filepath+"] is failed!");
		}
		// updata file state
		
		String updateSql = "UPDATE "+fileType+" SET FILESTATE ='0' WHERE ID ='"+fileId+"'";
		try {
			st = con.prepareStatement(updateSql);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return 1;
	}
	/**
	 * remove file to cycle
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	public int cycleFile(String fileType,int fileId){
		
		//file full path
		String fileFullPath = null;
		//table record path
		String filepath = getFilePath(fileType, fileId);
		
		fileFullPath = naspath +filepath;
		
		File targetFile = new File(fileFullPath);
		
		if(!targetFile.exists()){
			logger.error("cycleFile() nas file is not exist!");
			return 0;
		}
		
		
		String findMaxIdSql = "SELECT MAX(FILESTATE) AS FILESTATE FROM "+fileType+" WHERE FILEPATH = '"+filepath+"' AND FILESTATE >=0";
		int maxFileState = 0;
		try {
			st = con.prepareStatement(findMaxIdSql);
			rs = st.executeQuery();
			
			if(rs.next()){
				if(rs.getString("FILESTATE")!=null && !"".equals(rs.getString("FILESTATE"))){
					//maxState = maxState+1;
					maxFileState = Integer.parseInt(rs.getString("FILESTATE"))+1;
				}
			}
			//update state by id 
			
			String updateState = "UPDATE "+fileType+" SET FILESTATE ='"+maxFileState+"' WHERE ID='"+fileId+"'";
			st = con.prepareStatement(updateState);
			st.executeUpdate();
			
			boolean renameFileRes = targetFile.renameTo(new File(fileFullPath+"."+maxFileState));
			if(!renameFileRes){
				logger.error("cycleFile() rename nas file is failed!");
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return 1;
	}
	
	public long getFileSize(String fileType,int state){
		String findSql = "SELECT SUM(FILESIZE) AS FILESIZE FROM "+fileType+" WHERE FILESTATE = '"+state+"'";
		try {
			st = con.prepareStatement(findSql);
			rs = st.executeQuery();
			
			if(rs.next()){
				if(rs.getString("FILESIZE")!=null && !"".equals(rs.getString("FILESIZE"))){
					return Long.valueOf(rs.getString("FILESIZE"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return 0;
	}
	/**
	 * 根据文件路径获取文件大小
	 * @param filepath
	 * @return
	 */
	private long getSingleFileSize(String filepath){
		
		File f = new File(filepath);
		if(!f.exists()){
			logger.error("getSingleFileSize() file is not exit!");
			return -1;
		}
		
		return f.length();
		
	}
	
	/**
	 * //real delete file
	 * @param filePath  full path
	 * @param json string
	 * @return
	 */
	public int deleteFile(String fileType,int fileId){
		
		String filestate = getFileState(fileType, fileId);
		
		//
		if(filestate!=null && filestate.equals("-1")){
			logger.error("deleteFile() file is deleted before!");
			return 0;
		}
		//file full path
		String fileFullPath = null;
		//table record path
		String filepath = getFilePath(fileType, fileId);
		//file state
		if(filestate!=null && !"0".equals(filestate)){
			fileFullPath = naspath +filepath+"."+filestate;
		}else{
			fileFullPath = naspath +filepath;
		}
		
		File targetFile = new File(fileFullPath);
		
		if(!targetFile.exists()){
			logger.error("deleteFile() nas file is not exist!");
			return 0;
		}
		
		//delete file failed
		if(!targetFile.delete()){
			logger.error("deleteFile() delete nas file is failed!");
			return 0;
		}
		
		logger.info("deleteFile() delete "+fileFullPath+" file success!");
		//update oracle record 
			
		String updateSql = "UPDATE "+fileType +" SET FILESTATE = '-1' WHERE ID = '"+fileId+"'";
		try {
			st = con.prepareStatement(updateSql);
			st.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("deleteFile() "+e.getMessage());
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return 0;
	}
	
	/**
	 * 
	 * @param filePath  full path
	 * @param json string
	 * @return
	 */
	public int saveFile(String filePath,String json){
		
		Map<String,String> resultMap  = getMapByJsonStr(json);
		
		if(resultMap==null){
			logger.error("saveFile() json paraser is failed!");
			return 0;
		}
		String filetype = resultMap.get("FILETYPE");
		if(filetype==null || filetype.equals("")){
			logger.error("saveFile() file type is null!");
			return 0;
		}
		
		File saveFile = new File(filePath);
		
		if(!saveFile.exists()){
			logger.error("saveFile() filePath is not exist!");
			return 0;
		}
		
		
		boolean isInclude = isIncludeDefName(filetype);
		
		//source zip file full path
		String sourceFileFullPath = null;
		if(!isInclude){
			sourceFileFullPath = fileCompress(filePath);
			if(sourceFileFullPath==null){
				logger.error("saveFile() compress is failed!"); 
				return 0;
			}
		}else{
			sourceFileFullPath = filePath;
		}
		String fileName = saveFile.getName();
		resultMap.put("default", fileName);
		String targetPath = createFilePath(resultMap);
		if(targetPath == null || "".equals(targetPath)){
			logger.error("saveFile() target name is failed!");
			return 0;
		}
		
		//target zip file full path
		String targetFileFullPath = naspath + targetPath;
		String targetFileName = targetPath.substring(targetPath.lastIndexOf("/")+1,targetPath.length());
		
		int copyRes = fileCopy(sourceFileFullPath, targetFileFullPath);
		if(copyRes ==0){
			logger.error("saveFile() copy is failed");
			return 0;
		}
		
		//zip file size
		long fileSize = new File(sourceFileFullPath).length();
		String propertyRes = getFileProperty(filetype);
		if(propertyRes == null || "".equals(propertyRes)){
			logger.error("saveFile() get file property is failied!");
			return 0 ;
		}
		
		String id = null;
		String findSql = "SELECT ID FROM "+filetype+" WHERE FILEPATH = '"+targetPath+"' AND FILESTATE = '0'";
		try {
			st = con.prepareStatement(findSql);
			rs = st.executeQuery();
			
			if(rs.next()){
				id = rs.getString("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("saveFile() select file record is failied!");
			return 0 ;
		}finally{
			
			try {
				if(rs!=null){
				rs.close();
				rs=null;
				}
				if(st!=null){
					st.close();
					st=null;}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		String []propertyArray = propertyRes.split(";");
		String property = null;
		String nameList [] = null;
		String temName = null;
		String value = null;
		//String insertSql = "INSERT INTO "+filetype+" (CREATEDATE,FILESIZE,FILEPATH,FILENAME,FILESTATE,FILETYPE) VALUES(?,?,?,?,?,?)";
		String insertSql = "INSERT INTO "+filetype+" (CREATEDATE,FILESIZE,FILEPATH,FILENAME,FILESTATE,FILETYPE,WORKDATE";
		String insertValueSql = " VALUES(?,?,?,?,?,?,?";
		String updateSql = "UPDATE "+filetype+" " +
		"SET CREATEDATE=?,FILESIZE=?,FILEPATH=?,FILENAME=?,FILESTATE=?,FILETYPE=?,WORKDATE=?";
		String updateValueSql ="  WHERE ID=?";
		for (int i = 0; i < propertyArray.length; i++) {
			property = propertyArray[i];
			nameList = property.split(":");
			//field name
			temName = nameList[0];
			//field value
			value = resultMap.get(temName);
			
			if(temName.contains("ID") || temName.contains("FILESIZE") || temName.contains("FILENAME")
					|| temName.contains("FILEPATH") || temName.contains("FILESTATE") || temName.contains("CREATEDATE") || temName.contains("FILETYPE") ||  temName.contains("WORKDATE")){
				continue;
			}
			
			if(temName!=null && !temName.equals("") && value!=null && !value.equals("")){
				
				insertSql+=","+temName;
				insertValueSql+=",'"+value+"'";
				updateSql+=","+temName+"='"+value+"'";
			}
			
		}
		
		insertSql+=")";
		insertValueSql+=")";
		//id is null insert sql
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(id==null){
			try {
				st = con.prepareStatement(insertSql+insertValueSql);
				st.setTimestamp(1, new Timestamp(new Date().getTime()));
				st.setString(2, fileSize+"");
				st.setString(3, targetPath);
				st.setString(4, targetFileName);
				st.setString(5, "0");
				st.setString(6, filetype);
				st.setTimestamp(7, new Timestamp(sdf.parse(resultMap.get("WORKDATE")).getTime()));
				st.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("saveFile() insert file record is failed!");
				return 0;
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("saveFile() workdate style is failed!");
				return 0;
			}finally{
				
				try {
					if(rs!=null){
					rs.close();
					rs=null;
					}
					if(st!=null){
						st.close();
						st=null;}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}else{
			//update
			try {
				st = con.prepareStatement(updateSql+updateValueSql);
				st.setTimestamp(1, new Timestamp(new Date().getTime()));
				st.setString(2, fileSize+"");
				st.setString(3, targetPath);
				st.setString(4, targetFileName);
				st.setString(5, "0");
				st.setString(6, filetype);
				st.setTimestamp(7, new Timestamp(sdf.parse(resultMap.get("WORKDATE")).getTime()));
				st.setString(8,id);
				st.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("saveFile() update file record is failed!");
				return 0;
			}catch (ParseException e) {
				e.printStackTrace();
				logger.error("saveFile() workdate style is failed!");
				return 0;
			}finally{
				
				try {
					if(rs!=null){
					rs.close();
					rs=null;
					}
					if(st!=null){
						st.close();
						st=null;}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		return 1;
	}
	
	/**
	 * 根据目标文件目录和文件类型和文件id，完成文件的拷贝
	 * @param targetPath
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	public int getFile(String targetPath,String fileType,int fileId){
		
		String fullpath = naspath +getFilePath(fileType, fileId);
		
		if(fullpath ==null || "".equals(fullpath)){
			logger.error("getFile() full path is null!");
			return 0;
		}
		targetPath = targetPath.replaceAll("\\\\", "/");
		//包含文件名称的文件全路径
		targetPath = targetPath+"/"+fullpath.substring(fullpath.lastIndexOf("/")+1,fullpath.length());
		
		return fileCopy(fullpath, targetPath);
	}
	
	private boolean isIncludeDefName(String fileType){
		String filePath = templatemap.get(fileType);
		
		if(filePath==null || "".equals(filePath)){
			return false;
		}
		return (filePath.toLowerCase()).contains("$(null)");
	}
	
	/**
	 * return zip full path
	 * @param unzipPath
	 * @return
	 */
	
	//将o文件放到目录下面
	private String fileCompress(String unzipPath){
		String dPath = unzipPath;
		
		String zipPath = null;
		if(unzipPath.toLowerCase().endsWith(".z") || unzipPath.toLowerCase().endsWith(".gz")
				|| unzipPath.toLowerCase().endsWith(".zip")){
			return dPath;
		}
		if(unzipPath.toLowerCase().endsWith("o") && unzipPath.toLowerCase().contains(".")){
			dPath = dPath.substring(0,dPath.toLowerCase().lastIndexOf("o"))+"d";
			String cmd = "chmod 777 "+unzipPath +";rnx2crx < \"" + unzipPath + "\" > \"" +dPath+"\";exit";
			int dReturnCode =  execute(cmd);
			
			if(dReturnCode == 0){
				File f = new File(dPath);
				f.delete();
				
				logger.error("comprss "+dPath+"failed!");
				return null;
			}
		}
		
		zipPath = dPath+".Z";
		String cmd2 = "chmod 777 "+dPath +";gzip -c -f   \"" + dPath + "\" > \"" +zipPath+"\";exit";
		int zipReturnCode =  execute(cmd2);
		if(zipReturnCode == 0){
			File f = new File(zipPath);
			f.delete();
			
			logger.error("comprss "+zipPath+"failed!");
			return null;
		} 
		return zipPath;
	}
	/**
	 * LINUX环境下执行命令（多个命令 “;”分割command）
	 */
	private int execute(String command){
		
		File wd = new File("/bin");
		Process proc = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
			proc.getErrorStream().close();
			if (proc != null) {
				in = new BufferedReader(new InputStreamReader(proc
						.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(proc.getOutputStream())), true);
				//logger.info("------正在执行Linux命令(command):【" + "chmod 777 "+cshPath+";csh "+cshPath+";exit】");
				//**************此处代码执行多个任务，至关重要的代码
				out.println(command);
				//logger.info("------command-------" + command);
					String line;
					//while ((line = in.readLine()) != null) {
						//logger.info("------" + line+"------");
					//}
					
					//**************阻塞式依次执行，至关重要的代码
					proc.waitFor();
					
					Thread.sleep(100);
					return 1;
			}
		}catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
			if(proc!=null){
				proc.destroy();
			}
		}
		return 0;
	}
	public static void main(String[] args) {
		//DataStore ds = new DataStore();
		//ds.initConn();
//		ds.closeConn();
		//ds.dateConvert("2000-1-1 1:30:12");
		//System.out.println(wwww);
//		int i = 2;
//		 char c = 'a';
//		  c+=i;
//		  System.out.println(c);
		//System.out.println(ds.fileCopy(null, "e:\\a\\b\\test.exe"));
		
//		Map<String,String> jsonMap = new HashMap<String,String>();
//		jsonMap.put("SSSS", "BJFS");
//		jsonMap.put("WORKDATE", "2013-05-16 12:12:12");
//		jsonMap.put("FILETYPE", "GNSS_RINEX_30S");
//		jsonMap.put("default", "aa");
//	
//		System.out.println(ds.createFilePath(jsonMap));
		
		//System.out.println(ds.getFilePath("gnss_rinex_30s", 237688));
//		Map map =ds.getMapByJsonStr("{'SSSS':'BJFS','WORKDATE':'2012-11-14 12:12:12','FILETYPE':'GNSS_RINEX_30S'}");
//		
//		Set set = map.entrySet();
//		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
//			Map.Entry<String, String> name = (Map.Entry<String, String>) iterator.next();
//			System.out.println(name.getKey()+","+name.getValue());
//		}
		//System.out.println(ds.getFileProperty("GNSS_RINEX_30S"));
		
		//ds.getFile("E:\\mm\\nn", "GNSS_RINEX_30S", 237688);
		
		//System.out.println(ds.getSingleFileSize("e:\\mm\\nn\\test.exe"));
		
		//System.out.println(ds.isIncludeDefName("GNSS_RINEX_30S"));
		
		//System.out.println(ds.getFileSize("gnss_rinex_1hz", -1));
		
		//System.out.println(ds.fileCompress("/home/redhat/test2081O.13o"));
		
//		File source = new File("/home/redhat/nas/gnss/rinex/30s/2004/347/bjfs3470.04d.Z");
//		System.out.println(source.renameTo(new File("/home/redhat/nas/gnss/rinex/30s/2004/347/gggg3470.04d.Z")));
		
//		
//		int saveRes= ds.saveFile("/home/redhat/workspace/test/testeer1.00o","{\"WORKDATE\":\"2004-12-12 12:12:12\",\"SSSS\":\"BJSH\",\"FILETYPE\":\"GNSS_RINEX_30S\"}");
//		logger.info("saveRes:"+saveRes);
//		
//		int cycleRes = ds.cycleFile("GNSS_RINEX_30S",248871);
//		logger.info("cycleRes:"+cycleRes);
//		
		//int recycleRes = ds.recycleFile("GNSS_RINEX_30S",248871);
		///logger.info("recycleRes:"+recycleRes);
		
//		int deleteRes = ds.deleteFile("GNSS_RINEX_30S",248871);
//		logger.info("deleteRes:"+deleteRes);
	//	int closeRes = ds.closeConn();
		//logger.info("closeRes:"+closeRes);
		
		System.out.println("************file*************");
		
	}
	
	
}
