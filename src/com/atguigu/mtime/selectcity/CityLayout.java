package com.atguigu.mtime.selectcity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 功能：自定义的ViewGroup，用来装载每一个城市
 * 
 * @author 朱志强
 *
 */
public class CityLayout extends ViewGroup {

	private static final String TAG = "CitiesLayout";
	/**
	 * 列数
	 */
	private static final int COLUMN_COUNT = 4;

	/**
	 * 水平间距
	 */
	private static final int HORIZONTAL_SPACE = 0;

	/**
	 * 垂直间距
	 */
	private static final int VERTICAL_SPACE = 0;

	private int maxChildWidth = 0;
	private int maxChildHeight = 0;

	public CityLayout(Context context) {
		super(context);
	}

	public CityLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int paddingLeft = this.getPaddingLeft();
		int paddingRight = this.getPaddingRight();
		int paddingTop = this.getPaddingTop();
		int paddingBottom = this.getPaddingBottom();

		// Log.d(TAG,
		// "调用onMeasure,width="+widthSize+",height="+heightSize+",paddingLeft="+paddingLeft+",paddingRight="+paddingRight+",paddingTop="+paddingTop+",paddingBottom="+paddingBottom);

		// 类似9宫格的形式
//		maxChildHeight = maxChildWidth = (widthSize - paddingLeft - paddingRight) / COLUMN_COUNT - HORIZONTAL_SPACE * 2;
		maxChildWidth = (widthSize - paddingLeft - paddingRight) / COLUMN_COUNT - HORIZONTAL_SPACE * 2;
		maxChildHeight = maxChildWidth - 60;
		
		int childMeasureWidthSpec = MeasureSpec.makeMeasureSpec(maxChildWidth, MeasureSpec.EXACTLY);
		int childMeasureHeightSpec = MeasureSpec.makeMeasureSpec(maxChildHeight, MeasureSpec.EXACTLY);

		int childCount = getChildCount();

		for (int index = 0; index < childCount; index++) {
			final View child = getChildAt(index);
			// measure
			child.measure(childMeasureWidthSpec, childMeasureHeightSpec);
		}

		int rowCount = (childCount % COLUMN_COUNT == 0) ? (childCount / COLUMN_COUNT) : (childCount / COLUMN_COUNT + 1);

		heightSize = (maxChildHeight + VERTICAL_SPACE * 2) * rowCount + paddingTop + paddingBottom;
		// heightSize = (maxChildHeight + VERTICAL_SPACE * 2) * rowCount;

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);

		setMeasuredDimension(resolveSize(widthSize, widthMeasureSpec), resolveSize(heightSize, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// Log.d(TAG, "maxChildWidth="+maxChildWidth);

		int paddingLeft = this.getPaddingLeft();
		int paddingTop = this.getPaddingTop();

		int total = getChildCount();

		for (int i = 0; i < total; i++) {
			final View child = getChildAt(i);
			int row = i / COLUMN_COUNT;
			int colomn = i % COLUMN_COUNT;

			int left = paddingLeft + (maxChildWidth + HORIZONTAL_SPACE * 2) * colomn + HORIZONTAL_SPACE;
			int top = paddingTop + (maxChildHeight + VERTICAL_SPACE * 2) * row + VERTICAL_SPACE;

			// Log.d(TAG, "left="+left+",top="+top);
			child.layout(left, top, left + maxChildWidth, top + maxChildHeight);
		}
	}

}
