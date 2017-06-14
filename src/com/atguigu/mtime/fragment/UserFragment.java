package com.atguigu.mtime.fragment;

import com.atguigu.mtime.R;
import com.atguigu.mtime.UserCartActivity;
import com.atguigu.mtime.UserChildRootActivity;
import com.atguigu.mtime.UserLoginActivity;
import com.atguigu.mtime.UserMessageActivity;
import com.atguigu.mtime.UserRegisterActivity;
import com.atguigu.mtime.user.data.LoacalUserInfo;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 首页Fragment
 * 
 * @author Diaotong
 */

public class UserFragment extends Fragment implements OnClickListener {

	private static String UserName;
	private static final String USERPOSITION = "USERPOSITION";
	private static final int FILMTICKETORDER = 4;
	private static final int GOODSORDER = 5;
	private static final int GIFTCARD = 6;
	private static final int MYOFFSET = 7;
	private static final int MYFILM = 8;
	private static final int MYFOCUS = 9;
	private static final int CHANGEINFO = 10;
	private static final int SETTINGS = 11;
	private static final int SCANNER = 12;
	private static final int ADVICE = 13;
	private static final int GOFORSCORE = 14;
	private static final int USERHELP = 15;

	protected static final int CHECK_SCROLLVIEW = 253;
	protected static final int STOP_CHECK = 254;
	private static final int REFRESH = 255;
	private static Context context;
	static LoacalUserInfo localUserInfo;
	private View view;
	LoacalUserInfo loacalUserInfo;
	private ImageView iv_user_fm_main_message;
	private TextView tv_user_fm_main_login;
	private TextView tv_user_fm_main_register;
	private static TextView tv_user_fm_username;
	private TextView tv_user_fm_logout;
	private static LinearLayout ll_user_fm_bottom_logout;
	private static LinearLayout ll_user_fm_login_linearlayout;
	private static LinearLayout ll_user_fm_headll;
	private static ScrollView sv_user_fm_main;
	private ImageView iv_user_fm_gift_card, iv_user_fm_goods_order,
			iv_user_fm_ticket_order, iv_user_fm_cart;
	private LinearLayout ll_user_fm_my_offset, ll_user_fm_my_film,
			ll_user_fm_my_focus, ll_user_fm_change_info, ll_user_fm_settings,
			ll_user_fm_scanner, ll_user_fm_advice, ll_user_fm_go_for_score,
			ll_user_fm_user_help;

	/**
	 * 暂时不用handler
	 * 
	 * 可以做类似于服务的功能持续监听 ScrollView的状态
	 */
	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CHECK_SCROLLVIEW:
				float scrollY = sv_user_fm_main.getScrollY();
				float height = ll_user_fm_headll.getMeasuredHeight();
				if (height >= scrollY) {
					ll_user_fm_headll.setAlpha(scrollY / height);
				} else {
					ll_user_fm_headll.setAlpha(1);
				}
				handler.sendEmptyMessageDelayed(CHECK_SCROLLVIEW, 500);
				break;

			case STOP_CHECK:
				handler.removeCallbacksAndMessages(null);
				break;

			case REFRESH:
				refreshLayout();
				Log.e("TAG", "调用了refreshLayout()");
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		UserFragment.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	protected static void refreshLayout() {
		UserName = CacheUtils.getString(context, "ISLOG");
		realName = localUserInfo.getUserName();
		ll_user_fm_bottom_logout.setVisibility(View.VISIBLE);
		// 登录后修改LinearLayout中的子view样式
		if (ll_user_fm_login_linearlayout.getChildCount() == 1) {
			ll_user_fm_login_linearlayout.addView(View.inflate(context,
					R.layout.item_login_success_linearlayout, null));
			tv_user_fm_username = (TextView) ll_user_fm_login_linearlayout
					.findViewById(R.id.tv_user_fm_username);
		} else if (ll_user_fm_login_linearlayout.getChildCount() == 2) {
			ll_user_fm_login_linearlayout.removeViewAt(1);
			ll_user_fm_login_linearlayout.addView(View.inflate(context,
					R.layout.item_login_success_linearlayout, null));
			tv_user_fm_username = (TextView) ll_user_fm_login_linearlayout
					.findViewById(R.id.tv_user_fm_username);
		}
		tv_user_fm_username.setText(realName);
		Log.e("TAG", "设置了realName");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.user_fm_diaotong, container, false);
		initLayout();
		initData();
		initOnClick();

		sv_user_fm_main.setOnTouchListener(new MyOnTouchListener());

		return view;
	}

	private void initOnClick() {
		iv_user_fm_main_message.setOnClickListener(this);
		tv_user_fm_logout.setOnClickListener(this);
		iv_user_fm_ticket_order.setOnClickListener(this);
		iv_user_fm_gift_card.setOnClickListener(this);
		iv_user_fm_goods_order.setOnClickListener(this);
		iv_user_fm_cart.setOnClickListener(this);
		ll_user_fm_my_offset.setOnClickListener(this);
		ll_user_fm_change_info.setOnClickListener(this);
		ll_user_fm_my_film.setOnClickListener(this);
		ll_user_fm_my_focus.setOnClickListener(this);
		ll_user_fm_settings.setOnClickListener(this);
		ll_user_fm_scanner.setOnClickListener(this);
		ll_user_fm_advice.setOnClickListener(this);
		ll_user_fm_go_for_score.setOnClickListener(this);
		ll_user_fm_user_help.setOnClickListener(this);

	}

	/**
	 * 滑动监听 ScrollView 完成标题头的隐藏/显示
	 * 
	 * @author dtstart
	 *
	 */
	class MyOnTouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				break;

			case MotionEvent.ACTION_MOVE:

				float scrollY = sv_user_fm_main.getScrollY();

				float height = ll_user_fm_headll.getMeasuredHeight();

				if (height >= scrollY) {
					ll_user_fm_headll.setAlpha(scrollY / height);
				} else {
					ll_user_fm_headll.setAlpha(1);
				}
				break;

			case MotionEvent.ACTION_UP:

				break;

			default:
				break;
			}
			return false;
		}

	}

	/**
	 * 根据用户等于与否决定顶部用户界面的展示类型：登录/未登录
	 * 
	 */
	private void initData() {
		localUserInfo = (LoacalUserInfo) getActivity().getApplication();
		realName = localUserInfo.getUserName();
		if (UserName != "") {
			ll_user_fm_bottom_logout.setVisibility(View.VISIBLE);
			// 登录后修改LinearLayout中的子view样式
			if (ll_user_fm_login_linearlayout.getChildCount() == 1) {
				ll_user_fm_login_linearlayout.addView(View.inflate(context,
						R.layout.item_login_success_linearlayout, null));
				tv_user_fm_username = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_username);
			} else if (ll_user_fm_login_linearlayout.getChildCount() == 2) {
				ll_user_fm_login_linearlayout.removeViewAt(1);
				ll_user_fm_login_linearlayout.addView(View.inflate(context,
						R.layout.item_login_success_linearlayout, null));
				tv_user_fm_username = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_username);
			}
			tv_user_fm_username.setText(realName);
		} else {
			ll_user_fm_bottom_logout.setVisibility(View.GONE);
			// 未登录状态下检索 登录LinearLayout所需要的子View样式
			if (ll_user_fm_login_linearlayout.getChildCount() == 1) {
				ll_user_fm_login_linearlayout.addView(View.inflate(context,
						R.layout.item_login_fail_linearlayout, null));
				tv_user_fm_main_register = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_register);
				tv_user_fm_main_login = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_login);
				tv_user_fm_main_register.setOnClickListener(this);
				tv_user_fm_main_login.setOnClickListener(this);
			} else if (ll_user_fm_login_linearlayout.getChildCount() == 2) {
				ll_user_fm_login_linearlayout.removeViewAt(1);
				ll_user_fm_login_linearlayout.addView(View.inflate(context,
						R.layout.item_login_fail_linearlayout, null));
				tv_user_fm_main_register = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_register);
				tv_user_fm_main_login = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_login);
				tv_user_fm_main_register.setOnClickListener(this);
				tv_user_fm_main_login.setOnClickListener(this);
			}

		}
	}

	private void initLayout() {
		iv_user_fm_main_message = (ImageView) view
				.findViewById(R.id.iv_user_fm_main_message);

		ll_user_fm_login_linearlayout = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_login_linearlayout);

		ll_user_fm_bottom_logout = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_bottom_logout);

		ll_user_fm_headll = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_headll);

		sv_user_fm_main = (ScrollView) view.findViewById(R.id.sv_user_fm_main);
		iv_user_fm_cart = (ImageView) view.findViewById(R.id.iv_user_fm_cart);
		iv_user_fm_goods_order = (ImageView) view
				.findViewById(R.id.iv_user_fm_goods_order);
		iv_user_fm_gift_card = (ImageView) view
				.findViewById(R.id.iv_user_fm_gift_card);
		iv_user_fm_ticket_order = (ImageView) view
				.findViewById(R.id.iv_user_fm_ticket_order);

		tv_user_fm_logout = (TextView) view
				.findViewById(R.id.tv_user_fm_logout);

		ll_user_fm_my_offset = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_my_offset);
		ll_user_fm_my_film = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_my_film);
		ll_user_fm_my_focus = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_my_focus);
		ll_user_fm_change_info = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_change_info);
		ll_user_fm_settings = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_settings);
		ll_user_fm_scanner = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_scanner);
		ll_user_fm_advice = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_advice);
		ll_user_fm_go_for_score = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_go_for_score);
		ll_user_fm_user_help = (LinearLayout) view
				.findViewById(R.id.ll_user_fm_user_help);

	}

	/**
	 * 退出登录后完成顶部用户信息展示的界面
	 * 
	 */
	private Intent userIntent;
	private static String realName;

	@Override
	public void onClick(View v) {
		userIntent = new Intent(context, UserChildRootActivity.class);
		switch (v.getId()) {
		case R.id.iv_user_fm_main_message:
			startActivity(new Intent(context, UserMessageActivity.class));
			break;

		case R.id.tv_user_fm_main_register:
			startActivity(new Intent(context, UserRegisterActivity.class));
			break;

		case R.id.tv_user_fm_main_login:
			startActivity(new Intent(context, UserLoginActivity.class));
			break;
		case R.id.tv_user_fm_logout:
			ll_user_fm_bottom_logout.setVisibility(View.GONE);
			CacheUtils.setString(context, "ISLOG", "");
			UserName = CacheUtils.getString(context, "ISLOG");
			if (ll_user_fm_login_linearlayout.getChildCount() == 1) {
				ll_user_fm_login_linearlayout.addView(View.inflate(context,
						R.layout.item_login_fail_linearlayout, null));
				tv_user_fm_main_register = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_register);
				tv_user_fm_main_login = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_login);
				tv_user_fm_main_register.setOnClickListener(this);
				tv_user_fm_main_login.setOnClickListener(this);
			} else if (ll_user_fm_login_linearlayout.getChildCount() == 2) {
				ll_user_fm_login_linearlayout.removeViewAt(1);
				ll_user_fm_login_linearlayout.addView(View.inflate(context,
						R.layout.item_login_fail_linearlayout, null));
				tv_user_fm_main_register = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_register);
				tv_user_fm_main_login = (TextView) ll_user_fm_login_linearlayout
						.findViewById(R.id.tv_user_fm_main_login);
				tv_user_fm_main_register.setOnClickListener(this);
				tv_user_fm_main_login.setOnClickListener(this);
			}
			break;

		case R.id.iv_user_fm_gift_card:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, GIFTCARD);
				startActivity(userIntent);
			}
			break;

		case R.id.iv_user_fm_goods_order:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, GOODSORDER);
				startActivity(userIntent);
			}
			break;

		case R.id.iv_user_fm_ticket_order:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, FILMTICKETORDER);
				startActivity(userIntent);
			}
			break;

		case R.id.iv_user_fm_cart:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				startActivity(new Intent(context, UserCartActivity.class));
			}
			break;

		case R.id.ll_user_fm_my_offset:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, MYOFFSET);
				startActivity(userIntent);
			}
			break;

		case R.id.ll_user_fm_my_film:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, MYFILM);
				startActivity(userIntent);
			}
			break;

		case R.id.ll_user_fm_my_focus:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, MYFOCUS);
				startActivity(userIntent);
			}
			break;

		case R.id.ll_user_fm_change_info:
			if (UserName.equals("")) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				userIntent.putExtra(USERPOSITION, CHANGEINFO);
				startActivity(userIntent);
			}
			break;

		case R.id.ll_user_fm_settings:
			userIntent.putExtra(USERPOSITION, SETTINGS);
			startActivity(userIntent);
			break;

		case R.id.ll_user_fm_scanner:
			userIntent.putExtra(USERPOSITION, SCANNER);
			startActivity(userIntent);
			break;

		case R.id.ll_user_fm_advice:
			userIntent.putExtra(USERPOSITION, ADVICE);
			startActivity(userIntent);
			break;

		case R.id.ll_user_fm_go_for_score:
			userIntent.putExtra(USERPOSITION, GOFORSCORE);
			startActivity(userIntent);
			break;

		case R.id.ll_user_fm_user_help:
			userIntent.putExtra(USERPOSITION, USERHELP);
			startActivity(userIntent);
			break;

		default:
			break;
		}

	}
}
