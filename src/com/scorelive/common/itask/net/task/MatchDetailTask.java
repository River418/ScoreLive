package com.scorelive.common.itask.net.task;

import org.apache.http.message.BasicHeader;

import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;
import com.scorelive.common.utils.AppConstants;

public class MatchDetailTask extends INetTask{

	public MatchDetailTask(int type,long taskId,int id) {
		super(type,taskId);
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
