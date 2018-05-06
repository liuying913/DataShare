package com.linux;


import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class To2ToTgd {

	//程序超时时间秒数
	private static int timeoutSeconds = 60;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = args[0];///home/tomcat/apache-tomcat-51jobrecruit
		String fileName=args[1];//aaa
		String pathFileName = path+"/"+fileName;
		System.out.println("文件： "+pathFileName);
		String command1 = "chmod 777 "+pathFileName+".T02;runpkr00 -g -d "+pathFileName+".T02;exit";
		to2ToTgd(command1);
		
		String result1 = null;
		result1 = callMethod(new Z_TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1 } );
		if(result1==null){
			System.out.println("*****to2 tgd失败！*****【"+command1+"】");
		}else{
			System.out.println("*****to2 tgd成功*****【"+command1+"】");
		}
		String command2 = "chmod 777 "+pathFileName+".tgd;teqc +quiet ++err "+path+"/teqc.log +obs "+pathFileName+".17o +nav "+pathFileName+".17n +met "+pathFileName+".17m -week 1932 "+pathFileName+".tgd;exit";
		String result2 = null;
		result2 = callMethod(new Z_TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command2 } );
		if(result2==null){
			System.out.println("*****tgd o文件 执行失败！*****【"+command2+"】");
		}else{
			System.out.println("*****tgd o文件 执行成功*****【"+command2+"】");
		}
		
		
		
		String command3 = "chmod 777 "+pathFileName+".17o;teqc +qc "+pathFileName+".17o;exit";
		String result3 = null;
		result3 = callMethod(new Z_TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command3 } );
		
		if(result3==null){
			System.out.println("*****o 到 s文件失败！*****【"+command3+"】");
		}else{
			System.out.println("*****o 到 s文件成功*****【"+command3+"】");
		}
		
		if(result3!=null){
			
			String resultSfilePath = ""+pathFileName+".17o";
			// 解析结果文件入库
			if(new File(resultSfilePath).length() > 100){
				Z_TEQC_LINUX.jiexiSfile(resultSfilePath, "", "");
			}else{
				System.out.println("test() "+resultSfilePath+" 文件大小小于100字节，不能上传！");
			}
		}
		
		
	}
	
	public static void to2ToTgd(String command1){
		//超时执行的话 结果还是null
		String result1 = null;
		result1 = callMethod(new Z_TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1 } );
		if(result1==null){
			System.out.println("*****to2 tsd失败！*****【"+command1+"】");
			return;
		}else{
			System.out.println("*****to2 tsd执行成功*****【"+command1+"】");
		}
	}
	
	
	/***
	 * 方法参数说明
	 * @param target 调用方法的当前对象
	 * @param methodName 方法名称
	 * @param parameterTypes 调用方法的参数类型
	 * @param params 参数  可以传递多个参数
	 * 
	 * */
	public static String callMethod(final Object target , final String methodName ,final Class<?>[] parameterTypes,final Object[]params){
		ExecutorService executorService = Executors.newSingleThreadExecutor();  
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
            	String value = null  ; 
            	try {
					Method method = null ; 
					method = target.getClass().getDeclaredMethod(methodName , parameterTypes ) ;  
					
					Object returnValue = method.invoke(target, params) ;  
					value = returnValue != null ? returnValue.toString() : null ;
				} catch (Exception e) {
					e.printStackTrace() ;
					throw e ; 
				}
                return value ;
            }  
        });  
          
        executorService.execute(future);  
        String result = null;  
        try{
        	/**获取方法返回值 并设定方法执行的时间为60秒*/
            result = future.get(timeoutSeconds , TimeUnit.SECONDS );  
            
        }catch (InterruptedException e) {  
            future.cancel(true);  
            System.out.println("方法执行中断"); 
        }catch (ExecutionException e) {  
            future.cancel(true);  
            System.out.println("Excuti on异常");  
        }catch (TimeoutException e) {  
            future.cancel(true);  
            System.out.println("TimeoutException异常");
        }
        executorService.shutdownNow(); 
        return result ;
	}

}
