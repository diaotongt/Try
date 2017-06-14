package com.atguigu.mtime;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用户中心 的消息页面
 * 
 * @author Diaotong
 */

public class UserMessageActivity extends Activity implements OnClickListener {
	private static final String USERPOSITION = "USERPOSITION";
	private static final int LOAD_ANNOUNCE = 0;
	private static final int LOAD_BORADCAST = 1;
	private static final int LOAD_FINISH = 3;
	private static final int SETTINGS = 11;
	private TextView tv_user_fm_act_message_announce;
	private TextView tv_user_fm_act_message_broadcast;
	private View v_user_fm_act_message_left;
	private View v_user_fm_act_message_right;
	private LinearLayout ll_fm_act_message_loading;
	private ImageView iv_user_fm_act_message_back;
	private ImageView iv_user_fm_act_message_gotosettings;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_ANNOUNCE:
				handler.sendEmptyMessageDelayed(LOAD_FINISH, 2000);
				ll_fm_act_message_loading.setVisibility(View.VISIBLE);
				break;

			case LOAD_BORADCAST:
				handler.sendEmptyMessageDelayed(LOAD_FINISH, 2000);
				ll_fm_act_message_loading.setVisibility(View.VISIBLE);
				break;

			case LOAD_FINISH:
				ll_fm_act_message_loading.setVisibility(View.GONE);
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_fm_act_message_diaotong);
		initLayout();
		tv_user_fm_act_message_announce.setOnClickListener(this);
		tv_user_fm_act_message_broadcast.setOnClickListener(this);
		iv_user_fm_act_message_back.setOnClickListener(this);
		iv_user_fm_act_message_gotosettings.setOnClickListener(this);
	}

	private void initLayout() {
		tv_user_fm_act_message_announce = (TextView) findViewById(R.id.tv_user_fm_act_message_announce);
		tv_user_fm_act_message_broadcast = (TextView) findViewById(R.id.tv_user_fm_act_message_broadcast);
		v_user_fm_act_message_left = findViewById(R.id.v_user_fm_act_message_left);
		v_user_fm_act_message_right = findViewById(R.id.v_user_fm_act_message_right);
		ll_fm_act_message_loading = (LinearLayout) findViewById(R.id.ll_fm_act_message_loading);
		iv_user_fm_act_message_back = (ImageView) findViewById(R.id.iv_user_fm_act_message_back);
		iv_user_fm_act_message_gotosettings = (ImageView) findViewById(R.id.iv_user_fm_act_message_gotosettings);
	}

	/**
	 * 模拟数据请求
	 * 
	 * @author Diaotong
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_user_fm_act_message_announce:
			tv_user_fm_act_message_announce.setTextColor(Color
					.parseColor("#0075c4"));
			tv_user_fm_act_message_broadcast.setTextColor(Color
					.parseColor("#000000"));
			v_user_fm_act_message_left.setVisibility(View.VISIBLE);
			v_user_fm_act_message_right.setVisibility(View.INVISIBLE);
			handler.sendEmptyMessage(LOAD_ANNOUNCE);
			break;

		case R.id.tv_user_fm_act_message_broadcast:
			tv_user_fm_act_message_broadcast.setTextColor(Color
					.parseColor("#0075c4"));
			tv_user_fm_act_message_announce.setTextColor(Color
					.parseColor("#000000"));
			v_user_fm_act_message_left.setVisibility(View.INVISIBLE);
			v_user_fm_act_message_right.setVisibility(View.VISIBLE);
			handler.sendEmptyMessage(LOAD_BORADCAST);
			break;

		case R.id.iv_user_fm_act_message_back:
			finish();
			break;

		case R.id.iv_user_fm_act_message_gotosettings:
			Intent userIntent = new Intent(UserMessageActivity.this,
					UserChildRootActivity.class);
			userIntent.putExtra(USERPOSITION, SETTINGS);
			startActivity(userIntent);
			break;

		default:
			break;
		}

	}
}
