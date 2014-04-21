package com.scorelive.common.itask.pool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Handler;
import android.os.HandlerThread;

import com.scorelive.common.itask.INetTask;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.common.itask.ITask;

/**
 * 线程管理类
 * 
 * @author River
 * 
 */
public class ThreadManager {

	private volatile static ThreadManager mInstance;
	private static Handler SUB_THREAD_HANDLER;
	private static HandlerThread SUB_THREAD;
	private static ThreadPoolExecutor NETWORK_EXECUTOR;
	private static AtomicInteger mTaskId;

	public static ThreadManager getInstance() {
		if (mInstance == null) {
			synchronized (ThreadManager.class) {
				if (mInstance == null) {
					mInstance = new ThreadManager();
					mTaskId = new AtomicInteger(1);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 构造方法初始化线程池和HandlerThread
	 */
	public ThreadManager() {
		SUB_THREAD = new HandlerThread("SUB");
		SUB_THREAD.start();
		SUB_THREAD_HANDLER = new Handler(SUB_THREAD.getLooper());
		NETWORK_EXECUTOR = new ThreadPoolExecutor(3, 256, 0L,
				TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
	}

	public int getNewTaskId() {
		return mTaskId.getAndIncrement();
	}

	/**
	 * 添加一个任务
	 * 
	 * @param task
	 *            添加的任务
	 */
	public void addTask(ITask task) {
		if (task instanceof IShortTask) {// 短任务走HandlerThread
			SUB_THREAD_HANDLER.post(task);
		}
		if (task instanceof INetTask) {// 网络请求走优先级线程池
			if (!isTaskInQueue(task)) {//任务不在队列中，才放入线程池
				NETWORK_EXECUTOR.execute(task);
			}
		}
	}

	/**
	 * 移除一个任务
	 * 
	 * @param task
	 *            移除的任务
	 */
	public void removeTask(ITask task) {
		if (task instanceof IShortTask) {// 从HandlerThread中移除任务
			SUB_THREAD_HANDLER.removeCallbacks(task);
		}
		if (task instanceof INetTask) {
			NETWORK_EXECUTOR.remove(task);
		}
	}

	private boolean isTaskInQueue(ITask task) {
		BlockingQueue<Runnable> list = null;
		if (NETWORK_EXECUTOR != null) {
			list = NETWORK_EXECUTOR.getQueue();
			for (Runnable runnable : list) {
				if (((INetTask) runnable).equals(task)) {
					return true;
				}
			}
		}
		return false;
	}
}
