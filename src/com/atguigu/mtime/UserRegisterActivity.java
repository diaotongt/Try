package com.atguigu.mtime;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户中心 注册页面
 * 
 * @author Diaotong
 */
public class UserRegisterActivity extends Activity implements OnClickListener {

	/**
	 * 密码显示出事状态与 验证码倒计时
	 * 
	 * @author Diaotong
	 */
	private boolean passwordIsHide = false;
	private int CHECK_TIME = 60;
	private static final int START_CHECKTIME = 0;
	protected static final int REDUCE_CHECKNUM = 1;
	protected static final int READY_TOGETNUM = 2;
	private ImageView iv_user_fm_act_register_back;
	private ImageView iv_user_fm_act_register_male;
	private ImageView iv_user_fm_act_register_female;
	private ImageView iv_user_fm_act_register_checkpassword;
	private EditText et_user_fm_act_register_password;
	private TextView tv_user_fm_act_register_checknum;

	/**
	 * 利用handler 完成验证码倒计时
	 * 
	 * @author Diaotong
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START_CHECKTIME:
				tv_user_fm_act_register_checknum
						.setBackgroundResource(R.drawable.set_favorited_cinema_btn);
				tv_user_fm_act_register_checknum.setTextColor(Color
						.parseColor("#aaaaaa"));
				handler.sendEmptyMessage(REDUCE_CHECKNUM);

				break;
			case REDUCE_CHECKNUM:
				if (CHECK_TIME > 0) {
					tv_user_fm_act_register_checknum.setText(CHECK_TIME
							+ "秒后获取");
					CHECK_TIME--;
					handler.sendEmptyMessageDelayed(REDUCE_CHECKNUM, 1000);
				} else {
					CHECK_TIME = 60;
					handler.sendEmptyMessage(READY_TOGETNUM);
				}
				break;

			case READY_TOGETNUM:
				tv_user_fm_act_register_checknum.setEnabled(true);
				tv_user_fm_act_register_checknum
						.setBackgroundResource(R.drawable.register_get_signcode_icon);
				tv_user_fm_act_register_checknum.setTextColor(Color
						.parseColor("#0075c4"));
				tv_user_fm_act_register_checknum.setText("获取验证码");
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_fm_act_register_diaotong);
		initLayoute();
		iv_user_fm_act_register_back.setOnClickListener(this);
		tv_user_fm_act_register_checknum.setOnClickListener(this);
		iv_user_fm_act_register_male.setOnClickListener(this);
		iv_user_fm_act_register_female.setOnClickListener(this);
		iv_user_fm_act_register_checkpassword.setOnClickListener(this);
	}

	private void initLayoute() {
		iv_user_fm_act_register_back = (ImageView) findViewById(R.id.iv_user_fm_act_register_back);
		tv_user_fm_act_register_checknum = (TextView) findViewById(R.id.tv_user_fm_act_register_checknum);
		iv_user_fm_act_register_male = (ImageView) findViewById(R.id.iv_user_fm_act_register_male);
		iv_user_fm_act_register_female = (ImageView) findViewById(R.id.iv_user_fm_act_register_female);
		iv_user_fm_act_register_checkpassword = (ImageView) findViewById(R.id.iv_user_fm_act_register_checkpassword);
		et_user_fm_act_register_password = (EditText) findViewById(R.id.et_user_fm_act_register_password);
	}

	/**
	 * 切换密码显示/隐藏状态
	 * 
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_user_fm_act_register_back:
			finish();
			break;

		case R.id.tv_user_fm_act_register_checknum:
			tv_user_fm_act_register_checknum.setEnabled(false);
			handler.sendEmptyMessage(START_CHECKTIME);
			break;

		case R.id.iv_user_fm_act_register_male:
			iv_user_fm_act_register_male
					.setImageResource(R.drawable.register_male_select);
			iv_user_fm_act_register_female
					.setImageResource(R.drawable.register_female);
			break;

		case R.id.iv_user_fm_act_register_female:
			iv_user_fm_act_register_male
					.setImageResource(R.drawable.register_male);
			iv_user_fm_act_register_female
					.setImageResource(R.drawable.register_female_select);
			break;
		case R.id.iv_user_fm_act_register_checkpassword:
			if (passwordIsHide) {
				iv_user_fm_act_register_checkpassword
						.setBackgroundResource(R.drawable.password_show);
				et_user_fm_act_register_password
						.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				passwordIsHide = false;
			} else {
				iv_user_fm_act_register_checkpassword
						.setBackgroundResource(R.drawable.password_hide);
				et_user_fm_act_register_password
						.setInputType(InputType.TYPE_CLASS_TEXT
								| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				passwordIsHide = true;
			}

			break;
		default:
			break;
		}

	}
}
