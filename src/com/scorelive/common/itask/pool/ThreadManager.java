package com.scorelive.common.itask.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

	public static ThreadManager getInstance() {
		if (mInstance == null) {
			synchronized (ThreadManager.class) {
				if (mInstance == null) {
					mInstance = new ThreadManager();
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
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		// NETWORK_EXECUTOR = AsyncTask.THREAD_POOL_EXECUTOR;
		// } else {
		// Executor tmp = null;
		// try {
		// Field field = AsyncTask.class.getDeclaredField("sExecutor");
		// field.setAccessible(true);
		// tmp = (Executor) field.get(null);
		// } catch (Exception e) {
		// // 反射失败
		// tmp = new ThreadPoolExecutor(3, 256, 0L, TimeUnit.MILLISECONDS,
		// new PriorityBlockingQueue<Runnable>());
		// }
		// NETWORK_EXECUTOR = tmp;
		// }

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
			NETWORK_EXECUTOR.execute(task);
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
		if(task instanceof INetTask){
			NETWORK_EXECUTOR.remove(task);
		}
	}
}
