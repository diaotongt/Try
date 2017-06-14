package com.atguigu.mtime;

import com.atguigu.mtime.fragment.UserFragment;
import com.atguigu.mtime.user.data.LoacalUserInfo;
import com.atguigu.mtime.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户中心 登录页面
 * 
 * @author Diaotong
 */
public class UserLoginActivity extends Activity implements OnClickListener {

	LoacalUserInfo loacalUserInfo;
	private static final int REFRESH = 255;
	private String UserName = CacheUtils.getString(UserLoginActivity.this,
			"ISLOG");
	/**
	 * 当前页面控件
	 */
	private ImageView iv_user_fm_act_login_back;
	private TextView tv_user_fm_act_login_freeregister;
	private EditText et_user_fm_act_login_name;
	private EditText et_user_fm_act_login_password;
	private TextView tv_user_fm_act_login_lognow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_fm_act_login_diaotong);
		loacalUserInfo = (LoacalUserInfo) getApplication();
		initLayout();
		iv_user_fm_act_login_back.setOnClickListener(this);
		tv_user_fm_act_login_freeregister.setOnClickListener(this);
		tv_user_fm_act_login_lognow.setOnClickListener(this);
	}

	private void initLayout() {
		iv_user_fm_act_login_back = (ImageView) findViewById(R.id.iv_user_fm_act_login_back);
		tv_user_fm_act_login_freeregister = (TextView) findViewById(R.id.tv_user_fm_act_login_freeregister);
		et_user_fm_act_login_name = (EditText) findViewById(R.id.et_user_fm_act_login_name);
		et_user_fm_act_login_password = (EditText) findViewById(R.id.et_user_fm_act_login_password);
		tv_user_fm_act_login_lognow = (TextView) findViewById(R.id.tv_user_fm_act_login_lognow);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_user_fm_act_login_back:
			finish();
			break;

		case R.id.tv_user_fm_act_login_freeregister:
			startActivity(new Intent(UserLoginActivity.this,
					UserRegisterActivity.class));
			break;

		case R.id.tv_user_fm_act_login_lognow:
			/**
			 * 登录成功跳转Activity的同时 使fragment 更新至需要的页面 完成信息交互
			 * 
			 * @author Diaotong
			 */
			String name = et_user_fm_act_login_name.getText().toString();
			String password = et_user_fm_act_login_password.getText()
					.toString();
			if ("zhiliandui".equals(name)
					&& loacalUserInfo.getPassWord().equals(password)) {
				Intent intent = new Intent(UserLoginActivity.this,
						MainActivity.class);
				CacheUtils.setString(UserLoginActivity.this, "ISLOG", name);
				intent.putExtra("Position", 4);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(UserLoginActivity.this, "登录失败请确认信息", 0).show();
			}

			break;

		default:
			break;
		}
	}
}
