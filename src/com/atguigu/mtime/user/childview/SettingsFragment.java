package com.atguigu.mtime.user.childview;

import com.atguigu.mtime.R;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsFragment extends Fragment implements OnClickListener {
	private static final String GRANDPOSITION = "GRANDPOSITION";
	private static final int BROADRETURN = 224;
	private static final int ANNOUNCECONTRL = 225;
	private static final int ABOUTUS = 226;
	private static String UserName;
	private static Context context;
	private View view;
	private Intent intent;
	private ImageView iv_fm_user_settings_back;
	private LinearLayout ll_user_settings_broadreturn;
	private LinearLayout ll_user_settings_announcecontrl;
	private LinearLayout ll_user_settings_aboutus;
	private LinearLayout ll_user_settings_clearrom;
	private TextView tv_fm_user_settings_clearrom_content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_settings, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		intent = new Intent(context, UserGrandChildRootActivity.class);
		tv_fm_user_settings_clearrom_content.setText(10 * Math.random() + "MB");
	}

	private void initOnClick() {
		iv_fm_user_settings_back.setOnClickListener(this);
		ll_user_settings_broadreturn.setOnClickListener(this);
		ll_user_settings_announcecontrl.setOnClickListener(this);
		ll_user_settings_aboutus.setOnClickListener(this);
		ll_user_settings_clearrom.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_settings_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_settings_back);
		ll_user_settings_broadreturn = (LinearLayout) view
				.findViewById(R.id.ll_user_settings_broadreturn);
		ll_user_settings_announcecontrl = (LinearLayout) view
				.findViewById(R.id.ll_user_settings_announcecontrl);
		ll_user_settings_aboutus = (LinearLayout) view
				.findViewById(R.id.ll_user_settings_aboutus);
		ll_user_settings_clearrom = (LinearLayout) view
				.findViewById(R.id.ll_user_settings_clearrom);
		tv_fm_user_settings_clearrom_content = (TextView) view
				.findViewById(R.id.tv_fm_user_settings_clearrom_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_settings_back:
			getActivity().finish();
			break;

		case R.id.ll_user_settings_broadreturn:
			intent.putExtra(GRANDPOSITION, BROADRETURN);
			startActivity(intent);
			break;

		case R.id.ll_user_settings_announcecontrl:
			intent.putExtra(GRANDPOSITION, ANNOUNCECONTRL);
			startActivity(intent);
			break;

		case R.id.ll_user_settings_aboutus:
			intent.putExtra(GRANDPOSITION, ABOUTUS);
			startActivity(intent);
			break;

		case R.id.ll_user_settings_clearrom:
			tv_fm_user_settings_clearrom_content.setText("0.000MB");
			break;

		default:
			break;
		}
	}
}
