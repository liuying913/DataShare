package com.linux;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 该类是属性文件工具类，用来获取属性文件的流。
 * 
 * @version 1.0
 * @Date 2012-09-05
 */
public class PropertyHelper {

	/**
	 * 用来获取指定属性文件的流
	 * 
	 * @param cls
	 *            类的class对象
	 * @param propFile
	 *            属性文件名称
	 * @return 文件流
	 */
	public static InputStream getPropFile(Class cls, String propFile) {
		try {
			ClassLoader loader = cls.getClassLoader();
			// 先从当前类所处路径的根目录中寻找属性文件
			InputStream in = loader.getResourceAsStream(propFile);
			if (in != null) {
				return in;
			}
			// 没有找到，就从该类所处的包目录中查找属性文件
			Package pack = cls.getPackage();
			if (pack != null) {
				String packName = pack.getName();
				String path = "";
				if (packName.indexOf(".") < 0) {
					path = packName + "/";
				} else {
					int start = 0, end = 0;
					end = packName.indexOf(".");
					while (end != -1) {
						path = path + packName.substring(start, end) + "/";
						start = end + 1;
						end = packName.indexOf(".", start);
					}
					path = path + packName.substring(start) + "/";
				}
				in = loader.getResourceAsStream(path + propFile);
				if (in != null) {
					return in;
				}
			}
			// 如果没有找到，再从当前系统的用户目录中进行查找
			File f = null;
			String curDir = System.getProperty("user.dir");
			f = new File(curDir, propFile);
			if (f.exists()) {
				return new FileInputStream(f);
			}
			// 如果还是没有找到，则从系统所有的类路径中查找
			String classpath = System.getProperty("java.class.path");
			String[] cps = classpath
					.split(System.getProperty("path.separator"));
			for (int i = 0; i < cps.length; i++) {
				f = new File(cps[i], propFile);
				if (f.exists()) {
					break;
				}
				f = null;
			}
			if (f != null) {
				return new java.io.FileInputStream(f);
			}
			return null;
		} catch (FileNotFoundException e) {
			throw new DataServiceRuntimeException(e);
		}
	}
}
