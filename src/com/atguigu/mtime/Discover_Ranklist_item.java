package com.atguigu.mtime;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.discover.bean.Ranklist_Item_Bean;
import com.atguigu.mtime.discover.bean.Ranklist_Item_Bean.Movies;
import com.atguigu.mtime.discover.bean.Ranklist_Item_Bean.Persons;
import com.google.gson.Gson;

/**
 * 点击排行榜每个item，打开以后的布局
 * 
 * @author lupeng
 *
 */
public class Discover_Ranklist_item extends Activity {

	// Loading界面的视图和他的动画
	private FrameLayout fl__main_animation;
	private AnimationDrawable animationDrawable;

	// header中的视图
	private TextView tvtitle;
	private TextView tvsummary;

	// listview
	private ListView lv_ranklist_content;

	// 内存缓存集合
	private LruCache<String, Bitmap> lruCache;
	/**
	 * ListView的数据
	 */
	// 电影
	private List<Movies> movies;
	// 人物
	private List<Persons> persons;
	// ListView的适配器
	private RanklistItemAdapter adapter;

	// 数据的请求地址
	private String dataurl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_ranklist_item);
		// 初始化视图
		initview();

		// 启动loading动画
		fl__main_animation.setVisibility(View.VISIBLE);
		animationDrawable.start();
		// 获取数据的url
		Intent intent = getIntent();
		Uri uri = intent.getData();
		dataurl = uri.toString();
		Log.e("TAG", "dataurl=" + dataurl);
		// 联网请求获取数据
		getdatafromnet();
	}

	/**
	 * 初始化视图
	 */
	private void initview() {

		// 初始化ListView
		lv_ranklist_content = (ListView) findViewById(R.id.lv_ranklist_content);

		// 初始化Loading的视图和动画
		fl__main_animation = (FrameLayout) findViewById(R.id.fl__main_animation);
		ImageView iv_main_loading = (ImageView) findViewById(R.id.iv_main_loading);
		iv_main_loading.setImageResource(R.drawable.loading_animation_list);
		animationDrawable = (AnimationDrawable) iv_main_loading.getDrawable();

		View header = View.inflate(this, R.layout.ranklist_item_header, null);
		tvtitle = (TextView) header.findViewById(R.id.tv_header_name);
		tvsummary = (TextView) header.findViewById(R.id.tv_header_summary);
		header.setEnabled(false);
		lv_ranklist_content.addHeaderView(header);

	}

	/**
	 * 联网请求获取数据
	 */
	private void getdatafromnet() {
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest request = new StringRequest(Request.Method.GET, dataurl,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.e("TAG", "排行榜item的数据获取成功");
						// 解析数据
						progressdata(response);

						// 隐藏Loading视图
						animationDrawable.stop();
						fl__main_animation.setVisibility(View.GONE);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "排行榜item的数据获取失败");

						// 隐藏Loading视图
						animationDrawable.stop();
						fl__main_animation.setVisibility(View.GONE);
					}
				});
		queue.add(request);
	}

	/**
	 * 解析数据
	 */
	protected void progressdata(String arg0) {
		Gson gson = new Gson();
		Ranklist_Item_Bean ranklist_Item_Bean = gson.fromJson(arg0,
				Ranklist_Item_Bean.class);
		// 设置header的数据
		tvtitle.setText(ranklist_Item_Bean.topList.name);
		tvsummary.setText(ranklist_Item_Bean.topList.summary);

		if (ranklist_Item_Bean.movies != null)
			movies = ranklist_Item_Bean.movies;
		else {
			persons = ranklist_Item_Bean.persons;
		}
		Log.e("TAG", "movies=" + movies);
		Log.e("TAG", "persons" + persons);
		adapter = new RanklistItemAdapter();
		// 设置Adapter
		lv_ranklist_content.setAdapter(adapter);
	}

	class RanklistItemAdapter extends BaseAdapter {

		public RanklistItemAdapter() {
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
			if (movies != null)
				return movies.size();
			else
				return persons.size();
		}

		@Override
		public Object getItem(int position) {
			if (movies != null)
				return movies.get(position);
			else
				return persons.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressWarnings("unused")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MoviesViewHolder moviesviewholder = null;
			PersonsViewHolder personsviewholder = null;
			if (movies != null) {
				if (moviesviewholder == null) {
					moviesviewholder = new MoviesViewHolder();
					convertView = View.inflate(Discover_Ranklist_item.this,
							R.layout.ranklist_item_movies, null);

					moviesviewholder.tvposition = (TextView) convertView
							.findViewById(R.id.tv_item_position);
					moviesviewholder.ivicon = (ImageView) convertView
							.findViewById(R.id.iv_item_icon);
					moviesviewholder.tvname = (TextView) convertView
							.findViewById(R.id.tv_item_name);
					moviesviewholder.tvrating = (TextView) convertView
							.findViewById(R.id.tv_item_rating);
					moviesviewholder.tvnameEn = (TextView) convertView
							.findViewById(R.id.tv_item_nameEn);
					moviesviewholder.tvdirector = (TextView) convertView
							.findViewById(R.id.tv_item_director);
					moviesviewholder.tvactor = (TextView) convertView
							.findViewById(R.id.tv_item_actor);
					moviesviewholder.tvreleaseDate = (TextView) convertView
							.findViewById(R.id.tv_item_releaseDate);
					moviesviewholder.tvreleaseLocation = (TextView) convertView
							.findViewById(R.id.tv_item_releaseLocation);
					moviesviewholder.tvremark = (TextView) convertView
							.findViewById(R.id.tv_item_remark);

					convertView.setTag(moviesviewholder);

				} else {
					moviesviewholder = (MoviesViewHolder) convertView.getTag();
				}

				Movies movie = movies.get(position);

				moviesviewholder.tvposition.setText(movie.rankNum + "");

				// 获取图片的地址
				String movieimageurl = movie.posterUrl;
				// 根据内存中有无，选择设置bitmap的方式(1.直接内存中获取，2.联网获取)
				Bitmap bitmap = lruCache.get(movieimageurl);
				if (bitmap != null) {
					Drawable drawable = new BitmapDrawable(bitmap);
					moviesviewholder.ivicon.setBackgroundDrawable(drawable);
				} else {
					// 设置tag值，防止闪图
					moviesviewholder.ivicon.setTag(movieimageurl);
					// 联网请求，设置图片
					setImageView(moviesviewholder.ivicon, movieimageurl);
				}

				moviesviewholder.tvname.setText(movie.name);

				double rating = movie.rating;
				if (rating <= 0) {
					moviesviewholder.tvrating.setText(null);
				} else {
					moviesviewholder.tvrating.setText(movie.rating + "");
				}

				moviesviewholder.tvnameEn.setText(movie.nameEn);
				moviesviewholder.tvdirector.setText("导演：" + movie.director);
				moviesviewholder.tvactor.setText("主演：" + movie.actor);
				moviesviewholder.tvreleaseDate.setText("上映日期："
						+ movie.releaseDate);
				moviesviewholder.tvreleaseLocation
						.setText(movie.releaseLocation);
				moviesviewholder.tvremark.setText(movie.remark);

				switch (position) {
				case 0:
					moviesviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numa);
					break;
				case 1:
					moviesviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numb);
					break;
				case 2:
					moviesviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numc);
					break;

				default:
					moviesviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numd);
					break;
				}

			} else {
				if (moviesviewholder == null) {

					personsviewholder = new PersonsViewHolder();

					convertView = View.inflate(Discover_Ranklist_item.this,
							R.layout.ranklist_item_person, null);

					personsviewholder.tvposition = (TextView) convertView
							.findViewById(R.id.tv_item_position);
					personsviewholder.ivicon = (ImageView) convertView
							.findViewById(R.id.iv_item_icon);
					personsviewholder.tvnameCn = (TextView) convertView
							.findViewById(R.id.tv_item_nameCn);
					personsviewholder.tvrating = (TextView) convertView
							.findViewById(R.id.tv_item_rating);
					personsviewholder.tvnameEn = (TextView) convertView
							.findViewById(R.id.tv_item_nameEn);
					personsviewholder.tvsex = (TextView) convertView
							.findViewById(R.id.tv_item_sex);
					personsviewholder.tvbirthDay = (TextView) convertView
							.findViewById(R.id.tv_item_birthDay);
					personsviewholder.tvbirthLocation = (TextView) convertView
							.findViewById(R.id.tv_item_birthLocation);
					personsviewholder.tvsummary = (TextView) convertView
							.findViewById(R.id.tv_item_summary);

					convertView.setTag(personsviewholder);

				} else
					personsviewholder = (PersonsViewHolder) convertView
							.getTag();
				Persons person = persons.get(position);

				personsviewholder.tvposition.setText(person.rankNum + "");

				// 获取图片的地址
				String personimageurl = person.posterUrl;
				// 根据内存中有无，选择设置bitmap的方式(1.直接内存中获取，2.联网获取)
				Bitmap bitmap = lruCache.get(personimageurl);
				if (bitmap != null) {
					Drawable drawable = new BitmapDrawable(bitmap);
					personsviewholder.ivicon.setBackgroundDrawable(drawable);
				} else {
					// 设置tag值，防止闪图
					personsviewholder.ivicon.setTag(personimageurl);
					// 联网请求，设置图片
					setImageView(personsviewholder.ivicon, personimageurl);
				}

				personsviewholder.tvnameCn.setText(person.nameCn);

				double rating = person.rating;
				if (rating <= 0) {
					personsviewholder.tvrating.setText(null);
				} else {
					personsviewholder.tvrating.setText(person.rating + "");
				}

				personsviewholder.tvnameEn.setText(person.nameEn);
				personsviewholder.tvsex.setText(person.sex + ",");
				personsviewholder.tvbirthDay.setText(person.birthDay);
				personsviewholder.tvbirthLocation.setText("("
						+ person.birthLocation + ")");
				personsviewholder.tvsummary.setText(person.summary);

				switch (position) {
				case 0:
					personsviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numa);
					break;
				case 1:
					personsviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numb);
					break;
				case 2:
					personsviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numc);
					break;

				default:
					personsviewholder.tvposition
							.setBackgroundResource(R.drawable.topmovie_list_numd);
					break;
				}
			}

			return convertView;
		}
	}

	/**
	 * movies的ViewHolder
	 * 
	 * @author Administrator
	 *
	 */
	static class MoviesViewHolder {
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

	/**
	 * persons的ViewHolder
	 * 
	 * @author Administrator
	 *
	 */
	static class PersonsViewHolder {
		TextView tvposition;
		ImageView ivicon;
		TextView tvnameCn;
		TextView tvrating;
		TextView tvnameEn;
		TextView tvsex;
		TextView tvbirthDay;
		TextView tvbirthLocation;
		TextView tvsummary;
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
