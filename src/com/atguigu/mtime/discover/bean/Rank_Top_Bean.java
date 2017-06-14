package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 时光Top100的封装类
 * 
 * @author Mr lu
 *
 */
public class Rank_Top_Bean {
	public List<Top_Movies> movies;
	public NextTopList nextTopList;
	public int pageCount;
	public PreviousTopList previousTopList;
	public Top_TopList topList;
	public int totalCount;

	public class Top_Movies {
		public String actor;
		public int decade;
		public String director;
		public int id;
		public boolean isPresell;
		public boolean isTicket;
		public String movieType;
		public String name;
		public String nameEn;
		public String posterUrl;
		public String rankNum;
		public double rating;
		public String releaseDate;
		public String releaseLocation;
		public String remark;

		/**
		 * 新加的两项
		 */
		public String totalBoxOffice;
		public String weekBoxOffice;
	}

	public class NextTopList {
		public int toplistId;
		public int toplistItemType;
		public int toplistType;
	}

	public class PreviousTopList {
		public int toplistId;
		public int toplistItemType;
		public int toplistType;
	}

	public class Top_TopList {
		public int id;
		public String name;
		public String summary;
		public int totalCount;
	}
}
