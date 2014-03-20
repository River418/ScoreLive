package com.scorelive.common.itask;

public abstract class ITask implements Runnable{

	private long mTaskId;
	
	public ITask(long taskId){
		mTaskId = taskId;
	}
	
	public long getTaskId(){
		return mTaskId;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 线程执行完之后的执行方法
	 * @param object
	 */
	public abstract void onFinish(Object object);
	
	/**
	 * 线程错误的执行方法
	 * @param exception
	 */
	public abstract void onError(Exception exception);
	
	/**
	 * 线程的回调方法
	 * @param listener
	 */
	public abstract void setListener(ITaskListener listener);

	@Override
	public  abstract boolean equals(Object o);
	
	

}
