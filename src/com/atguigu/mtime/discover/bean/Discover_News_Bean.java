package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 发现中新闻ListView数据的封装类
 * 
 * @author Administrator
 *
 */
public class Discover_News_Bean {
	public List<NewsList> newsList;
	public int pageCount;
	public int totalCount;

	// 新闻ListView每个item数据的封装类
	public class NewsList {
		public int commentCount;
		public int id;
		public String image;
		public int publishTime;
		public String summary;
		public String summaryInfo;
		public String tag;
		public String title;
		public String title2;
		public int type;
		public List<News_Images> images;

		@Override
		public String toString() {
			return "News [commentCount=" + commentCount + ", id=" + id
					+ ", image=" + image + ", publishTime=" + publishTime
					+ ", summary=" + summary + ", summaryInfo=" + summaryInfo
					+ ", tag=" + tag + ", title=" + title + ", title2="
					+ title2 + ", type=" + type + ", images=" + images + "]";
		}

	}

	// item中有的有多张图片，每个图片的封装类
	public class News_Images {
		public String desc;
		public int gId;
		public String title;
		public String url1;
		public String url2;

		@Override
		public String toString() {
			return "News_Images [desc=" + desc + ", gId=" + gId + ", title="
					+ title + ", url1=" + url1 + ", url2=" + url2 + "]";
		}

	}

	@Override
	public String toString() {
		return "Discover_News_Bean [newsList=" + newsList + ", pageCount="
				+ pageCount + ", totalCount=" + totalCount + "]";
	}

}
