package com.atguigu.mtime;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.discover.bean.Rank_Top_Bean;
import com.atguigu.mtime.discover.bean.Rank_Top_Bean.Top_Movies;
import com.google.gson.Gson;

/**
 * 华语Top100
 * 
 * @author Mr lu
 *
 */
public class Chinese_Top_Activity extends Activity {
	// http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?pageIndex=1&pageSubAreaID=2066&locationId={2}
	// 说明：pageIndex为页码数，pageSubAreaID为每个排行榜的编号
	private String pageSubAreaID;
	private String baseurl = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?pageIndex=1&pageSubAreaID="
			+ pageSubAreaID + "&locationId={2}";

	// ListView的数据
	private List<Top_Movies> movies;
	private Myadapter adapter;

	// 内存缓存集合
	private LruCache<String, Bitmap> lruCache;

	// 视图对象
	private ListView lv_ranklist_content;

	private FrameLayout fl__main_animation;
	private AnimationDrawable animationDrawable;

	// 头视图
	private TextView tvtitle;
	private TextView tvsummary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_ranklist_item);
		Intent intent = getIntent();
		pageSubAreaID = intent.getStringExtra("pageSubAreaID");
		// 初始化视图
		initview();

		if (pageSubAreaID != null) {
			baseurl = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?pageIndex=1&pageSubAreaID="
					+ pageSubAreaID + "&locationId={2}";
			// 联网请求数据
			getdatafromnet();
		}
	}

	/**
	 * 初始化视图
	 */
	private void initview() {
		lv_ranklist_content = (ListView) findViewById(R.id.lv_ranklist_content);
		fl__main_animation = (FrameLayout) findViewById(R.id.fl__main_animation);
		ImageView iv_main_loading = (ImageView) findViewById(R.id.iv_main_loading);
		iv_main_loading.setImageResource(R.drawable.loading_animation_list);
		animationDrawable = (AnimationDrawable) iv_main_loading.getDrawable();

		// 添加头视图
		View header = View.inflate(this, R.layout.ranklist_item_header, null);
		tvtitle = (TextView) header.findViewById(R.id.tv_header_name);
		tvsummary = (TextView) header.findViewById(R.id.tv_header_summary);
		lv_ranklist_content.addHeaderView(header);

	}

	/**
	 * 联网请求数据
	 */
	private void getdatafromnet() {

		fl__main_animation.setVisibility(View.VISIBLE);
		animationDrawable.start();
		Log.e("TAG", baseurl);
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(baseurl,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// 解析排行榜的数据
						progressdata(response);

						animationDrawable.stop();
						fl__main_animation.setVisibility(View.GONE);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "加载排行榜失败");
						Toast.makeText(Chinese_Top_Activity.this, "加载排行榜失败",
								Toast.LENGTH_SHORT).show();

						animationDrawable.stop();
						fl__main_animation.setVisibility(View.GONE);
					}
				});
		queue.add(stringRequest);
	}

	/**
	 * 解析数据
	 * 
	 * @param response
	 */
	protected void progressdata(String response) {
		Gson gson = new Gson();
		Rank_Top_Bean Rank_Top_Bean = gson.fromJson(response,
				Rank_Top_Bean.class);

		tvtitle.setText(Rank_Top_Bean.topList.name);
		tvsummary.setText(Rank_Top_Bean.topList.summary);

		movies = Rank_Top_Bean.movies;
		adapter = new Myadapter();
		lv_ranklist_content.setAdapter(adapter);
	}

	class Myadapter extends BaseAdapter {
		public Myadapter() {
			// 在创建适配器的时候，创建内存的缓存集合LruCache
			// 得到运行时最大的内存
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			lruCache = new LruCache<String, Bitmap>(maxMemory / 8) {
				@Override
				protected int sizeOf(String key, Bitmap value) {

					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		@Override
		public int getCount() {
			return movies.size();
		}

		@Override
		public Object getItem(int position) {
			return movies.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viweholder = null;
			if (convertView == null) {
				viweholder = new ViewHolder();
				convertView = View.inflate(Chinese_Top_Activity.this,
						R.layout.ranklist_item_movies, null);

				viweholder.tvposition = (TextView) convertView
						.findViewById(R.id.tv_item_position);
				viweholder.ivicon = (ImageView) convertView
						.findViewById(R.id.iv_item_icon);
				viweholder.tvname = (TextView) convertView
						.findViewById(R.id.tv_item_name);
				viweholder.tvrating = (TextView) convertView
						.findViewById(R.id.tv_item_rating);
				viweholder.tvnameEn = (TextView) convertView
						.findViewById(R.id.tv_item_nameEn);
				viweholder.tvdirector = (TextView) convertView
						.findViewById(R.id.tv_item_director);
				viweholder.tvactor = (TextView) convertView
						.findViewById(R.id.tv_item_actor);
				viweholder.tvreleaseDate = (TextView) convertView
						.findViewById(R.id.tv_item_releaseDate);
				viweholder.tvreleaseLocation = (TextView) convertView
						.findViewById(R.id.tv_item_releaseLocation);
				viweholder.tvremark = (TextView) convertView
						.findViewById(R.id.tv_item_remark);

				convertView.setTag(viweholder);

			} else {
				viweholder = (ViewHolder) convertView.getTag();
			}

			Top_Movies movie = movies.get(position);

			viweholder.tvposition.setText(movie.rankNum + "");

			// 获取图片的地址
			String movieimageurl = movie.posterUrl;
			// 根据内存中有无，选择设置bitmap的方式(1.直接内存中获取，2.联网获取)
			Bitmap bitmap = lruCache.get(movieimageurl);
			if (bitmap != null) {
				Drawable drawable = new BitmapDrawable(bitmap);
				viweholder.ivicon.setBackgroundDrawable(drawable);
			} else {
				// 设置tag值，防止闪图
				viweholder.ivicon.setTag(movieimageurl);
				// 联网请求，设置图片
				setImageView(viweholder.ivicon, movieimageurl);
			}

			viweholder.tvname.setText(movie.name);

			double rating = movie.rating;
			if (rating <= 0) {
				viweholder.tvrating.setText(null);
			} else {
				viweholder.tvrating.setText(movie.rating + "");
			}

			viweholder.tvnameEn.setText(movie.nameEn);
			viweholder.tvdirector.setText("导演：" + movie.director);
			viweholder.tvactor.setText("主演：" + movie.actor);
			viweholder.tvreleaseDate.setText("上映日期：" + movie.releaseDate);
			viweholder.tvreleaseLocation.setText(movie.releaseLocation);
			viweholder.tvremark.setText(movie.remark);

			switch (position) {
			case 0:
				viweholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numa);
				break;
			case 1:
				viweholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numb);
				break;
			case 2:
				viweholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numc);
				break;

			default:
				viweholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numd);
				break;
			}

			return convertView;
		}

	}

	static class ViewHolder {
		TextView tvposition;
		ImageView ivicon;
		TextView tvname;
		TextView tvrating;
		TextView tvnameEn;
		TextView tvdirector;
		TextView tvactor;
		TextView tvreleaseDate;
		TextView tvreleaseLocation;
		TextView tvremark;
	}

	public void setImageView(final ImageView ivIcon, final String imagepath) {
		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)
		ivIcon.setBackgroundResource(R.drawable.img_default);
		// 说明已经不是最新的请求地址了，就没必要请求了
		String tagurl = (String) ivIcon.getTag();
		if (tagurl != imagepath) {
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(this);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(imagepath,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						Log.e("TAG", "获取排行榜item图片成功");
						// 在设置之前也判断一下
						String tagurl = (String) ivIcon.getTag();
						if (tagurl != imagepath) {
							return;
						}
						// 保存到内存中
						lruCache.put(imagepath, response);

						// 设置图片到对应的ImageView中
						Drawable drawable = new BitmapDrawable(response);
						ivIcon.setBackgroundDrawable(drawable);

					}
				}, 80, 120, Config.ARGB_4444, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取排行榜item图片失败");
					}
				});
		queue.add(imageRequest);
	}

}
