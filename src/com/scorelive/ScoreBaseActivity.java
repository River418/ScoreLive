package com.scorelive;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.scorelive.ui.widget.dialog.ProgressDialogMe;

public class ScoreBaseActivity extends FragmentActivity {

	// protected ProgressDialogMe mProgressDialog;
	protected ProgressDialog mProgressDialog;

	protected ProgressDialog getProgressDialog() {
		if (mProgressDialog == null) {
			// mProgressDialog = new ProgressDialogMe(this);
			mProgressDialog = new ProgressDialog(this);
		}
		return mProgressDialog;
	}

	protected void showProgressDialog(String text) {
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog
					.show(this, null, text, false, true);
		} else {
			mProgressDialog.show();
		}
	}

	protected void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	// public MyHandler getHandler() {
	// return mHandler;
	// }
	//
	// private final MyHandler mHandler = new MyHandler();
	//
	// private class MyHandler extends Handler {
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// handleMessage(msg);
	// }
	//
	// }

}
