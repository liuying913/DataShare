package listener;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.apache.ftpserver.ftplet.User;

import com.highfd.bean.DownHistoryInfo;
import com.highfd.common.ApplicationContextUtil.ApplicationContextUtils;
import com.highfd.common.file.FileTool;
import com.highfd.common.map.MapAll;
import com.highfd.dao.FtpDao;
import com.jdbc.service.AlarmJdbcService;
import com.jdbc.service.impl.AlarmJdbcServiceImpl;

public class EventListener extends DefaultFtplet{

	@Override
	public FtpletResult beforeCommand(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		String command = request.getCommand();
		if("LIST".equals(command)){
			FileSystemView fileSystemView = session.getFileSystemView();
			fileSystemView.dispose();
		}
		return super.beforeCommand(session, request);
	}


	@Override
	public void init(FtpletContext ftpletContext) throws FtpException {
		System.out.println("ini");
		super.init(ftpletContext);
	}
	
	@Override
	public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException {
		return super.afterCommand(session, request, reply);
	}


	@Override
	public void destroy() {
		System.out.println("ccc");
		super.destroy();
	}

	@Override
	public FtpletResult onAppendEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
		System.out.println("eee");
		return super.onAppendEnd(session, request);
	}

	@Override
	public FtpletResult onAppendStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
		System.out.println("f");
		return super.onAppendStart(session, request);
	}

	

	@Override
	public FtpletResult onDeleteEnd(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("h");
		return super.onDeleteEnd(session, request);
	}

	@Override
	public FtpletResult onDeleteStart(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("i");
		return super.onDeleteStart(session, request);
	}

	@Override
	public FtpletResult onDisconnect(FtpSession session) throws FtpException,IOException {
		//System.out.println("j");
		return super.onDisconnect(session);
	}

	@Override
	public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
		System.out.println("k");
		//String requestLine = request.getRequestLine();//获得路径
		return super.onDownloadEnd(session, request);
	}

	@Override
	public FtpletResult onConnect(FtpSession session) throws FtpException, IOException {
		return super.onConnect(session);
	}
	@Override
	public FtpletResult onLogin(FtpSession session, FtpRequest request)throws FtpException, IOException {
		return super.onLogin(session, request);
	}
	
	@Override
	public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
		String argument = request.getArgument();
		String userName = "";
		User user = session.getUser();
		if(null!=user){
			userName = user.getName();
		}else{
			System.out.println("session   user 为null");
		}
		
		String ftpRealPath = MapAll.mapUserName.get(userName).getFtpRealUserPath();
		if(argument.startsWith(ftpRealPath) || ftpRealPath.startsWith(argument)){//判断是否是 用户自己的ftp空间路径
			System.out.println("用户自己的空间，不需要进行下载记录！！！");
		}else{
			DownHistoryInfo info = new DownHistoryInfo();
			info.setUserName(userName);
			info.setDownTime(new Timestamp(System.currentTimeMillis()));
			
			//判断是陆态30S数据  还是  地震应急数据
			info.setFileName(FileTool.getFileNameByPath(argument));//原来的  info.setFileName(FileTool.getFileNameByFTPPath(name,argument));//根据  ftp账号中的  id
			
			FtpDao ftpDao=(FtpDao)ApplicationContextUtils.context.getBean("FtpDao");
			ftpDao.insertDictionary(info);
		}
		return super.onDownloadStart(session, request);
	}

	

	@Override
	public FtpletResult onMkdirEnd(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("n");
		return super.onMkdirEnd(session, request);
	}

	@Override
	public FtpletResult onMkdirStart(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("o");
		return super.onMkdirStart(session, request);
	}

	@Override
	public FtpletResult onRenameEnd(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("p");
		return super.onRenameEnd(session, request);
	}

	@Override
	public FtpletResult onRenameStart(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("q");
		return super.onRenameStart(session, request);
	}

	@Override
	public FtpletResult onRmdirEnd(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("r");
		return super.onRmdirEnd(session, request);
	}

	@Override
	public FtpletResult onRmdirStart(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("s");
		return super.onRmdirStart(session, request);
	}

	@Override
	public FtpletResult onSite(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("t");
		return super.onSite(session, request);
	}

	@Override
	public FtpletResult onUploadEnd(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("上传完毕");
		MapAll.ftpUpTime=System.currentTimeMillis();
		return super.onUploadEnd(session, request);
	}

	@Override
	public FtpletResult onUploadStart(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
        /*try {
        	String fileName = request.getArgument();
        	FtpFile file = session.getFileSystemView().getFile(fileName);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }*/
		return super.onUploadStart(session, request);
	}

	@Override
	public FtpletResult onUploadUniqueEnd(FtpSession session, FtpRequest request)
			throws FtpException, IOException {
		System.out.println("w");
		return super.onUploadUniqueEnd(session, request);
	}

	@Override
	public FtpletResult onUploadUniqueStart(FtpSession session,
			FtpRequest request) throws FtpException, IOException {
		System.out.println("x");
		return super.onUploadUniqueStart(session, request);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		System.out.println("y");
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("z");
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("zz1");
		super.finalize();
	}

	@Override
	public int hashCode() {
		System.out.println("zz2");
		return super.hashCode();
	}

	@Override
	public String toString() {
		System.out.println("zz3");
		return super.toString();
	}
	

}
