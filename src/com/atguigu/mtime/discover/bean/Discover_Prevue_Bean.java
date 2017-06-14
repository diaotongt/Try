package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 发现预告片ListView数据的封装类
 * 
 * @author Administrator
 *
 */
public class Discover_Prevue_Bean {
	public List<Prevue_trailers> trailers;

	/**
	 * 每个Item的数据
	 * 
	 * @author Administrator
	 *
	 */
	public class Prevue_trailers {
		public String coverImg;
		public String hightUrl;
		public int id;
		public int movieId;
		public String movieName;
		public float rating;
		public String summary;
		public List<String> type;
		public String url;
		public int videoLength;
		public String videoTitle;

		@Override
		public String toString() {
			return "Prevue_trailers [coverImg=" + coverImg + ", hightUrl="
					+ hightUrl + ", id=" + id + ", movieId=" + movieId
					+ ", movieName=" + movieName + ", rating=" + rating
					+ ", summary=" + summary + ", type=" + type + ", url="
					+ url + ", videoLength=" + videoLength + ", videoTitle="
					+ videoTitle + "]";
		}

	}

	@Override
	public String toString() {
		return "Discover_Prevue_Bean [trailers=" + trailers + "]";
	}

}
