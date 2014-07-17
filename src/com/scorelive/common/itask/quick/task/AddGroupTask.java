package com.scorelive.common.itask.quick.task;

import java.util.ArrayList;

import android.database.sqlite.SQLiteException;

import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.module.Group;

/**
 * 添加一个分组
 * @author River
 *
 */
public class AddGroupTask extends IShortTask {

	private Group mGroup = null;

	public AddGroupTask(int type, long taskId, Group group) {
		super(type, taskId);
		mGroup = group;
	}

	@Override
	public void run() {
		try {
			ScoreDBHandler.getInstance().addGroup(mGroup);
			ArrayList<Group> list = ScoreDBHandler.getInstance().getGroupList();
			onFinish(list);
		} catch (SQLiteException e) {
			onError(e);
		}
	}

}
