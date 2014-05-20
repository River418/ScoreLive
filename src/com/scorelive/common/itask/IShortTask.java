package com.scorelive.common.itask;

/**
 * 数据库和文件操作任务类
 * 
 * @author River
 * 
 */
public class IShortTask extends ITask {

	private IShortTaskListener mListener;

	public IShortTask(int type, long taskId) {
		super(type, taskId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFinish(Object object) {
		// TODO Auto-generated method stub
		mListener.onTaskFinish(this, object);
	}

	@Override
	public void onError(Exception exception) {
		// TODO Auto-generated method stub
		mListener.onTaskError(this, exception);
	}

	@Override
	public void setListener(ITaskListener listener) {
		// TODO Auto-generated method stub
		mListener = (IShortTaskListener) listener;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

}
