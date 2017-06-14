package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AboutUsFragment extends Fragment implements OnClickListener {

	private static String UserName;
	private static Context context;
	private View view;
	private ImageView iv_fm_user_grandchild_aboutus_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_grandchild_aboutus,
				null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {

	}

	private void initOnClick() {
		iv_fm_user_grandchild_aboutus_back.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_grandchild_aboutus_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_aboutus_back);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_grandchild_aboutus_back:
			getActivity().finish();
			break;

		default:
			break;
		}
	}
}
