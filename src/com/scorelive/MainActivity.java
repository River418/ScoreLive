package com.scorelive;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.scorelive.ui.widget.TabItem;
import com.scorelive.ui.widget.TabItem.OnCheckedChangeListener;
import com.tencent.android.tpush.XGPushManager;

public class MainActivity extends TabActivity implements
		OnCheckedChangeListener {

	public TabHost mHost;
	private Intent mScoreIntent;
	private Intent mIndexIntent;
	private Intent mProfileIntent;
	private TabItem radio_button0;
	private TabItem radio_button1;
	private TabItem radio_button2;
	private final static String TAB_SCORE="mScore_tab";
	private final static String TAB_INDEX="mIndex_tab";
	private final static String TAB_PROFILE="mProfile_tab";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		XGPushManager.registerPush(getApplicationContext());
		this.mScoreIntent = new Intent(this, ScorePageActivity.class);
		this.mIndexIntent = new Intent(this, IndexActivity.class);
		this.mProfileIntent = new Intent(this, ProfileFragmentActivity.class);
		initRadios();
		setupIntent();
		setDefaultCheck(0);

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	private void initRadios() {
		radio_button0 = (TabItem) findViewById(R.id.score);
		radio_button0.setOnCheckedChangeListener(this);
		radio_button1 = (TabItem) findViewById(R.id.index);
		radio_button1.setOnCheckedChangeListener(this);
		radio_button2 = (TabItem) findViewById(R.id.profile);
		radio_button2.setOnCheckedChangeListener(this);
	}

	private void setupIntent() {
		this.mHost = getTabHost();
		TabHost localTabHost = this.mHost;
		localTabHost.addTab(buildTabSpec("mScore_tab", R.string.scorelive,
				R.drawable.score, this.mScoreIntent));

		localTabHost.addTab(buildTabSpec("mIndex_tab", R.string.indexlive,
				R.drawable.index, this.mIndexIntent));

		localTabHost.addTab(buildTabSpec("mProfile_tab", R.string.mysetting,
				R.drawable.profile, this.mProfileIntent));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);

	}

	@Override
	public void onCheckedChanged(View buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.score:
				mHost.setCurrentTabByTag(TAB_SCORE);
				radio_button0.setChecked(true);
				radio_button1.setChecked(false);
				radio_button2.setChecked(false);
				break;
			case R.id.index:
				mHost.setCurrentTabByTag(TAB_INDEX);
				radio_button1.setChecked(true);
				radio_button0.setChecked(false);
				radio_button2.setChecked(false);
				break;
			case R.id.profile:
				mHost.setCurrentTabByTag(TAB_PROFILE);
				radio_button2.setChecked(true);
				radio_button0.setChecked(false);
				radio_button1.setChecked(false);
				break;
			}
		}
	}
	
	private void setDefaultCheck(int tabIndex) {
		int tag = -1;
		if (tabIndex != -1) {
			tag = tabIndex;
		} 
		if (tag == 0) {
			mHost.setCurrentTabByTag(TAB_SCORE);
			radio_button0.setChecked(true);
			radio_button1.setChecked(false);
			radio_button2.setChecked(false);
		} else if (tag == 1) {
			mHost.setCurrentTabByTag(TAB_INDEX);
			radio_button1.setChecked(true);
			radio_button0.setChecked(false);
			radio_button2.setChecked(false);
		} else if (tag == 2) {
			mHost.setCurrentTabByTag(TAB_PROFILE);
			radio_button2.setChecked(true);
			radio_button0.setChecked(false);
			radio_button1.setChecked(false);
		} 
	}
}
