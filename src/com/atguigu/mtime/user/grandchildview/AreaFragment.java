package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AreaFragment extends Fragment {

	private static String UserName;
	private static Context context;
	private View view;
	private ImageView iv_fm_user_grandchild_area_back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_grandchild_area,
				null);
		iv_fm_user_grandchild_area_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_grandchild_area_back);
		iv_fm_user_grandchild_area_back
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().finish();
					}
				});
		return view;
	}
}
