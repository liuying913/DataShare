import java.io.File;

public class DeleteNoOdZ {
	
	public static void main(String[] args) throws Exception {
		//analysisAll_o(new File(""));
	}
	
	public static void analysisAll_o(File dir) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_o(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("o") || fileName.toLowerCase().endsWith("d.z")){
					
				}else{
					String absolutePath = fs[i].getAbsoluteFile().toString();
					System.out.println(absolutePath);
					deleteFile(absolutePath);
				}
			}
		}
	}
	
	
	
	public static boolean deleteFile(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			 System.out.println("deleteFile() " + filePath + "  file is not exist!");
			return false;
		}
		if (f.isDirectory()) {
			 System.out.println("deleteFile() " + filePath+ " is directory,delete file is failed!");
			return false;
		}
		return f.delete();
	}

}
