package com.scorelive.common.itask.quick.task;

import java.util.ArrayList;

import android.database.sqlite.SQLiteException;

import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.module.Group;

public class DelGroupTask extends IShortTask{

	private int mGroupId;
	
	public DelGroupTask(int type, long taskId,int groupId) {
		super(type, taskId);
		// TODO Auto-generated constructor stub
		mGroupId = groupId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			ScoreDBHandler.getInstance().deleteGroup(mGroupId);
			ArrayList<Group> list = ScoreDBHandler.getInstance().getGroupList();
			onFinish(list);
		}catch(SQLiteException e){
			onError(e);
		}
	}

	
}
