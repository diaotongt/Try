package com.atguigu.mtime.user.childview;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.CacheUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GiftCardFragment extends Fragment implements OnClickListener {

	protected static final int ADD_GIFTCARD = 0;
	protected static final int DELETE_GIFTCARD = 1;
	private static String UserName;
	private static Context context;
	private View view;
	private ImageView iv_fm_user_gift_card_back;
	private TextView tv_fm_user_gift_card_bottom_add_card;
	private List<String> giftCardList;
	private ListView lv_fm_user_gift_card_mid_content;
	private TextView tv_fm_user_gift_card_mid_content;
	private MyListAdapter myListAdapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ADD_GIFTCARD:
				String newCard = (String) msg.obj;
				if (newCard.equals("") || newCard == null) {
					return;
				}
				tv_fm_user_gift_card_mid_content.setVisibility(View.GONE);
				giftCardList.add(0, newCard);
				myListAdapter.notifyDataSetChanged();
				break;

			case DELETE_GIFTCARD:
				int positionLocal = (Integer) msg.obj;
				if (giftCardList.size() < 2) {
					tv_fm_user_gift_card_mid_content
							.setVisibility(View.VISIBLE);
					giftCardList.remove(positionLocal);
				} else {
					giftCardList.remove(positionLocal);
					myListAdapter.notifyDataSetChanged();
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
			tvLocal.setText("确定要删除编号为 : " + giftCardList.get(position)
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_gift_card, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		giftCardList = new ArrayList<String>();
		myListAdapter = new MyListAdapter();
		lv_fm_user_gift_card_mid_content.setAdapter(myListAdapter);
		lv_fm_user_gift_card_mid_content
				.setOnItemClickListener(new MyOnItemClickListener());
	}

	private void initOnClick() {
		iv_fm_user_gift_card_back.setOnClickListener(this);
		tv_fm_user_gift_card_bottom_add_card.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_gift_card_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_gift_card_back);
		tv_fm_user_gift_card_bottom_add_card = (TextView) view
				.findViewById(R.id.tv_fm_user_gift_card_bottom_add_card);
		lv_fm_user_gift_card_mid_content = (ListView) view
				.findViewById(R.id.lv_fm_user_gift_card_mid_content);
		tv_fm_user_gift_card_mid_content = (TextView) view
				.findViewById(R.id.tv_fm_user_gift_card_mid_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_gift_card_back:
			getActivity().finish();
			break;

		case R.id.tv_fm_user_gift_card_bottom_add_card:
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
									msg.what = ADD_GIFTCARD;
									msg.obj = etLocal.getText().toString()
											.trim();
									handler.sendMessage(msg);
								}
							}).setNegativeButton("取消", null).show();
			break;

		default:
			break;
		}
	}

	class MyListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return giftCardList.size();
		}

		@Override
		public Object getItem(int position) {
			return giftCardList.get(position);
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
				cHolder.tvGiftCard = (TextView) convertView
						.findViewById(R.id.tv_item_fm_user_gift_card);
				convertView.setTag(cHolder);

			} else {
				cHolder = (cViewHolder) convertView.getTag();
			}
			String useName = giftCardList.get(position);
			cHolder.tvGiftCard.setText(useName);
			return convertView;
		}
	}

	static class cViewHolder {
		TextView tvGiftCard;
	}
}
