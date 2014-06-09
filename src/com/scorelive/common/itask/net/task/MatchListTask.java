package com.scorelive.common.itask.net.task;

import org.apache.http.message.BasicHeader;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;

public class MatchListTask extends INetTask {

	public MatchListTask(int type,long taskId, String date) {
		super(type,taskId);
		mUrl = AppConstants.MATCH_INFO+"date="
				+ date;
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
