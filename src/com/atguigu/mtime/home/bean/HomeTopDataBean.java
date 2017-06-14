package com.atguigu.mtime.home.bean;

import java.util.List;

/**
 * 首页上部JSON_Bean
 * @author Wangnan
 *
 */
public class HomeTopDataBean {

	public int count;
	public List<DetailMovie> movies;
	public int totalCinemaCount;
	public int totalComingMovie;
	public int totalHotMovie;
	
	public class DetailMovie{
		public String actorName1;
		public String actorName2;
		public String btnText;
		public String commonSpecial;
		public String directorName;
		public String img;
		public boolean is3D;
		public boolean isDMAX;
		public boolean isFilter;
		public boolean isHot;
		public boolean isIMAX;
		public boolean isIMAX3D;
		public boolean isNew;
		public int length;
		public int movieId;
		public NearestShowtime nearestShowtime;
		public int rDay;
		public int rMonth;
		public int rYear;
		public float ratingFinal;
		public String titleCn;
		public String titleEn;
		public String type;
		public int wantedCount;
	}
	
	public class NearestShowtime{
		public String isTicket;
		public int nearestCinemaCount;
		public int nearestShowDay;
		public int nearestShowtimeCount;
	}
}
