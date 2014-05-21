package com.scorelive.ui.widget.dialog;

import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scorelive.R;
import com.scorelive.common.db.ScoreDBHandler;
import com.scorelive.ui.widget.BaseDialog;

public class EditGroupDialog extends BaseDialog implements OnClickListener {

	public static final int EDIT_NAME = 1;
	public static final int ADD_GROUP = 0;
	private int mType;
	private Activity mAct;
	private TextView mTitle;
	private EditText mEditText;
	private TextView mConfirmBtn, mCancelBtn;

	public EditGroupDialog(Activity act, int type, String name) {
		initDialog(act, R.layout.edit_group_dialog, TYPE_CENTER, false);
		mTitle = (TextView) mDialog.findViewById(R.id.title);
		mEditText = (EditText) mDialog.findViewById(R.id.edit);
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
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.confirm:
			if (mType == ADD_GROUP) {
				try {
					ScoreDBHandler.getInstance().addGroup(
							mEditText.getText().toString());
					Toast.makeText(mAct, "添加成功", Toast.LENGTH_SHORT).show();
				} catch (SQLiteException e) {
					Toast.makeText(mAct, "添加失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				// ScoreDBHandler.getInstance().
			}
			mDialog.dismiss();
			break;
		case R.id.cancel:
			mDialog.dismiss();
			break;
		}
	}

}
