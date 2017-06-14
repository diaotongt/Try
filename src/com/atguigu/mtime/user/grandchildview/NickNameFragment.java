package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.UserChildRootActivity;
import com.atguigu.mtime.UserGrandChildRootActivity;
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
import android.widget.EditText;
import android.widget.ImageView;

public class NickNameFragment extends Fragment implements OnClickListener {
	private static final String USERPOSITION = "USERPOSITION";
	private static final int CHANGEINFO = 10;
	private static String UserName;
	private static Context context;
	LoacalUserInfo localUserInfo;
	private View view;
	private ImageView iv_fm_user_grandchild_nickname_save;
	private ImageView iv_fm_user_grandchild_nickname_back;
	private ImageView iv_fm_user_grandchild_nickname_clear;
	private EditText et_fm_user_grandchild_nickname_change;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context,
				R.layout.fragment_user_grandchild_nickname, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		localUserInfo = (LoacalUserInfo) getActivity().getApplication();
		et_fm_user_grandchild_nickname_change.setText(localUserInfo
				.getUserName());
	}

	private void initOnClick() {
		iv_fm_user_grandchild_nickname_back.setOnClickListener(this);
		iv_fm_user_grandchild_nickname_save.setOnClickListener(this);
		iv_fm_user_grandchild_nickname_clear.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_grandchild_nickname_save = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_nickname_save);
		iv_fm_user_grandchild_nickname_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_nickname_back);
		et_fm_user_grandchild_nickname_change = (EditText) view
				.findViewById(R.id.et_fm_user_grandchild_nickname_change);
		iv_fm_user_grandchild_nickname_clear = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_nickname_clear);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_grandchild_nickname_back:
			getActivity().finish();
			break;

		case R.id.iv_fm_user_grandchild_nickname_save:
			Intent intent = new Intent(context,
					UserChildRootActivity.class);
			String changeName = et_fm_user_grandchild_nickname_change.getText()
					.toString().trim();
			localUserInfo.setUserName(changeName);
			intent.putExtra(USERPOSITION, CHANGEINFO);
			startActivity(intent);
			break;

		case R.id.iv_fm_user_grandchild_nickname_clear:
			et_fm_user_grandchild_nickname_change.setText("");
			break;
			
		default:
			break;
		}
	}
}
