package com.atguigu.mtime.home.bean;

import java.util.List;

public class BottomMall {

	public int count;
	public String goodsIds;
	public List<BottomMallGood> goodsList;
	public int pageCount;

	public class BottomMallGood {
		public String background;
		public int goodsId;
		public String iconText;
		public String image;
		public String longName;
		public int marketPrice;
		public int minSalePrice;
		public String name;
		public String url;
	}

}
