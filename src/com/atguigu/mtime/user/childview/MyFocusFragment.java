package com.atguigu.mtime.user.childview;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyFocusFragment extends Fragment implements OnClickListener {

	private static final int BEGIN_SEARCH = 0;
	private static String UserName;
	private static Context context;
	private View view;
	private View childView;
	private ViewPager vp_fm_user_my_focus_content;
	private RadioGroup rg_fm_user_my_focus_top;
	private ImageView iv_fm_user_my_focus_back;
	private ImageView iv_fm_user_my_focus_search;
	private EditText et_fm_user_my_focus_top_search_et;
	private LinearLayout ll_fm_user_my_focus_top_search_ll;
	private TextView tv_fm_user_my_focus_top_search_cancel;
	private LinearLayout ll_fm_user_my_focus_noresult;
	private TextView tv_fm_user_my_focus_noresult_info;
	private List<TextView> myTvList;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BEGIN_SEARCH:
				et_fm_user_my_focus_top_search_et
						.addTextChangedListener(new MyTextWatcher());
				ll_fm_user_my_focus_top_search_ll.setVisibility(View.VISIBLE);

				break;

			default:
				break;
			}
		}
	};

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				rg_fm_user_my_focus_top.check(R.id.rb_fm_user_my_focus_film);
				break;

			case 1:
				rg_fm_user_my_focus_top.check(R.id.rb_fm_user_my_focus_cinema);
				break;

			case 2:
				rg_fm_user_my_focus_top.check(R.id.rb_fm_user_my_focus_actor);
				break;

			case 3:
				rg_fm_user_my_focus_top.check(R.id.rb_fm_user_my_focus_goods);
				break;

			case 4:
				rg_fm_user_my_focus_top.check(R.id.rb_fm_user_my_focus_news);
				break;

			case 5:
				rg_fm_user_my_focus_top
						.check(R.id.rb_fm_user_my_focus_estimate);
				break;

			default:
				break;
			}
		}
	}

	class MyOnCheckedChangeListener implements
			RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rb_fm_user_my_focus_film:
				vp_fm_user_my_focus_content.setCurrentItem(0);
				break;

			case R.id.rb_fm_user_my_focus_cinema:
				vp_fm_user_my_focus_content.setCurrentItem(1);
				break;

			case R.id.rb_fm_user_my_focus_actor:
				vp_fm_user_my_focus_content.setCurrentItem(2);
				break;

			case R.id.rb_fm_user_my_focus_goods:
				vp_fm_user_my_focus_content.setCurrentItem(3);
				break;

			case R.id.rb_fm_user_my_focus_news:
				vp_fm_user_my_focus_content.setCurrentItem(4);
				break;

			case R.id.rb_fm_user_my_focus_estimate:
				vp_fm_user_my_focus_content.setCurrentItem(5);
				break;

			default:
				break;
			}
		}
	}

	class MyTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.length() != 0) {
				et_fm_user_my_focus_top_search_et.getText().toString().trim();
				tv_fm_user_my_focus_top_search_cancel.setText("搜索");
			} else {
				tv_fm_user_my_focus_top_search_cancel.setText("取消");
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
		view = View.inflate(context, R.layout.fragment_user_my_focus, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		myTvList = new ArrayList<TextView>();
		addTextViewToViewPager();
		rg_fm_user_my_focus_top.check(R.id.rb_fm_user_my_focus_film);
		vp_fm_user_my_focus_content
				.setOnPageChangeListener(new MyOnPageChangeListener());
		rg_fm_user_my_focus_top
				.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
	}

	private void addTextViewToViewPager() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		TextView tv1 = new TextView(context);
		tv1.setText("这是电影界面");
		TextView tv2 = new TextView(context);
		tv2.setText("这是影院界面");
		TextView tv3 = new TextView(context);
		tv3.setText("这是影人界面");
		TextView tv4 = new TextView(context);
		tv4.setText("这是商品界面");
		TextView tv5 = new TextView(context);
		tv5.setText("这是新闻界面");
		TextView tv6 = new TextView(context);
		tv6.setText("这是影评界面");
		myTvList.add(tv1);
		myTvList.add(tv2);
		myTvList.add(tv3);
		myTvList.add(tv4);
		myTvList.add(tv5);
		myTvList.add(tv6);
		for (int i = 0; i < myTvList.size(); i++) {
			TextView textView = myTvList.get(i);
			textView.setTextSize(30);
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(params);
		}
		vp_fm_user_my_focus_content.setAdapter(new MyPageAdapter());
	}

	private void initOnClick() {
		iv_fm_user_my_focus_back.setOnClickListener(this);
		iv_fm_user_my_focus_search.setOnClickListener(this);
		tv_fm_user_my_focus_top_search_cancel.setOnClickListener(this);
	}

	private void initLayout() {
		vp_fm_user_my_focus_content = (ViewPager) view
				.findViewById(R.id.vp_fm_user_my_focus_content);
		rg_fm_user_my_focus_top = (RadioGroup) view
				.findViewById(R.id.rg_fm_user_my_focus_top);
		iv_fm_user_my_focus_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_focus_back);
		iv_fm_user_my_focus_search = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_focus_search);
		ll_fm_user_my_focus_top_search_ll = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_my_focus_top_search_ll);
		et_fm_user_my_focus_top_search_et = (EditText) view
				.findViewById(R.id.et_fm_user_my_focus_top_search_et);
		tv_fm_user_my_focus_top_search_cancel = (TextView) view
				.findViewById(R.id.tv_fm_user_my_focus_top_search_cancel);
		tv_fm_user_my_focus_noresult_info = (TextView) view
				.findViewById(R.id.tv_fm_user_my_focus_noresult_info);
		ll_fm_user_my_focus_noresult = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_my_focus_noresult);
	}

	class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return myTvList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(myTvList.get(position));
			return myTvList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_my_focus_back:
			getActivity().finish();
			break;

		case R.id.iv_fm_user_my_focus_search:
			handler.sendEmptyMessage(BEGIN_SEARCH);
			break;

		case R.id.tv_fm_user_my_focus_top_search_cancel:
			if (tv_fm_user_my_focus_top_search_cancel.getText().toString()
					.trim().equals("搜索")) {
				tv_fm_user_my_focus_noresult_info.setText("未找到命名为"
						+ et_fm_user_my_focus_top_search_et.getText()
								.toString() + "的搜索结果");
				et_fm_user_my_focus_top_search_et.setText("");
				ll_fm_user_my_focus_noresult.setVisibility(View.VISIBLE);
			} else {
				ll_fm_user_my_focus_top_search_ll.setVisibility(View.GONE);
				ll_fm_user_my_focus_noresult.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}
}
