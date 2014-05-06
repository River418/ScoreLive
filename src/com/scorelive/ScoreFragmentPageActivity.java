package com.scorelive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.common.config.MsgType;
import com.scorelive.common.core.fragment.ScoreBaseFragment;
import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.net.task.MatchListTask;
import com.scorelive.common.utils.JsonUtils;
import com.scorelive.module.AppConstants;
import com.scorelive.module.AppConstants.MatchStatus;
import com.scorelive.module.Match;
import com.scorelive.ui.widget.PagerSlidingTabStrip;

/**
 * 即时比分Activity
 * 
 * @author River
 * 
 */
public class ScoreFragmentPageActivity extends ScoreBaseActivity implements
		INetTaskListener {

	private ScoreFragmentPageAdapter mScoreFragmentPageAdapter;
	private ViewPager mViewPager;
	private PagerSlidingTabStrip mTabs;
	private TextView mTitle;
	private ArrayList<Match> mAllList = new ArrayList<Match>(),
			mBJList = new ArrayList<Match>(),
			mSMGList = new ArrayList<Match>(),
			mZCList = new ArrayList<Match>();
	private FragmentManager mFragmentManager;
	private ImageView mLeftBtn, mRightBtn;

	@Override
	protected void onCreate(Bundle savedInstantceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstantceState);
		setContentView(R.layout.score_list_activity);
		initUI();
		// INetTask task = new MatchListTask(ThreadManager.getInstance()
		// .getNewTaskId(), "20140419");
		// task.setListener(this);
		getMatchList("20140424");
		// ThreadManager.getInstance().addTask(task);
		mFragmentManager = getSupportFragmentManager();
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
		mTitle.setText(getString(R.string.scorelive));
		mViewPager.setCurrentItem(1);
		mLeftBtn = (ImageView) findViewById(R.id.left_btn);
		mLeftBtn.setBackgroundResource(R.drawable.calendar);
		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		mRightBtn.setBackgroundResource(R.drawable.filter);

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

	private final MyHandler mHandler = new MyHandler();

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// handleMessage(msg);
			handlerMessage(msg);
		}

	}

	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case MsgType.GET_SCORE_LIST_SUCCESS:
			refreshFragment();
			break;
		}
	}

	/**
	 * 根据时间拉取全部比赛列表
	 * 
	 * @param date
	 */
	private void getMatchList(String date) {
		MatchListTask task = new MatchListTask(ThreadManager.getInstance()
				.getNewTaskId(), date);
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	private void refreshFragment() {
		ScoreBaseFragment fragment = null;
		for (int i = 1; i < 5; i++) {
			switch (i) {
			case ScoreFragmentPageAdapter.ALL:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				fragment.setData(mAllUnstartList, mAllMatchingList,
						mAllEndedList);
				break;
			case ScoreFragmentPageAdapter.BJ:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				fragment.setData(mBJUnstartList, mBJMatchingList, mBJEndedList);
				break;
			case ScoreFragmentPageAdapter.SMG:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				fragment.setData(mSMGUnstartList, mSMGMatchingList,
						mSMGEndedList);
				break;
			case ScoreFragmentPageAdapter.ZC:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				fragment.setData(mZCUnstartList, mZCMatchingList, mZCEndedList);
				break;
			}
		}
	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		// 任务错误，移除任务
		ThreadManager.getInstance().removeTask(task);
	}

	@Override
	public void onTaskFinish(ITask task, InputStream is) {
		// TODO Auto-generated method stub
		try {
			String str = Http.getString(is);
			mAllList = JsonUtils.json2MatchList(str);
			handleMatchList();
			mHandler.obtainMessage(MsgType.GET_SCORE_LIST_SUCCESS)
					.sendToTarget();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Match> getAllList() {
		return mAllList;
	}

	public List<Match> getBJList() {
		return mBJList;
	}

	public List<Match> getSMGList() {
		return mSMGList;
	}

	private void handleMatchList() {
		for (Match match : mAllList) {
			String typeList = match.matchBet;
			String[] typeArray = null;
			if (typeList.contains(",")) {
				typeArray = typeList.split(",");
				for (int i = 0; i < typeArray.length; i++) {
					addMatchToBetList(Integer.valueOf(typeArray[i]), match);
				}
			} else {
				addMatchToBetList(Integer.valueOf(typeList), match);
			}
			addMatchToAllList(match);
		}
	}

	private void addMatchToAllList(Match match) {
		switch (match.matchState) {
		case AppConstants.MatchStatus.UNSTART:
			mAllUnstartList.add(match);
			break;
		case AppConstants.MatchStatus.MATCHING:
		case AppConstants.MatchStatus.MIDDLE:
		case AppConstants.MatchStatus.UPADDED:
		case AppConstants.MatchStatus.DOWNADDED:
		case AppConstants.MatchStatus.PAUSEFOUL:
		case AppConstants.MatchStatus.PAUSEHURT:
			mAllMatchingList.add(match);
			break;
		case AppConstants.MatchStatus.CANCEL:
		case AppConstants.MatchStatus.ENDED:
		case AppConstants.MatchStatus.DELAY:
			mAllEndedList.add(match);
			break;
		}
	}

	private void addMatchToBetList(int betType, Match match) {
		switch (betType) {
		case AppConstants.BetType.BJ:
			switch (match.matchState) {
			case AppConstants.MatchStatus.UNSTART:
				mBJUnstartList.add(match);
				break;
			case AppConstants.MatchStatus.MATCHING:
			case AppConstants.MatchStatus.MIDDLE:
			case AppConstants.MatchStatus.UPADDED:
			case AppConstants.MatchStatus.DOWNADDED:
			case AppConstants.MatchStatus.PAUSEFOUL:
			case AppConstants.MatchStatus.PAUSEHURT:
				mBJMatchingList.add(match);
				break;
			case AppConstants.MatchStatus.CANCEL:
			case AppConstants.MatchStatus.ENDED:
			case AppConstants.MatchStatus.DELAY:
				mBJEndedList.add(match);
				break;
			}
			mBJList.add(match);
			break;
		case AppConstants.BetType.SMG:
			switch (match.matchState) {
			case AppConstants.MatchStatus.UNSTART:
				mSMGUnstartList.add(match);
				break;
			case AppConstants.MatchStatus.MATCHING:
			case AppConstants.MatchStatus.MIDDLE:
			case AppConstants.MatchStatus.UPADDED:
			case AppConstants.MatchStatus.DOWNADDED:
			case AppConstants.MatchStatus.PAUSEFOUL:
			case AppConstants.MatchStatus.PAUSEHURT:
				mSMGMatchingList.add(match);
				break;
			case AppConstants.MatchStatus.CANCEL:
			case AppConstants.MatchStatus.ENDED:
			case AppConstants.MatchStatus.DELAY:
				mSMGEndedList.add(match);
				break;
			}
			mSMGList.add(match);
			break;
		case AppConstants.BetType.ZC:
			switch (match.matchState) {
			case AppConstants.MatchStatus.UNSTART:
				mZCUnstartList.add(match);
				break;
			case AppConstants.MatchStatus.MATCHING:
			case AppConstants.MatchStatus.MIDDLE:
			case AppConstants.MatchStatus.UPADDED:
			case AppConstants.MatchStatus.DOWNADDED:
			case AppConstants.MatchStatus.PAUSEFOUL:
			case AppConstants.MatchStatus.PAUSEHURT:
				mZCMatchingList.add(match);
				break;
			case AppConstants.MatchStatus.CANCEL:
			case AppConstants.MatchStatus.ENDED:
			case AppConstants.MatchStatus.DELAY:
				mZCEndedList.add(match);
				break;
			}
			mZCList.add(match);
			break;
		}

	}

}
