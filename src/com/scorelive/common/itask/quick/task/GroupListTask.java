package com.scorelive.common.itask.quick.task;

import java.util.ArrayList;

import android.database.sqlite.SQLiteException;

import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.module.Group;

public class GroupListTask extends IShortTask {

	public GroupListTask(int type, long taskId) {
		super(type, taskId);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ArrayList<Group> list = ScoreDBHandler.getInstance().getGroupList();
			onFinish(list);
		} catch (SQLiteException exception) {
			onError(exception);
		}
	}

}
