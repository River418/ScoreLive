package com.scorelive.common.itask.net.task;

import org.apache.http.message.BasicHeader;

import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;

public class DownloadPicTask extends INetTask {

	public DownloadPicTask(int type, long taskId, String imgUrl) {
		super(type, taskId);
		mUrl = imgUrl;
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
