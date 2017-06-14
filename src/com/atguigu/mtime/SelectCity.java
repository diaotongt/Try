package com.atguigu.mtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.atguigu.mtime.adapter.CityAdapter;
import com.atguigu.mtime.selectcity.CityEntity;
import com.atguigu.mtime.selectcity.ProvinceEntity;
import com.atguigu.mtime.utils.Constants;
import com.atguigu.mtime.utils.LogUtil;
import com.atguigu.mtime.utils.SPUtil;
import com.atguigu.mtime.utils.StringUtil;
import com.atguigu.mtime.utils.ToastUtil;
import com.atguigu.mtime.utils.VolleyUtil;
import com.google.gson.Gson;

/**
 * 功能:选择城市 可以考虑使用GridLayout的方式往里面添加数据
 * 
 * @author 朱志强
 *
 */
public class SelectCity extends Activity implements OnClickListener {

	/**
	 * 城市集合
	 */
	private List<CityEntity> cities;

	/**
	 * 省会集合，也就是首字母
	 */
	private List<ProvinceEntity> provinces;

	/**
	 * 城市列表
	 */
	private ListView lv;

	/**
	 * lv的适配器
	 */
	private CityAdapter adapter;
	private Context context;
	
	/**
	 * 返回按钮
	 */
	private RelativeLayout back;

	/**
	 * 搜索城市框
	 */
	private EditText et_search;

	/**
	 * 搜索显示的ListView
	 */
	private ListView lv_search;

	/**
	 * 取消
	 */
	private TextView tv_cancle;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:// 已经获取到了城市列表
				getProvinces();
				break;
			case 2://获取点击的城市
				finish();

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectcity);
		context = SelectCity.this;

		
		getViews();
		setListener();
		Initialization();
		getCities();

	}

	/**
	 * 初始化数据
	 */
	private void Initialization() {
		cities = new ArrayList<CityEntity>();
		searchCities = new ArrayList<CityEntity>();// 创建搜索的集合
		searchAdapter = new SearchAdapter(searchCities);
		lv_search.setAdapter(searchAdapter);
	}

	/**
	 * 隐藏城市列表
	 */
	private void hide() {
		findViewById(R.id.ll_dangqianchengshi).setVisibility(View.GONE);
		lv.setVisibility(View.GONE);
		lv_search.setVisibility(View.VISIBLE);
		tv_cancle.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示城市列表
	 */
	private void show() {
		lv_search.setVisibility(View.GONE);
		findViewById(R.id.ll_dangqianchengshi).setVisibility(View.VISIBLE);
		lv.setVisibility(View.VISIBLE);
		tv_cancle.setVisibility(View.GONE);
	}

	/**
	 * EditText输入的内容
	 */
	private String input;

	/**
	 * 用来通过输入框的内容搜索cities得到的集合
	 */
	private List<CityEntity> searchCities;

	/**
	 * 列表形式的城市列表
	 */
	private SearchAdapter searchAdapter;

	/**
	 * 添加监听
	 */
	private void setListener() {
		back.setOnClickListener(this);
		// 点击搜索框之后，隐藏网格城市列表，显示搜索列表
		et_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hide();
				if (searchCities.size() == 0) {
					searchCities.addAll(cities);
				}
			}
		});
		// 输入框一开始不获取焦点，点击之后才获取焦点，第一次点击的时候et_search.setOnClickListenerbuqizuoyong
		et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					hide();
					if (searchCities.size() == 0) {
						searchCities.addAll(cities);
					}
				}
			}
		});

		// 对EditText实时获取输入的内容
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				input = s.toString();
				searchCities.clear();
				for (int i = 0; i < cities.size(); i++) {
					CityEntity entity = cities.get(i);
					String name = entity.getN();
					if (name.contains(input)) {
						searchCities.add(entity);
					}
				}
				searchAdapter.notifyDataSetChanged();
				Log.i("DTT", searchCities.size() + "");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		// 点击取消按钮之后，显示网格式的城市列表
		tv_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show();
			}
		});
		// 对列表形式的城市列表添加监听
		lv_search.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CityEntity cityEntity = searchAdapter.getItem(position);
				
				//带结果的返回
				Intent intent = getIntent();
				intent.putExtra("city", cityEntity.getN());
				setResult(1, intent);
				finish();
			}
		});

	}

	private void getViews() {
		lv = (ListView) findViewById(R.id.lv);
		et_search = (EditText) findViewById(R.id.et_sousuo);
		lv_search = (ListView) findViewById(R.id.lv_search);
		tv_cancle = (TextView) findViewById(R.id.tv_cancle);
		back = (RelativeLayout) findViewById(R.id.back);
	}

	/**
	 * 得到省会列表
	 */
	private void getProvinces() {
		// 如果返回false的话，说明cities中的数据没有排过序,否则的话就排过序
		if (!SPUtil.getInstance(SelectCity.this).getValue(SPUtil.CITY_DATA, false)) {
			// 对集合中的数据按首字母进行排序,主要是排序消耗时间，应该把排好序的cities存贮在文件中
			Collections.sort(cities);
			// 将排好序的cities的json存入SP文件中
			String json = new Gson().toJson(cities);
			LogUtil.i("Gson()" + json);
			SPUtil.getInstance(SelectCity.this).putValue(SPUtil.CITY, json);
			// SPUtil.getInstance(SelectCity.this).getValue(SPUtil.CITY_DATA,
			// true);
		}
		// 获取省会的数据
		provinces = new ArrayList<ProvinceEntity>();
		int length = cities.size();// 城市的数量

		for (int i = 0; i < length; i++) {

			CityEntity cityEntity = cities.get(i);

			ArrayList<CityEntity> arrayList = new ArrayList<CityEntity>();// 首字母对应下的城市列表
			arrayList.add(cityEntity);// 先把第一个加载进来

			String head = cityEntity.getPinyinFull().substring(0, 1);// 头字母

			int index = i + 1;// 寻找相同的首字母的城市的指示器

			for (; index < length; index++) {

				// 如果下一个城市的首字母与上一个相同的话，就一直循环
				CityEntity cityEntity2 = cities.get(index);// 获取下一个城市

				if (head.equals(cityEntity2.getPinyinFull().subSequence(0, 1))) {
					arrayList.add(cityEntity2);
				} else {// 否则的话，退出这层循环
					i = (index - 1);// 记录下指示器的位置
					ProvinceEntity provinceEntity = new ProvinceEntity(head, arrayList);
					provinces.add(provinceEntity);
					break;
				}
			}
		}
		LogUtil.i("provinces:" + provinces.size());

		// 显示数据
		adapter = new CityAdapter(SelectCity.this, provinces,handler);
		lv.setAdapter(adapter);
	}

	/**
	 * 获取数据完成,发送handler消息通知主线程
	 */
	private void getCitiesFinish() {
		Message msg = Message.obtain();
		msg.what = 1;
		handler.sendMessage(msg);
	}

	/**
	 * 联网下载城市列表
	 */
	private void getCities() {
		// 1、缓存中是否有数据

		// 2、本地中是否有数据
		String json = SPUtil.getInstance(SelectCity.this).getValue(SPUtil.CITY, null);
		if (json != null) {
			List<CityEntity> parseJSON = parseJSON(json);
			if (parseJSON != null) {
				cities.addAll(parseJSON);
			}
			getCitiesFinish();
			return;
		}
		// 3、都没有的话就从网络下载
		JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(Constants.CITY_URL), null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						JSONArray jsonArray = response.optJSONArray("p");
						if (jsonArray.length() == 0) {
							return;
						}
						List<CityEntity> parseJSON = parseJSON(jsonArray.toString());
						if (parseJSON != null) {
							cities.addAll(parseJSON);
						}
						LogUtil.i("Num:" + cities.size());
						getCitiesFinish();
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						ToastUtil.showToast(SelectCity.this, "获取数据失败了");
					}
				});
		// 请求加上Tag,用于取消请求
		request.setTag(this);
		VolleyUtil.getQueue(SelectCity.this).add(request);
	}

	/**
	 * 解析JSON数据
	 * 
	 * @param json
	 *            null:数据有错
	 */
	private List<CityEntity> parseJSON(String json) {
		if (json == null) {
			return null;
		}
		List<CityEntity> list = new ArrayList<CityEntity>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			if (jsonArray.length() == 0) {
				return null;
			}
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = jsonArray.optJSONObject(i);
				int count = object.optInt("count");
				int id = object.optInt("id");
				String n = object.optString("n");
				String pinyinFull = object.optString("pinyinFull");// 保证首字母是大写的
				String pinyinShort = object.optString("pinyinShort");
				CityEntity entity = new CityEntity(count, id, n, pinyinFull, pinyinShort);
				list.add(entity);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	class SearchAdapter extends BaseAdapter {

		private List<CityEntity> datas;

		public SearchAdapter(List<CityEntity> datas) {
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public CityEntity getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
			}

			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);

			CityEntity cityEntity = getItem(position);
			String name = cityEntity.getN();

			tv.setText(name);

			return convertView;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
}
