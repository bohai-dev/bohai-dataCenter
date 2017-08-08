package com.bohai.dataCenter.controller.exception;

/**
 * 渤海期货异常类
 * @author caojia
 */
public class BohaiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6638752890080097020L;

	private String errorCode;
	
	public BohaiException(String errorCode, String errorMsg){
		super(errorMsg);
		this.errorCode = errorCode;
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
