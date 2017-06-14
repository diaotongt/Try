package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.user.data.LoacalUserInfo;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordFragment extends Fragment implements OnClickListener {

	private static String UserName;
	private static Context context;
	private View view;
	LoacalUserInfo loacalUserInfo;
	private ImageView iv_fm_user_grandchild_changepassword_back;
	private EditText et_fm_user_grandchild_changepassword_oldpwd;
	private EditText et_fm_user_grandchild_changepassword_newpwd;
	private EditText et_fm_user_grandchild_changepassword_pwdconfirm;
	private TextView tv_fm_user_grandchild_changepassword_deep;
	private TextView tv_fm_user_grandchild_changepassword_changepwd;

	class MyTextWatcher implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.length() == 0) {
				tv_fm_user_grandchild_changepassword_deep
						.setVisibility(View.INVISIBLE);
			} else if (s.length() > 0 && s.length() < 4) {
				tv_fm_user_grandchild_changepassword_deep.setText("弱");
				tv_fm_user_grandchild_changepassword_deep
						.setTextColor(Color.RED);
				tv_fm_user_grandchild_changepassword_deep
						.setVisibility(View.VISIBLE);
			} else if (s.length() >= 4 && s.length() < 8) {
				tv_fm_user_grandchild_changepassword_deep.setText("中");
				tv_fm_user_grandchild_changepassword_deep
						.setTextColor(Color.YELLOW);
			} else {
				tv_fm_user_grandchild_changepassword_deep.setText("强");
				tv_fm_user_grandchild_changepassword_deep
						.setTextColor(Color.BLUE);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	}

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
				R.layout.fragment_user_grandchild_changepassword, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		loacalUserInfo = (LoacalUserInfo) getActivity().getApplication();
	}

	private void initOnClick() {
		iv_fm_user_grandchild_changepassword_back.setOnClickListener(this);
		tv_fm_user_grandchild_changepassword_changepwd.setOnClickListener(this);
		et_fm_user_grandchild_changepassword_newpwd
				.addTextChangedListener(new MyTextWatcher());
	}

	private void initLayout() {
		iv_fm_user_grandchild_changepassword_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_changepassword_back);
		tv_fm_user_grandchild_changepassword_changepwd = (TextView) view
				.findViewById(R.id.tv_fm_user_grandchild_changepassword_changepwd);
		et_fm_user_grandchild_changepassword_oldpwd = (EditText) view
				.findViewById(R.id.et_fm_user_grandchild_changepassword_oldpwd);
		et_fm_user_grandchild_changepassword_newpwd = (EditText) view
				.findViewById(R.id.et_fm_user_grandchild_changepassword_newpwd);
		et_fm_user_grandchild_changepassword_pwdconfirm = (EditText) view
				.findViewById(R.id.et_fm_user_grandchild_changepassword_pwdconfirm);
		tv_fm_user_grandchild_changepassword_deep = (TextView) view
				.findViewById(R.id.tv_fm_user_grandchild_changepassword_deep);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_grandchild_changepassword_back:
			getActivity().finish();
			break;

		case R.id.tv_fm_user_grandchild_changepassword_changepwd:
			String oldPwd = et_fm_user_grandchild_changepassword_oldpwd
					.getText().toString().trim();
			String newPwd = et_fm_user_grandchild_changepassword_newpwd
					.getText().toString().trim();
			String pwdConfirm = et_fm_user_grandchild_changepassword_pwdconfirm
					.getText().toString().trim();
			if (!oldPwd.equals(loacalUserInfo.getPassWord())) {
				Toast.makeText(context, "旧密码不正确", Toast.LENGTH_SHORT).show();
			} else {
				if (newPwd.length() < 3) {
					Toast.makeText(context, "密码至少需要由3位数字或字母或字符组成",
							Toast.LENGTH_SHORT).show();
				} else {
					if (newPwd.equals(oldPwd)) {
						Toast.makeText(context, "新密码与旧密码相同，请重新输入",
								Toast.LENGTH_SHORT).show();
						et_fm_user_grandchild_changepassword_newpwd.setText("");
						et_fm_user_grandchild_changepassword_pwdconfirm
								.setText("");
					} else {
						if (!newPwd.equals(pwdConfirm)) {
							Toast.makeText(context, "确认密码与新密码不同，请重新输入",
									Toast.LENGTH_SHORT).show();
							et_fm_user_grandchild_changepassword_newpwd
									.setText("");
							et_fm_user_grandchild_changepassword_pwdconfirm
									.setText("");
						} else {
							Toast.makeText(context, "密码修改成功",
									Toast.LENGTH_SHORT).show();
							loacalUserInfo.setPassWord(newPwd);
							getActivity().finish();
						}
					}
				}
			}

			break;

		default:
			break;
		}
	}
}
