package com.scorelive.common.itask;

public abstract class ITask implements Runnable {

	private long mTaskId;
	private int mType;

	public static final int TYPE_MATCH_LIST = 1;
	public static final int TYPE_MATCH_DETAIL = 2;
	public static final int TYPE_GROUP_LIST = 3;
	public static final int TYPE_ADD_GROUP = 4;
	public static final int TYPE_RENAME_GROUP = 5;
	public static final int TYPE_DEL_GROUP = 6;
	public static final int TYPE_ADD_MATCH2GROUP = 7;
	public static final int TYPE_QUERY_MATCH_BY_GROUP = 8;
	public static final int TYPE_DOWNLOAD_PIC = 9;

	public ITask(int type, long taskId) {
		mType = type;
		mTaskId = taskId;
	}

	public int getTaskType() {
		return mType;
	}

	public long getTaskId() {
		return mTaskId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	/**
	 * 线程执行完之后的执行方法
	 * 
	 * @param object
	 */
	public abstract void onFinish(Object object);

	/**
	 * 线程错误的执行方法
	 * 
	 * @param exception
	 */
	public abstract void onError(Exception exception);

	/**
	 * 线程的回调方法
	 * 
	 * @param listener
	 */
	public abstract void setListener(ITaskListener listener);

	@Override
	public abstract boolean equals(Object o);

}
