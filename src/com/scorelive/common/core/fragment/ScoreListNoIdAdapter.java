package com.scorelive.common.core.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.R;
import com.scorelive.module.Match;

public class ScoreListNoIdAdapter extends BaseExpandableListAdapter {

	private String[] mMatchArray = { "正在进行", "未开始", "已结束" };
	private ArrayList<Match> mEndedList;// 已结束
	private ArrayList<Match> mMatchingList;// 正在进行
	private ArrayList<Match> mUnstartList;// 未开始
	private Context mContext;

	public ScoreListNoIdAdapter(Context context) {
		mContext = context;
	}

	public void setData(ArrayList<Match> unstartList,
			ArrayList<Match> matchingList, ArrayList<Match> endedList) {
		mUnstartList = unstartList;
		mMatchingList = matchingList;
		mEndedList = endedList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		// switch (groupPosition) {
		// case 0:
		// return mMatchingList.get(childPosition);
		// case 1:
		// return mUnstartList.get(childPosition);
		// case 2:
		// return mEndedList.get(childPosition);
		// }
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MatchItem matchItem = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.score_match_item, null);
			matchItem = new MatchItem(convertView);
			convertView.setTag(matchItem);
		} else {
			matchItem = (MatchItem) convertView.getTag();
		}
		Match match = (Match) getChild(groupPosition, childPosition);
		if (match != null) {
			matchItem.setHostName(match.hostTeamName);
			matchItem.setHostIndex(match.hostTeamIndex);
			matchItem.setHostYellow(match.hostTeamYellow);
			matchItem.setHostRed(match.hostTeamRed);
			matchItem.setVisitName(match.visitTeamName);
			matchItem.setVisitIndex(match.visitTeamIndex);
			matchItem.setVisitYellow(match.visitTeamYellow);
			matchItem.setVisitRed(match.visitTeamRed);
			matchItem.setScore(match.matchScore);
			matchItem.setTime(match.matchTime);
			matchItem.setLeague(match.matchLeague);
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mMatchArray[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mMatchArray.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
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
		switch (groupPosition) {
		case 0:
			// if(mMatchingList.size()!=0){
			tv.setText("正在进行");
			// }
			break;
		case 1:
			// if(mEndedList.size()!=0){
			tv.setText("已经结束");
			// }
			break;
		case 2:
			// if(mUnstartList.size()!=0){
			tv.setText("还没开始");
			// }
			break;
		}
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
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	private class MatchItem {
		TextView league_tv;
		TextView hostName_tv;
		TextView hostIndex_tv;
		TextView visitName_tv;
		TextView visitIndex_tv;
		TextView hostYellow_tv;
		TextView hostRed_tv;
		TextView visitYellow_tv;
		TextView visitRed_tv;
		TextView score_tv;
		TextView time_tv;

		public MatchItem(View view) {
			league_tv = (TextView) view.findViewById(R.id.league);
			View hostView = (View) view.findViewById(R.id.host_team);
			hostName_tv = (TextView) hostView.findViewById(R.id.team_name);
			hostIndex_tv = (TextView) hostView.findViewById(R.id.team_index);
			hostYellow_tv = (TextView) hostView.findViewById(R.id.yellow_card);
			hostRed_tv = (TextView) hostView.findViewById(R.id.red_card);
			score_tv = (TextView) view.findViewById(R.id.score);
			time_tv = (TextView) view.findViewById(R.id.time);
			View visitView = (View) view.findViewById(R.id.visit_team);
			visitName_tv = (TextView) visitView.findViewById(R.id.team_name);
			visitIndex_tv = (TextView) visitView.findViewById(R.id.team_index);
			visitYellow_tv = (TextView) visitView
					.findViewById(R.id.yellow_card);
			visitRed_tv = (TextView) visitView.findViewById(R.id.red_card);
		}

		public void setLeague(String league) {
			league_tv.setText(league);
		}

		public void setScore(String score) {
			score_tv.setText(score);
		}

		public void setTime(String time) {
			time_tv.setText(time);
		}

		public void setHostName(String name) {
			hostName_tv.setText(name);
		}

		public void setHostIndex(String index) {
			hostIndex_tv.setText(index);
		}

		public void setHostYellow(String count) {
			hostYellow_tv.setText(count);
		}

		public void setHostRed(String count) {
			hostRed_tv.setText(count);
		}

		public void setVisitName(String name) {
			visitName_tv.setText(name);
		}

		public void setVisitIndex(String index) {
			visitIndex_tv.setText(index);
		}

		public void setVisitYellow(String count) {
			visitYellow_tv.setText(count);
		}

		public void setVisitRed(String count) {
			visitRed_tv.setText(count);
		}

	}

}
