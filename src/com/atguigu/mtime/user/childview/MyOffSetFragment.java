package com.atguigu.mtime.user.childview;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.mtime.R;
import com.atguigu.mtime.user.childview.GiftCardFragment.cViewHolder;
import com.atguigu.mtime.utils.CacheUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyOffSetFragment extends Fragment implements OnClickListener {

	protected static final int ADD_OFFSET = 0;
	protected static final int DELETE_GIFTCARD = 1;
	protected static final int FIRST_LV = 10;
	protected static final int SECOND_LV = 11;
	protected static final int THIRD_LV = 12;
	protected static final int FOURTH_LV = 13;
	private static String UserName;
	private static Context context;
	private View view;
	private View childView;
	private View grandView1;
	private View grandView2;
	private View grandView3;
	private ImageView iv_fm_user_my_offset_back;
	private ImageView iv_fm_user_my_offset_top_left;
	private ImageView iv_fm_user_my_offset_top_right;
	private LinearLayout ll_fm_user_my_offset_content;
	private ListView lv_fm_user_my_offset_ticket_mid_content;
	private TextView tv_fm_user_my_offset_ticket_mid_content;
	private TextView tv_fm_user_my_offset_bottom_add_card;
	private TextView tv_fm_user_my_offset_goods_top_left,
			tv_fm_user_my_offset_goods_top_mid,
			tv_fm_user_my_offset_goods_top_right;
	private View v_fm_user_my_offset_goods_top_left,
			v_fm_user_my_offset_goods_top_mid,
			v_fm_user_my_offset_goods_top_right;
	private ListView lv_fm_user_my_offset_goods_mid_content;
	private TextView tv_fm_user_my_offset_goods_mid_content;
	private ListView lv_fm_user_my_offset_goods_mid_content2;
	private TextView tv_fm_user_my_offset_goods_mid_content2;
	private ListView lv_fm_user_my_offset_goods_mid_content3;
	private TextView tv_fm_user_my_offset_goods_mid_content3;
	private ViewPager vp_fm_user_my_offset_goods_cotent;
	private List<String> ticketList;
	private List<String> secondList;
	private List<String> thirdList;
	private List<String> fourthList;
	private List<View> pagerViewList;
	private int currentList;
	private List<String> localList;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ADD_OFFSET:

				Log.e("TAG", "OFFSETRECEIVE??");
				String newCard = (String) msg.obj;
				if (newCard.equals("") || newCard == null) {
					return;
				}
				switch (currentList) {
				case FIRST_LV:
					Log.e("TAG", "GONE1??");
					ticketList.add(0, newCard);
					tv_fm_user_my_offset_ticket_mid_content
							.setVisibility(View.GONE);
					lv_fm_user_my_offset_ticket_mid_content
							.setAdapter(new MyListAdapter(ticketList));
					break;

				case SECOND_LV:
					Log.e("TAG", "GONE2??");
					secondList.add(0, newCard);
					tv_fm_user_my_offset_goods_mid_content
							.setVisibility(View.GONE);
					lv_fm_user_my_offset_goods_mid_content
							.setAdapter(new MyListAdapter(secondList));
					setGoodsChildViewCheck(1);
					break;

				case THIRD_LV:
					Log.e("TAG", "GONE3??");
					thirdList.add(0, newCard);
					tv_fm_user_my_offset_goods_mid_content2
							.setVisibility(View.GONE);
					lv_fm_user_my_offset_goods_mid_content2
							.setAdapter(new MyListAdapter(thirdList));
					setGoodsChildViewCheck(2);
					break;

				case FOURTH_LV:
					Log.e("TAG", "GONE3??");
					fourthList.add(0, newCard);
					tv_fm_user_my_offset_goods_mid_content3
							.setVisibility(View.GONE);
					lv_fm_user_my_offset_goods_mid_content3
							.setAdapter(new MyListAdapter(fourthList));
					setGoodsChildViewCheck(3);
					break;

				default:
					break;
				}
				break;

			case DELETE_GIFTCARD:
				int positionLocal = (Integer) msg.obj;
				switch (currentList) {
				case FIRST_LV:
					if (ticketList.size() < 2) {
						tv_fm_user_my_offset_ticket_mid_content
								.setVisibility(View.VISIBLE);
						ticketList.remove(positionLocal);
					} else {
						ticketList.remove(positionLocal);
						lv_fm_user_my_offset_ticket_mid_content
								.setAdapter(new MyListAdapter(ticketList));
					}
					break;

				case SECOND_LV:
					if (secondList.size() < 2) {
						tv_fm_user_my_offset_goods_mid_content
								.setVisibility(View.VISIBLE);
						secondList.remove(positionLocal);
					} else {
						secondList.remove(positionLocal);
						lv_fm_user_my_offset_goods_mid_content
								.setAdapter(new MyListAdapter(secondList));
					}
					setGoodsChildViewCheck(1);
					break;

				case THIRD_LV:
					if (thirdList.size() < 2) {
						tv_fm_user_my_offset_goods_mid_content2
								.setVisibility(View.VISIBLE);
						thirdList.remove(positionLocal);
					} else {
						thirdList.remove(positionLocal);
						lv_fm_user_my_offset_goods_mid_content2
								.setAdapter(new MyListAdapter(thirdList));
					}
					setGoodsChildViewCheck(2);
					break;

				case FOURTH_LV:
					if (fourthList.size() < 2) {
						tv_fm_user_my_offset_goods_mid_content3
								.setVisibility(View.VISIBLE);
						fourthList.remove(positionLocal);
					} else {
						fourthList.remove(positionLocal);
						lv_fm_user_my_offset_goods_mid_content3
								.setAdapter(new MyListAdapter(fourthList));
					}
					setGoodsChildViewCheck(3);
					break;

				default:
					break;
				}
				break;

			default:
				break;
			}
		}
	};

	class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id) {
			final TextView tvLocal = new TextView(context);
			// localList = new ArrayList<String>();
			switch (currentList) {
			case FIRST_LV:
				removeBeforeListInfo(localList);
				localList.addAll(ticketList);
				break;
			case SECOND_LV:
				removeBeforeListInfo(localList);
				localList.addAll(secondList);
				break;

			case THIRD_LV:
				removeBeforeListInfo(localList);
				localList.addAll(thirdList);
				break;

			case FOURTH_LV:
				removeBeforeListInfo(localList);
				localList.addAll(fourthList);
				break;

			default:
				break;
			}
			tvLocal.setText("确定要删除编号为 : " + localList.get(position)
					+ "的礼品卡么？（无法恢复）");
			new AlertDialog.Builder(context)
					.setTitle("删除礼品卡")
					.setView(tvLocal)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Message msg = Message.obtain();
									msg.obj = position;
									msg.what = DELETE_GIFTCARD;
									handler.sendMessage(msg);
								}
							}).setNegativeButton("取消", null).show();
		}
	}

	public void removeBeforeListInfo(List<String> localList) {
		for (int i = 0; i < localList.size(); i++) {
			localList.remove(i);
		}
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				setGoodsChildViewCheck(1);
				break;

			case 1:
				setGoodsChildViewCheck(2);
				break;

			case 2:
				setGoodsChildViewCheck(3);
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_my_offset, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		ticketList = new ArrayList<String>();
		secondList = new ArrayList<String>();
		thirdList = new ArrayList<String>();
		fourthList = new ArrayList<String>();
		localList = new ArrayList<String>();
		getLeftChildView();
	}

	private void getLeftChildView() {
		currentList = FIRST_LV;
		childView = View.inflate(context,
				R.layout.childview_user_fm_myoffset_ticket, null);
		ll_fm_user_my_offset_content.removeAllViews();
		ll_fm_user_my_offset_content.addView(childView);
		lv_fm_user_my_offset_ticket_mid_content = (ListView) childView
				.findViewById(R.id.lv_fm_user_my_offset_ticket_mid_content);
		tv_fm_user_my_offset_ticket_mid_content = (TextView) childView
				.findViewById(R.id.tv_fm_user_my_offset_ticket_mid_content);
		if (ticketList.size() != 0) {
			tv_fm_user_my_offset_ticket_mid_content.setVisibility(View.GONE);
		} else {
			tv_fm_user_my_offset_ticket_mid_content.setVisibility(View.VISIBLE);
		}
		lv_fm_user_my_offset_ticket_mid_content.setAdapter(new MyListAdapter(
				ticketList));
		lv_fm_user_my_offset_ticket_mid_content
				.setOnItemClickListener(new MyOnItemClickListener());
	}

	private void getRightChildView() {
		childView = View.inflate(context,
				R.layout.childview_user_fm_myoffset_goods, null);
		grandView1 = View.inflate(context,
				R.layout.chidview_user_fm_myoffset_goods_child1, null);
		grandView2 = View.inflate(context,
				R.layout.chidview_user_fm_myoffset_goods_child2, null);
		grandView3 = View.inflate(context,
				R.layout.chidview_user_fm_myoffset_goods_child3, null);
		pagerViewList = new ArrayList<View>();
		pagerViewList.add(grandView1);
		pagerViewList.add(grandView2);
		pagerViewList.add(grandView3);
		ll_fm_user_my_offset_content.removeAllViews();
		ll_fm_user_my_offset_content.addView(childView);
		vp_fm_user_my_offset_goods_cotent = (ViewPager) childView
				.findViewById(R.id.vp_fm_user_my_offset_goods_cotent);
		vp_fm_user_my_offset_goods_cotent.setAdapter(new MyPagerAdapter());
		tv_fm_user_my_offset_goods_mid_content = (TextView) grandView1
				.findViewById(R.id.tv_fm_user_my_offset_goods_mid_content);
		tv_fm_user_my_offset_goods_mid_content2 = (TextView) grandView2
				.findViewById(R.id.tv_fm_user_my_offset_goods_mid_content2);
		tv_fm_user_my_offset_goods_mid_content3 = (TextView) grandView3
				.findViewById(R.id.tv_fm_user_my_offset_goods_mid_content3);
		tv_fm_user_my_offset_goods_top_left = (TextView) childView
				.findViewById(R.id.tv_fm_user_my_offset_goods_top_left);
		tv_fm_user_my_offset_goods_top_mid = (TextView) childView
				.findViewById(R.id.tv_fm_user_my_offset_goods_top_mid);
		tv_fm_user_my_offset_goods_top_right = (TextView) childView
				.findViewById(R.id.tv_fm_user_my_offset_goods_top_right);
		lv_fm_user_my_offset_goods_mid_content = (ListView) grandView1
				.findViewById(R.id.lv_fm_user_my_offset_goods_mid_content);
		lv_fm_user_my_offset_goods_mid_content2 = (ListView) grandView2
				.findViewById(R.id.lv_fm_user_my_offset_goods_mid_content2);
		lv_fm_user_my_offset_goods_mid_content3 = (ListView) grandView3
				.findViewById(R.id.lv_fm_user_my_offset_goods_mid_content3);
		v_fm_user_my_offset_goods_top_left = childView
				.findViewById(R.id.v_fm_user_my_offset_goods_top_left);
		v_fm_user_my_offset_goods_top_mid = childView
				.findViewById(R.id.v_fm_user_my_offset_goods_top_mid);
		v_fm_user_my_offset_goods_top_right = childView
				.findViewById(R.id.v_fm_user_my_offset_goods_top_right);
		tv_fm_user_my_offset_goods_top_left.setOnClickListener(this);
		tv_fm_user_my_offset_goods_top_mid.setOnClickListener(this);
		tv_fm_user_my_offset_goods_top_right.setOnClickListener(this);
		lv_fm_user_my_offset_goods_mid_content
				.setOnItemClickListener(new MyOnItemClickListener());
		lv_fm_user_my_offset_goods_mid_content2
				.setOnItemClickListener(new MyOnItemClickListener());
		lv_fm_user_my_offset_goods_mid_content3
				.setOnItemClickListener(new MyOnItemClickListener());
		vp_fm_user_my_offset_goods_cotent
				.setOnPageChangeListener(new MyOnPageChangeListener());
		setGoodsChildViewCheck(1);
	}

	private void setGoodsChildViewCheck(int position) {
		if (secondList.size() != 0) {
			tv_fm_user_my_offset_goods_top_left.setText("未使用("
					+ secondList.size() + ")");
		} else {
			tv_fm_user_my_offset_goods_top_left.setText("未使用");
		}

		if (thirdList.size() != 0) {
			tv_fm_user_my_offset_goods_top_mid.setText("已使用("
					+ thirdList.size() + ")");
		} else {
			tv_fm_user_my_offset_goods_top_mid.setText("已使用");
		}

		if (fourthList.size() != 0) {
			tv_fm_user_my_offset_goods_top_right.setText("已过期("
					+ fourthList.size() + ")");
		} else {
			tv_fm_user_my_offset_goods_top_right.setText("已过期");
		}

		switch (position) {
		case 1:
			vp_fm_user_my_offset_goods_cotent.setCurrentItem(0);
			currentList = SECOND_LV;
			if (secondList.size() > 0) {
				tv_fm_user_my_offset_goods_mid_content.setVisibility(View.GONE);
			} else {
				tv_fm_user_my_offset_goods_mid_content
						.setVisibility(View.VISIBLE);
			}
			tv_fm_user_my_offset_goods_top_left.setTextColor(Color
					.parseColor("#0075c4"));
			tv_fm_user_my_offset_goods_top_mid.setTextColor(Color
					.parseColor("#000000"));
			tv_fm_user_my_offset_goods_top_right.setTextColor(Color
					.parseColor("#000000"));
			v_fm_user_my_offset_goods_top_left.setVisibility(View.VISIBLE);
			v_fm_user_my_offset_goods_top_mid.setVisibility(View.INVISIBLE);
			v_fm_user_my_offset_goods_top_right.setVisibility(View.INVISIBLE);
			lv_fm_user_my_offset_goods_mid_content
					.setAdapter(new MyListAdapter(secondList));
			break;

		case 2:
			vp_fm_user_my_offset_goods_cotent.setCurrentItem(1);
			if (thirdList.size() > 0) {
				tv_fm_user_my_offset_goods_mid_content2
						.setVisibility(View.GONE);
			} else {
				tv_fm_user_my_offset_goods_mid_content2
						.setVisibility(View.VISIBLE);
			}
			currentList = THIRD_LV;
			tv_fm_user_my_offset_goods_top_left.setTextColor(Color
					.parseColor("#000000"));
			tv_fm_user_my_offset_goods_top_mid.setTextColor(Color
					.parseColor("#0075c4"));
			tv_fm_user_my_offset_goods_top_right.setTextColor(Color
					.parseColor("#000000"));
			v_fm_user_my_offset_goods_top_left.setVisibility(View.INVISIBLE);
			v_fm_user_my_offset_goods_top_mid.setVisibility(View.VISIBLE);
			v_fm_user_my_offset_goods_top_right.setVisibility(View.INVISIBLE);
			lv_fm_user_my_offset_goods_mid_content2
					.setAdapter(new MyListAdapter(thirdList));
			break;

		case 3:
			vp_fm_user_my_offset_goods_cotent.setCurrentItem(2);
			if (fourthList.size() > 0) {
				tv_fm_user_my_offset_goods_mid_content3
						.setVisibility(View.GONE);
			} else {
				tv_fm_user_my_offset_goods_mid_content3
						.setVisibility(View.VISIBLE);
			}
			currentList = FOURTH_LV;
			tv_fm_user_my_offset_goods_top_left.setTextColor(Color
					.parseColor("#000000"));
			tv_fm_user_my_offset_goods_top_mid.setTextColor(Color
					.parseColor("#000000"));
			tv_fm_user_my_offset_goods_top_right.setTextColor(Color
					.parseColor("#0075c4"));
			v_fm_user_my_offset_goods_top_left.setVisibility(View.INVISIBLE);
			v_fm_user_my_offset_goods_top_mid.setVisibility(View.INVISIBLE);
			v_fm_user_my_offset_goods_top_right.setVisibility(View.VISIBLE);
			lv_fm_user_my_offset_goods_mid_content3
					.setAdapter(new MyListAdapter(fourthList));
			break;

		default:
			break;
		}

	}

	private void initOnClick() {
		iv_fm_user_my_offset_back.setOnClickListener(this);
		iv_fm_user_my_offset_top_left.setOnClickListener(this);
		iv_fm_user_my_offset_top_right.setOnClickListener(this);
		tv_fm_user_my_offset_bottom_add_card.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_my_offset_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_offset_back);
		iv_fm_user_my_offset_top_left = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_offset_top_left);
		iv_fm_user_my_offset_top_right = (ImageView) view
				.findViewById(R.id.iv_fm_user_my_offset_top_right);
		ll_fm_user_my_offset_content = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_my_offset_content);
		tv_fm_user_my_offset_bottom_add_card = (TextView) view
				.findViewById(R.id.tv_fm_user_my_offset_bottom_add_card);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_my_offset_back:
			getActivity().finish();
			break;

		case R.id.iv_fm_user_my_offset_top_left:
			iv_fm_user_my_offset_top_left
					.setImageResource(R.drawable.image_user_my_offset_ticket_on);
			iv_fm_user_my_offset_top_right
					.setImageResource(R.drawable.image_user_my_offset_goods_off);
			getLeftChildView();
			currentList = FIRST_LV;
			break;

		case R.id.iv_fm_user_my_offset_top_right:
			iv_fm_user_my_offset_top_left
					.setImageResource(R.drawable.image_user_my_offset_ticket_off);
			iv_fm_user_my_offset_top_right
					.setImageResource(R.drawable.image_user_my_offset_goods_on);
			getRightChildView();
			break;

		case R.id.tv_fm_user_my_offset_bottom_add_card:
			final EditText etLocal = new EditText(context);
			new AlertDialog.Builder(context)
					.setTitle("请输入礼品卡号码")
					.setView(etLocal)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Message msg = new Message();
									msg.what = ADD_OFFSET;
									msg.obj = etLocal.getText().toString()
											.trim();
									handler.sendMessage(msg);
									Log.e("TAG", "OFFSETSEND??");
								}
							}).setNegativeButton("取消", null).show();
			break;

		case R.id.tv_fm_user_my_offset_goods_top_left:
			setGoodsChildViewCheck(1);
			break;

		case R.id.tv_fm_user_my_offset_goods_top_mid:
			setGoodsChildViewCheck(2);
			break;

		case R.id.tv_fm_user_my_offset_goods_top_right:
			setGoodsChildViewCheck(3);
			break;

		default:
			break;
		}
	}

	class MyListAdapter extends BaseAdapter {
		List<String> localList = new ArrayList<String>();

		public MyListAdapter(List<String> ticketList) {
			localList = ticketList;
		}

		@Override
		public int getCount() {
			return localList.size();
		}

		@Override
		public Object getItem(int position) {
			return localList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			cViewHolder cHolder = new cViewHolder();
			if (convertView == null) {

				convertView = View.inflate(context,
						R.layout.item_fm_user_gift_card, null);
				cHolder.tvMyOffset = (TextView) convertView
						.findViewById(R.id.tv_item_fm_user_gift_card);
				convertView.setTag(cHolder);

			} else {
				cHolder = (cViewHolder) convertView.getTag();
			}
			String useName = localList.get(position);
			cHolder.tvMyOffset.setText(useName);
			return convertView;
		}
	}

	static class cViewHolder {
		TextView tvMyOffset;
	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pagerViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pagerViewList.get(position));
			return pagerViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
}
