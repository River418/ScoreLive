package com.scorelive.common.core.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.R;
import com.scorelive.common.config.AppConstants;
import com.scorelive.common.config.AppConstants.BetType;
import com.scorelive.common.utils.Utility;
import com.scorelive.module.Group;
import com.scorelive.module.Match;

public class ScoreCustomListAdapter extends ScoreListBaseAdapter {

	private List<Group> mGroupList = new ArrayList<Group>();
	private HashMap<String, ArrayList<Match>> mMatchMap = new HashMap<String, ArrayList<Match>>();

	public ScoreCustomListAdapter(Context context, int type) {
		super(context, type);
	}

	public void setData(List<Group> groupList,
			HashMap<String, ArrayList<Match>> matchMap) {
		mGroupList = groupList;
		mMatchMap = matchMap;
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		Group group = mGroupList.get(groupPosition);
		mMatchMap.get(String.valueOf(group.netId));
		return mMatchMap.get(String.valueOf(group.netId)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return super.getChildId(groupPosition, childPosition);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MatchItem matchItem = null;
		if (convertView == null) {
			switch (mAdapterType) {
			case AppConstants.BetType.CUSTOMIZE:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.score_match_item, null);
				break;
			}
			matchItem = new MatchItem(convertView);
			convertView.setTag(matchItem);
		} else {
			matchItem = (MatchItem) convertView.getTag();
		}
		Match match = (Match) getChild(groupPosition, childPosition);
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
		case AppConstants.MatchStatus.MIDDLE:
			match.matchTime = "中场休息";
			break;
		default:
			match.matchTime = Utility.parseTimeToDate(match.matchStartTime);
			break;
		}
		if (match != null) {
			int leagueId = match.leagueId;
			int color = Utility.pickLeagueColor(mContext, leagueId);
			matchItem.setLeagueColor(color);
			matchItem.setLeague(match.leagueName);
			if (TextUtils.isEmpty(match.hostTeamIndex)
					|| TextUtils.isEmpty(match.visitTeamIndex)) {
				matchItem.setHostName(match.hostTeamName);
				matchItem.setVisitName(match.visitTeamName);
			} else {
				matchItem.setHostName("(" + match.hostTeamIndex + ")"
						+ match.hostTeamName);
				matchItem.setVisitName("(" + match.visitTeamIndex + ")"
						+ match.visitTeamName);
			}
			matchItem.setHostYellow(match.hostTeamYellow);
			matchItem.setHostRed(match.hostTeamRed);
			matchItem.setVisitName(match.visitTeamName);
			matchItem.setVisitYellow(match.visitTeamYellow);
			matchItem.setVisitRed(match.visitTeamRed);
			matchItem.setScore(match.hostTeamScore+":"+match.visitTeamScore);
			matchItem.setTime(match.matchTime);
			switch (mAdapterType) {
			case BetType.BJ:
				matchItem.setEvent(match.bjNum);
				break;
			case BetType.SMG:
				matchItem.setEvent(match.SMGNum);
				break;
			case BetType.ZC:
				matchItem.setEvent(match.zcNum);
				break;
			}
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		List<Match> list = mMatchMap.get(String.valueOf(mGroupList
				.get(groupPosition).netId));
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mGroupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mGroupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return super.getGroupId(groupPosition);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.score_group_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.group_name);
		ImageView iv = (ImageView) convertView.findViewById(R.id.expand_img);
		tv.setText(mGroupList.get(groupPosition).grounName);
		if (isExpanded) {
			iv.setImageResource(R.drawable.arrow_down);
		} else {
			iv.setImageResource(R.drawable.arrow_up);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return super.hasStableIds();
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return super.isChildSelectable(groupPosition, childPosition);
	}

}
