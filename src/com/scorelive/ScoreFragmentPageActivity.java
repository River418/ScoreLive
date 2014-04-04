package com.scorelive;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.scorelive.ui.widget.PagerSlidingTabStrip;

/**
 * 即时比分Activity
 * @author River
 *
 */
public class ScoreFragmentPageActivity extends FragmentActivity{
	
	private ScoreFragmentPageAdapter mScoreFragmentPageAdapter;
	private ViewPager mViewPager;
	private PagerSlidingTabStrip mTabs;
	private TextView mTitle;

	@Override
	protected void onCreate(Bundle savedInstantceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstantceState);
		setContentView(R.layout.score_activity);
		mScoreFragmentPageAdapter = new ScoreFragmentPageAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mScoreFragmentPageAdapter);
		mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mTabs.setViewPager(mViewPager);
		mTabs.setShouldExpand(true);
		mTabs.setSelectColor(mViewPager.getCurrentItem());
		mTitle = (TextView)findViewById(R.id.middle_title);
		mTitle.setText("即时比分");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
	
	
	

}
