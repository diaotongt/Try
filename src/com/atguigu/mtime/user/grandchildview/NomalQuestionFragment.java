package com.atguigu.mtime.user.grandchildview;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NomalQuestionFragment extends Fragment {

	private static String UserName;
	private static Context context;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context,
				R.layout.fragment_user_grandchild_nomalquestion, null);
		view.findViewById(R.id.iv_fm_user_grandchild_nomalquestion_back)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getActivity().finish();
					}
				});
		return view;
	}
}
