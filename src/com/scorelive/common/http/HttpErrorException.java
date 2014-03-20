package com.scorelive.common.http;


import java.io.IOException;

public class HttpErrorException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8842974215324366086L;

	private int stateCode = 200;

	private String errorStr;
	
	public HttpErrorException() {
		
	}
	public HttpErrorException(int aStateCode,String errorMsg) {
		stateCode = aStateCode;
		errorStr = errorMsg;
	}

	public String getErrorStr() {
		return errorStr;
	}
	
	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}
	
	@Override
	public String toString() {
		return "["+ errorStr + "]  code : " + stateCode;
	}

}
