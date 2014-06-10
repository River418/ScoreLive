package com.scorelive;

import java.io.File;

import android.os.Bundle;

import com.scorelive.common.config.AppConstants;

public class SplashActivity extends ScoreBaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.splash_activity);
		initApplication();
		
	}

	private void initApplication() {
		File file = new File(AppConstants.SCORELIVE_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}
