package com.scorelive.common.core.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import android.support.v4.app.Fragment;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.utils.Utility;
import com.scorelive.module.Match;

public class ScoreBaseFragment extends Fragment {

	protected ArrayList<Match> mUnstartList = new ArrayList<Match>();
	protected ArrayList<Match> mMatchingList = new ArrayList<Match>();
	protected ArrayList<Match> mEndedList = new ArrayList<Match>();
	protected Timer updateTimer = new Timer();

	public void setData(ArrayList<Match> unstart, ArrayList<Match> matching,
			ArrayList<Match> ended) {
		mUnstartList = unstart;
		mMatchingList = matching;
		mEndedList = ended;
	}
	
	private void updateTime(ArrayList<Match> list){
		Iterator<Match> iterator = list.iterator();
		while (iterator.hasNext()) {
			Match match = iterator.next();
			int time = Utility.caculateMatchingTime(match.matchStartTime);
			switch (match.matchState) {
			case AppConstants.MatchStatus.UP:
				if (time > 45) {
					match.matchTime = "上半场45'+";
				} else {
					match.matchTime = "上半场" + time + "'";
				}
				break;
			case AppConstants.MatchStatus.DOWN:
				if (time > 45) {
					match.matchTime = "下半场90'+";
				} else {
					match.matchTime = "下半场" + (45+time) + "'";
				}
				break;
			case AppConstants.MatchStatus.ADDED:
				if (time > 30) {
					match.matchTime = "加时赛120'+";
				} else {
					match.matchTime = "加时赛" + (90+time) + "'";
				}
				break;
			case AppConstants.MatchStatus.ENDED:
				match.matchTime = "比赛结束";
				break;
			default:
				break;
			}
		}
	}

	public void updateTime() {
//		updateTime(mUnstartList);
		updateTime(mMatchingList);
//		updateTime(mEndedList);
	}
}
