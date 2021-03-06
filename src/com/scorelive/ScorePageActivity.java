package com.scorelive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.common.cache.GroupListCacheHandler;
import com.scorelive.common.cache.MatchListCacheHandler;
import com.scorelive.common.config.AppConstants;
import com.scorelive.common.core.fragment.ScoreBaseFragment;
import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;
import com.scorelive.common.itask.INetTaskListener;
import com.scorelive.common.itask.IShortTask;
import com.scorelive.common.itask.IShortTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.net.task.LeagueMatchListTask;
import com.scorelive.common.itask.net.task.MatchListTask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.monitor.Debug;
import com.scorelive.common.utils.JsonUtils;
import com.scorelive.common.utils.Utility;
import com.scorelive.module.Group;
import com.scorelive.module.League;
import com.scorelive.module.Match;
import com.scorelive.ui.widget.PagerSlidingTabStrip;
import com.scorelive.ui.widget.dialog.LeagueFilterDialog;
import com.scorelive.ui.widget.dialog.LeagueFilterDialog.OnLeagueSelectedListener;
import com.tencent.stat.StatService;

/**
 * 即时比分Activity
 * 
 * @author River
 * 
 */
public class ScorePageActivity extends ScoreBaseActivity implements
		INetTaskListener, IShortTaskListener {

	private ScorePageAdapter mScoreFragmentPageAdapter;
	private ViewPager mViewPager;
	private PagerSlidingTabStrip mTabs;
	private TextView mTitle;
	private ImageView mLeftBtn, mRightBtn;
	private ArrayList<Group> mGroupList;
	private DatePickerDialog mDatePickerDialog;
	private Context mContext;
	private String mCurrentDate;

	private IntentFilter mUpdateFilter;
	private static boolean DEBUG = Debug.isDebug;

	@Override
	protected void onCreate(Bundle savedInstantceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstantceState);
		setContentView(R.layout.score_list_activity);
		mContext = this;
		initUI();
		// INetTask task = new MatchListTask(ThreadManager.getInstance()
		// .getNewTaskId(), "20140419");
		// task.setListener(this);
		initMatchList(Utility.getDateOfToday("yyyyMMdd"));
		// initGroupList();
		mGroupList = GroupListCacheHandler.getInstance().getGroupCache();
		// ThreadManager.getInstance().addTask(task);
		// ScoreDBHandler.getInstance().addGroup("自定义分组1");
	}

	private void initUI() {
		mScoreFragmentPageAdapter = new ScorePageAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mScoreFragmentPageAdapter);
		mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mTabs.setViewPager(mViewPager);
		mTabs.setShouldExpand(true);
		mTabs.setSelectColor(mViewPager.getCurrentItem());
		mTabs.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == ScorePageAdapter.CUSTOMIZE) {
					mScoreFragmentPageAdapter.refreshFragment(arg0);
				}
			}

		});
		mTitle = (TextView) findViewById(R.id.middle_title);
		mTitle.setText(getString(R.string.scorelive));
		mViewPager.setCurrentItem(1);
		mLeftBtn = (ImageView) findViewById(R.id.left_btn);
		mLeftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDatePickerDialog = new DatePickerDialog(mContext, null,
						Calendar.getInstance().get(Calendar.YEAR), Calendar
								.getInstance().get(Calendar.MONTH), Calendar
								.getInstance().get(Calendar.DAY_OF_MONTH));
				mDatePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
						"确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								int year = mDatePickerDialog.getDatePicker()
										.getYear();
								int month = mDatePickerDialog.getDatePicker()
										.getMonth() + 1;
								int day = mDatePickerDialog.getDatePicker()
										.getDayOfMonth();
								String date = null;
								if (month < 10) {
									date = year + "0" + month + day;
								} else {
									date = String.valueOf(year)
											+ String.valueOf(month)
											+ String.valueOf(day);
								}
								initMatchList(date);
							}
						});
				mDatePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
						"取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mDatePickerDialog.dismiss();
							}
						});
				mDatePickerDialog.show();
			}

		});
		mLeftBtn.setBackgroundResource(R.drawable.calendar);
		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		mRightBtn.setBackgroundResource(R.drawable.filter);
		mRightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LeagueFilterDialog dialog = new LeagueFilterDialog(
						ScorePageActivity.this);
				dialog.setOnLeagueSelectedListener(new OnLeagueSelectedListener() {

					@Override
					public void onLeagueSelected(ArrayList<League> list) {

						LeagueMatchListTask task = new LeagueMatchListTask(
								ITask.TYPE_LEAGUE_MATCH_LIST, ThreadManager
										.getInstance().getNewTaskId(),
								mCurrentDate, list);
						task.setListener(ScorePageActivity.this);
						ThreadManager.getInstance().addTask(task);
					}

				});
				dialog.show();
			}

		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionmenu, menu);
		return true;
	}

	private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// int eventType = intent.getIntExtra("type", 0);
			// String sTime = intent.getStringExtra("stime");
			// String matchId = intent.getStringExtra("matchId");
			ArrayList<Match> list = intent
					.getParcelableArrayListExtra("push_info");
			MatchListCacheHandler.getInstance().updateMatchStatus(list);
			mScoreFragmentPageAdapter.notifyDataSetChanged();
		}

	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mUpdateFilter == null) {
			mUpdateFilter = new IntentFilter(
					AppConstants.ActionType.UPDATE_MATCH_INFO);
		}
		registerReceiver(mUpdateReceiver, mUpdateFilter);
		StatService.onResume(this);
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
		case AppConstants.MsgType.GET_SCORE_LIST_SUCCESS:
			refreshFragment();
			AppConstants.isPullingMatchList = false;
			break;
		}
	}

	/**
	 * 根据时间拉取全部比赛列表
	 * 
	 * @param date
	 */
	public void initMatchList(String date) {
		mCurrentDate = date;
		AppConstants.isPullingMatchList = true;
		ITask task = new MatchListTask(ITask.TYPE_MATCH_LIST, ThreadManager
				.getInstance().getNewTaskId(), date);
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	// private void initGroupList() {
	// ITask task = new GroupListTask(ITask.TYPE_GROUP_LIST, ThreadManager
	// .getInstance().getNewTaskId());
	// task.setListener(this);
	// ThreadManager.getInstance().addTask(task);
	// }

	private void refreshFragment() {
		ScoreBaseFragment fragment = null;
		for (int i = 0; i < 5; i++) {
			HashMap<Integer, ArrayList<Match>> map = null;
			switch (i) {
			case ScorePageAdapter.ALL:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				map = MatchListCacheHandler.getInstance().getMatchList(
						AppConstants.BetType.ALL);
				fragment.setData(map.get(AppConstants.MatchStatus.UNSTART),
						map.get(AppConstants.MatchStatus.MATCHING),
						map.get(AppConstants.MatchStatus.ENDED));
				// fragment.setData(mAllUnstartList, mAllMatchingList,
				// mAllEndedList);
				break;
			case ScorePageAdapter.BJ:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				map = MatchListCacheHandler.getInstance().getMatchList(
						AppConstants.BetType.BJ);
				// fragment.setData(mBJUnstartList, mBJMatchingList,
				// mBJEndedList);
				fragment.setData(map.get(AppConstants.MatchStatus.UNSTART),
						map.get(AppConstants.MatchStatus.MATCHING),
						map.get(AppConstants.MatchStatus.ENDED));
				break;
			case ScorePageAdapter.SMG:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				map = MatchListCacheHandler.getInstance().getMatchList(
						AppConstants.BetType.SMG);
				// fragment.setData(mSMGUnstartList, mSMGMatchingList,
				// mSMGEndedList);
				fragment.setData(map.get(AppConstants.MatchStatus.UNSTART),
						map.get(AppConstants.MatchStatus.MATCHING),
						map.get(AppConstants.MatchStatus.ENDED));
				break;
			case ScorePageAdapter.ZC:
				fragment = mScoreFragmentPageAdapter.getFragment(i);
				map = MatchListCacheHandler.getInstance().getMatchList(
						AppConstants.BetType.ZC);
				// fragment.setData(mZCUnstartList, mZCMatchingList,
				// mZCEndedList);
				fragment.setData(map.get(AppConstants.MatchStatus.UNSTART),
						map.get(AppConstants.MatchStatus.MATCHING),
						map.get(AppConstants.MatchStatus.ENDED));
				break;
			// case ScorePageAdapter.CUSTOMIZE:
			// fragment = mScoreFragmentPageAdapter.getFragment(i);
			// fragment.setData(mZCUnstartList, mZCMatchingList, mZCEndedList);
			// break;
			}
		}
	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		// 任务错误，移除任务
		if (task instanceof IShortTask) {

		}
		if (task instanceof INetTask) {

		}
	}

	@Override
	public void onTaskFinish(ITask task, InputStream is) {
		// TODO Auto-generated method stub
		switch (task.getTaskType()) {
		case ITask.TYPE_MATCH_LIST:
		case ITask.TYPE_LEAGUE_MATCH_LIST:
			try {
				String str = Http.getString(is);
				if (DEBUG) {
					Log.e("json", str);
				}
				// 由内存管理的类来处理比赛列表
				MatchListCacheHandler.getInstance().setAllList(
						JsonUtils.json2MatchList(str));
				// mAllList = JsonUtils.json2MatchList(str);
				// handleMatchList();
				mHandler.obtainMessage(
						AppConstants.MsgType.GET_SCORE_LIST_SUCCESS)
						.sendToTarget();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	// public void clearMatchCache() {
	// mAllMatchingList.clear();
	// mBJList.clear();
	// mSMGList.clear();
	// mZCList.clear();
	// mAllUnstartList.clear();
	// mAllMatchingList.clear();
	// mAllEndedList.clear();
	// mBJUnstartList.clear();
	// mBJMatchingList.clear();
	// mBJEndedList.clear();
	// mSMGUnstartList.clear();
	// mSMGMatchingList.clear();
	// mSMGEndedList.clear();
	// mZCUnstartList.clear();
	// mZCMatchingList.clear();
	// mZCEndedList.clear();
	//
	// }
	//
	// private void handleMatchList() {
	// clearMatchCache();
	// for (Match match : mAllList) {
	// String typeList = match.matchBet;
	// String[] typeArray = null;
	// if (typeList.contains(",")) {
	// typeArray = typeList.split(",");
	// for (int i = 0; i < typeArray.length; i++) {
	// addMatchToBetList(Integer.valueOf(typeArray[i]), match);
	// }
	// } else if (!typeList.equalsIgnoreCase("")) {
	// addMatchToBetList(Integer.valueOf(typeList), match);
	// }
	// addMatchToAllList(match);
	// }
	// }
	//
	// private void addMatchToAllList(Match match) {
	// switch (match.matchState) {
	// case AppConstants.MatchStatus.UNSTART:
	// mAllUnstartList.add(match);
	// break;
	// case AppConstants.MatchStatus.MIDDLE:
	// case AppConstants.MatchStatus.UP:
	// case AppConstants.MatchStatus.DOWN:
	// case AppConstants.MatchStatus.ADDED:
	// mAllMatchingList.add(match);
	// break;
	// case AppConstants.MatchStatus.CANCEL:
	// case AppConstants.MatchStatus.ENDED:
	// case AppConstants.MatchStatus.DELAY:
	// mAllEndedList.add(match);
	// break;
	// }
	// }
	//
	// private void addMatchToBetList(int betType, Match match) {
	// switch (betType) {
	// case AppConstants.BetType.BJ:
	// switch (match.matchState) {
	// case AppConstants.MatchStatus.UNSTART:
	// mBJUnstartList.add(match);
	// break;
	// case AppConstants.MatchStatus.MIDDLE:
	// case AppConstants.MatchStatus.UP:
	// case AppConstants.MatchStatus.DOWN:
	// case AppConstants.MatchStatus.ADDED:
	// mBJMatchingList.add(match);
	// break;
	// case AppConstants.MatchStatus.CANCEL:
	// case AppConstants.MatchStatus.ENDED:
	// case AppConstants.MatchStatus.DELAY:
	// mBJEndedList.add(match);
	// break;
	// }
	// mBJList.add(match);
	// break;
	// case AppConstants.BetType.SMG:
	// switch (match.matchState) {
	// case AppConstants.MatchStatus.UNSTART:
	// mSMGUnstartList.add(match);
	// break;
	// case AppConstants.MatchStatus.MIDDLE:
	// case AppConstants.MatchStatus.UP:
	// case AppConstants.MatchStatus.DOWN:
	// case AppConstants.MatchStatus.ADDED:
	// mSMGMatchingList.add(match);
	// break;
	// case AppConstants.MatchStatus.CANCEL:
	// case AppConstants.MatchStatus.ENDED:
	// case AppConstants.MatchStatus.DELAY:
	// mSMGEndedList.add(match);
	// break;
	// }
	// mSMGList.add(match);
	// break;
	// case AppConstants.BetType.ZC:
	// switch (match.matchState) {
	// case AppConstants.MatchStatus.UNSTART:
	// mZCUnstartList.add(match);
	// break;
	// case AppConstants.MatchStatus.MIDDLE:
	// case AppConstants.MatchStatus.UP:
	// case AppConstants.MatchStatus.DOWN:
	// case AppConstants.MatchStatus.ADDED:
	// mZCMatchingList.add(match);
	// break;
	// case AppConstants.MatchStatus.CANCEL:
	// case AppConstants.MatchStatus.ENDED:
	// case AppConstants.MatchStatus.DELAY:
	// mZCEndedList.add(match);
	// break;
	// }
	// mZCList.add(match);
	// break;
	// }
	//
	// }

	@Override
	public void onTaskFinish(ITask task, Object object) {
		// TODO Auto-generated method stub
		switch (task.getTaskType()) {
		case ITask.TYPE_GROUP_LIST:
			mGroupList = (ArrayList<Group>) object;
			break;
		}
	}

}
