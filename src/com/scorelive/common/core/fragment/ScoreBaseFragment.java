package com.scorelive.common.core.fragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v4.app.Fragment;

import com.scorelive.module.Match;

public class ScoreBaseFragment extends Fragment{

	protected ArrayList<Match> mUnstartList = new ArrayList<Match>();
	protected ArrayList<Match> mMatchingList = new ArrayList<Match>();
	protected ArrayList<Match> mEndedList = new ArrayList<Match>();
	protected Timer updateTimer = new Timer();
	
	public void setData(ArrayList<Match> unstart,ArrayList<Match> matching,ArrayList<Match> ended){
		mUnstartList = unstart;
		mMatchingList = matching;
		mEndedList = ended;
	}
	
	TimerTask task = new TimerTask(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			updateTime();
		}
		
	};
	
	public void updateTime(){
		
	}
	
}
