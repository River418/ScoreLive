package com.scorelive.common.http;


/**
 * @author SawRen
 * @email: sawren@tencent.com
 * @date 2010-4-27
 */
public class HttpResponseException extends Exception {
	private static final long serialVersionUID = 1L;

	private int stateCode = 200;

	public int getStateCode() {
		return stateCode;
	}

	public HttpResponseException(int aStateCode) {
		stateCode = aStateCode;
	}
}
