package com.atguigu.mtime.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.mtime.R;
import com.atguigu.mtime.SelectCity;
import com.atguigu.mtime.fragment.HomeFragment;
import com.atguigu.mtime.selectcity.CityEntity;
import com.atguigu.mtime.selectcity.CityLayout;
import com.atguigu.mtime.selectcity.ProvinceEntity;
import com.atguigu.mtime.utils.ToastUtil;

/**
 * 功能：网格的城市列表
 * 
 * @author 朱志强
 *
 */
public class CityAdapter extends BaseAdapter {

	private Context mContext;
	// private LayoutInflater inflater;
	private List<ProvinceEntity> dataList;
	// int unitWidth;

	public CityAdapter(Context context, List<ProvinceEntity> datas, Handler handler) {
		mContext = context;
		// inflater = (LayoutInflater)
		// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = datas;
		this.handler = handler;
		// DisplayMetrics metrics = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// unitWidth=(metrics.widthPixels-5*6)/5;
	}

	@Override
	public int getCount() {
		return dataList != null ? dataList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// convertView = View.inflate(mContext, R.layout.list_item_city, parent,
		// null);
		convertView = View.inflate(mContext, R.layout.list_item_city, null);
		// 省名称
		TextView provinceName = (TextView) convertView.findViewById(R.id.title);
		// 装载城市名称的布局
		CityLayout container = (CityLayout) convertView.findViewById(R.id.city_container);

		// 获取省会
		ProvinceEntity province = dataList.get(position);
		// 省会对应的城市
		List<CityEntity> cities = province.getCities();

		provinceName.setText(province.getName());
		int len = cities.size();
		for (int i = 0; i < len; i++) {
			CityEntity city = cities.get(i);
			TextView cityName = createTextView(city);
			container.addView(cityName);
		}

		return convertView;
	}
	
	private Handler handler;

	/**
	 * 创建一个TextView
	 * 
	 * @author LiChaofei <br/>
	 *         2013-12-10 下午2:48:59
	 * @param city
	 *            TODO
	 * @return
	 */
	private TextView createTextView(final CityEntity city) {
		final TextView view = new TextView(mContext);
		// LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// params.leftMargin = 40;
		// params.rightMargin = 2;
		view.setLayoutParams(params);

		// view.setPadding(10, 10, 10, 10);

		view.setTextColor(Color.BLACK);
		// view.setBackgroundResource(R.drawable.bg_city_selector);

		// view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		view.setTextSize(18);

		view.setGravity(Gravity.CENTER);
		view.setText(city.getN());
		view.setTag(city.getId());
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				HomeFragment.selectcity.setText(city.getN());
				handler.sendMessage(handler.obtainMessage(2));
			}
		});

		return view;
	}

}
