package com.atguigu.mtime.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.atguigu.mtime.MipcaActivityCapture;
import com.atguigu.mtime.R;
import com.atguigu.mtime.home.bean.BottomMall;
import com.atguigu.mtime.home.bean.BottomMall.BottomMallGood;
import com.atguigu.mtime.home.bean.Category;
import com.atguigu.mtime.home.bean.Cell;
import com.atguigu.mtime.home.bean.CellC;
import com.atguigu.mtime.home.bean.NavigatorFirthIcon;
import com.atguigu.mtime.home.bean.NavigatorIcon;
import com.atguigu.mtime.home.bean.ScrollImg;
import com.atguigu.mtime.home.bean.ScrollImg.Good;
import com.atguigu.mtime.home.bean.Topic;
import com.atguigu.mtime.home.bean.Topic.SubGood;
import com.atguigu.mtime.mall.BrowserActivity;
import com.atguigu.mtime.mall.BrowserCartActivity;
import com.atguigu.mtime.mall.BrowserNoTitleActivity;
import com.atguigu.mtime.mall.BrowserScrollActivity;
import com.atguigu.mtime.mall.MallSearchAty;
import com.atguigu.mtime.utils.HttpDownBitmap;
import com.atguigu.mtime.utils.LogUtil;
import com.atguigu.mtime.utils.NetUtils;
import com.atguigu.mtime.utils.StringUtil;
import com.atguigu.mtime.utils.ToastUtil;
import com.atguigu.mtime.utils.VolleyUtil;
import com.atguigu.mtime.view.ScrollGridview;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 首页Fragment
 * 
 * @author Wangnan
 */
public class ShopFragment extends Fragment implements OnClickListener {

	private static final int SCANNIN_GREQUEST_CODE = 1;
	protected static final int GET_LUN_BO_TU_DATA = 2;
	protected static final int START_LUN_BO_TU = 1;
	protected static final int SCROLL_LISTENER = 3;

	private Context context;

	// title
	private ImageButton scan; // 二维码扫描
	private ImageView cart; // 购物车
	private EditText search_content; // 搜索框

	// 直接滑到上部
	private ImageView gotop_btn;

	// 广告展示位
	private ViewPager viewPager;
	private LinearLayout point_container;

	// 四个分类
	private ImageView mall_navigator_firstline_firstIcon; // 玩具
	private TextView mall_navigator_firstline_firstIconTxt;

	private ImageView mall_navigator_firstline_SecondIcon; // 数码
	private TextView mall_navigator_firstline_SecondIconTxt;

	private ImageView mall_navigator_firstline_ThirdIcon; // 服饰
	private TextView mall_navigator_firstline_ThirdIconTxt;

	private ImageView mall_navigator_firstline_ForthIcon; // 家具
	private TextView mall_navigator_firstline_ForthIconTxt;

	// 4个单元格
	private ImageView cella; // 最左面的
	private TextView cella_main_title;
	private TextView cella_sub_title;

	private ImageView cellb; // 最下面的大的
	private TextView cellb_main_title;
	private TextView cellb_sub_title;

	private ImageView cellc1; // 这是右上角的第一个
	private TextView cellc1_main_title;
	private TextView cellc1_sub_title;

	private ImageView cellc2; // 右上角的第二个
	private TextView cellc2_main_title;
	private TextView cellc2_sub_title;

	// 星球大战系列(topic系列)
	private ImageView background_img; // 背景图片
	private HorizontalScrollView mall_home_topic_scroll; // 水平滑动部分
	private LinearLayout mall_home_topic_title;
	private TextView topic_title_en; // 下面的小主题
	private TextView topic_title_cn; // 大的主题
	private ScrollGridview mall_home_topic_grid; // topic系列商品
	private TextView topic_more; // 更多商品

	/**
	 * 获取商品失败时的显示的信息
	 */
	private RelativeLayout loading_failed_layout;

	/**
	 * 下拉刷新
	 */
	private PullToRefreshScrollView pull_refresh_scrollview;

	// 四个大的分类:玩具模型，数码配件等
	private LinearLayout mall_home_category_layout;
	// 你可能感兴趣的商品展示
	private ScrollGridview product_grid;
	// 所有图片的URL的地址
	private List<String> allImages = null;

	/**
	 * 顶部轮播图的信息类
	 */
	private ScrollImg mScrollImg;
	private LinearLayout ll_content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mall_home_layout, container, false);
		getViews(view);
		setListeners();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 商城底部的数据适配器
		gridViewAdapter2 = new InterestedAdapter(context, goods);
		product_grid.setAdapter(gridViewAdapter2);

		getDataFromNet();
	}

	/**
	 * 从网络获取数据
	 */
	private void getDataFromNet() {
		// 检查是否联网
		if (!NetUtils.isConnected(context)) {
			loading_failed_layout.setVisibility(View.VISIBLE);
			ll_content.setVisibility(View.INVISIBLE);
		} else {
			// 获取数据
			getJsonInfo(StringUtil.preUrl(URL_TOP));
			// 获取底部商城数据
			getJsonInfo2(StringUtil.preUrl(URL_BOTTOM));
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
		viewPager.removeAllViews();
		point_container.removeAllViews();
	}

	/**
	 * 获取组件的ID值
	 * 
	 * @param view2
	 *            rootView
	 */
	private void getViews(View view) {

		// Title背景
		background = (ImageView) view.findViewById(R.id.background);
		background.getBackground().setAlpha(0);
		ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
		// 下拉刷新
		pull_refresh_scrollview = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
		// 没有数据时显示的布局
		loading_failed_layout = (RelativeLayout) view.findViewById(R.id.loading_failed_layout);
		// title
		scan = (ImageButton) view.findViewById(R.id.scan);
		cart = (ImageView) view.findViewById(R.id.cart);
		search_content = (EditText) view.findViewById(R.id.search_content);

		// 直接滑动到上部
		gotop_btn = (ImageView) view.findViewById(R.id.gotop_btn);

		// 广告展示位
		viewPager = (ViewPager) view.findViewById(R.id.scroll_gallery);
		point_container = (LinearLayout) view.findViewById(R.id.gallery_button_line);

		// 四个分类
		mall_navigator_firstline_firstIcon = (ImageView) view.findViewById(R.id.mall_navigator_firstline_firstIcon);
		mall_navigator_firstline_firstIconTxt = (TextView) view
				.findViewById(R.id.mall_navigator_firstline_firstIconTxt);

		mall_navigator_firstline_SecondIcon = (ImageView) view.findViewById(R.id.mall_navigator_firstline_SecondIcon);
		mall_navigator_firstline_SecondIconTxt = (TextView) view
				.findViewById(R.id.mall_navigator_firstline_SecondIconTxt);

		mall_navigator_firstline_ThirdIcon = (ImageView) view.findViewById(R.id.mall_navigator_firstline_ThirdIcon);
		mall_navigator_firstline_ThirdIconTxt = (TextView) view
				.findViewById(R.id.mall_navigator_firstline_ThirdIconTxt);

		mall_navigator_firstline_ForthIcon = (ImageView) view.findViewById(R.id.mall_navigator_firstline_ForthIcon);
		mall_navigator_firstline_ForthIconTxt = (TextView) view
				.findViewById(R.id.mall_navigator_firstline_ForthIconTxt);

		// 4个单元格
		cella = (ImageView) view.findViewById(R.id.cella);
		cella_main_title = (TextView) view.findViewById(R.id.cella_main_title);
		cella_sub_title = (TextView) view.findViewById(R.id.cella_sub_title);

		cellb = (ImageView) view.findViewById(R.id.cellb);
		cellb_main_title = (TextView) view.findViewById(R.id.cellb_main_title);
		cellb_sub_title = (TextView) view.findViewById(R.id.cellb_sub_title);

		cellc1 = (ImageView) view.findViewById(R.id.cellc1);
		cellc1_main_title = (TextView) view.findViewById(R.id.cellc1_main_title);
		cellc1_sub_title = (TextView) view.findViewById(R.id.cellc1_sub_title);

		cellc2 = (ImageView) view.findViewById(R.id.cellc2);
		cellc2_main_title = (TextView) view.findViewById(R.id.cellc2_main_title);
		cellc2_sub_title = (TextView) view.findViewById(R.id.cellc2_sub_title);

		// 星球大战系列(topic系列)
		background_img = (ImageView) view.findViewById(R.id.background_img);
		mall_home_topic_scroll = (HorizontalScrollView) view.findViewById(R.id.mall_home_topic_scroll);
		mall_home_topic_title = (LinearLayout) view.findViewById(R.id.mall_home_topic_title);
		topic_title_en = (TextView) view.findViewById(R.id.topic_title_en);
		topic_title_cn = (TextView) view.findViewById(R.id.topic_title_cn);
		mall_home_topic_grid = (ScrollGridview) view.findViewById(R.id.mall_home_topic_grid);
		mall_home_category_layout = (LinearLayout) view.findViewById(R.id.mall_home_category_layout);
		topic_more = (TextView) view.findViewById(R.id.topic_more);
		product_grid = (ScrollGridview) view.findViewById(R.id.product_grid);
	}

	/**
	 * 添加监听
	 */
	@SuppressLint("ClickableViewAccessibility")
	private void setListeners() {
		// title
		scan.setOnClickListener(this);
		cart.setOnClickListener(this);
		search_content.setOnClickListener(this);
		// 直接滑动到上部
		gotop_btn.setOnClickListener(this);
		// 四个分类
		mall_navigator_firstline_firstIcon.setOnClickListener(this);
		mall_navigator_firstline_SecondIcon.setOnClickListener(this);
		mall_navigator_firstline_ThirdIcon.setOnClickListener(this);
		mall_navigator_firstline_ForthIcon.setOnClickListener(this);
		// 4个单元格
		cella.setOnClickListener(this);
		cellb.setOnClickListener(this);
		cellc1.setOnClickListener(this);
		cella.setOnClickListener(this);
		// 星球大战系列(topic系列)
		topic_more.setOnClickListener(this);

		/**
		 * 下拉刷新与上拉加载
		 */
		pull_refresh_scrollview.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

			// 下拉刷新
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
				new GetDataTask2().execute();
			}

			// 上拉加载
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
				pages += 1;
				String format = String.format("api.m.mtime.cn/ECommerce/RecommendProducts.api?pageIndex=%d&goodsIds=",
						pages);
				LogUtil.i(format);
				new GetDataTask().execute(format);
			}
		});

		// 监听scrollView的滑动
		scrollView = pull_refresh_scrollview.getRefreshableView();
		scrollView.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					background.getBackground().setAlpha(scrollView.getScrollY());
				}
			}
		});
		scrollView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					// LogUtil.i(scrollView.getScrollY() + "");
					if (scrollView.getScrollY() < 255) {
						background.getBackground().setAlpha(scrollView.getScrollY());
					}
					if (scrollView.getScrollY() < 1000) {
						gotop_btn.setVisibility(View.GONE);
					}
					if (scrollView.getScrollY() > 1000) {
						gotop_btn.setVisibility(View.VISIBLE);
					}
					break;
				case MotionEvent.ACTION_UP:// 在这里实现对scrollView的滚动监听，使用Handler实现
					handler.sendMessageDelayed(handler.obtainMessage(SCROLL_LISTENER), 100);
					break;
				default:
					break;
				}
				return false;
			}
		});

	}

	/**
	 * 顶部的数据bean
	 */
	private NavigatorFirthIcon mNavigatorFirthIcon;
	private NavigatorIcon mNavigatorIcon;
	private Topic mTopic;
	private List<com.atguigu.mtime.home.bean.Topic.SubGood> mall_topic_goods;
	private TopicAdapter gridViewAdapter;
	private List<Category> mCategorys;

	private BottomMall mBottomMall;
	private InterestedAdapter gridViewAdapter2;
	/**
	 * 商城底部所有的数据
	 */
	private List<BottomMallGood> goods = new ArrayList<BottomMallGood>();

	/**
	 * 底部商城的数据页数
	 */
	private int pages = 1;

	/**
	 * 获取商城上半部分json信息
	 */
	private void getJsonInfo(String shopUrl) {
		if (shopUrl == null) {
			return;
		}
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, StringUtil.preUrl(shopUrl), null,
				new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						LogUtil.i("getJsonInfo()");
						try {
							// 1、获取轮播图的数据
							if (response.has("scrollImg")) {
								JSONArray jsonArray = response.optJSONArray("scrollImg");
								mScrollImg = new ScrollImg();
								// new TypeToken<List<ShopInfo>>(){}.getType()
								mScrollImg.goods = new Gson().fromJson(jsonArray.toString(),
										new TypeToken<List<Good>>() {
								}.getType());
								handler.sendMessage(handler.obtainMessage(GET_LUN_BO_TU_DATA));
							}
							// 2、四个分类之家居
							if (response.has("navigatorFirthIcon")) {
								JSONObject jsonObject = response.optJSONObject("navigatorFirthIcon");
								mNavigatorFirthIcon = new Gson().fromJson(jsonObject.toString(),
										NavigatorFirthIcon.class);
							}
							HttpDownBitmap.getInstance().setImage(context, mall_navigator_firstline_ForthIcon,
									mNavigatorFirthIcon.img1);
							mall_navigator_firstline_ForthIconTxt.setText(mNavigatorFirthIcon.iconTitle1);

							if (response.has("navigatorIcon")) {
								mNavigatorIcon = new NavigatorIcon();
								JSONArray jsonArray = response.optJSONArray("navigatorIcon");
								mNavigatorIcon.goods = new Gson().fromJson(jsonArray.toString(),
										new TypeToken<List<com.atguigu.mtime.home.bean.NavigatorIcon.Good>>() {
								}.getType());
							}
							HttpDownBitmap.getInstance().setImage(context, mall_navigator_firstline_firstIcon,
									mNavigatorIcon.goods.get(0).image);
							mall_navigator_firstline_firstIconTxt.setText(mNavigatorIcon.goods.get(0).iconTitle);

							HttpDownBitmap.getInstance().setImage(context, mall_navigator_firstline_SecondIcon,
									mNavigatorIcon.goods.get(1).image);
							mall_navigator_firstline_SecondIconTxt.setText(mNavigatorIcon.goods.get(1).iconTitle);

							HttpDownBitmap.getInstance().setImage(context, mall_navigator_firstline_ThirdIcon,
									mNavigatorIcon.goods.get(2).image);
							mall_navigator_firstline_ThirdIconTxt.setText(mNavigatorIcon.goods.get(2).iconTitle);

							// 3、4个Cell值CellA
							if (response.has("cellA")) {
								JSONObject jsonObject = response.optJSONObject("cellA");
								final Cell cellA = new Gson().fromJson(jsonObject.toString(), Cell.class);
								HttpDownBitmap.getInstance().setImage(context, cella, cellA.img);
								cella.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										String url = StringUtil.preUrl("http://m.mtime.cn/" + cellA.url);
										startBrwserActivity(url, cellA.title);
									}
								});
							}
							if (response.has("cellB")) {
								JSONObject jsonObject = response.optJSONObject("cellB");
								final Cell cellB = new Gson().fromJson(jsonObject.toString(), Cell.class);
								HttpDownBitmap.getInstance().setImage(context, cellb, cellB.img);
								cellb.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										String url = StringUtil.preUrl(cellB.url);
										startBrwserActivity(url, cellB.title);
									}
								});
							}
							if (response.has("cellC")) {
								JSONObject jsonObject = response.optJSONObject("cellC");
								JSONArray jsonArray = jsonObject.optJSONArray("list");
								CellC cellC = new CellC();
								cellC.goods = new Gson().fromJson(jsonArray.toString(),
										new TypeToken<List<com.atguigu.mtime.home.bean.CellC.Good>>() {
								}.getType());
								final com.atguigu.mtime.home.bean.CellC.Good goodc1 = cellC.goods.get(0);
								HttpDownBitmap.getInstance().setImage(context, cellc1, goodc1.image);

								cellc1.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										String url = StringUtil.preUrl(goodc1.url);
										startBrwserActivity(url, goodc1.title);
									}
								});

								final com.atguigu.mtime.home.bean.CellC.Good goodc2 = cellC.goods.get(1);
								HttpDownBitmap.getInstance().setImage(context, cellc2, goodc2.image);
								cellc2.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										String url = StringUtil.preUrl(goodc2.url);
										startBrwserActivity(url, goodc2.title);
									}
								});

							}
							// 4、获取主题信息
							if (response.has("topic")) {
								JSONArray jsonArray = response.optJSONArray("topic");
								mTopic = new Topic();
								mTopic.goods = new Gson().fromJson(jsonArray.toString(),
										new TypeToken<List<com.atguigu.mtime.home.bean.Topic.Good>>() {
								}.getType());

								// 5个Topic
								HttpDownBitmap.getInstance().setImage(context, background_img,
										mTopic.goods.get(2).backgroupImage);

								mall_topic_goods = new ArrayList<com.atguigu.mtime.home.bean.Topic.SubGood>();

								mall_topic_goods.addAll(mTopic.goods.get(2).subList);
								gridViewAdapter = new TopicAdapter(context, mall_topic_goods);
								mall_home_topic_grid.setAdapter(gridViewAdapter);

								for (int i = 0; i < mTopic.goods.size(); i++) {
									ImageView imageView = new ImageView(context);
									HttpDownBitmap.getInstance().setImage(context, imageView,
											mTopic.goods.get(i).uncheckImage);
									final com.atguigu.mtime.home.bean.Topic.Good good = mTopic.goods.get(i);
									topic_title_en.setText(good.titleEn);
									topic_title_cn.setText(good.titleCn);

									imageView.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											HttpDownBitmap.getInstance().setImage(context, background_img,
													good.backgroupImage);
											HttpDownBitmap.getInstance().setImage(context, (ImageView) v,
													good.checkedImage);
											// 设置下方的文字
											topic_title_en.setText(good.titleEn);
											topic_title_cn.setText(good.titleCn);
											// 设置数据的变化
											mall_topic_goods.clear();
											mall_topic_goods.addAll(good.subList);
											gridViewAdapter.notifyDataSetChanged();
											// 设置下方的图片变换
											for (int j = 0; j < mTopic.goods.size(); j++) {
												View childAt = mall_home_topic_title.getChildAt(j);
												if (childAt != v) {
													HttpDownBitmap.getInstance().setImage(context, (ImageView) childAt,
															mTopic.goods.get(j).uncheckImage);
												}
											}
										}
									});
									LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
									// LayoutParams params = new
									// LayoutParams(LayoutParams.WRAP_CONTENT,
									// LayoutParams.WRAP_CONTENT);
									params.leftMargin = 40;
									imageView.setLayoutParams(params);
									mall_home_topic_title.addView(imageView, i);
								}
							}
							// 5、Topic下的商品信息获取，第四部步已经获取到了，可以直接使用

							// 6、获取分类的信息
							if (response.has("category")) {
								mCategorys = new ArrayList<Category>();
								JSONArray jsonArray = response.optJSONArray("category");
								mCategorys = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Category>>() {
								}.getType());
								// 向LinearLayout中添加数据
								for (int i = 0; i < mCategorys.size(); i++) {
									final Category category = mCategorys.get(i);

									View view = View.inflate(context, R.layout.item_mall_home_category_layout, null);

									View category_color = view.findViewById(R.id.category_color);
									category_color.setBackgroundColor(Color.parseColor(category.colorValue));

									TextView category_name = (TextView) view.findViewById(R.id.category_name);
									category_name.setText(category.name);

									TextView category_more = (TextView) view.findViewById(R.id.category_more);
									category_more.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											startNoTitleBrwserActivity(StringUtil.preUrl(category.moreUrl));
										}
									});

									ImageView iv = (ImageView) view.findViewById(R.id.category_img);
									HttpDownBitmap.getInstance().setImage(context, iv, category.image);
									iv.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											startBrwserActivity(category.imageUrl, category.name);
										}
									});

									List<com.atguigu.mtime.home.bean.Category.Good> goods = category.subList;

									ImageView iv1 = (ImageView) view.findViewById(R.id.mall_category_img1);
									TextView title = (TextView) view.findViewById(R.id.mall_category_title1);
									TextView price = (TextView) view.findViewById(R.id.mall_category_price1);

									final com.atguigu.mtime.home.bean.Category.Good good1 = goods.get(0);
									HttpDownBitmap.getInstance().setImage(context, iv1, good1.image);
									title.setText(good1.title);
									iv1.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											startBrwserActivity(
													"http://m.mtime.cn/#!/commerce/item/" + good1.goodsId + "/",
													good1.title);
										}
									});

									ImageView iv2 = (ImageView) view.findViewById(R.id.mall_category_img2);
									TextView title2 = (TextView) view.findViewById(R.id.mall_category_title2);
									TextView price2 = (TextView) view.findViewById(R.id.mall_category_price2);

									final com.atguigu.mtime.home.bean.Category.Good good2 = goods.get(1);
									HttpDownBitmap.getInstance().setImage(context, iv2, good2.image);
									title.setText(good2.title);
									iv2.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											startBrwserActivity(
													"http://m.mtime.cn/#!/commerce/item/" + good2.goodsId + "/",
													good2.title);
										}
									});

									ImageView iv3 = (ImageView) view.findViewById(R.id.mall_category_img3);
									TextView title3 = (TextView) view.findViewById(R.id.mall_category_title3);
									TextView price3 = (TextView) view.findViewById(R.id.mall_category_price3);

									final com.atguigu.mtime.home.bean.Category.Good good3 = goods.get(2);
									HttpDownBitmap.getInstance().setImage(context, iv3, good3.image);
									title.setText(good3.title);
									iv3.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											startBrwserActivity(
													"http://m.mtime.cn/#!/commerce/item/" + good3.goodsId + "/",
													good3.title);
										}
									});
									mall_home_category_layout.addView(view, i);
								}
							}
						} catch (Exception e) {
							LogUtil.e("getJsonInfo()获取数据失败");
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						ToastUtil.showToast(context, "获取数据失败");
					}
				});
		VolleyUtil.getQueue(context).add(request);
	}

	/**
	 * 获取商城下半部分的信息
	 */
	private void getJsonInfo2(String url) {
		JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(url), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				LogUtil.i(response.toString());
				mBottomMall = new Gson().fromJson(response.toString(), BottomMall.class);
				goods.addAll(mBottomMall.goodsList);
				gridViewAdapter2.notifyDataSetChanged();
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				ToastUtil.showToast(context, "获取数据失败");
			}
		});
		VolleyUtil.getQueue(context).add(request);
	}

	// rootView
	private View view;

	public static String URL_TOP = "api.m.mtime.cn/PageSubArea/MarketFirstPageNew.api?lastTime={0}";
	// public String URL_BOTTOM =
	// "api.m.mtime.cn/ECommerce/RecommendProducts.api?pageIndex=" + pages +
	// "&goodsIds=";
	public String URL_BOTTOM = String.format("api.m.mtime.cn/ECommerce/RecommendProducts.api?pageIndex=%d&goodsIds=",
			pages);

	/**
	 * 开始轮播图自动播放
	 */
	private void startBanner() {
		Message msg = Message.obtain();
		msg.what = START_LUN_BO_TU;
		msg.obj = currentPosition++;
		handler.sendMessageDelayed(msg, 500);
	}

	private MyPagerAdapter viewPagerAdapter;

	/**
	 * 初始化轮播view
	 */
	private void initSlidingPic() {
		// 轮播图图片资源
		for (int i = 0; i < mScrollImg.goods.size(); i++) {

			ImageView imageView = new ImageView(getActivity());
			imageViews.add(imageView);

			// 指示点图片资源
			ImageView imageView2 = new ImageView(context);
			imageView2.setBackgroundResource(R.drawable.select_iv);
			// 制定添加的点的距离
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			params.rightMargin = 10;
			imageView2.setLayoutParams(params);
			if (i == 0) {// 设置第一个点为白色点
				imageView2.setEnabled(true);
			} else {
				imageView2.setEnabled(false);
			}
			points.add(imageView2);
		}
		// 添加指示点
		for (int i = 0; i < points.size(); i++) {
			point_container.addView(points.get(i));
		}

		ShopFragment.this.autoSelect = true;// 设置自动播放

		viewPagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setCurrentItem(0);
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				ShopFragment.this.currentPosition = position;
				position %= DEFAULT_BANNER_SIZE;
				points.get(position).setEnabled(true);
				points.get(lastItem).setEnabled(false);
				lastItem = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_DRAGGING) {
					LogUtil.i("移动中");
					// 如果目前不是自动移动，就是人为的滑动，这时就要移除消息
					ShopFragment.this.autoSelect = false;
					handler.removeMessages(1);
				} else if (state == ViewPager.SCROLL_STATE_IDLE) {
					if (!autoSelect) {
						// 如果处于静止状态的话,就先一出所有的消息
						handler.removeCallbacksAndMessages(null);
						// 设置自动滚动为true
						ShopFragment.this.autoSelect = true;
						// 发送自动滚动的消息
						Message send = Message.obtain();
						send.what = 1;
						send.obj = ShopFragment.this.currentPosition;
						handler.sendMessageDelayed(send, 500);
					}
				}
			}
		});
	}

	private ScrollView scrollView;

	/**
	 * 首页的背景图片
	 */
	private ImageView background;

	/**
	 * 下拉刷新
	 */
	private class GetDataTask2 extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			//在有网的情况下，不刷新
			if (NetUtils.isConnected(context) && loading_failed_layout.isShown() && !ll_content.isShown()) {
				getJsonInfo(StringUtil.preUrl(URL_TOP));
				return new String[] { "0" };
			} 
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			if (result != null && result[0].endsWith("0")) {
				loading_failed_layout.setVisibility(View.INVISIBLE);
				ll_content.setVisibility(View.VISIBLE);
			}
//			if (result[0].endsWith("1")) {
//				ll_content.setVisibility(View.VISIBLE);
//				loading_failed_layout.setVisibility(View.INVISIBLE);
//			}
			pull_refresh_scrollview.onRefreshComplete();
		}
	}

	/**
	 * 上拉加载
	 */
	private class GetDataTask extends AsyncTask<String, Void, String[]> {

		@Override
		protected String[] doInBackground(String... params) {
			String param = params[0];
			try {
				getJsonInfo2(param);
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			pull_refresh_scrollview.onRefreshComplete();
		}
	}

	/**
	 * 循环广告位总数量
	 */
	private final int FAKE_BANNER_SIZE = 10000;

	/**
	 * 循环广告位图片的数量
	 */
	private int DEFAULT_BANNER_SIZE = 0;

	/**
	 * ViewPager当前的位置
	 */
	private int currentPosition = 0;

	/**
	 * 上一个ViewPager指示器的圆点
	 */
	private int lastItem = 0;

	/**
	 * ViewPager指示器，就是下面的圆点
	 */
	private List<ImageView> points = new ArrayList<ImageView>();

	/**
	 * 
	 */
	private List<ImageView> imageViews = new ArrayList<ImageView>();

	/**
	 * 标志位:ViewPager是否自动互动
	 */
	private boolean autoSelect = true;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START_LUN_BO_TU:// 轮播图自动轮播
				handlerBannerMessage(msg);
				break;
			case GET_LUN_BO_TU_DATA:
				DEFAULT_BANNER_SIZE = mScrollImg.goods.size();
				// 1、显示轮播图
				initSlidingPic();
				startBanner();
				scrollView.scrollTo(0, 0);
				break;
			case SCROLL_LISTENER:// 对scroll的滚动监听
				handScrollMessage();
				handler.sendMessageDelayed(handler.obtainMessage(SCROLL_LISTENER), 100);
				break;
			default:
				break;
			}
		}

		private void handScrollMessage() {
			if (scrollView.getScrollY() < 255) {
				background.getBackground().setAlpha(scrollView.getScrollY());
			}
			if (scrollView.getScrollY() < 1000) {
				gotop_btn.setVisibility(View.GONE);
			}
			if (scrollView.getScrollY() > 1000) {
				gotop_btn.setVisibility(View.VISIBLE);
			}
		}

		// 让轮播图循环播放
		private void handlerBannerMessage(android.os.Message msg) {
			viewPager.setCurrentItem((Integer) msg.obj, true);
			Message send = Message.obtain();
			send.what = 1;
			send.obj = currentPosition++;
			if (currentPosition == FAKE_BANNER_SIZE) {
				currentPosition = 0;
				send.obj = currentPosition;
			}
			if (autoSelect) {
				handler.sendMessageDelayed(send, 2000);
				points.get((Integer) msg.obj % DEFAULT_BANNER_SIZE).setEnabled(true);
			}
		};
	};

	/**
	 * 顶部图片滚动的适配器类 ViewPager Adapter
	 * 
	 * @author DTT
	 */
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return FAKE_BANNER_SIZE;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public ImageView instantiateItem(ViewGroup container, int position) {
			position %= DEFAULT_BANNER_SIZE;
			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ScaleType.FIT_XY);
			// 下载图片
			if (mScrollImg != null && mScrollImg.goods.size() > 0) {
				HttpDownBitmap.getInstance().setImage(context, imageView, mScrollImg.goods.get(position).image);
			}

			final String url = StringUtil.preUrl(mScrollImg.goods.get(position).url);

			// 添加点击
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("url", url);
					intent.setClass(context, BrowserScrollActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});

			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((ImageView) object);
		}
	}

	/**
	 * 主题GridView的适配器类
	 * 
	 * @author 朱志强
	 *
	 */
	class TopicAdapter extends BaseAdapter {
		// 可以在初始化Adapter的时候获取数据

		private List<com.atguigu.mtime.home.bean.Topic.SubGood> shops;
		private LayoutInflater inflater;

		public TopicAdapter(Context context, List<com.atguigu.mtime.home.bean.Topic.SubGood> shops) {

			this.shops = shops;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (shops == null) {
				return 0;
			} else {
				return shops.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (shops == null || position >= shops.size()) {
				return null;
			} else {
				return shops.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.item_mall_shop_topic, parent, false);
				viewHolder = new ViewHolder();

				viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_shop);
				viewHolder.name = (TextView) convertView.findViewById(R.id.name);
				viewHolder.price = (TextView) convertView.findViewById(R.id.price);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final com.atguigu.mtime.home.bean.Topic.SubGood info = (com.atguigu.mtime.home.bean.Topic.SubGood) getItem(
					position);
			viewHolder.name.setText(info.title);

			viewHolder.iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 这里处理点击事件;http://m.mtime.cn/#!/commerce/item/101487/
					startBrwserActivity("http://m.mtime.cn/#!/commerce/item/" + info.goodsId + "/", info.title);
				}
			});

			HttpDownBitmap.getInstance().setImage(context, viewHolder.iv, info.image);

			return convertView;
		}

		class ViewHolder {
			ImageView iv;
			TextView name;
			TextView price;
		}

	}

	/**
	 * 底部感兴趣GridView的适配器类
	 * 
	 * @author 朱志强
	 *
	 */
	class InterestedAdapter extends BaseAdapter {
		// 可以在初始化Adapter的时候获取数据

		private List<BottomMallGood> shops;
		private LayoutInflater inflater;

		public InterestedAdapter(Context context, List<BottomMallGood> shops) {
			this.shops = shops;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (shops == null) {
				return 0;
			} else {
				return shops.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (shops == null || position >= shops.size()) {
				return null;
			} else {
				return shops.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.mall_home_intrested_item, parent, false);
				viewHolder = new ViewHolder();

				viewHolder.iv = (ImageView) convertView.findViewById(R.id.product_img);
				viewHolder.name = (TextView) convertView.findViewById(R.id.product_name);
				viewHolder.price = (TextView) convertView.findViewById(R.id.product_price);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final BottomMallGood info = (BottomMallGood) getItem(position);

			HttpDownBitmap.getInstance().setImage(context, viewHolder.iv, info.image);
			viewHolder.name.setText(info.name);

			viewHolder.iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 这里处理点击事件
					startBrwserActivity(info.url, info.name);
				}
			});

			return convertView;
		}

		class ViewHolder {
			ImageView iv;
			TextView name;
			TextView price;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				//Toast.makeText(context, bundle.getString("result"), 0).show();
				// // 显示扫描到的内容
				// mTextView.setText(bundle.getString("result"));
				// // 显示
				// mImageView.setImageBitmap((Bitmap)
				// data.getParcelableExtra("bitmap"));
				startSystemBrowser(bundle.getString("result"));
			}
			break;
		}
	}

	/**
	 * 使用浏览器打开二维码内容
	 * @param url
	 */
	private void startSystemBrowser(String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scan:
			startScan();
			break;
		case R.id.cart:
			startCartBrowser(StringUtil.preUrl("http://m.mtime.cn/#!/commerce/cart/"));
			break;
		case R.id.search_content:
			Intent intent = new Intent(context, MallSearchAty.class);
			context.startActivity(intent);
			break;
		case R.id.gotop_btn:
			scrollView.scrollTo(0, 0);
			background.getBackground().setAlpha(0);
			break;
		case R.id.mall_navigator_firstline_firstIcon:
			startNoTitleBrwserActivity(StringUtil.preUrl("http://m.mtime.cn/#!/commerce/list/?c1=25"));
			break;
		case R.id.mall_navigator_firstline_SecondIcon:
			startNoTitleBrwserActivity(StringUtil.preUrl("http://m.mtime.cn/#!/commerce/list/?c1=8"));
			break;
		case R.id.mall_navigator_firstline_ThirdIcon:
			startNoTitleBrwserActivity(StringUtil.preUrl("http://m.mtime.cn/#!/commerce/list/?c1=42"));
			break;
		case R.id.mall_navigator_firstline_ForthIcon:
			startNoTitleBrwserActivity(StringUtil.preUrl("http://m.mtime.cn/#!/commerce/list/?c1=43"));
			break;
		case R.id.topic_more:
			startNoTitleBrwserActivity(StringUtil.preUrl("http://m.mtime.cn/#!/commerce/list/?tid=31"));
			break;
		default:
			break;
		}
	}

	/**
	 * 跳转到浏览器
	 * 
	 * @param url
	 */
	private void startBrwserActivity(String url, String title) {
		Intent intent = new Intent();
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		intent.setClass(context, BrowserActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	/**
	 * 购物车浏览器
	 * 
	 * @param string
	 */
	private void startCartBrowser(String preUrl) {
		Intent intent = new Intent();
		intent.putExtra("url", preUrl);
		intent.setClass(context, BrowserCartActivity.class);
		startActivity(intent);
	}

	/**
	 * 开启没有Tile的浏览器
	 * 
	 * @param preUrl
	 */
	private void startNoTitleBrwserActivity(String preUrl) {
		Intent intent = new Intent();
		intent.putExtra("url", preUrl);
		intent.setClass(context, BrowserNoTitleActivity.class);
		startActivity(intent);
	}

	private void startScan() {
		Intent intent = new Intent();
		intent.setClass(context, MipcaActivityCapture.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		startActivityForResult(intent, Activity.RESULT_FIRST_USER);
	}
}
