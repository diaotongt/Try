package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 新闻中item的封装类
 * 
 * @author lupeng
 */
public class News_Item_Bean {
	public String author;
	public int commentCount;
	public String content;
	public String editor;
	public int id;
	public int nextNewsID;
	public int previousNewsID;
	public List<News_Item_Relations> relations;
	public String source;
	public String time;
	public String title;
	public String title2;
	public int type;
	public String url;
	public String wapUrl;

	public class News_Item_Relations {
		public int id;
		public String image;
		public String name;
		public double rating;
		public String relaseLocation;
		public String releaseDate;
		public int scoreCount;
		public int type;
		public String year;
	}
}
