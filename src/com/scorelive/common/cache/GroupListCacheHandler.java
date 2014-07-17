package com.scorelive.common.cache;

import java.util.ArrayList;

import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.itask.quick.task.AddGroupTask;
import com.scorelive.common.itask.quick.task.DelGroupTask;
import com.scorelive.common.itask.quick.task.GroupListTask;
import com.scorelive.common.itask.quick.task.RenameGroupTask;
import com.scorelive.module.Group;

public class GroupListCacheHandler implements IShortTaskListener {

	public volatile static GroupListCacheHandler mInstance;
	private ArrayList<Group> mGroupList = null;

	public GroupListCacheHandler() {
		ITask task = new GroupListTask(ITask.TYPE_GROUP_LIST, ThreadManager
				.getInstance().getNewTaskId());
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	public static GroupListCacheHandler getInstance() {
		if (mInstance == null) {
			synchronized (GroupListCacheHandler.class) {
				if (mInstance == null) {
					mInstance = new GroupListCacheHandler();
				}
			}
		}
		return mInstance;
	}

	public ArrayList<Group> getGroupCache() {
		if (mGroupList == null) {
			mGroupList = ScoreDBHandler.getInstance().getGroupList();
		}
		return mGroupList;
	}

	/**
	 * 添加一个分组
	 * 
	 * @param name
	 */
	public void addGroupToCache(String name) {
		if (mGroupList == null) {
			mGroupList = getGroupCache();
		}
		Group newGroup = new Group();
		if (mGroupList.size() > 0) {
			Group group = mGroupList.get(mGroupList.size() - 1);
			newGroup.netId = group.netId + 1;
			newGroup.grounName = name;
			mGroupList.add(newGroup);
		}else{
			newGroup.netId = 1;
			newGroup.grounName = name;
			mGroupList.add(newGroup);
		}
		ITask task = new AddGroupTask(ITask.TYPE_ADD_GROUP, ThreadManager
				.getInstance().getNewTaskId(), newGroup);
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	/**
	 * 删除一个分组
	 * 
	 * @param groupId
	 */
	public void delGroupInCache(int groupId) {
		if (mGroupList == null) {
			getGroupCache();
		}
		for (Group group : mGroupList) {
			if (group.netId == groupId) {
				mGroupList.remove(group);
				break;
			}
		}
		ITask task = new DelGroupTask(ITask.TYPE_DEL_GROUP, ThreadManager
				.getInstance().getNewTaskId(), groupId);
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	/**
	 * 重命名一个分组
	 * 
	 * @param groupId
	 * @param name
	 */
	public void renameGroupInCache(int groupId, String name) {
		if (mGroupList == null) {
			getGroupCache();
		}
		for (Group group : mGroupList) {
			if (group.netId == groupId) {
				group.grounName = name;
				break;
			}
		}

		ITask task = new RenameGroupTask(ITask.TYPE_RENAME_GROUP, ThreadManager
				.getInstance().getNewTaskId(), name, groupId);
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskFinish(ITask task, Object object) {
		// TODO Auto-generated method stub
		switch (task.getTaskType()) {
		case ITask.TYPE_ADD_GROUP:
			mGroupList = (ArrayList<Group>) object;
			break;
		case ITask.TYPE_DEL_GROUP:
			mGroupList = (ArrayList<Group>) object;
			break;
		case ITask.TYPE_RENAME_GROUP:
			mGroupList = (ArrayList<Group>) object;
			break;
		case ITask.TYPE_GROUP_LIST:
			mGroupList = (ArrayList<Group>) object;
			break;
		}
	}

}
