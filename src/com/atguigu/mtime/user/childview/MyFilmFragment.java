package com.atguigu.mtime.user.childview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFilmFragment extends Fragment implements OnClickListener {

	private static final int BEGIN_SEARCH = 0;
	protected static final int CHECK_SEARCH = 1;
	protected static final int FINISH_SEARCH = 2;
	private static String UserName;
	private static Context context;
	private View view;
	private View childView;
	private ImageView iv_fm_user_my_film_back;
	private ImageView iv_fm_user_my_film_search;
	private LinearLayout ll_fm_user_my_film_content;
	private ImageView iv_fm_user_my_film_top_left,
			iv_fm_user_my_film_top_right;
	private LinearLayout ll_fm_user_my_film_top_search_ll;
	private LinearLayout ll_fm_user_my_film_noresult;
	private EditText et_fm_user_my_film_top_search_et;
	private TextView tv_fm_user_my_film_top_search_cancel;
	private TextView tv_fm_user_my_film_noresult_info;
	private TextView tv;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BEGIN_SEARCH:
				ll_fm_user_my_film_top_search_ll.setVisibility(View.VISIBLE);
				handler.sendEmptyMessageDelayed(CHECK_SEARCH, 100);
				break;

			case CHECK_SEARCH:
				if (et_fm_user_my_film_top_search_et.getText().toString()
						.trim().length() != 0) {
					tv_fm_user_my_film_top_search_cancel.setText("搜索");
				} else {
					tv_fm_user_my_film_top_search_cancel.setText("取消");
				}
				handler.sendEmptyMessageDelayed(CHECK_SEARCH, 100);
				break;

			case FINISH_SEARCH:
				handler.removeCallbacksAndMessages(null);
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_my_film, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		tv = new TextView(context);
		tv.setText("这是想看页面");
		tv.setTextSize(30);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(params);
		ll_fm_user_my_film_content.addView(tv);
	}

	private void initOnClick() {
		iv_fm_user_my_film_back.setOnClickListener(this);
		iv_fm_user_my_film_top_left.setOnClickListener(this);
		iv_fm_user_my_film_top_right.setOnClickListener(this);
		iv_fm_user_my_film_search.setOnClickListener(this);
		tv_fm_user_my_film_top_search_cancel.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_my_film_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_film_back);
		ll_fm_user_my_film_content = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_my_film_content);
		iv_fm_user_my_film_top_left = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_film_top_left);
		iv_fm_user_my_film_top_right = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_film_top_right);
		ll_fm_user_my_film_top_search_ll = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_my_film_top_search_ll);
		et_fm_user_my_film_top_search_et = (EditText) view
				.findViewById(R.id.et_fm_user_my_film_top_search_et);
		tv_fm_user_my_film_top_search_cancel = (TextView) view
				.findViewById(R.id.tv_fm_user_my_film_top_search_cancel);
		iv_fm_user_my_film_search = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_film_search);
		ll_fm_user_my_film_noresult = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_my_film_noresult);
		tv_fm_user_my_film_noresult_info = (TextView) view
				.findViewById(R.id.tv_fm_user_my_film_noresult_info);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_my_film_back:
			getActivity().finish();
			break;

		case R.id.iv_fm_user_my_film_top_left:
			iv_fm_user_my_film_top_left
					.setBackgroundResource(R.drawable.image_user_myfilm_top_left_on);
			iv_fm_user_my_film_top_right
					.setBackgroundResource(R.drawable.image_user_myfilm_top_right_off);
			tv.setText("这是想看页面");
			ll_fm_user_my_film_content.removeAllViews();
			ll_fm_user_my_film_content.addView(tv);
			break;

		case R.id.iv_fm_user_my_film_top_right:
			iv_fm_user_my_film_top_left
					.setBackgroundResource(R.drawable.image_user_myfilm_top_left_off);
			iv_fm_user_my_film_top_right
					.setBackgroundResource(R.drawable.image_user_myfilm_top_right_on);
			tv.setText("这是看过页面");
			ll_fm_user_my_film_content.removeAllViews();
			ll_fm_user_my_film_content.addView(tv);
			break;

		case R.id.iv_fm_user_my_film_search:
			handler.sendEmptyMessage(BEGIN_SEARCH);
			break;

		case R.id.tv_fm_user_my_film_top_search_cancel:
			if (tv_fm_user_my_film_top_search_cancel.getText().toString()
					.trim().equals("搜索")) {
				tv_fm_user_my_film_noresult_info.setText("未找到命名为"
						+ et_fm_user_my_film_top_search_et.getText().toString()
						+ "的搜索结果");
				et_fm_user_my_film_top_search_et.setText("");
				ll_fm_user_my_film_noresult.setVisibility(View.VISIBLE);
			} else {
				ll_fm_user_my_film_top_search_ll.setVisibility(View.GONE);
				ll_fm_user_my_film_noresult.setVisibility(View.GONE);
				handler.sendEmptyMessage(FINISH_SEARCH);
			}
			break;

		default:
			break;
		}
	}
}
