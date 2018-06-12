import java.io.File;

import com.highfd.bean.FileInfo;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.service.ApplyFileService;


public class ReName {
	
	public static void main(String[] args) throws Exception {
		try {
			reNameDz(new File("G:\\test"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void reNameDz(final File dir) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					reNameDz(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("d.z")){//主要小写这里
					File dzFile = fs[i];
					String zPathAndName = dzFile.getPath();
					String dzName = dzFile.getName();
					String dzNameNew = dzName.substring(0, dzName.length()-3).toLowerCase()+"d.Z";
					new File(zPathAndName+"/"+dzName).renameTo(new File(dzNameNew));  
				}
			}
		}
	}
	

}
