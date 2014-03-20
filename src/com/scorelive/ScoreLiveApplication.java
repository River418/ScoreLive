package com.scorelive;

import android.app.Application;

public class ScoreLiveApplication extends Application {

	private volatile static ScoreLiveApplication mInstance;

	public static ScoreLiveApplication getInstance() {
		if (mInstance == null) {
			synchronized (ScoreLiveApplication.class) {
				if (mInstance == null) {
					mInstance = new ScoreLiveApplication();
				}
			}
		}
		return mInstance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
	}

}
