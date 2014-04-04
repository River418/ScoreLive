package com.scorelive.fragment;

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

public class ScoreListViewAdapter extends BaseExpandableListAdapter {

	private String[] mMatchArray = { "正在进行", "未开始", "已结束" };
	private ArrayList<Match> mEndedList;// 已结束
	private ArrayList<Match> mMatchingList;// 正在进行
	private ArrayList<Match> mUnstartList;// 未开始
	private Context mContext;

	public ScoreListViewAdapter(Context context) {
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
		switch (groupPosition) {
		case 0:
			return mMatchingList.get(childPosition);
		case 1:
			return mUnstartList.get(childPosition);
		case 2:
			return mEndedList.get(childPosition);
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
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
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
//			if(mMatchingList.size()!=0){
				tv.setText("正在进行");
//			}
			break;
		case 1:
//			if(mEndedList.size()!=0){
				tv.setText("已经结束");
//			}
			break;
		case 2:
//			if(mUnstartList.size()!=0){
				tv.setText("还没开始");
//			}
			break;
		}
		if(isExpanded){
			iv.setImageResource(R.drawable.arrow_down);
		}else{
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

}
