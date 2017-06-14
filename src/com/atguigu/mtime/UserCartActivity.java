package com.atguigu.mtime;

import com.atguigu.mtime.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class UserCartActivity extends Activity implements OnClickListener {

	private String UserName = CacheUtils.getString(UserCartActivity.this,
			"ISLOG");
	private ImageView iv_user_fm_act_cart_back;
	private ImageView iv_user_fm_act_cart_go_shopping;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_fm_act_cart);

		initLayout();

		iv_user_fm_act_cart_back.setOnClickListener(this);
		iv_user_fm_act_cart_go_shopping.setOnClickListener(this);

	}

	private void initLayout() {
		iv_user_fm_act_cart_back = (ImageView) findViewById(R.id.iv_user_fm_act_cart_back);
		iv_user_fm_act_cart_go_shopping = (ImageView) findViewById(R.id.iv_user_fm_act_cart_go_shopping);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_user_fm_act_cart_back:
			finish();
			break;

		case R.id.iv_user_fm_act_cart_go_shopping:
			Intent intent = new Intent(UserCartActivity.this,
					MainActivity.class);
			intent.putExtra("Position", 2);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
}
