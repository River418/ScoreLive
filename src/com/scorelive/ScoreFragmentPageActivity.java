package com.scorelive;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.net.task.MatchListTask;
import com.scorelive.ui.widget.PagerSlidingTabStrip;

/**
 * 即时比分Activity
 * 
 * @author River
 * 
 */
public class ScoreFragmentPageActivity extends FragmentActivity implements
		INetTaskListener {

	private ScoreFragmentPageAdapter mScoreFragmentPageAdapter;
	private ViewPager mViewPager;
	private PagerSlidingTabStrip mTabs;
	private TextView mTitle;

	@Override
	protected void onCreate(Bundle savedInstantceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstantceState);
		setContentView(R.layout.score_activity);
		initUI();
	}

	private void initUI() {
		mScoreFragmentPageAdapter = new ScoreFragmentPageAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mScoreFragmentPageAdapter);
		mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mTabs.setViewPager(mViewPager);
		mTabs.setShouldExpand(true);
		mTabs.setSelectColor(mViewPager.getCurrentItem());
		mTitle = (TextView) findViewById(R.id.middle_title);
		mTitle.setText("即时比分");
		mViewPager.setCurrentItem(1);
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

	/**
	 * 根据时间拉取全部比赛列表
	 * @param date
	 */
	private void getMatchList(String date) {
		MatchListTask task = new MatchListTask(ThreadManager.getInstance().getNewTaskId(),"2013-4-10");
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskFinish(ITask task, InputStream is) {
		// TODO Auto-generated method stub
		try {
			String str = Http.getString(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
