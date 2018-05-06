package com.linux;
/**
 * 数据服务运行时异常
 * 
 * @version 1.0
 * @Date 2012-09-05
 */
public class DataServiceRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8235182081639017874L;

	/**
	 * Creates a new instance of IntegrateRuntimeException.
	 * 
	 */
	public DataServiceRuntimeException() {
		super();
	}

	/**
	 * Creates a new instance of IntegrateRuntimeException.
	 * 
	 * @param msg
	 */
	public DataServiceRuntimeException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new instance of IntegrateRuntimeException.
	 * 
	 * @param msg
	 * @param cause
	 */
	public DataServiceRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Creates a new instance of IntegrateRuntimeException.
	 * 
	 * @param cause
	 */
	public DataServiceRuntimeException(Throwable cause) {
		super(cause);
	}
}
