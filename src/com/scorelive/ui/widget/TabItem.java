package com.scorelive.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scorelive.R;

public class TabItem extends LinearLayout {

	private final static String TAG = "TabItem";
	public ImageView mImage = null;
	private ImageView mDivider = null;
	private OnCheckedChangeListener mOnCheckedChangeListener;

	private boolean mChecked;

	public TabItem(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs);
	}

	public TabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TabItem(Context context) {
		this(context, null);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		init();
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "onclick " + v);
				if (mOnCheckedChangeListener != null) {
					mOnCheckedChangeListener.onCheckedChanged(v, true);
				}
			}
		});
	}

	private void init() {
		mImage = (ImageView) findViewWithTag(getResources().getString(
				R.string.main_tab_tag));
	}

	public void setChecked(boolean ischecked) {
		if (mChecked != ischecked) {
			mChecked = ischecked;
		} else {
			return;
		}
		if (mImage != null) {
			mImage.setSelected(ischecked);
		}
		if (null != mOnCheckedChangeListener) {
			mOnCheckedChangeListener.onCheckedChanged(this, ischecked);
		}
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener;
	}

	public interface OnCheckedChangeListener {
		void onCheckedChanged(View buttonView, boolean isChecked);
	}

}
