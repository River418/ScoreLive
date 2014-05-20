package com.scorelive.common.itask.quick.task;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.sqlite.SQLiteException;

import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.module.Group;
import com.scorelive.module.Match;

public class QueryMatchListByGroup extends IShortTask {

	private ArrayList<Group> mGroupList = null;

	public QueryMatchListByGroup(int type, long taskId, ArrayList<Group> list) {
		super(type, taskId);
		mGroupList = list;
	}

	@Override
	public void run() {
		try {
			HashMap<String, ArrayList<Match>> mMap = new HashMap<String, ArrayList<Match>>();
			if (mGroupList != null) {
				for (Group group : mGroupList) {
					int groupId = group.id;
					ArrayList<Match> matchList = ScoreDBHandler.getInstance()
							.getMatchByGroupId(groupId);
					mMap.put(String.valueOf(groupId), matchList);
				}
				onFinish(mMap);
			} else {
				onError(new NullPointerException("GroupList is null"));
			}
		} catch (SQLiteException exception) {
			onError(exception);
		}

	}

}
