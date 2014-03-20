package com.scorelive.common.http;


import java.io.IOException;

/**
 * for 416 range
 * 
 * @author ivanyang
 * 
 */
public class RangeException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RangeException() {
		super();
	}
}
