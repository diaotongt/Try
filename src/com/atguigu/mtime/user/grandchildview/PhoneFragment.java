package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.UserChildRootActivity;
import com.atguigu.mtime.user.data.LoacalUserInfo;
import com.atguigu.mtime.utils.CacheUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneFragment extends Fragment implements OnClickListener {
	private static final String USERPOSITION = "USERPOSITION";
	private static final int CHANGEINFO = 10;
	private static String UserName;
	private static Context context;
	private View view;
	LoacalUserInfo localUserInfo;
	private ImageView iv_fm_user_grandchild_phone_back;
	private TextView tv_user_grandchild_phone_changephonenum;
	private TextView tv_fm_user_grandchild_phone_newnum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_grandchild_phone,
				null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		localUserInfo = (LoacalUserInfo) getActivity().getApplication();
		String headNum = localUserInfo.getPhoneNum().substring(0, 4);
		String endNum = localUserInfo.getPhoneNum().substring(8);

		tv_fm_user_grandchild_phone_newnum.setText(headNum + "****" + endNum);
	}

	private void initOnClick() {
		iv_fm_user_grandchild_phone_back.setOnClickListener(this);
		tv_user_grandchild_phone_changephonenum.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_grandchild_phone_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_phone_back);
		tv_user_grandchild_phone_changephonenum = (TextView) view
				.findViewById(R.id.tv_user_grandchild_phone_changephonenum);
		tv_fm_user_grandchild_phone_newnum = (TextView) view
				.findViewById(R.id.tv_fm_user_grandchild_phone_newnum);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_grandchild_phone_back:
			getActivity().finish();
			break;

		case R.id.tv_user_grandchild_phone_changephonenum:
			final EditText etLocal = new EditText(context);
			etLocal.setGravity(Gravity.CENTER);
			etLocal.setHint("请输入新号码");
			new AlertDialog.Builder(context)
					.setTitle("新号码")
					.setView(etLocal)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (etLocal.getText().toString().trim()
											.length() != 11) {
										Toast.makeText(context,
												"请输入正确的11位手机号码",
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(context, "手机号码修改成功",
												Toast.LENGTH_SHORT).show();
										localUserInfo.setPhoneNum(etLocal
												.getText().toString().trim());
										Intent intent = new Intent(context,
												UserChildRootActivity.class);
										intent.putExtra(USERPOSITION,
												CHANGEINFO);
										startActivity(intent);
									}
								}
							}).setNegativeButton("取消", null).show();
			break;

		default:
			break;
		}
	}
}
