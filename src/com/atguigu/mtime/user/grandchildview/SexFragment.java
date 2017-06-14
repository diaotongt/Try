package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.UserChildRootActivity;
import com.atguigu.mtime.user.data.LoacalUserInfo;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

public class SexFragment extends Fragment implements OnClickListener {
	private static final String USERPOSITION = "USERPOSITION";
	private static final int CHANGEINFO = 10;
	private static String UserName;
	private static Context context;
	LoacalUserInfo localUserInfo;
	private View view;
	private ImageView iv_fm_user_grandchild_sex_back;
	private ImageView iv_fm_user_grandchild_sex_save;
	private CheckBox cb_fm_user_grandchild_sex_boy;
	private CheckBox cb_fm_user_grandchild_sex_girl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_grandchild_sex,
				null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		localUserInfo = (LoacalUserInfo) getActivity().getApplication();
		if (localUserInfo.getSex().equals("男")) {
			cb_fm_user_grandchild_sex_boy.setChecked(true);
			cb_fm_user_grandchild_sex_girl.setChecked(false);
		} else {
			cb_fm_user_grandchild_sex_boy.setChecked(false);
			cb_fm_user_grandchild_sex_girl.setChecked(true);
		}
	}

	private void initOnClick() {
		iv_fm_user_grandchild_sex_back.setOnClickListener(this);
		iv_fm_user_grandchild_sex_save.setOnClickListener(this);
		cb_fm_user_grandchild_sex_boy.setOnClickListener(this);
		cb_fm_user_grandchild_sex_girl.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_grandchild_sex_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_sex_back);
		iv_fm_user_grandchild_sex_save = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_sex_save);
		cb_fm_user_grandchild_sex_boy = (CheckBox) view
				.findViewById(R.id.cb_fm_user_grandchild_sex_boy);
		cb_fm_user_grandchild_sex_girl = (CheckBox) view
				.findViewById(R.id.cb_fm_user_grandchild_sex_girl);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_grandchild_sex_back:
			getActivity().finish();
			break;

		case R.id.iv_fm_user_grandchild_sex_save:
			if (cb_fm_user_grandchild_sex_boy.isChecked()) {
				localUserInfo.setSex("男");
			} else {
				localUserInfo.setSex("女");
			}
			Intent intent = new Intent(context, UserChildRootActivity.class);
			intent.putExtra(USERPOSITION, CHANGEINFO);
			startActivity(intent);
			break;

		case R.id.cb_fm_user_grandchild_sex_boy:
			cb_fm_user_grandchild_sex_boy.setEnabled(false);
			cb_fm_user_grandchild_sex_girl.setEnabled(true);
			cb_fm_user_grandchild_sex_boy.setChecked(true);
			cb_fm_user_grandchild_sex_girl.setChecked(false);
			break;

		case R.id.cb_fm_user_grandchild_sex_girl:
			cb_fm_user_grandchild_sex_boy.setEnabled(true);
			cb_fm_user_grandchild_sex_girl.setEnabled(false);
			cb_fm_user_grandchild_sex_boy.setChecked(false);
			cb_fm_user_grandchild_sex_girl.setChecked(true);
			break;

		default:
			break;
		}
	}
}
