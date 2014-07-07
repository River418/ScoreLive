package com.scorelive.common.itask.net.task;

import java.net.URLEncoder;

import org.apache.http.message.BasicHeader;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;

public class LoginTask extends INetTask {

	public LoginTask(int type, long taskId, String name, String url,
			String openId, String userType) {
		super(type, taskId);
		mUrl = AppConstants.LOGIN_URL + "name=" + name + "&avatar=" + url
				+ "&baseid=" + openId + "&usertype=" + userType;
	}

	@Override
	public String getRequestType() {
		// TODO Auto-generated method stub
		return Http.GET;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return mUrl;
	}

	@Override
	public BasicHeader[] getHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getRequestBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
