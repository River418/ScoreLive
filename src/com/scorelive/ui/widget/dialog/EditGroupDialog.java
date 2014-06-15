package com.scorelive.ui.widget.dialog;

import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scorelive.R;
import com.scorelive.common.cache.GroupListCacheHandler;
import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.ui.widget.ScoreToast;

public class EditGroupDialog extends BaseDialog implements OnClickListener {

	public static final int EDIT_NAME = 1;
	public static final int ADD_GROUP = 0;
	public static final int DEL_GROUP = 2;
	private int mType;
	private int mId;// group id
	private Activity mAct;
	private TextView mTitle,mDelText;
	private EditText mEditText;
	private TextView mConfirmBtn, mCancelBtn;
	private ActionResult mListener;

	public EditGroupDialog(Activity act, int type, String name) {
		initDialog(act, R.layout.edit_group_dialog, TYPE_CENTER, false);
		mAct = act;
		mTitle = (TextView) mDialog.findViewById(R.id.title);
		mEditText = (EditText) mDialog.findViewById(R.id.edit);
		mDelText = (TextView)mDialog.findViewById(R.id.del_text);
		mConfirmBtn = (TextView) mDialog.findViewById(R.id.confirm);
		mConfirmBtn.setOnClickListener(this);
		mCancelBtn = (TextView) mDialog.findViewById(R.id.cancel);
		mCancelBtn.setOnClickListener(this);
		mType = type;
		switch (type) {
		case EDIT_NAME:
			mTitle.setText("重命名分组");
			if (name != null) {
				mEditText.setText(name);
			}
			break;
		case ADD_GROUP:
			mTitle.setText("新建分组");
			break;
		case DEL_GROUP:
			mTitle.setText("删除分组");
			mEditText.setVisibility(View.GONE);
			mDelText.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void setGroupId(int id) {
		mId = id;
	}
	
	public void setActionResultListener(ActionResult actionResultListener){
		this.mListener = actionResultListener;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.confirm:
			switch(mType){
			case ADD_GROUP:
				try {
//					ScoreDBHandler.getInstance().addGroup(
//							mEditText.getText().toString());
					GroupListCacheHandler.getInstance().addGroupToCache(mEditText.getText().toString());
					mListener.onActionSuccess();
					ScoreToast.makeText(mAct, "添加成功", Toast.LENGTH_SHORT).show();
				} catch (SQLiteException e) {
					ScoreToast.makeText(mAct, "添加失败", Toast.LENGTH_SHORT).show();
				}
				break;
			case EDIT_NAME:
				try {
//					ScoreDBHandler.getInstance().renameGroup(
//							mEditText.getText().toString(), mId);
					GroupListCacheHandler.getInstance().renameGroupInCache(mId, mEditText.getText().toString());
					mListener.onActionSuccess();
					ScoreToast.makeText(mAct, "修改成功", Toast.LENGTH_SHORT).show();
				} catch (SQLiteException e) {
					ScoreToast.makeText(mAct, "修改失败", Toast.LENGTH_SHORT).show();
				}
				break;
			case DEL_GROUP:
				try {
//					ScoreDBHandler.getInstance().renameGroup(
//							mEditText.getText().toString(), mId);
					GroupListCacheHandler.getInstance().delGroupInCache(mId);
					mListener.onActionSuccess();
					ScoreToast.makeText(mAct, "修改成功", Toast.LENGTH_SHORT).show();
				} catch (SQLiteException e) {
					ScoreToast.makeText(mAct, "修改失败", Toast.LENGTH_SHORT).show();
				}
				break;
				
			}
			mDialog.dismiss();
			break;
		case R.id.cancel:
			mDialog.dismiss();
			break;
		}
	}
	
	public interface ActionResult{
		public void onActionSuccess();
	}

}
