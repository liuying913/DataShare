/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ftpserver.filesystem.nativefs.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.impl.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.highfd.bean.FTPApplyYear;
import com.highfd.bean.FileInfo;
import com.highfd.common.ApplicationContextUtil.ApplicationContextUtils;
import com.highfd.common.file.FileTool;
import com.highfd.common.map.MapAll;
import com.highfd.dao.FtpDao;
import com.jdbc.service.AlarmJdbcService;
import com.jdbc.service.impl.AlarmJdbcServiceImpl;

/**
 * <strong>Internal class, do not use directly.</strong>
 * 
 * This class wraps native file object.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NativeFtpFile implements FtpFile {

    private final Logger LOG = LoggerFactory.getLogger(NativeFtpFile.class);

    // the file name with respect to the user root.
    // The path separator character will be '/' and
    // it will always begin with '/'.
    private String fileName;

    private File file;

    private User user;

    /**
     * Constructor, internal do not use directly.
     */
    protected NativeFtpFile(final String fileName, final File file,
            final User user) {
    	//System.out.println("GG  "+user.getName()+"  "+ file.getName()+file.getPath());
        if (fileName == null) {
            throw new IllegalArgumentException("fileName can not be null");
        }
        if (file == null) {
            throw new IllegalArgumentException("file can not be null");
        }

        if (fileName.length() == 0) {
            throw new IllegalArgumentException("fileName can not be empty");
        } else if (fileName.charAt(0) != '/') {
            throw new IllegalArgumentException(
                    "fileName must be an absolut path");
        }
        this.fileName = fileName;
        this.file = file;
        this.user = user;
    }

    /**
     * Get full name.
     */
    public String getAbsolutePath() {
        // strip the last '/' if necessary
        String fullName = fileName;
        int filelen = fullName.length();
        if ((filelen != 1) && (fullName.charAt(filelen - 1) == '/')) {
            fullName = fullName.substring(0, filelen - 1);
        }
        return fullName;
    }

    /**
     * Get short name.
     */
    public String getName() {

        // root - the short name will be '/'
        if (fileName.equals("/")) {
            return "/";
        }

        // strip the last '/'
        String shortName = fileName;
        int filelen = fileName.length();
        if (shortName.charAt(filelen - 1) == '/') {
            shortName = shortName.substring(0, filelen - 1);
        }

        // return from the last '/'
        int slashIndex = shortName.lastIndexOf('/');
        if (slashIndex != -1) {
            shortName = shortName.substring(slashIndex + 1);
        }
        return shortName;
    }

    /**
     * Is a hidden file?
     */
    public boolean isHidden() {
        return file.isHidden();
    }

    /**
     * Is it a directory?
     */
    public boolean isDirectory() {
        return file.isDirectory();
    }

    /**
     * Is it a file?
     */
    public boolean isFile() {
        return file.isFile();
    }

    /**
     * Does this file exists?
     */
    public boolean doesExist() {
        return file.exists();
    }

    /**
     * Get file size.
     */
    public long getSize() {
        return file.length();
    }

    /**
     * Get file owner.
     */
    public String getOwnerName() {
        return "user";
    }

    /**
     * Get group name
     */
    public String getGroupName() {
        return "group";
    }

    /**
     * Get link count
     */
    public int getLinkCount() {
        return file.isDirectory() ? 3 : 1;
    }

    /**
     * Get last modified time.
     */
    public long getLastModified() {
        return file.lastModified();
    }

    /**
     * {@inheritDoc}
     */
    public boolean setLastModified(long time) {
        return file.setLastModified(time);
    }

    /**
     * Check read permission.
     */
    public boolean isReadable() {
        return file.canRead();
    }

    /**
     * Check file write permission.
     */
    public boolean isWritable() {
        LOG.debug("Checking authorization for " + getAbsolutePath());
        if (user.authorize(new WriteRequest(getAbsolutePath())) == null) {
            LOG.debug("Not authorized");
            return false;
        }

        LOG.debug("Checking if file exists");
        if (file.exists()) {
            LOG.debug("Checking can write: " + file.canWrite());
            return file.canWrite();
        }

        LOG.debug("Authorized");
        return true;
    }

    /**
     * Has delete permission.
     */
    public boolean isRemovable() {

        // root cannot be deleted
        if ("/".equals(fileName)) {
            return false;
        }

        /* Added 12/08/2008: in the case that the permission is not explicitly denied for this file
         * we will check if the parent file has write permission as most systems consider that a file can
         * be deleted when their parent directory is writable.
        */
        String fullName = getAbsolutePath();

        // we check FTPServer's write permission for this file.
        if (user.authorize(new WriteRequest(fullName)) == null) {
            return false;
        }
        // In order to maintain consistency, when possible we delete the last '/' character in the String
        int indexOfSlash = fullName.lastIndexOf('/');
        String parentFullName;
        if (indexOfSlash == 0) {
            parentFullName = "/";
        } else {
            parentFullName = fullName.substring(0, indexOfSlash);
        }

        // we check if the parent FileObject is writable.
        NativeFtpFile parentObject = new NativeFtpFile(parentFullName, file
                .getAbsoluteFile().getParentFile(), user);
        return parentObject.isWritable();
    }

    /**
     * Delete file.
     */
    public boolean delete() {
        boolean retVal = false;
        if (isRemovable()) {
            retVal = file.delete();
        }
        return retVal;
    }

    /**
     * Move file object.
     */
    public boolean move(final FtpFile dest) {
        boolean retVal = false;
        if (dest.isWritable() && isReadable()) {
            File destFile = ((NativeFtpFile) dest).file;

            if (destFile.exists()) {
                // renameTo behaves differently on different platforms
                // this check verifies that if the destination already exists,
                // we fail
                retVal = false;
            } else {
                retVal = file.renameTo(destFile);
            }
        }
        return retVal;
    }

    /**
     * Create directory.
     */
    public boolean mkdir() {
        boolean retVal = false;
        if (isWritable()) {
            retVal = file.mkdir();
        }
        return retVal;
    }

    /**
     * Get the physical file object.
     */
    public File getPhysicalFile() {
        return file;
    }
    
    
    /*public List<File> getTest(File[] files){
    	List<File> list = new ArrayList<File>();
        //成功案例
        boolean ftpManager = false;
        List<FileInfo> applyFileList = new ArrayList<FileInfo>();
        Set<String> applyFileSet = new HashSet<String>();
        AlarmJdbcService as = new AlarmJdbcServiceImpl();
        try {
        	String userName = user.getName();
        	//String year = FileTool.getYearByFileName(files[0].getName());
        	String year = userName.substring(0, 4);
        	String applyId = userName.substring(userName.indexOf("_")+1, userName.length());
        	ftpManager = as.ftpManageFlag(applyId);
        	
        	//不是管理员走这里
        	if(!ftpManager){
        		if(year !=null){
            		applyFileList = as.applyFileList(applyId,year);//放入list集合
            		
            		//路径去重列表
            		for(int i=0;i<applyFileList.size();i++){
            			String fileInfoPaths = applyFileList.get(i).getFilePath();
            			if(null!=fileInfoPaths && !"".equals(fileInfoPaths)){
            				applyFileSet.add(fileInfoPaths);
            			}
            		}
            	}
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
    	if(ftpManager){//管理员走这里
    		 for(int i=0;i<files.length;i++){
    			 list.add(files[i]);
             }
    	}else{//非 管理员
            for(int i=0;i<files.length;i++){
            	if(files[i].isDirectory()){//目录权限设置
            		String absolutePath = files[i].getAbsolutePath();
            		for (String str : applyFileSet) {
            			if(str.indexOf(absolutePath)>-1){
            				list.add(files[i]);
            				return list;
            			}
            		}
            	}else{//文件权限设置
            		for(int f=0;f<applyFileList.size();f++){
                		FileInfo applyFile = applyFileList.get(f);
                		if(null!=applyFile.getFileName() && !"".endsWith(applyFile.getFileName())){
                    		if(applyFile.getFileName().equalsIgnoreCase(files[i].getName()) ){
                    			list.add(files[i]);
                    		}
                		}
                		
                	}
            	}
            }
    	}
    	return list;
    }*/


    /**
     * List files. If not a directory or does not exist, null will be returned.
     */
    public List<FtpFile> listFiles() {
        // is a directory
        if (!file.isDirectory()) {
            return null;
        }
        
        //System.out.println(file.getName()+"  文件夹名称"+file.getAbsolutePath() +" 11 "+" "+file.getParent()+"  222 "+file.getPath());
        //System.out.println("MM  "+user.getName()+"    MM");
        // directory - return all the files
        File[] files = file.listFiles();
        System.out.println(file.getAbsolutePath()+"     "+file.getPath());
        if (files == null) {
            return null;
        }
        //成功案例
        //boolean ftpManager = false;
        String userName = user.getName();
        List<FileInfo> applyFileList = new ArrayList<FileInfo>();
        Set<String> applyFileSet = new HashSet<String>();
        //AlarmJdbcService as = new AlarmJdbcServiceImpl();
        try {
        	//String year = FileTool.getYearByFileName(files[0].getName());
        	//String year = userName.substring(0, 4);
        	//String applyId = userName.substring(userName.indexOf("_")+1, userName.length());
        	//ftpManager = as.ftpManageFlag(applyId);//判断是否管理员
        	//不是管理员走这里
        	//if(!ftpManager){
        		//if(year !=null){
        			//boolean s30OrEarth = true;
        			//if(userName.substring(0, userName.indexOf("_")).length()==14){
        				//applyId = as.earthEventId(applyId)+"";
        				//s30OrEarth=false;
        			//}
            		//applyFileList = as.applyFileList(applyId,year,s30OrEarth);//放入list集合
        			//applyFileList = as.queryFtpApplyYear(MapAll.mapUserName.get(userName)+"", "5", "0");
        			FtpDao ftpDao=(FtpDao)ApplicationContextUtils.context.getBean("FtpDao");
        			applyFileList=ftpDao.queryAllFilesByUserName(userName, file.getPath());
        			if(null==applyFileList){
        				applyFileList = new ArrayList<FileInfo>();
        			}
        			
            		//添加用户所拥有的路径集合    路径去重列表
            		for(int i=0;i<applyFileList.size();i++){
            			String fileInfoPaths = applyFileList.get(i).getFilePath();
            			if(null!=fileInfoPaths && !"".equals(fileInfoPaths)){
            				applyFileSet.add(fileInfoPaths);
            				//System.out.println("路径集合： "+fileInfoPaths);
            			}
            		}
            	//}
        	//}
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        List<File> list = new ArrayList<File>();
        
        
    	//if(ftpManager){//管理员走这里
    		 //for(int i=0;i<files.length;i++){
    		 //	list.add(files[i]);
             //}
    	//}else{//非 管理员
        	String ftpRealPath = MapAll.mapUserName.get(userName).getFtpRealUserPath();
            for(int i=0;i<files.length;i++){
            	if(files[i].isDirectory()){//目录权限设置
            		String absolutePath = files[i].getAbsolutePath();
            		//System.out.println(absolutePath+" 2 "+ftpRealPath);
            		if(absolutePath.startsWith(ftpRealPath) || ftpRealPath.startsWith(absolutePath)){//判断是否是 用户自己的ftp空间路径
            			list.add(files[i]);
            		}else{
            			for (String str : applyFileSet) {
                			if(str.indexOf(absolutePath)>-1){
                				list.add(files[i]);
                				break;
                			}
                		}
            		}
            		
            	}else{//数据文件  权限设置
            		if(files[i].getParent().startsWith(ftpRealPath)){//判断是否是 用户自己的ftp空间路径
            			list.add(files[i]);
            			continue;
            		}
            		for(int f=0;f<applyFileList.size();f++){
                		FileInfo applyFile = applyFileList.get(f);
                		if(null!=applyFile.getFileName() && !"".endsWith(applyFile.getFileName())){
                    		if(applyFile.getFileName().equalsIgnoreCase(files[i].getName()) ){
                    			list.add(files[i]);
                    		}
                		}
                		
                	}
            	}
            }
    	//}

        
        files = new File[list.size()];
        for(int i=0;i<list.size();i++){
        	files[i]=list.get(i);
        }
      
        // make sure the files are returned in order
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        // get the virtual name of the base directory
        String virtualFileStr = getAbsolutePath();
        if (virtualFileStr.charAt(virtualFileStr.length() - 1) != '/') {
            virtualFileStr += '/';
        }

        // now return all the files under the directory
        FtpFile[] virtualFiles = new FtpFile[files.length];
        for (int i = 0; i < files.length; ++i) {
            File fileObj = files[i];
            String fileName = virtualFileStr + fileObj.getName();
            virtualFiles[i] = new NativeFtpFile(fileName, fileObj, user);
        }
        return Collections.unmodifiableList(Arrays.asList(virtualFiles));
    }

    /**
     * Create output stream for writing.
     */
    public OutputStream createOutputStream(final long offset)
            throws IOException {

        // permission check
        if (!isWritable()) {
            throw new IOException("No write permission : " + file.getName());
        }

        // create output stream
        final RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(offset);
        raf.seek(offset);

        // The IBM jre needs to have both the stream and the random access file
        // objects closed to actually close the file
        return new FileOutputStream(raf.getFD()) {
            @Override
            public void close() throws IOException {
                super.close();
                raf.close();
            }
        };
    }

    /**
     * Create input stream for reading.
     */
    public InputStream createInputStream(final long offset) throws IOException {

        // permission check
        if (!isReadable()) {
            throw new IOException("No read permission : " + file.getName());
        }

        // move to the appropriate offset and create input stream
        final RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(offset);

        // The IBM jre needs to have both the stream and the random access file
        // objects closed to actually close the file
        return new FileInputStream(raf.getFD()) {
            @Override
            public void close() throws IOException {
                super.close();
                raf.close();
            }
        };
    }

    /**
     * Normalize separate character. Separate character should be '/' always.
     */
    public final static String normalizeSeparateChar(final String pathName) {
        String normalizedPathName = pathName.replace(File.separatorChar, '/');
        normalizedPathName = normalizedPathName.replace('\\', '/');
        return normalizedPathName;
    }

    /**
     * Get the physical canonical file name. It works like
     * File.getCanonicalPath().
     * 
     * @param rootDir
     *            The root directory.
     * @param currDir
     *            The current directory. It will always be with respect to the
     *            root directory.
     * @param fileName
     *            The input file name.
     * @return The return string will always begin with the root directory. It
     *         will never be null.
     */
    public final static String getPhysicalName(final String rootDir,
            final String currDir, final String fileName) {
        return getPhysicalName(rootDir, currDir, fileName, false);
    }

    public final static String getPhysicalName(final String rootDir,
            final String currDir, final String fileName,
            final boolean caseInsensitive) {

        // get the starting directory
        String normalizedRootDir = normalizeSeparateChar(rootDir);
        if (normalizedRootDir.charAt(normalizedRootDir.length() - 1) != '/') {
            normalizedRootDir += '/';
        }

        String normalizedFileName = normalizeSeparateChar(fileName);
        String resArg;
        String normalizedCurrDir = currDir;
        if (normalizedFileName.charAt(0) != '/') {
            if (normalizedCurrDir == null) {
                normalizedCurrDir = "/";
            }
            if (normalizedCurrDir.length() == 0) {
                normalizedCurrDir = "/";
            }

            normalizedCurrDir = normalizeSeparateChar(normalizedCurrDir);

            if (normalizedCurrDir.charAt(0) != '/') {
                normalizedCurrDir = '/' + normalizedCurrDir;
            }
            if (normalizedCurrDir.charAt(normalizedCurrDir.length() - 1) != '/') {
                normalizedCurrDir += '/';
            }

            resArg = normalizedRootDir + normalizedCurrDir.substring(1);
        } else {
            resArg = normalizedRootDir;
        }

        // strip last '/'
        if (resArg.charAt(resArg.length() - 1) == '/') {
            resArg = resArg.substring(0, resArg.length() - 1);
        }

        // replace ., ~ and ..
        // in this loop resArg will never end with '/'
        StringTokenizer st = new StringTokenizer(normalizedFileName, "/");
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();

            // . => current directory
            if (tok.equals(".")) {
                continue;
            }

            // .. => parent directory (if not root)
            if (tok.equals("..")) {
                if (resArg.startsWith(normalizedRootDir)) {
                    int slashIndex = resArg.lastIndexOf('/');
                    if (slashIndex != -1) {
                        resArg = resArg.substring(0, slashIndex);
                    }
                }
                continue;
            }

            // ~ => home directory (in this case the root directory)
            if (tok.equals("~")) {
                resArg = normalizedRootDir.substring(0, normalizedRootDir
                        .length() - 1);
                continue;
            }

            if (caseInsensitive) {
                File[] matches = new File(resArg)
                        .listFiles(new NameEqualsFileFilter(tok, true));

                if (matches != null && matches.length > 0) {
                    tok = matches[0].getName();
                }
            }

            resArg = resArg + '/' + tok;
        }

        // add last slash if necessary
        if ((resArg.length()) + 1 == normalizedRootDir.length()) {
            resArg += '/';
        }

        // final check
        if (!resArg.regionMatches(0, normalizedRootDir, 0, normalizedRootDir
                .length())) {
            resArg = normalizedRootDir;
        }

        return resArg;
    }

    /**
     * Implements equals by comparing getCanonicalPath() for the underlying file instabnce.
     * Ignores the fileName and User fields
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NativeFtpFile) {
            String thisCanonicalPath;
            String otherCanonicalPath;
            try {
                thisCanonicalPath = this.file.getCanonicalPath();
                otherCanonicalPath = ((NativeFtpFile) obj).file
                        .getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException("Failed to get the canonical path", e);
            }

            return thisCanonicalPath.equals(otherCanonicalPath);
        }
        return false;
    }


	@Override
	public int hashCode() {
		try {
			return file.getCanonicalPath().hashCode();
		} catch (IOException e) {
			return 0;
		}
	}
}
