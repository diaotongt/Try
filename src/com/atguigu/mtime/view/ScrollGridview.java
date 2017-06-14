package com.atguigu.mtime.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ScrollGridview extends GridView {

	public ScrollGridview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScrollGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ScrollGridview(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
