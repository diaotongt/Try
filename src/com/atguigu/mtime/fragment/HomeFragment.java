package com.atguigu.mtime.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.MainActivity;
import com.atguigu.mtime.R;
import com.atguigu.mtime.SelectCity;
import com.atguigu.mtime.home.bean.HomeContentBean;
import com.atguigu.mtime.home.bean.HomeContentBean.AdvItem;
import com.atguigu.mtime.home.bean.HomeContentBean.AreaSecond;
import com.atguigu.mtime.home.bean.HomeContentBean.HotPoint;
import com.atguigu.mtime.home.bean.HomeTopDataBean;
import com.atguigu.mtime.home.bean.HomeTopDataBean.DetailMovie;
import com.atguigu.mtime.utils.DimenUtils;
import com.atguigu.mtime.utils.TimeDateUtils;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 首页Fragment
 * @author Wangnan
 */
public class HomeFragment extends Fragment {

	/**
	 * Gallery开始滚动的What标示(正常模式)
	 */
	private static final int GALLERY_START_SLIDE = 1;
	
	/**
	 * Gallery开始滚动的What标示(传感器模式)
	 */
	private static final int SENSOR_GALLERY_START_SLIDE = 2;

	/**
	 * 上下文对象
	 */
	private Context context;

	/**
	 * 北京选择城市
	 */
	public static TextView selectcity;

	/**
	 * 根布局
	 */
	private RelativeLayout rl_fragment_home;
	
	/**
	 * 首页时光网Logo
	 */
	private ImageView iv_fragment_home_logo;
	
	/**
	 * 联网请求图片的消息队列
	 */
	private RequestQueue imageQueue;
	
	/**
	 * 画廊(容器)
	 */
	private Gallery gallery_fragment_home;
	
	/**
	 *画廊对应的当前电影名 TextView
	 */
	private TextView tv_fragment_home_gallery_moviename;
	
	/**
	 * 评分布局(用于显示隐藏评分数值)
	 */
	private LinearLayout ll_fragment_home_score;
	
	/**
	 * 影片评分TextView(个位数值)
	 */
	private TextView tv_fragment_home_score1;
	
	/**
	 * 影片评分TextView(小数位数值)
	 */
	private TextView tv_fragment_home_score2;
	
	/**
	 * 选择购票Button（对应画廊当前选中电影）
	 */
	private Button btn_fragment_home_payticket;
	
	/**
	 * 选择购票按钮下的宣传标语TextView(2选1)
	 */
	private TextView tv_fragment_home_below_btn_word;
	
	/**
	 * 选择购票按钮下的宣传标语TextView2(2选1)
	 */
	private LinearLayout ll_fragment_home_below_btn_word2;
	
	/**
	 * 选择购票按钮下的宣传标语TextView2:多少人想看
	 */
	private TextView tv_fragment_home_below_btn_word2_peoplecount;

	/**
	 * 选择购票按钮下的宣传标语TextView2:影片类型
	 */
	private TextView tv_fragment_home_below_btn_word2_movietype;
	
	/**
	 * 正在热映的TextView(数值)
	 */
	private TextView tv_fragment_home_hotmovie;
	
	/**
	 * 即将上映的TextView(数值)
	 */
	private TextView tv_fragment_home_comingmovie;
	
	/**
	 * 找影院的数值的TextView(数值)
	 */
	private TextView tv_fragment_home_cinemacount;
	
	/**
	 * 画廊的适配器引用
	 */
	private ImageAdapter imageAdapter;
	
	/**
	 * 记录画廊被选中的图片位置
	 */
	private int currentPosition = 0;
	
	/**
	 * 首页上方电影信息集合
	 */
	private List<DetailMovie> movies;
	
	/**
	 * 画廊图片缓存集合
	 */
	private LruCache<String, Bitmap> lruCache;
	
	/**
	 * 首页内容第1部分-头标题布局（用户点击跳转）
	 */
	private RelativeLayout rl_fragment_home_first_head;
	
	/**
	 * 商品1图片
	 */
	private ImageView iv_fragment_home_goods1;
	
	/**
	 * 商品2图片
	 */
	private ImageView iv_fragment_home_goods2;
	
	/**
	 * 商品3图片
	 */
	private ImageView iv_fragment_home_goods3;
	
	/**
	 * 商品4图片
	 */
	private ImageView iv_fragment_home_goods4;
	
	/**
	 * 广告条幅ViewPager
	 */
	private ViewPager viewpager_fragment_home_banner;
	
	/**
	 * 广告条幅ViewPager的Adapter
	 */
	private BannerPagerAdapter bannerPageradapter;
	
	/**
	 * 广告条幅的视图容器
	 */
	private List<ImageView> bannerImages = new ArrayList<ImageView>();
	
	/**
	 * 首页内容第2部分-头标题布局（用户点击跳转）
	 */
	private RelativeLayout rl_fragment_home_second_content_head;
	
	/**
	 * 动态填充新闻的内容的视图容器(FrameLayout)
	 */
	private LinearLayout ll_fragment_home_second_content_news;
	
	/**
	 * 首页内容第3部分-头标题布局（用户点击跳转）
	 */
	private RelativeLayout rl_fragment_home_three_content_head;
	
	/**
	 * 首页内容第3部分ImageView
	 */
	private ImageView iv_fragment_home_three_content_img;
	
	/**
	 * 首页内容第3部分TextView-title(标题)
	 */
	private TextView tv_fragment_home_three_content_title;
	
	/**
	 * 首页内容第3部分TextView-desc(描述)
	 */
	private TextView tv_fragment_home_three_content_desc;
	
	/**
	 * 首页内容第3部分ImageView2（小图片）
	 */
	private ImageView iv_fragment_home_three_content_img2;
	
	/**
	 * 首页内容第3部分TextView（小图片对应的中文标题、外文标题）
	 */
	private TextView tv_fragment_home_three_content_titleCn;
	private TextView tv_fragment_home_three_content_titleEn;
	
	/**
	 * View点击事件的监听器
	 */
	private HomeOnClickListener homeOnClickListener = new HomeOnClickListener();
	
	/**
	 * Gallery的ImageView的Touch时间监听器对象
	 */
	private GalleryImageOnTouchListener galleryImageOnTouchListener = new GalleryImageOnTouchListener();
	
	/**
	 * 传感器管理器(home)
	 */
	private SensorManager homeSensorManager;
	
	/**
	 * 判断重力感应是否开启
	 */
	private boolean gravitySensorStart = false;
	
	/**
	 * Handler(消息处理中心)
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == GALLERY_START_SLIDE){//正常轮播
				
				if(gravitySensorStart == false){
					gallery_fragment_home.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					
					removeMessages(GALLERY_START_SLIDE);
					
					if(gallery_fragment_home.getSelectedItemPosition() == movies.size() - 1){
						gallery_fragment_home.setSelection(0);
					}
					
					handler.sendEmptyMessageDelayed(GALLERY_START_SLIDE, 5000);
				}
			}else if(msg.what == SENSOR_GALLERY_START_SLIDE){//传感器模式轮播
				if(msg.arg1 < 0){
					gallery_fragment_home.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					
					if(gallery_fragment_home.getSelectedItemPosition() == movies.size() - 1){
						gallery_fragment_home.setSelection(0);
					}					
				}else{
					gallery_fragment_home.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
					
					if(gallery_fragment_home.getSelectedItemPosition() == 0){
						gallery_fragment_home.setSelection(movies.size()-1);
					}
				}
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = getActivity(); 
		//实例化传感器管理器
		homeSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		//获取根布局
		rl_fragment_home = (RelativeLayout) View.inflate(context, R.layout.fragment_home, null);
		
		//初始化视图
		initView();
		
		//初始化监听器(主要是：对视图的点击监听)
		initListener();
		
		//初始化数据
		initData();
		
		return rl_fragment_home;
	}

	/**
	 * 初始化视图
	 * @author Wangnan
	 */
	private void initView() {
		selectcity = (TextView) rl_fragment_home.findViewById(R.id.selectcity);
		tv_fragment_home_hotmovie = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_hotmovie);
		iv_fragment_home_logo = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_logo);
		tv_fragment_home_comingmovie = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_comingmovie);
		tv_fragment_home_cinemacount = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_cinemacount);
		ll_fragment_home_score = (LinearLayout) rl_fragment_home.findViewById(R.id.ll_fragment_home_score);
		tv_fragment_home_score1 = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_score1);
		tv_fragment_home_score2 = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_score2);
		tv_fragment_home_gallery_moviename = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_gallery_moviename);
		btn_fragment_home_payticket = (Button) rl_fragment_home.findViewById(R.id.btn_fragment_home_payticket);
		tv_fragment_home_below_btn_word = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_below_btn_word);
		ll_fragment_home_below_btn_word2 = (LinearLayout) rl_fragment_home.findViewById(R.id.ll_fragment_home_below_btn_word2);
		tv_fragment_home_below_btn_word2_peoplecount = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_below_btn_word2_peoplecount);
		tv_fragment_home_below_btn_word2_movietype = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_below_btn_word2_movietype);
		gallery_fragment_home = (Gallery) rl_fragment_home.findViewById(R.id.gallery_fragment_home);
		rl_fragment_home_first_head = (RelativeLayout) rl_fragment_home.findViewById(R.id.rl_fragment_home_first_head);
		iv_fragment_home_goods1 = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_goods1);
		iv_fragment_home_goods2 = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_goods2);
		iv_fragment_home_goods3 = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_goods3);
		iv_fragment_home_goods4 = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_goods4);	
		viewpager_fragment_home_banner = (ViewPager) rl_fragment_home.findViewById(R.id.viewpager_fragment_home_banner);
		ll_fragment_home_second_content_news = (LinearLayout) rl_fragment_home.findViewById(R.id.ll_fragment_home_second_content_news);
		iv_fragment_home_three_content_img = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_three_content_img);
		tv_fragment_home_three_content_title = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_three_content_title);
		tv_fragment_home_three_content_desc = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_three_content_desc);
		iv_fragment_home_three_content_img2 = (ImageView) rl_fragment_home.findViewById(R.id.iv_fragment_home_three_content_img2);
		tv_fragment_home_three_content_titleCn = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_three_content_titleCn);
		tv_fragment_home_three_content_titleEn = (TextView) rl_fragment_home.findViewById(R.id.tv_fragment_home_three_content_titleEn);
		rl_fragment_home_second_content_head = (RelativeLayout) rl_fragment_home.findViewById(R.id.rl_fragment_home_second_content_head);
		rl_fragment_home_three_content_head = (RelativeLayout) rl_fragment_home.findViewById(R.id.rl_fragment_home_three_content_head);
	}
	
	/**
	 * 初始化数据
	 * @author Wangnan
	 */
	private void initData() {
		
		//设置画廊页被选中监听
		gallery_fragment_home.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				imageAdapter.setSelectItem(position); 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		//设置画廊的触摸监听
		gallery_fragment_home.setOnTouchListener(galleryImageOnTouchListener);
		
		//设置图片加载最大缓存
		long maxMemory = Runtime.getRuntime().maxMemory();	
		lruCache = new LruCache<String,Bitmap>((int) (maxMemory/4)){
			
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				
				return value.getRowBytes() * value.getHeight();
			}
		};
		
		//初始化ViewPager适配器
		bannerPageradapter = new BannerPagerAdapter();
		viewpager_fragment_home_banner.setAdapter(bannerPageradapter);
		
		//联网请求
		getDataFromNet();
		
	}

	/**
	 * 使用Volley请求网络
	 */
	private void getDataFromNet() {
		Log.e("TAG", "请求网络");
		//创建请求队列
		RequestQueue mQueue = Volley.newRequestQueue(context);
		
		//请求首页第一部分数据
		String url = "http://api.m.mtime.cn/PageSubArea/HotPlayMovies.api?locationId=290";
		StringRequest requestQueue = new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				Log.e("TAG", "请求首页第一部分数据,成功！");
				
				//解析Json数据
				processData(response);
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.e("TAG", "请求首页第一部分数据,失败！");
			}
		});
		
		//请求首页第二部分数据
		//设置模拟数据
		processData3();

		//数据源请求不到JSON数据，暂时注销
//		String url2 = "http://api.m.mtime.cn/PageSubArea/GetFirstPageAdvAndNews.api";
//		StringRequest requestQueue2 = new StringRequest(url2, new Listener<String>() {
//
//			@Override
//			public void onResponse(String response) {
//				// TODO Auto-generated method stub
//				Log.e("TAG", "请求首页第二部分数据,成功！");
//				processData2(response);
//			}
//		}, new ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				// TODO Auto-generated method stub
//				Log.e("TAG", "请求首页第二部分数据,失败,设置第三部分数据！");
//				processData3();
//			}
//		});
		
		//将请求添加请求队列
		mQueue.add(requestQueue);
//		mQueue.add(requestQueue2);
	}

	/**
	 * 解析首页头部Json并设置相应数据
	 * @param response
	 */
	protected void processData(String response) {
		
		//首页头部Bean集合数据
		HomeTopDataBean homeTopDataBean = new Gson().fromJson(response, HomeTopDataBean.class);
		
		//在已经联网的情况下，判断服务器是否返回了首页头部数据
		if(homeTopDataBean==null){
			Toast.makeText(context, "首页头部数据，加载失败...", 0).show();
			Log.e("TAG", "homeTopDataBean==null");
			return;
		}
		Log.e("TAG", homeTopDataBean.toString());
		//正在热映、即将上映、找影院的数字信设置
		tv_fragment_home_hotmovie.setText(homeTopDataBean.totalHotMovie+"");
		tv_fragment_home_comingmovie.setText(homeTopDataBean.totalComingMovie+"");
		tv_fragment_home_cinemacount.setText(homeTopDataBean.totalCinemaCount+"");
		
		//Bean中首页上方电影信息集合
		movies = homeTopDataBean.movies;
		
		//为Gallery设置适配器
		imageAdapter = new ImageAdapter();
		gallery_fragment_home.setAdapter(imageAdapter);
		
		handler.sendEmptyMessageDelayed(GALLERY_START_SLIDE, 5000);
	}
	
	/**
	 * 解析首页内容Json并设置相应数据
	 * @param response
	 */
	protected void processData2(String response) {
		
		//首页内容Bean集合数据
		HomeContentBean homeContentBean = new Gson().fromJson(response, HomeContentBean.class);
		
		//在已经联网的情况下，判断服务器是否返回了首页内容数据
		if(homeContentBean == null){		
			Toast.makeText(context, "首页内容数据，加载失败...", 0).show();
			Log.e("TAG", "processData2 homeContentBean == null");
			return;
		}
		Log.e("TAG", homeContentBean.toString());
		//请求商品第一张图片
		requestImageViewFromNet(iv_fragment_home_goods1, homeContentBean.areaSecond.subFifth.image);
		
		//请求商品第二张图片
		requestImageViewFromNet(iv_fragment_home_goods2, homeContentBean.areaSecond.subFirst.image);
		
		//请求商品第三张图片
		requestImageViewFromNet(iv_fragment_home_goods3, homeContentBean.areaSecond.subSecond.image);
		
		//请求商品第四张图片
		requestImageViewFromNet(iv_fragment_home_goods4, homeContentBean.areaSecond.subThird.image);

		//联网获取所有广告条幅图片	
		final List<AdvItem> advList = homeContentBean.advList;
		
		//判断广告条幅图片是否"有"(>=1)
		if(advList == null){
			return;
		}
		
		//创建消息队列
		RequestQueue bannerQueue = Volley.newRequestQueue(context);
		
		//请求条幅广告处理，处理方式不一样(单独联网请求)
		for(int i = 0;i < advList.size();i++){
			
			//联网请求广告条幅图片
			ImageRequest bannerImageRequest = new ImageRequest(advList.get(i).img, new Listener<Bitmap>() {

				@Override
				public void onResponse(Bitmap response) {
					//获取图片
					ImageView imageView = new ImageView(context);
					imageView.setImageBitmap(response);
					imageView.setScaleType(ScaleType.FIT_XY);
					bannerImages.add(imageView);
					//刷新适配器
					bannerPageradapter.notifyDataSetChanged();
				}
			}, 0, 0, Config.RGB_565, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
				
				}
			});	
			
			//广告条幅图片请求，添加至消息队列
			bannerQueue.add(bannerImageRequest);
		}
		
		//设置今日热点数据(分类型处理)
		List<HotPoint> hotPoints = homeContentBean.hotPoints;
		for(int i = 0; i < hotPoints.size();i++){
			
			if( hotPoints.get(i).type == 2 || hotPoints.get(i).type == 0){			
				RelativeLayout rl = (RelativeLayout) View.inflate(context, R.layout.fragment_home_second_content_news_style1, null);	
				((TextView)rl.findViewById(R.id.tv_fragment_home_second_content_news_style1_title)).setText(hotPoints.get(i).title);
				((TextView)rl.findViewById(R.id.tv_fragment_home_second_content_news_style1_desc)).setText(hotPoints.get(i).desc);
				
				long lastTime = System.currentTimeMillis() - hotPoints.get(i).publishTime * 1000 + 8 * 3600 * 1000; 
				if(hotPoints.get(i).type == 2){
					lastTime -= 8 * 3600 * 1000;
				}
				((TextView)rl.findViewById(R.id.tv_fragment_home_second_content_news_style1_time)).setText(TimeDateUtils.FormatTime(lastTime));	
				ImageView imageView = (ImageView) rl.findViewById(R.id.iv_fragment_home_second_content_news_style1);
				requestImageViewFromNet(imageView,hotPoints.get(i).img);
				ll_fragment_home_second_content_news.addView(rl);
				
			}else if(hotPoints.get(i).type == 1){
				RelativeLayout rl = (RelativeLayout) View.inflate(context, R.layout.fragment_home_second_content_news_style2, null);
				((TextView)rl.findViewById(R.id.tv_fragment_home_second_content_news_style2_title)).setText(hotPoints.get(i).title);
				((TextView)rl.findViewById(R.id.tv_fragment_home_second_content_news_style2_desc)).setText(hotPoints.get(i).desc);
				long lastTime = System.currentTimeMillis() - hotPoints.get(i).publishTime * 1000; 
				((TextView)rl.findViewById(R.id.tv_fragment_home_second_content_news_style2_time)).setText(TimeDateUtils.FormatTime(lastTime));;
				ImageView imageView1 = (ImageView) rl.findViewById(R.id.iv_fragment_home_second_content_news_style2_img1);
				ImageView imageView2 = (ImageView) rl.findViewById(R.id.iv_fragment_home_second_content_news_style2_img2);
				ImageView imageView3 = (ImageView) rl.findViewById(R.id.iv_fragment_home_second_content_news_style2_img3);
				requestImageViewFromNet(imageView1, hotPoints.get(i).images.get(0).url1);
				requestImageViewFromNet(imageView2, hotPoints.get(i).images.get(1).url1);
				requestImageViewFromNet(imageView3, hotPoints.get(i).images.get(2).url1);
				ll_fragment_home_second_content_news.addView(rl);			
			}
		}
		
		//首页内容第3部分界面设置
		tv_fragment_home_three_content_title.setText(homeContentBean.hotMovie.title);
		tv_fragment_home_three_content_desc.setText(homeContentBean.hotMovie.movie.desc);
		tv_fragment_home_three_content_titleCn.setText(homeContentBean.hotMovie.movie.titleCn);
		tv_fragment_home_three_content_titleEn.setText(homeContentBean.hotMovie.movie.titleEn);
		requestImageViewFromNet(iv_fragment_home_three_content_img, homeContentBean.hotMovie.topCover);
		requestImageViewFromNet(iv_fragment_home_three_content_img2, homeContentBean.hotMovie.movie.image);
		
	}
	
	/**
	 * 联网失败时设置数据
	 */
	private void processData3(){
		
		//广告条目设置
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(R.drawable.ad);
		imageView.setScaleType(ScaleType.FIT_XY);
		bannerImages.add(imageView);
		
		//刷新适配器
		bannerPageradapter.notifyDataSetChanged();
	}

	/**
	 * 通用的联网请求加载图片的方法
	 * @param imageView
	 * @param img
	 * @author Wangnan
	 */
	private void requestImageViewFromNet(final ImageView imageView, String url) {
	
		//请求队列的创建
		if(imageQueue == null){
			imageQueue = Volley.newRequestQueue(context);
		}
		
		//图片请求
		ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				//为ImageView设置联网图片
				imageView.setImageBitmap(response);
			}
		}, 0, 0, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//为ImageView设置默认图片
				imageView.setImageResource(R.drawable.img_default_300x200);
			}
		});
		
		//将图片请求添加到消息队列
		imageQueue.add(imageRequest);
	}

	/**
	 * 为Gallery设置适配器
	 * @author Wangnan
	 */
	class ImageAdapter extends BaseAdapter{	
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return movies.size();
		}

		public void setSelectItem(int position) {
			// TODO Auto-generated method stub
			if(currentPosition != position){
				currentPosition = position;
				imageAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return movies.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ImageView iv = null;
			if(convertView == null){
				iv = new ImageView(context);
			}else{
				iv = (ImageView) convertView;
			}
			
			String imgUrl = movies.get(position).img;
			Bitmap bitmap = lruCache.get(imgUrl);
			if(bitmap != null){
				iv.setImageBitmap(bitmap);
			}else{
				iv.setImageResource(R.drawable.img_default);
				//联网加载图片
				setImageView(iv,imgUrl);
			}
			
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(DimenUtils.dip2px(context, 120),DimenUtils.dip2px(context, 160)));
			
			if(currentPosition == position){
				iv.setLayoutParams(new Gallery.LayoutParams(DimenUtils.dip2px(context, 144),DimenUtils.dip2px(context, 192)));
				//设置被选中的电影名
				tv_fragment_home_gallery_moviename.setText(movies.get(position).titleCn);
				//按钮下方宣传标语
				tv_fragment_home_below_btn_word.setText(movies.get(position).commonSpecial);
				
				if((int)(movies.get(position).ratingFinal) != -1 && movies.get(position).commonSpecial.length() > 0){
					//设置Button的显示文字,颜色
					btn_fragment_home_payticket.setText("选座购票");
					btn_fragment_home_payticket.setBackgroundResource(R.drawable.home_fragment_btn_shape1);
					//设置整数位得分
					tv_fragment_home_score1.setText((int)(movies.get(position).ratingFinal)+"");
					//设置小数位得分
					tv_fragment_home_score2.setText((int)(10*(movies.get(position).ratingFinal-(int)(movies.get(position).ratingFinal)))+"");
					
					//显示影片评分板
					if(!ll_fragment_home_score.isShown()){
						ll_fragment_home_score.setVisibility(View.VISIBLE);
					}
					
					
					//如果TextView1没有显示
					if(!tv_fragment_home_below_btn_word.isShown()){
						//(TextView1)设置为显示
						tv_fragment_home_below_btn_word.setVisibility(View.VISIBLE);
						
						//(TextView2)设置为隐藏
						ll_fragment_home_below_btn_word2.setVisibility(View.GONE);
					}
				}else{
					//设置Button的显示文字,颜色
					btn_fragment_home_payticket.setText("超前预售");
					btn_fragment_home_payticket.setBackgroundResource(R.drawable.home_fragment_btn_shape2);
					
					//隐藏影片评分板
					ll_fragment_home_score.setVisibility(View.GONE);
					
					//(TextView2)设置多少人想看
					tv_fragment_home_below_btn_word2_peoplecount.setText(Math.abs(movies.get(position).wantedCount)+"");
					
					//(TextView2)设置影片类型
					tv_fragment_home_below_btn_word2_movietype.setText(movies.get(position).type);
								
					//(TextView2)设置为显示
					ll_fragment_home_below_btn_word2.setVisibility(View.VISIBLE);
						
					//(TextView1)设置为隐藏
					tv_fragment_home_below_btn_word.setVisibility(View.GONE);				

				}
			}
				
			return iv;
		}
		
	}
	
	/**
	 * 联网加载缓存图片的方法
	 * @param iv
	 * @param imgUrl
	 */
	public void setImageView(final ImageView iv, final String imgUrl) {
		// TODO Auto-generated method stub
		RequestQueue mQueue = Volley.newRequestQueue(context);
		ImageRequest imageRequest = new ImageRequest(imgUrl, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				//设置图片到对应的ImageView中
				iv.setImageBitmap(response);
				//将Bitmap缓存到内存中
				lruCache.put(imgUrl, response);
			}
		}, 360, 640, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
		
			}
		});
		
		//将图片添加到请求队列
		mQueue.add(imageRequest);
	}
	
	/**
	 * PagerAdapter广告条幅适配器
	 * @author Wangnan
	 *
	 */
	class BannerPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bannerImages.size();
		}
		
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			ImageView imageView = bannerImages.get(position);
			container.addView(imageView);
			return imageView;
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}
	
	/**
	 * 为画廊图片添加监听器
	 * @author Wangnan
	 */
	class GalleryImageOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				handler.removeMessages(GALLERY_START_SLIDE);
				Log.e("TAG", "MotionEvent.ACTION_DOWN");
				break;
				
			case MotionEvent.ACTION_UP:
				handler.sendEmptyMessageDelayed(GALLERY_START_SLIDE, 5000);
				Log.e("TAG", "MotionEvent.ACTION_UP");
			default:
				break;
			}
			
			return false;
		}
		
	}
	
	/**
	 * 初始化监听器
	 */
	private void initListener() {
		selectcity.setOnClickListener(homeOnClickListener);//选择城市
		rl_fragment_home_first_head.setOnClickListener(homeOnClickListener);
		rl_fragment_home_second_content_head.setOnClickListener(homeOnClickListener);
		rl_fragment_home_three_content_head.setOnClickListener(homeOnClickListener);
		iv_fragment_home_logo.setOnClickListener(homeOnClickListener);
	}
	
	/**
	 * 点击监听器的实现类
	 * @author Wangnan
	 *
	 */
	class HomeOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == rl_fragment_home_first_head){
				//跳转至商城
				((MainActivity)context).rg_main.check(R.id.rb_shop);
			}else if(v == rl_fragment_home_second_content_head){
				//跳转至发现
				((MainActivity)context).rg_main.check(R.id.rb_discover);
			}else if(v == rl_fragment_home_three_content_head){
				//跳转至发现
				((MainActivity)context).rg_main.check(R.id.rb_discover);
			}else if(v == iv_fragment_home_logo){
				//开启/关闭重力感应模式
				toggleSenser();
			} else if (v == selectcity) {// 先择城市
				Intent intent = new Intent(context, SelectCity.class);
				startActivityForResult(intent, 100);
			}
		}	
	}
	
	/**
	 * 带结果的放回，用于接收返回城市
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == 100 && resultCode == 1){
			String city = data.getStringExtra("city");
			Log.e("TAG", "city");
			selectcity.setText(city);
		}
		Log.e("TAG", "onActivityResult");
	}
	
	/**
	 * 开启/关闭重力感应模式的方法
	 */
	private void toggleSenser() {
		// TODO Auto-generated method stub
		if(!gravitySensorStart){
			//注册监听器
			Toast.makeText(context, "重力感应模式已开启", 0).show();
			homeSensorManager.registerListener(homeSensorEventListener, homeSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
			handler.removeMessages(GALLERY_START_SLIDE);
			gravitySensorStart = true;
		}else{
			//解注册监听器
			Toast.makeText(context, "重力感应模式已关闭", 0).show();
			homeSensorManager.unregisterListener(homeSensorEventListener);
			handler.removeMessages(SENSOR_GALLERY_START_SLIDE);
			handler.sendEmptyMessageDelayed(GALLERY_START_SLIDE, 5000);
			gravitySensorStart = false;
		}
	}
	
	/**
	 * 创建并实例化传感器的实现类对象
	 */
	private HomeSensorEventListener homeSensorEventListener = new HomeSensorEventListener();
	class HomeSensorEventListener implements SensorEventListener{
		
		private float count = 0;

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float[] values = event.values;
			int sensorType = event.sensor.getType();
			if(sensorType == Sensor.TYPE_GRAVITY ){
				
				count = count + values[0];
				if(count > 50 || count < -50){
					Message msg = new Message();
					msg.what = SENSOR_GALLERY_START_SLIDE;
					msg.arg1 = (int) count;
					handler.sendMessage(msg);
					count = 0;
				}
				
			}
		}

		/**
		 * 当传感器精度发生改变时回调该方法
		 */
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		homeSensorManager.unregisterListener(homeSensorEventListener);
		gravitySensorStart = false;
		super.onPause();
	}
	
	@Override
	public void onStop() {
		homeSensorManager.unregisterListener(homeSensorEventListener);
		gravitySensorStart = false;
		super.onStop();
	}
	
	@Override
	public void onDetach() {
		homeSensorManager.unregisterListener(homeSensorEventListener);
		gravitySensorStart = false;
		handler.removeCallbacksAndMessages(null);
		super.onDetach();
	}
}
