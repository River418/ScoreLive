package com.scorelive.common.itask;

public interface IShortTaskListener extends ITaskListener{

	/**
	 * Task结束时的回调方法
	 */
	public void onTaskFinish(ITask task,Object object);
	
}
