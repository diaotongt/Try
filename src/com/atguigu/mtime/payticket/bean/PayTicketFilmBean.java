package com.atguigu.mtime.payticket.bean;

import java.util.List;

/**
 * 热门电影Bean
 * @author Wangnan
 *
 */
public class PayTicketFilmBean {
	public String bImg;
	public String date;
	public int lid;
	public List<FilmDetail> ms;
	public int newActivitiesTime;
	public int totalComingMovie;
	public String voucherMsg;
	
	public class FilmDetail{
		public int NearestCinemaCount;
		public long NearestDay;
		public int NearestShowtimeCount;
		public String aN1;
		public String aN2;
		public int cC;
		public String commonSpecial;
		public String d;
		public String dN;
		public int id;
		public String img;
		public boolean is3D;
		public boolean isDMAX;
		public boolean isFilter;
		public boolean isHot;
		public boolean isIMAX;
		public boolean isIMAX3D;
		public boolean isNew;
		public boolean isTicket;
		public String movieType;
		public List<MovieType> p;
		public double r;
		public int rc;
		public String rd;
		public int rsC;
		public int sC;
		public String t; 
		public String tCn;
		public String tEn;
		public int ua;
		public List<Version> versions;
		public int wantedCount;
	}
	
	public class MovieType{
		public String p0;
		public String p1;
		public String p2;
	}
	
	public class Version{
		public int version_enum;
		public String version;
	}
	
	
}
