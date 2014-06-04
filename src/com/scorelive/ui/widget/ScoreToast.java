package com.scorelive.ui.widget;

import com.scorelive.R;

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
public class ScoreToast extends Toast{


	private int mTop;
	private Toast mToast;
	private View mLayout;
	private TextView mHostName,mVisitName,mHostScore,mVisitScore,mTextView;
	public static final int GOAL_TOAST = 1;
	public static final int MATCH_TOAST = 2;
	private Context mContext;
	
	public ScoreToast(Context context) {
		super(context);
		mContext = context;
	}

	public void show(int top){
		this.show();
	}
	
	public void setView(int type){
		switch(type){
		case GOAL_TOAST:
			mLayout = LayoutInflater.from(mContext).inflate(R.layout.push_toast, null);
			mTextView = (TextView)mLayout.findViewById(R.id.text);
			setView(mLayout);
			break;
		}
	}
	
	@Override
	public void setDuration(int duration) {
		// TODO Auto-generated method stub
		super.setDuration(duration);
	}

	@Override
	public void setText(CharSequence s) {
		if(mTextView != null){
			mTextView.setText(s);
		}
	}

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub
		super.setView(view);
	}

}
