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

	public void updateTime() {
		Iterator<Match> iterator = mUnstartList.iterator();
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
				break;
			default:
				break;
			}
			if (time > 105) {
				// match.matchTime =
				// Utility.parseTimeToDate(match.matchStartTime);
				match.matchTime = "比赛结束";
				iterator.remove();
				mEndedList.add(match);
			} else if (0 < time && time < 110) {
				mMatchingList.add(match);
				if (time < 110 && time > 105) {
					match.matchTime = "下半场90'+";
				} else if (time <= 105 && time > 60) {
					match.matchTime = "下半场" + (time - 15) + "'";
				} else if (time >= 45 && time <= 50) {
					match.matchTime = "上半场45'+";
				} else if (0 < time && time < 45) {
					match.matchTime = "上半场" + time + "'";

				}
			}
		}
		for (Match match : mUnstartList) {
			int time = Utility.caculateMatchingTime(match.matchStartTime);
			if (time > 95) {
				// match.matchTime =
				// Utility.parseTimeToDate(match.matchStartTime);
			} else if (time < 95 && time > 90) {
				match.matchTime = "下半场90'+";
			} else if (time <= 90 && time > 45) {
				match.matchTime = "下半场" + time + "'";
			} else {
				match.matchTime = "上半场" + time + "'";
			}
		}
		for (Match match : mMatchingList) {
			int time = Utility.caculateMatchingTime(match.matchStartTime);
			if (time > 95) {
				// match.matchTime =
				// Utility.parseTimeToDate(match.matchStartTime);
			} else if (time < 95 && time > 90) {
				match.matchTime = "下半场90'+";
			} else if (time <= 90 && time > 45) {
				match.matchTime = "下半场" + time + "'";
			} else {
				match.matchTime = "上半场" + time + "'";
			}
		}
		for (Match match : mEndedList) {
			int time = Utility.caculateMatchingTime(match.matchStartTime);
			if (time > 95) {
				// match.matchTime =
				// Utility.parseTimeToDate(match.matchStartTime);
			} else if (time < 95 && time > 90) {
				match.matchTime = "下半场90'+";
			} else if (time <= 90 && time > 45) {
				match.matchTime = "下半场" + time + "'";
			} else {
				match.matchTime = "上半场" + time + "'";
			}
		}
	}
}
