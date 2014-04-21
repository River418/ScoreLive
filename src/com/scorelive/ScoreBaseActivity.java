package com.scorelive;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;

import com.scorelive.module.Match;

public class ScoreBaseActivity extends FragmentActivity{
	
	protected ArrayList<Match> mAllUnstartList = new ArrayList<Match>(),mAllMatchingList= new ArrayList<Match>(),mAllEndedList= new ArrayList<Match>();
	protected ArrayList<Match> mBJUnstartList= new ArrayList<Match>(),mBJMatchingList= new ArrayList<Match>(),mBJEndedList= new ArrayList<Match>();
	protected ArrayList<Match> mSMGUnstartList= new ArrayList<Match>(),mSMGMatchingList= new ArrayList<Match>(),mSMGEndedList= new ArrayList<Match>();
	protected ArrayList<Match> mZCUnstartList= new ArrayList<Match>(),mZCMatchingList= new ArrayList<Match>(),mZCEndedList= new ArrayList<Match>();
	
	
//	public MyHandler getHandler(){
//		return mHandler;
//	}

//	private final MyHandler mHandler = new MyHandler();
//
//	private class MyHandler extends Handler {
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			handleMessage(msg);
//		}
//
//	}
//	
//	protected void handleMessage(Message msg){
//		switch (msg.what) {
//		case MsgType.GET_SCORE_LIST_SUCCESS:
//			break;
//		}
//	}
}
