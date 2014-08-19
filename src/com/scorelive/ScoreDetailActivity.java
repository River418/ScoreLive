package com.scorelive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.http.Http;
import com.scorelive.common.itask.INetTask;
import com.scorelive.common.itask.INetTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.net.task.MatchDetailTask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.utils.JsonUtils;
import com.scorelive.module.Match;
import com.scorelive.module.MatchAccident;
import com.tencent.stat.StatService;

/**
 * 比分详情页
 * 
 * @author River
 * 
 */
public class ScoreDetailActivity extends ScoreBaseActivity implements INetTaskListener {

	private ImageView mBackBtn;
	private ImageView mCollectBtn;
	private ImageView mRefreshBtn;
	private TextView mTitleTV, mHostNameTV, mVisitNameTV, mStartTimeTV,
			mStatusTV, mHostScoreTV, mVisitScoreTV, mHalfScoreTV;
	private Match mMatch;
	private ArrayList<MatchAccident> mList;
	private ListView mListView;
	private ScoreDetailAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_detail_activity);
		initUI();
		mMatch = getIntent().getParcelableExtra("match");
		if (mMatch != null) {
			processData();
//			getMatchDetail();
		} else {
			// TODO 增加判空的逻辑，不过一般不会出现null
		}
	}

	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
//		getMenuInflater().inflate(R.menu.actionmenu, menu);
//		MenuItemCompat.setShowAsAction(menu.getItem(0), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
//		MenuItemCompat.setShowAsAction(menu.add("1").setIcon(R.drawable.refresh), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return false;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		refresh();
	}

	private void refresh(){
		mAdapter.cleadrData();
		final RotateAnimation animation = new RotateAnimation(0.0f,
				360.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setRepeatMode(Animation.RESTART);
		animation.setRepeatCount(Animation.INFINITE);
		animation.setFillAfter(false);
		animation.setDuration(500);
		mRefreshBtn.startAnimation(animation);
		getMatchDetail();
	}
	
	private void getMatchDetail() {

		MatchDetailTask task = new MatchDetailTask(ITask.TYPE_MATCH_DETAIL,
				ThreadManager.getInstance().getNewTaskId(), mMatch.matchId);
		task.setListener(ScoreDetailActivity.this);
		task.setPriority(INetTask.PRIORITY_HIGH);
		ThreadManager.getInstance().addTask(task);

	}

	private void initUI() {
//		ActionBar actionbar = getSupportActionBar();
//		 actionbar.show();
		mBackBtn = (ImageView) findViewById(R.id.left_btn);
		mBackBtn.setBackgroundResource(R.drawable.back);
		mBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
		mCollectBtn = (ImageView) findViewById(R.id.right_btn);
		mCollectBtn.setBackgroundResource(R.drawable.collect_normal);
		mRefreshBtn = (ImageView) findViewById(R.id.refresh_btn);
		mRefreshBtn.setBackgroundResource(R.drawable.refresh);
		mRefreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
			}

		});
		mTitleTV = (TextView) findViewById(R.id.middle_title);
		mHostNameTV = (TextView) findViewById(R.id.host_name);
		mVisitNameTV = (TextView) findViewById(R.id.visit_name);
		mStartTimeTV = (TextView) findViewById(R.id.start_time);
		mStatusTV = (TextView) findViewById(R.id.status);
		mHostScoreTV = (TextView) findViewById(R.id.host_score);
		mVisitScoreTV = (TextView) findViewById(R.id.visit_score);
		mListView = (ListView) findViewById(R.id.details);
		mAdapter = new ScoreDetailAdapter(this);
		mListView.setAdapter(mAdapter);
	}

	private void processData() {

		mTitleTV.setText(mMatch.leagueName);
		if (TextUtils.isEmpty(mMatch.hostTeamIndex)
				|| TextUtils.isEmpty(mMatch.visitTeamIndex)) {
			mHostNameTV.setText(mMatch.hostTeamName);
			mVisitNameTV.setText(mMatch.visitTeamName);
		} else {
			mHostNameTV.setText("(" + mMatch.hostTeamIndex + ")"
					+ mMatch.hostTeamName);
			mVisitNameTV.setText(mMatch.visitTeamName + "("
					+ mMatch.visitTeamIndex + ")");
		}
		// mHostNameTV.setText("(" + mMatch.hostTeamIndex + ")"
		// + mMatch.hostTeamName);
		// mVisitNameTV.setText(mMatch.visitTeamName + "(" +
		// mMatch.visitTeamIndex
		// + ")");
		mStartTimeTV.setText(mMatch.matchOfficalTime);
		mHostScoreTV.setText(mMatch.hostTeamScore);
		mVisitScoreTV.setText(mMatch.visitTeamScore);
		mStatusTV.setText(mMatch.matchTime);
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
		case AppConstants.MsgType.GET_MATCH_DETAIL_SUCCESS:
			setDetailView();
			break;
		}
	}

	private void setDetailView() {
		mAdapter.setData(mList);
		if(mRefreshBtn != null){
			mRefreshBtn.clearAnimation();
		}
	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskFinish(ITask task, InputStream is) {
		switch (task.getTaskType()) {
		case ITask.TYPE_MATCH_DETAIL:
			try {
				String str = Http.getString(is);
				Log.e("detail", str);
				mList = JsonUtils.json2MatchAccident(str);
				mHandler.sendEmptyMessage(AppConstants.MsgType.GET_MATCH_DETAIL_SUCCESS);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

	}

}
