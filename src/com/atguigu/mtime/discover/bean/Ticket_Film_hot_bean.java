package com.atguigu.mtime.discover.bean;

import java.io.Serializable;
import java.util.List;

public class Ticket_Film_hot_bean implements Serializable {

	private static final long serialVersionUID = -1300710102518019043L;
	public String bImg;
	public String date;
	public int lid;
	public List<Hot_Info> ms;

	public static class Hot_Info {
		public int NearestCinemaCount;
		public long NearestDay;
		public int NearestShowtimeCount;
		public String commonSpecial;
		public String img;

		public String t;
		public float r;
		public String rd;
	}

}
