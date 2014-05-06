package com.scorelive.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 推送的自定义Toast,根据推送的内容不同，采用不同的布局
 * @author River
 *
 */
public class ScoreToast {

	private int mTop;
	private Toast mToast;
	private View mLayout;
	private TextView mHostName,mVisitName,mHostScore,mVisitScore;

	public ScoreToast(Context context) {
		mToast = new Toast(context);
	}

	public void show(int top){
		
	}
	
	private void initView(Context context,int layoutId){
		mLayout = LayoutInflater.from(context).inflate(layoutId, null);
		
	}
}
