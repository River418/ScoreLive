package com.scorelive.common.itask.quick.task;

import java.util.ArrayList;

import android.database.sqlite.SQLiteException;

import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.module.Group;

public class RenameGroupTask extends IShortTask{

	private int mGroupId;
	private String mGroupName;
	
	public RenameGroupTask(int type, long taskId,String groupName,int groupId) {
		super(type, taskId);
		mGroupName = groupName;
		mGroupId = groupId;
	}

	@Override
	public void run() {
		try{
			ScoreDBHandler.getInstance().renameGroup(mGroupName, mGroupId);
			ArrayList<Group> list = ScoreDBHandler.getInstance().getGroupList();
			onFinish(list);
		}catch(SQLiteException e){
			onError(e);
		}
		
	}
	
	

}
