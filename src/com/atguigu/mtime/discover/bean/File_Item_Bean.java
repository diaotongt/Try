package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 影评item的封装类
 * 
 * @author Mr lu
 */
public class File_Item_Bean {
	public int commentCount;
	public String content;
	public int id;
	public String nickname;
	public double rating;
	public RelatedObj relatedObj;
	public String summaryInfo;
	public String time;
	public String title;
	public String topImgUrl;
	public List<String> type;
	public String url;
	public String userImage;

	public class RelatedObj {
		public int id;
		public String image;
		public String name;
		public double rating;
		public String releaseDate;
		public String releaseLocation;
		public String runtime;
		public String title;
		public String titleCn;
		public String titleEn;
		public int type;
		public String url;
		public String wapUrl;
	}
}
