package com.scorelive;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
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

/**
 * 比分详情页
 * 
 * @author River
 * 
 */
public class ScoreDetailActivity extends Activity implements INetTaskListener {

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
			getMatchDetail();
		} else {
			// TODO 增加判空的逻辑，不过一般不会出现null
		}
	}

	private void getMatchDetail() {

		MatchDetailTask task = new MatchDetailTask(ITask.TYPE_MATCH_DETAIL,
				ThreadManager.getInstance().getNewTaskId(), mMatch.matchId);
		task.setListener(ScoreDetailActivity.this);
		task.setPriority(INetTask.PRIORITY_HIGH);
		ThreadManager.getInstance().addTask(task);

	}

	private void initUI() {
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
		mRefreshBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getMatchDetail();
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
		mHostNameTV.setText("(" + mMatch.hostTeamIndex + ")"
				+ mMatch.hostTeamName);
		mVisitNameTV.setText(mMatch.visitTeamName + "(" + mMatch.visitTeamIndex
				+ ")");
		mStartTimeTV.setText(mMatch.matchStartTime);
		mHostScoreTV.setText(mMatch.hostTeamScore);
		mVisitScoreTV.setText(mMatch.visitTeamScore);
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
