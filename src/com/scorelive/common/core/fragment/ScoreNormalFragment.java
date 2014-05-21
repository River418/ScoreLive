package com.scorelive.common.core.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;

import com.scorelive.R;
import com.scorelive.ScoreDetailActivity;
import com.scorelive.ScorePageActivity;
import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.common.itask.IShortTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.itask.quick.task.GroupListTask;
import com.scorelive.common.itask.quick.task.QueryMatchListByGroup;
import com.scorelive.common.utils.AppConstants;
import com.scorelive.module.Group;
import com.scorelive.module.Match;
import com.scorelive.ui.widget.ProgressDialogMe;
import com.scorelive.ui.widget.dialog.EditGroupDialog;

public class ScoreNormalFragment extends ScoreBaseFragment implements
		IShortTaskListener {
	private ExpandableListView mListView;

	private int mFragmentType;
	private ScoreListBaseAdapter mAdapter;
	private Context mContext;
	private int MENU_MANAGER_GROUP = 1;
	private int MENU_MANAGER_MATCH = 0;
	private ProgressDialogMe mProgressDialog;
	private ArrayList<Group> mGroupList;
	private HashMap<String, ArrayList<Match>> mGroupMatchMap;

	public ScoreNormalFragment(int type) {
		mFragmentType = type;
	}

	private final MyHandler mHandler = new MyHandler();

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case AppConstants.MsgType.QUERY_MATCH_BY_GROUP_SUCCESS:
				setData();
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				break;
			}
		}

	}

	@Override
	public void setData(ArrayList<Match> unstart, ArrayList<Match> matching,
			ArrayList<Match> ended) {
		super.setData(unstart, matching, ended);
		if (mFragmentType != AppConstants.BetType.CUSTOMIZE && mAdapter != null) {
			mAdapter.setData(unstart, matching, ended);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View rootView = inflater.inflate(R.layout.score_fragment, container,
				false);
		mListView = (ExpandableListView) rootView
				.findViewById(R.id.match_listview);
		View v = null;
		switch (mFragmentType) {
		case AppConstants.BetType.ALL:
		case AppConstants.BetType.CUSTOMIZE:
			v = inflater.inflate(R.layout.score_match_header, null);
			break;
		case AppConstants.BetType.BJ:
		case AppConstants.BetType.SMG:
		case AppConstants.BetType.ZC:
			v = inflater.inflate(R.layout.score_match_header_id, null);
			break;
		}

		mListView.addHeaderView(v);
		if (mFragmentType != AppConstants.BetType.CUSTOMIZE) {
			mAdapter = new ScoreListBaseAdapter(this.getActivity(),
					mFragmentType);
		} else {
			mAdapter = new ScoreCustomListAdapter(this.getActivity(),
					mFragmentType);
		}
		mListView.setAdapter(mAdapter);
		mListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Match match = (Match) mAdapter.getChild(groupPosition,
						childPosition);
				Intent intent = new Intent();
				intent.setClass(mContext, ScoreDetailActivity.class);
				intent.putExtra("match", match);
				mContext.startActivity(intent);
				return true;
			}

		});
		// mListView.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		// });
		registerForContextMenu(mListView);
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		int type = ExpandableListView
				.getPackedPositionType(((ExpandableListContextMenuInfo) menuInfo).packedPosition);
		switch (mFragmentType) {
		case AppConstants.BetType.CUSTOMIZE:
			if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
				menu.setHeaderTitle("更改定制分组");
				menu.add(MENU_MANAGER_MATCH, 1, 0, "从该分组删除");
				List<Group> list = ScoreDBHandler.getInstance().getGroupList();
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						Group group = list.get(i);
						menu.add(MENU_MANAGER_MATCH, group.id, group.id, "移动到"
								+ group.grounName);
					}
				}
			}
			if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
				menu.setHeaderTitle("管理定制分组");
				menu.add(MENU_MANAGER_GROUP, 1, 1, "删除");
				menu.add(MENU_MANAGER_GROUP, 2, 2, "重命名");
			}
			break;
		default:
			if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
				menu.setHeaderTitle("添加到自定义分组");
				List<Group> list = ScoreDBHandler.getInstance().getGroupList();

				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Group group = list.get(i);
						menu.add(MENU_MANAGER_MATCH, group.id, group.id,
								group.grounName);
					}
				}
				menu.add(MENU_MANAGER_GROUP, list.size() + 1, list.size() + 1,
						"点击添加一个新的分组");
			}
			break;
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();
		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);
		switch (mFragmentType) {
		case AppConstants.BetType.CUSTOMIZE:
			if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			}
			if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			}
			break;
		default:
			if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
				int groupPos = ExpandableListView
						.getPackedPositionGroup(info.packedPosition);
				int childPos = ExpandableListView
						.getPackedPositionChild(info.packedPosition);
				Match match = (Match) mAdapter.getChild(groupPos, childPos);
				if (item.getGroupId() == MENU_MANAGER_GROUP) {
					EditGroupDialog dialog = new EditGroupDialog(getActivity(),EditGroupDialog.ADD_GROUP,null);
					dialog.show();
				} else {
					ScoreDBHandler.getInstance().addMatchToGroup(
							item.getItemId(), match);
				}
			} else {
				return false;
			}
			break;
		}
		return true;

	}

	public void refreshCustomFragment() {
		initGroupList();
	}

	private void initGroupList() {
		ITask task = new GroupListTask(ITask.TYPE_GROUP_LIST, ThreadManager
				.getInstance().getNewTaskId());
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	private void queryMatchByGroup() {
		ITask task = new QueryMatchListByGroup(ITask.TYPE_QUERY_MATCH_BY_GROUP,
				ThreadManager.getInstance().getNewTaskId(), mGroupList);
		task.setListener(this);
		ThreadManager.getInstance().addTask(task);
	}

	private void setData() {
		if (mFragmentType == AppConstants.BetType.CUSTOMIZE && mAdapter != null) {
			((ScoreCustomListAdapter) mAdapter).setData(mGroupList,
					mGroupMatchMap);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		switch (mFragmentType) {
		case AppConstants.BetType.CUSTOMIZE:
			// mProgressDialog = ProgressDialogMe.show(mContext, "", "正在加载...");
			// initGroupList();
			// break;
		default:
			mAdapter.setData(mUnstartList, mMatchingList, mEndedList);
		}
	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskFinish(ITask task, Object object) {
		switch (task.getTaskType()) {
		case ITask.TYPE_GROUP_LIST:
			mGroupList = (ArrayList<Group>) object;
			queryMatchByGroup();
			break;
		case ITask.TYPE_QUERY_MATCH_BY_GROUP:
			mGroupMatchMap = (HashMap<String, ArrayList<Match>>) object;
			mHandler.sendEmptyMessage(AppConstants.MsgType.QUERY_MATCH_BY_GROUP_SUCCESS);
		}

	}
}
