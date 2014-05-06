package com.scorelive.common.net.task;

import org.apache.http.message.BasicHeader;

import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;
import com.scorelive.module.AppConstants;

public class MatchDetailTask extends INetTask{

	public MatchDetailTask(long taskId,int id) {
		super(taskId);
		// TODO Auto-generated constructor stub
		mUrl = AppConstants.MATCH_DETAIL+"id="+id;
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
