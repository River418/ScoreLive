package com.scorelive;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorelive.module.MatchAccident;

public class ScoreDetailAdapter extends BaseAdapter {
	private ArrayList<MatchAccident> mList = new ArrayList<MatchAccident>();
	private Context mContext;

	public ScoreDetailAdapter(Context context) {
		mContext = context;
	}

	public void setData(ArrayList<MatchAccident> list) {
		mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.match_detail_item, null);
		}
		MatchAccident accident = mList.get(position);
//		switch (position % 2) {
//		case 0:
//			convertView.setBackgroundColor(R.color.match_accident_second_bg);
//			break;
//		case 1:
//			convertView.setBackgroundColor(R.color.match_accident_first_bg);
//			break;
//		}
		View homeView = convertView.findViewById(R.id.home_accident);
		View visitView = convertView.findViewById(R.id.visit_accident);
		TextView accidentContent = null;
		ImageView accidentIMG = null;
		TextView accidentTime = (TextView)convertView.findViewById(R.id.accident_time);
		accidentTime.setText(accident.accident_time);
		switch (accident.accident_team) {
		case 0:// 客队事件
			homeView.setVisibility(View.INVISIBLE);
			accidentContent = (TextView) visitView
					.findViewById(R.id.visit_accident_content);
			accidentContent.setText(accident.accident_content);
			accidentIMG = (ImageView) visitView
					.findViewById(R.id.visit_accident_img);
			break;
		case 1:// 主队事件
			visitView.setVisibility(View.INVISIBLE);
			accidentContent = (TextView) homeView
					.findViewById(R.id.home_accident_content);
			accidentContent.setText(accident.accident_content);
			accidentIMG = (ImageView) homeView
					.findViewById(R.id.home_accident_img);
			break;
		}
		switch (accident.accident_type) {
		case 1:
			accidentIMG.setBackgroundResource(R.drawable.redcardsmall);
			break;
		case 2:
			accidentIMG.setBackgroundResource(R.drawable.yellowcardsmall);
			break;
		case 3:
			accidentIMG.setBackgroundResource(R.drawable.goal);
			break;
		case 4:
			accidentIMG.setBackgroundResource(R.drawable.goal);
			break;
		case 5:
			accidentIMG.setBackgroundResource(R.drawable.goal);
			break;
		case 6:
			accidentIMG.setBackgroundResource(R.drawable.subsitution);
			break;
		}
		return convertView;
	}

}
