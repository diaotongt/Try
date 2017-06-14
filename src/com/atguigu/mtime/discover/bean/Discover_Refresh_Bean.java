package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 发现下拉刷新的界面数据的封装类
 * 
 * @author Lupeng
 *
 */
public class Discover_Refresh_Bean {
	public List<AdvWordList> advWordList;
	public Coupon coupon;
	public Startups startups;

	public class AdvWordList {
		public String movieName;
		public String word;
	}

	public class Coupon {
		public List activities;
		public String error;
		public boolean success;
	}

	public class Startups {
		public LoadingIcon LoadingIcon;
		public String allowHost;
		public BottomMarketIcon bottomMarketIcon;
		public List bottomOtherIcons;
		public String imageProxy;
		public boolean isCheckHost;
		public boolean isEditGender;
		public String mallDomain;
		public String newPeopleGiftImage;
		public String registerEmailUrl;
	}

	public class LoadingIcon {

	}

	public class BottomMarketIcon {
		public String img;
		public String selectedImg;
		public String text;
	}
}
