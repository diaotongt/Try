package com.atguigu.mtime.payticket.bean;

import java.util.List;

/**
 * 即将上映Bean
 * @author Wangnan
 *
 */
public class PayTicketComingBean {

	public List<MovieInfo> attention;
	public List<MovieInfo> moviecomings;
	
	public class MovieInfo{
		public String actor1;
		public String actor2;
		public String director;
		public int id;
		public String image;
		public boolean isFilter;
		public boolean isTicket;
		public boolean isVideo;
		public String locationName;
		public int rDay;
		public int rMonth;
		public int rYear;
		public String releaseDate;
		public String title;
		public String type;
		public int videoCount;
		public List<Video> videos;
		public int wantedCount;
	}
	
	public class Video{
		public String hightUrl;
		public String image;
		public int length;
		public String title;
		public String url;
		public int videoId;
	}
}
