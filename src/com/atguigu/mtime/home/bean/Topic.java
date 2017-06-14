package com.atguigu.mtime.home.bean;

import java.util.List;

public class Topic {

	public List<Good> goods;

	public class Good {
		public String backgroupImage;
		public String checkedImage;
		public int goodsId;
		public List<SubGood> subList;
		public String titleCn;
		public String titleEn;
		public String uncheckImage;
		public String url;
	}

	public class SubGood {
		public int goodsId;
		public String image;
		public String title;
		public String url;
	}

}
