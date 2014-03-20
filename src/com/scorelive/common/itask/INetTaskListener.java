package com.scorelive.common.itask;

import java.io.InputStream;

public interface INetTaskListener extends ITaskListener {

	/**
	 * Task结束时的回调方法
	 */
	public void onTaskFinish(ITask task, InputStream is);

}
