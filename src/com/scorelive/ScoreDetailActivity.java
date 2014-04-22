package com.scorelive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.module.Match;

public class ScoreDetailActivity extends Activity {

	private ImageView mBackBtn;
	private ImageView mCollectBtn;
	private ImageView mRefreshBtn;
	private TextView mTitleTV,mHostNameTV,mVisitNameTV,mStartTimeTV,mStatusTV,mHostScoreTV,mVisitScoreTV,mHalfScoreTV;
	private Match mMatch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_detail_activity);
		initUI();
		processData();
	}

	private void initUI() {
		mBackBtn = (ImageView) findViewById(R.id.left_btn);
		mBackBtn.setBackgroundResource(R.drawable.back);
		mBackBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		mCollectBtn = (ImageView) findViewById(R.id.right_btn);
		mCollectBtn.setBackgroundResource(R.drawable.collect_normal);
		mRefreshBtn = (ImageView) findViewById(R.id.refresh_btn);
		mRefreshBtn.setBackgroundResource(R.drawable.refresh);
		mTitleTV = (TextView) findViewById(R.id.middle_title);
		mHostNameTV = (TextView)findViewById(R.id.host_name);
		mVisitNameTV=(TextView)findViewById(R.id.visit_name);
		mStartTimeTV = (TextView)findViewById(R.id.start_time);
		mStatusTV = (TextView)findViewById(R.id.status);
		mHostScoreTV = (TextView)findViewById(R.id.host_score);
		mVisitScoreTV = (TextView)findViewById(R.id.visit_score);
	}
	
	private void processData(){
		Intent intent = getIntent();
		mMatch = intent.getParcelableExtra("match");
		mTitleTV.setText(mMatch.matchLeague);
		mHostNameTV.setText("("+mMatch.hostTeamIndex+")"+mMatch.hostTeamName);
		mVisitNameTV.setText(mMatch.visitTeamName+"("+mMatch.visitTeamIndex+")");
		mStartTimeTV.setText(mMatch.matchStartTime);
		mHostScoreTV.setText(mMatch.matchScore.substring(0, mMatch.matchScore.indexOf(":")));
		mVisitScoreTV.setText(mMatch.matchScore.substring(mMatch.matchScore.indexOf(":")+1));
	}

}
