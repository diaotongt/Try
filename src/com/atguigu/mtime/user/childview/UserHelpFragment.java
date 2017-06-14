package com.atguigu.mtime.user.childview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.UserGrandChildRootActivity;
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

public class UserHelpFragment extends Fragment implements OnClickListener {

	private static final String GRANDPOSITION = "GRANDPOSITION";
	private static final int SHOPFLOW = 210;
	private static final int NOMALQUESTION = 211;
	private static final int INVOICE = 212;
	private static final int TELLUS = 213;
	private static final int ONLINEPAY = 214;
	private static final int BALANCPAY = 215;
	private static final int OFFSETSTATE = 216;
	private static final int POSTAGE = 217;
	private static final int AREA = 218;
	private static final int SPEED = 219;
	private static final int CHECKRECEIVE = 220;
	private static final int SERVICE = 221;
	private static final int REBACK = 222;
	private static final int BACKFLOW = 223;
	private static String UserName;
	private static Context context;
	private View view;
	private Intent intent;
	private ImageView iv_user_fm_user_help_back;
	private LinearLayout ll_fm_user_userhelp_shopflow;
	private LinearLayout ll_fm_user_userhelp_nomalquestion;
	private LinearLayout ll_fm_user_userhelp_invoice;
	private LinearLayout ll_fm_user_userhelp_tellus;
	private LinearLayout ll_fm_user_userhelp_onlinepay;
	private LinearLayout ll_fm_user_userhelp_balancepay;
	private LinearLayout ll_fm_user_userhelp_offsetstate;
	private LinearLayout ll_fm_user_userhelp_postage;
	private LinearLayout ll_fm_user_userhelp_area;
	private LinearLayout ll_fm_user_userhelp_speed;
	private LinearLayout ll_fm_user_userhelp_checkreceive;
	private LinearLayout ll_fm_user_userhelp_service;
	private LinearLayout ll_fm_user_userhelp_reback;
	private LinearLayout ll_fm_user_userhelp_backflow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_user_help, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		intent = new Intent(context, UserGrandChildRootActivity.class);
	}

	private void initOnClick() {
		iv_user_fm_user_help_back.setOnClickListener(this);
		ll_fm_user_userhelp_shopflow.setOnClickListener(this);
		ll_fm_user_userhelp_nomalquestion.setOnClickListener(this);
		ll_fm_user_userhelp_invoice.setOnClickListener(this);
		ll_fm_user_userhelp_tellus.setOnClickListener(this);
		ll_fm_user_userhelp_onlinepay.setOnClickListener(this);
		ll_fm_user_userhelp_balancepay.setOnClickListener(this);
		ll_fm_user_userhelp_offsetstate.setOnClickListener(this);
		ll_fm_user_userhelp_postage.setOnClickListener(this);
		ll_fm_user_userhelp_area.setOnClickListener(this);
		ll_fm_user_userhelp_speed.setOnClickListener(this);
		ll_fm_user_userhelp_checkreceive.setOnClickListener(this);
		ll_fm_user_userhelp_reback.setOnClickListener(this);
		ll_fm_user_userhelp_service.setOnClickListener(this);
		ll_fm_user_userhelp_backflow.setOnClickListener(this);
	}

	private void initLayout() {
		iv_user_fm_user_help_back = (ImageView) view
				.findViewById(R.id.iv_user_fm_user_help_back);
		ll_fm_user_userhelp_shopflow = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_shopflow);
		ll_fm_user_userhelp_nomalquestion = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_nomalquestion);
		ll_fm_user_userhelp_invoice = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_invoice);
		ll_fm_user_userhelp_tellus = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_tellus);
		ll_fm_user_userhelp_onlinepay = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_onlinepay);
		ll_fm_user_userhelp_balancepay = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_balancepay);
		ll_fm_user_userhelp_offsetstate = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_offsetstate);
		ll_fm_user_userhelp_postage = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_postage);
		ll_fm_user_userhelp_area = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_area);
		ll_fm_user_userhelp_speed = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_speed);
		ll_fm_user_userhelp_checkreceive = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_checkreceive);
		ll_fm_user_userhelp_service = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_service);
		ll_fm_user_userhelp_reback = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_reback);
		ll_fm_user_userhelp_backflow = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_userhelp_backflow);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_user_fm_user_help_back:
			getActivity().finish();
			break;

		case R.id.ll_fm_user_userhelp_shopflow:
			intent.putExtra(GRANDPOSITION, SHOPFLOW);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_nomalquestion:
			intent.putExtra(GRANDPOSITION, NOMALQUESTION);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_invoice:
			intent.putExtra(GRANDPOSITION, INVOICE);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_tellus:
			intent.putExtra(GRANDPOSITION, TELLUS);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_onlinepay:
			intent.putExtra(GRANDPOSITION, ONLINEPAY);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_balancepay:
			intent.putExtra(GRANDPOSITION, BALANCPAY);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_offsetstate:
			intent.putExtra(GRANDPOSITION, OFFSETSTATE);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_postage:
			intent.putExtra(GRANDPOSITION, POSTAGE);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_area:
			intent.putExtra(GRANDPOSITION, AREA);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_speed:
			intent.putExtra(GRANDPOSITION, SPEED);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_checkreceive:
			intent.putExtra(GRANDPOSITION, CHECKRECEIVE);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_service:
			intent.putExtra(GRANDPOSITION, SERVICE);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_reback:
			intent.putExtra(GRANDPOSITION, REBACK);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_userhelp_backflow:
			intent.putExtra(GRANDPOSITION, BACKFLOW);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
