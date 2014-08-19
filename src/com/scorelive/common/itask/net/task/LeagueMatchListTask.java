package com.scorelive.common.itask.net.task;

import java.util.ArrayList;

import org.apache.http.message.BasicHeader;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;
import com.scorelive.module.League;

public class LeagueMatchListTask extends INetTask {

	public LeagueMatchListTask(int type, long taskId, String date,
			ArrayList<League> leagueIds) {
		super(type, taskId);
		StringBuilder sb = new StringBuilder();
		for (League league : leagueIds) {
			if (league.isSelected) {
				sb.append(league.id);
				sb.append(",");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		mUrl = AppConstants.MATCH_INFO_BY_LEAGUE + "date=" + date + "&lid="
				+ sb.toString();
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
