package com.scorelive.common.itask;

public interface ITaskListener {

	/**
	 * Task错误时的回调方法
	 */
	public void onTaskError(ITask task,Exception exception);
	
}
