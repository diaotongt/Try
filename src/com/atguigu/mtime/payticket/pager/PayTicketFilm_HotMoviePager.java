package com.atguigu.mtime.payticket.pager;


import java.util.ArrayList;
import java.util.List;



import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.R;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean.FilmDetail;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean.MovieType;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean.Version;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 电影-热门电影页
 * @author Wangnan
 *
 */
public class PayTicketFilm_HotMoviePager {

	/**
	 * 上下文对象
	 */
	private Context context;
	
	/**
	 * ListView数据列表
	 */
	private ListView lv;
	
	/**
	 * 根视图
	 */
	public View root;
	
	/**
	 * “正在热映”和“即将上映”的数据Bean对象
	 */
	public PayTicketFilmBean payTicketFilmBean;
	
	/**
	 * “正在热映”和“即将上映”的数据电影集合
	 */
	public List<FilmDetail> ms;
	
	/**
	 * 图片联网请求队列
	 */
	private RequestQueue imageQueue; 
	
	/**
	 * ListView图片缓存集合
	 */
	private LruCache<String, Bitmap> lruCache;
	
	public PayTicketFilm_HotMoviePager(Context context, PayTicketFilmBean payTicketFilmBean){
		this.context = context;
		root = initView();
		this.payTicketFilmBean = payTicketFilmBean;
	}

	/**
	 * 初始化视图
	 * @return
	 */
	private View initView() {
		View view = View.inflate(context, R.layout.payticketfilm_hotmoviepager_layout, null);
		lv = (ListView) view.findViewById(R.id.lv_payticketfilm_hotmoviepager_layout);	
		return view;
	}
	
	/**
	 * 初始化数据
	 */
	public void initDate(){
		
		//初始化图片请求队列
		imageQueue = Volley.newRequestQueue(context);
		
		//设置图片加载最大缓存
		long maxMemory = Runtime.getRuntime().maxMemory();	
		lruCache = new LruCache<String,Bitmap>((int) (maxMemory/4)){
			
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				
				return value.getRowBytes() * value.getHeight();
			}
		};
		
		//数据得到,开始填充
		if(payTicketFilmBean != null){
			ms = payTicketFilmBean.ms;
			lv.setAdapter(new HotMovieAdapter());
		}
		
	}

	
	/**
	 * ListView的适配器实现类
	 * @author Wangnan
	 *
	 */
	class HotMovieAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ms.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return ms.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_payticket_hotmovie, null);
				holder.iconIV = (ImageView) convertView.findViewById(R.id.iv_payticket_hotmovie_icon);
				holder.titleTV = (TextView) convertView.findViewById(R.id.tv_payticket_hotmovie_title);
				holder.scoreTV = (TextView) convertView.findViewById(R.id.tv_payticket_hotmovie_score);
				holder.commonspecialTV = (TextView) convertView.findViewById(R.id.tv_payticket_hotmovie_commonspecial);
				holder.comingtimeTV = (TextView) convertView.findViewById(R.id.tv_payticket_hotmovie_comingtime);
				holder.cinema_detailTV = (TextView) convertView.findViewById(R.id.tv_payticket_hotmovie_cinema_detail);
				holder.payBtn = (Button) convertView.findViewById(R.id.btn_payticket_hotmovie_pay);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			//得到数据
			FilmDetail filmDetail = ms.get(position);
			
			//设置标题
			holder.titleTV.setText(filmDetail.tCn);
			
			//根据评分是否为-1分情况处理
			if(((int)filmDetail.r) != -1){
				
				//设置评分
				holder.scoreTV.setText(String.valueOf(filmDetail.r));
				
				//评论长度大于0显示，小于0不显示
				if(filmDetail.commonSpecial.length() > 0){
					holder.commonspecialTV.setText(filmDetail.commonSpecial);
					holder.commonspecialTV.setVisibility(View.VISIBLE);
				}else{
					holder.commonspecialTV.setVisibility(View.GONE);
				}
				
				//上映时间长度大于0显示，否则不显示
				if(filmDetail.rd.length() > 0){
					holder.comingtimeTV.setText(filmDetail.rd.substring(4, 6)+"月"+filmDetail.rd.substring(6)+"日上映");
				}
				
				//设置按钮图标和文字
				holder.payBtn.setText("购票");
				holder.payBtn.setBackgroundResource(R.drawable.home_fragment_btn_shape1);
			
				//显示评分和广告语
				holder.scoreTV.setVisibility(View.VISIBLE);
				holder.commonspecialTV.setVisibility(View.VISIBLE);
			}else{

				//将"上映时间"变为"多少人想看"
				holder.comingtimeTV.setText(filmDetail.wantedCount+"人想看 - " + filmDetail.movieType);
				
				//设置按钮图标和文字
				holder.payBtn.setText("预约");
				holder.payBtn.setBackgroundResource(R.drawable.home_fragment_btn_shape2);
			
				//隐藏评分和广告语
				holder.scoreTV.setVisibility(View.GONE);
				holder.commonspecialTV.setVisibility(View.GONE);
			}

			holder.cinema_detailTV.setText("今日"+filmDetail.NearestCinemaCount+"家影院  "+filmDetail.NearestShowtimeCount+"场");;
			
			//加载图片
			Bitmap bitmap = lruCache.get(filmDetail.img);
			if(bitmap != null){
				holder.iconIV.setImageBitmap(bitmap);
			}else{
				holder.iconIV.setImageResource(R.drawable.img_default);
				setImageFromUrl(holder.iconIV,filmDetail.img);
			}
			
			return convertView;
		}	
	}
	
	//视图容器类
	static class ViewHolder{
		ImageView iconIV;
		TextView titleTV;
		TextView scoreTV;
		TextView commonspecialTV;
		TextView comingtimeTV;
		TextView cinema_detailTV;
		Button payBtn;
	}

	/**
	 * 联网下载图片
	 * @param iconIV
	 * @param img
	 */
	private void setImageFromUrl(final ImageView iconIV, final String url) {
		// TODO Auto-generated method stub
		//设置标示
		final String tag = System.currentTimeMillis()+"";
		iconIV.setTag(tag);
		ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {

				if((String)iconIV.getTag() == tag){
					//给ImageView设置图片
					iconIV.setImageBitmap(response);
					
					//缓存图片
					lruCache.put(url, response);
				}
				
			}
		}, 140, 200, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
		imageQueue.add(imageRequest);
	}
}
