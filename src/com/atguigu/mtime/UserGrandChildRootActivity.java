package com.atguigu.mtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;

import com.atguigu.mtime.user.grandchildview.AboutUsFragment;
import com.atguigu.mtime.user.grandchildview.AnnounceContrlFragment;
import com.atguigu.mtime.user.grandchildview.AreaFragment;
import com.atguigu.mtime.user.grandchildview.BackFlowFragment;
import com.atguigu.mtime.user.grandchildview.BalancePayFragment;
import com.atguigu.mtime.user.grandchildview.BroadReturnFragment;
import com.atguigu.mtime.user.grandchildview.ChangePasswordFragment;
import com.atguigu.mtime.user.grandchildview.CheckReceiveFragment;
import com.atguigu.mtime.user.grandchildview.HeadFragment;
import com.atguigu.mtime.user.grandchildview.InvoiceFragment;
import com.atguigu.mtime.user.grandchildview.NickNameFragment;
import com.atguigu.mtime.user.grandchildview.NomalQuestionFragment;
import com.atguigu.mtime.user.grandchildview.OffSetStateFragment;
import com.atguigu.mtime.user.grandchildview.OnLinePayFragment;
import com.atguigu.mtime.user.grandchildview.PhoneFragment;
import com.atguigu.mtime.user.grandchildview.PostageFragment;
import com.atguigu.mtime.user.grandchildview.RebackFragment;
import com.atguigu.mtime.user.grandchildview.ReceiveAddressFragment;
import com.atguigu.mtime.user.grandchildview.ServiceFragment;
import com.atguigu.mtime.user.grandchildview.SexFragment;
import com.atguigu.mtime.user.grandchildview.ShopFlowFragment;
import com.atguigu.mtime.user.grandchildview.SpeedFragment;
import com.atguigu.mtime.user.grandchildview.TellUsFragment;

public class UserGrandChildRootActivity extends FragmentActivity {

	private static final String GRANDPOSITION = "GRANDPOSITION";
	private static final int HEAD = 204;
	private static final int NICKNAME = 205;
	private static final int SEX = 206;
	private static final int PHONE = 207;
	private static final int CHANGEPASSWORD = 208;
	private static final int RECEIVEADDRESS = 209;
	private static final int SHOPFLOW = 210;
	private static final int NOMALQUESTION = 211;
	private static final int INVOICE = 212;
	private static final int TELLUS = 213;
	private static final int ONLINEPAY = 214;
	private static final int BALANCPAY = 215;
	private static final int OFFSETSTATE = 216;
	private static final int POSTAGE = 217;
	private static final int AREA = 218;
	private static final int SPEED = 219;
	private static final int CHECKRECEIVE = 220;
	private static final int SERVICE = 221;
	private static final int REBACK = 222;
	private static final int BACKFLOW = 223;
	private static final int BROADRETURN = 224;
	private static final int ANNOUNCECONTRL = 225;
	private static final int ABOUTUS = 226;
	private static final String qq = "TAG";

	private HeadFragment headFragment;
	private NickNameFragment nickNameFragment;
	private SexFragment sexFragment;
	private PhoneFragment phoneFragment;
	private ChangePasswordFragment changePasswordFragment;
	private ReceiveAddressFragment receiveAddressFragment;
	private ShopFlowFragment shopFlowFragment;
	private NomalQuestionFragment nomalQuestionFragment;
	private InvoiceFragment invoiceFragment;
	private TellUsFragment tellUsFragment;
	private OnLinePayFragment onLinePayFragment;
	private BalancePayFragment balancePayFragment;
	private OffSetStateFragment offSetStateFragment;
	private PostageFragment postageFragment;
	private AreaFragment areaFragment;
	private SpeedFragment speedFragment;
	private CheckReceiveFragment checkReceiveFragment;
	private ServiceFragment serviceFragment;
	private RebackFragment rebackFragment;
	private BackFlowFragment backFlowFragment;
	private BroadReturnFragment broadReturnFragment;
	private AnnounceContrlFragment announceContrlFragment;
	private AboutUsFragment aboutUsFragment;

	private FrameLayout fl_act_user_grand_child_root_main;
//	private FragmentTransaction beginTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_grand_child_root);
		initLayout();
		onNewIntent(getIntent());
	}

	private void initLayout() {
		fl_act_user_grand_child_root_main = (FrameLayout) findViewById(R.id.fl_act_user_grand_child_root_main);
//		beginTransaction = getSupportFragmentManager().beginTransaction();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		FragmentTransaction beginTransaction  = getSupportFragmentManager().beginTransaction();;
		Log.e(qq, "调用了 onNewIntent()");
		int position = intent.getIntExtra(GRANDPOSITION, -1);
		switch (position) {
		case HEAD:
			if (headFragment == null) {
				headFragment = new HeadFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						headFragment);
			} else {
				beginTransaction.show(headFragment);
			}
			break;

		case NICKNAME:
			if (nickNameFragment == null) {
				nickNameFragment = new NickNameFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						nickNameFragment);
			} else {
				beginTransaction.show(nickNameFragment);
			}
			break;

		case SEX:
			if (sexFragment == null) {
				sexFragment = new SexFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						sexFragment);
			} else {
				beginTransaction.show(sexFragment);
			}
			break;

		case PHONE:
			if (phoneFragment == null) {
				phoneFragment = new PhoneFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						phoneFragment);
			} else {
				beginTransaction.show(phoneFragment);
			}
			break;

		case CHANGEPASSWORD:
			if (changePasswordFragment == null) {
				changePasswordFragment = new ChangePasswordFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						changePasswordFragment);
			} else {
				beginTransaction.show(changePasswordFragment);
			}
			break;

		case RECEIVEADDRESS:
			if (receiveAddressFragment == null) {
				receiveAddressFragment = new ReceiveAddressFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						receiveAddressFragment);
			} else {
				beginTransaction.show(receiveAddressFragment);
			}
			break;

		case SHOPFLOW:
			if (shopFlowFragment == null) {
				shopFlowFragment = new ShopFlowFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						shopFlowFragment);
			} else {
				beginTransaction.show(shopFlowFragment);
			}
			break;

		case NOMALQUESTION:
			if (nomalQuestionFragment == null) {
				nomalQuestionFragment = new NomalQuestionFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						nomalQuestionFragment);
			} else {
				beginTransaction.show(nomalQuestionFragment);
			}
			break;

		case INVOICE:
			if (invoiceFragment == null) {
				invoiceFragment = new InvoiceFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						invoiceFragment);
			} else {
				beginTransaction.show(invoiceFragment);
			}
			break;

		case TELLUS:
			if (tellUsFragment == null) {
				tellUsFragment = new TellUsFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						tellUsFragment);
			} else {
				beginTransaction.show(tellUsFragment);
			}
			break;

		case ONLINEPAY:
			if (onLinePayFragment == null) {
				onLinePayFragment = new OnLinePayFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						onLinePayFragment);
			} else {
				beginTransaction.show(onLinePayFragment);
			}
			break;

		case BALANCPAY:
			if (balancePayFragment == null) {
				balancePayFragment = new BalancePayFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						balancePayFragment);
			} else {
				beginTransaction.show(balancePayFragment);
			}
			break;

		case OFFSETSTATE:
			if (offSetStateFragment == null) {
				offSetStateFragment = new OffSetStateFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						offSetStateFragment);
			} else {
				beginTransaction.show(offSetStateFragment);
			}
			break;

		case POSTAGE:
			if (postageFragment == null) {
				postageFragment = new PostageFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						postageFragment);
			} else {
				beginTransaction.show(postageFragment);
			}
			break;

		case AREA:
			if (areaFragment == null) {
				areaFragment = new AreaFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						areaFragment);
			} else {
				beginTransaction.show(areaFragment);
			}
			break;

		case SPEED:
			if (speedFragment == null) {
				speedFragment = new SpeedFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						speedFragment);
			} else {
				beginTransaction.show(speedFragment);
			}
			break;

		case CHECKRECEIVE:
			if (checkReceiveFragment == null) {
				checkReceiveFragment = new CheckReceiveFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						checkReceiveFragment);
			} else {
				beginTransaction.show(checkReceiveFragment);
			}
			break;

		case SERVICE:
			if (serviceFragment == null) {
				serviceFragment = new ServiceFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						serviceFragment);
			} else {
				beginTransaction.show(serviceFragment);
			}
			break;

		case REBACK:
			if (rebackFragment == null) {
				rebackFragment = new RebackFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						rebackFragment);
			} else {
				beginTransaction.show(checkReceiveFragment);
			}
			break;

		case BACKFLOW:
			if (backFlowFragment == null) {
				backFlowFragment = new BackFlowFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						backFlowFragment);
			} else {
				beginTransaction.show(backFlowFragment);
			}
			break;

		case BROADRETURN:
			if (broadReturnFragment == null) {
				broadReturnFragment = new BroadReturnFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						broadReturnFragment);
			} else {
				beginTransaction.show(broadReturnFragment);
			}
			break;

		case ANNOUNCECONTRL:
			if (announceContrlFragment == null) {
				announceContrlFragment = new AnnounceContrlFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						announceContrlFragment);
			} else {
				beginTransaction.show(announceContrlFragment);
			}
			break;

		case ABOUTUS:
			if (aboutUsFragment == null) {
				aboutUsFragment = new AboutUsFragment();
				beginTransaction.add(R.id.fl_act_user_grand_child_root_main,
						aboutUsFragment);
			} else {
				beginTransaction.show(aboutUsFragment);
			}
			break;

		default:
			break;
		}
		beginTransaction.commit();
	}
}
