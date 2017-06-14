package com.atguigu.mtime.home.bean;

import java.util.List;

public class Category {

	public String colorValue;
	public int goodsId;
	public String image;
	public String imageUrl;
	public String moreUrl;
	public String name;
	public List<Good> subList;
	
	public class Good{
		public int goodsId;
		public String image;
		public String title;
		public String url;
	}
	
}
