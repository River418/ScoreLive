package com.scorelive;

import com.scorelive.fragment.ScoreFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 即时比分的Adapter
 * @author River
 *
 */
public class ScoreFragmentPageAdapter extends FragmentPagerAdapter{

	public ScoreFragmentPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return new ScoreFragment();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		switch(position){
		case 0:
			return "定制";
		case 1:
			return "全部";
		case 2:
			return "北单";
		case 3:
			return "竞彩";
		}
		return super.getPageTitle(position);
	}

	

}
