package com.scorelive.common.core.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.R;
import com.scorelive.common.config.AppConstants;
import com.scorelive.common.config.AppConstants.BetType;
import com.scorelive.common.utils.Utility;
import com.scorelive.module.Match;

public class ScoreListBaseAdapter extends BaseExpandableListAdapter {

	protected int mAdapterType = -1;
	protected Context mContext;

	private String[] mMatchArray = { "正在进行", "未开始", "已结束" };
	private ArrayList<Match> mEndedList;// 已结束
	private ArrayList<Match> mMatchingList;// 正在进行
	private ArrayList<Match> mUnstartList;// 未开始

	public ScoreListBaseAdapter(Context context, int type) {
		mAdapterType = type;
		mContext = context;
	}

	public void setData(ArrayList<Match> unstartList,
			ArrayList<Match> matchingList, ArrayList<Match> endedList) {
		mUnstartList = unstartList;
		mMatchingList = matchingList;
		mEndedList = endedList;
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		Object object = null;
		switch (groupPosition) {
		case 0:
			if (mMatchingList != null) {
				object = mMatchingList.get(childPosition);
			}
			return object;
		case 1:
			if (mEndedList != null) {
				object = mEndedList.get(childPosition);
			}
			return object;
		case 2:
			if (mUnstartList != null) {
				object = mUnstartList.get(childPosition);
			}
			return object;
		}
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
			switch (mAdapterType) {
			case AppConstants.BetType.ALL:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.score_match_item, null);
				break;
			case AppConstants.BetType.CUSTOMIZE:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.score_match_item, null);
				break;
			case AppConstants.BetType.SMG:
			case AppConstants.BetType.BJ:
			case AppConstants.BetType.ZC:
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.score_match_item_id, null);
				break;
			}
			matchItem = new MatchItem(convertView);
			convertView.setTag(matchItem);
		} else {
			matchItem = (MatchItem) convertView.getTag();
		}
		Match match = (Match) getChild(groupPosition, childPosition);
		int time = Utility.caculateMatchingTime(match.matchStartTime);
		if (time > 95) {
			match.matchTime = Utility.parseTimeToDate(match.matchStartTime);
		} else if (time < 95 && time > 90) {
			match.matchTime = "下半场90'+";
		} else if (time <= 90 && time > 45) {
			match.matchTime = "下半场" + time + "'";
		} else {
			match.matchTime = "上半场" + time + "'";
		}
		if (match != null) {
			int leagueId = match.leagueId;
			if(!match.leagueColor.equalsIgnoreCase("")){
				int color = Color.parseColor(match.leagueColor);
				matchItem.setLeagueColor(color);
			}
			matchItem.setLeague(match.leagueName);
			matchItem.setHostName(match.hostTeamName);
			matchItem.setHostIndex("(" + match.hostTeamIndex + ")");
			matchItem.setHostYellow(match.hostTeamYellow);
			matchItem.setHostRed(match.hostTeamRed);
			matchItem.setVisitName(match.visitTeamName);
			matchItem.setVisitIndex("(" + match.visitTeamIndex + ")");
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
//		convertView.setOnLongClickListener(new OnLongClickListener(){
//
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//		});

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		switch (groupPosition) {
		case 0:
			if (mMatchingList != null) {
				return mMatchingList.size();
			}
			break;
		case 1:
			if (mEndedList != null) {
				return mEndedList.size();
			}
			break;
		case 2:
			if (mUnstartList != null) {
				return mUnstartList.size();
			}
			break;
		}
		return 0;
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
		return true;
	}

	class MatchItem {
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
		TextView event_tv;
		ImageView league_iv;

		public MatchItem(View view) {
			league_iv = (ImageView) view.findViewById(R.id.league_color);
			league_tv = (TextView) view.findViewById(R.id.league);
			switch (mAdapterType) {
			case BetType.BJ:
			case BetType.SMG:
			case BetType.ZC:
				event_tv = (TextView) view.findViewById(R.id.event);
				break;
			}
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

		public void setLeagueColor(int color) {
			league_iv.setBackgroundColor(color);
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

		public void setEvent(String event) {
			event_tv.setText(event);
		}

	}

}
