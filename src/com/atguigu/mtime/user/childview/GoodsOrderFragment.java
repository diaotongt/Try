package com.atguigu.mtime.user.childview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class GoodsOrderFragment extends Fragment implements OnClickListener {

	private static String UserName;
	private static Context context;
	private View view;
	private View childView;
	private ImageView iv_fm_user_goods_order_back;
	private RadioGroup rg_fm_user_goods_order_top;
	private RadioButton rb_fm_user_goods_order_all, rb_fm_user_goods_order_pay,
			rb_fm_user_goods_order_receive, rb_fm_user_goods_order_estimate;
	private LinearLayout ll_fm_user_goods_order_content;
	private TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_goods_order, null);
		initLayout();
		initOnClick();
		initData();
		rg_fm_user_goods_order_top.check(R.id.rb_fm_user_goods_order_all);
		return view;
	}

	private void initData() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		tv = new TextView(context);
		tv.setText("这是全部界面");
		tv.setTextSize(30);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(params);
		ll_fm_user_goods_order_content.addView(tv);
	}

	private void initOnClick() {
		iv_fm_user_goods_order_back.setOnClickListener(this);
		rb_fm_user_goods_order_all.setOnClickListener(this);
		rb_fm_user_goods_order_pay.setOnClickListener(this);
		rb_fm_user_goods_order_receive.setOnClickListener(this);
		rb_fm_user_goods_order_estimate.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_goods_order_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_goods_order_back);
		rg_fm_user_goods_order_top = (RadioGroup) view
				.findViewById(R.id.rg_fm_user_goods_order_top);
		rb_fm_user_goods_order_all = (RadioButton) view
				.findViewById(R.id.rb_fm_user_goods_order_all);
		rb_fm_user_goods_order_pay = (RadioButton) view
				.findViewById(R.id.rb_fm_user_goods_order_pay);
		rb_fm_user_goods_order_receive = (RadioButton) view
				.findViewById(R.id.rb_fm_user_goods_order_receive);
		rb_fm_user_goods_order_estimate = (RadioButton) view
				.findViewById(R.id.rb_fm_user_goods_order_estimate);
		ll_fm_user_goods_order_content = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_goods_order_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_goods_order_back:
			getActivity().finish();
			break;

		case R.id.rb_fm_user_goods_order_all:

			tv.setText("这是全部页面！");

			ll_fm_user_goods_order_content.removeAllViews();
			ll_fm_user_goods_order_content.addView(tv);
			break;

		case R.id.rb_fm_user_goods_order_pay:
			tv.setText("这是付款页面！");
			ll_fm_user_goods_order_content.removeAllViews();
			ll_fm_user_goods_order_content.addView(tv);
			break;

		case R.id.rb_fm_user_goods_order_receive:
			tv.setText("这是收货页面！");
			ll_fm_user_goods_order_content.removeAllViews();
			ll_fm_user_goods_order_content.addView(tv);
			break;

		case R.id.rb_fm_user_goods_order_estimate:
			tv.setText("这是评价页面！");
			ll_fm_user_goods_order_content.removeAllViews();
			ll_fm_user_goods_order_content.addView(tv);
			break;

		default:
			break;
		}
	}
}
