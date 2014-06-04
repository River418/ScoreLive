package com.scorelive.ui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.scorelive.R;

public class BaseDialog {

	public static final int TYPE_CENTER = 0;
	public static final int TYPE_BOTTOM = 1;
	protected CoreDialog mDialog;
	protected View dialogView;
	protected Activity act;

	public void initDialog(Activity act, int layoutResId, int styleResId,
			int type, boolean isFullScreen) {
		mDialog = new CoreDialog(act, styleResId);
		dialogView = LayoutInflater.from(act).inflate(layoutResId, null);
		mDialog.setContentView(layoutResId);
		mDialog.setCanceledOnTouchOutside(false);
		this.act = act;

		WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
		if (isFullScreen) {
			WindowManager.LayoutParams actlp = act.getWindow().getAttributes();
			lp.width = actlp.width;
			lp.height = actlp.height;
		}
		switch (type) {
		case TYPE_CENTER:
			break;
		case TYPE_BOTTOM:
			lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			lp.gravity = android.view.Gravity.BOTTOM;
			WindowManager.LayoutParams actlp = act.getWindow().getAttributes();
			lp.width = actlp.width;
			// lp.y = 50;
			mDialog.setCanceledOnTouchOutside(true);
			if (VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR) {
				// mDialog.getWindow().setWindowAnimations(R.style.)
			}
			break;
		}
		mDialog.getWindow().setAttributes(lp);
	}

	public void initDialog(Activity act, int layoutResId, int type,
			boolean isFullScreen) {
		this.initDialog(act, layoutResId, R.style.oppoTheme, type, isFullScreen);
	}

	protected void setTitle() {
		TextView textview = (TextView) mDialog.findViewById(R.id.title);
		TextPaint tp = textview.getPaint();
		tp.setFakeBoldText(true);
	}

	public void setOnCancelListener(OnCancelListener clistener) {
		mDialog.setOnCancelListener(clistener);

	}

	public void setOnDismissListener(
			android.content.DialogInterface.OnDismissListener clistener) {
		mDialog.setOnDismissListener(clistener);

	}

	public void setCanceledOnTouchOutside(boolean cancel) {
		mDialog.setCanceledOnTouchOutside(cancel);
	}

	public void setCancelable(boolean cancel) {
		mDialog.setCancelable(cancel);
	}

	public void show() {
		mDialog.show();
	}

	public void dismiss() {
		mDialog.dismiss();

	}

	public void hide() {
		mDialog.hide();
	}

	public void cancel() {
		mDialog.cancel();
	}

	public Context getContext() {
		return mDialog.getContext();
	}

	public boolean isShowing() {
		return mDialog.isShowing();
	}

	public Object getTouchListener() {
		return mDialog.getTouchListener();
	}

	public void setTouchListener(DialogTouchListener touchListener) {
		mDialog.setTouchListener(touchListener);
	}

	Object object = null;

	public void setTag(Object obj) {
		object = obj;
	}

	public Object getTag() {
		return object;
	}

	public class CoreDialog extends Dialog {
		private DialogTouchListener touchListener = null;

		public CoreDialog(Context context, int theme) {
			super(context, theme);
			// TODO Auto-generated constructor stub
		}

		public Object getTouchListener() {
			return touchListener;
		}

		public void setTouchListener(DialogTouchListener touchListener) {
			this.touchListener = touchListener;
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub

			return super.onKeyDown(keyCode, event);
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			// TODO Auto-generated method stub
			if (touchListener != null) {
				touchListener.touchHandle(ev);
			}
			return super.dispatchTouchEvent(ev);
		}

	}

}
