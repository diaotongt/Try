package com.atguigu.mtime.discover.bean;

/**
 * 发现顶部数据封装类
 * 
 * @author Administrator
 *
 */
public class DisCoverTopBean {
	public Top_News news;// 新闻
	public Top_Review review;// 影评
	public TopList topList;// 排行榜
	public Top_Trailer trailer;// 预告片
	public Top_ViewingGuide viewingGuide;

	/**
	 * 发现中新闻顶部的数据
	 * 
	 * @author Administrator
	 *
	 */
	public class Top_News {
		public String imageUrl;
		public int newsID;
		public String title;
		public int type;

		@Override
		public String toString() {
			return "Top_News [imageUrl=" + imageUrl + ", newsID=" + newsID
					+ ", title=" + title + ", type=" + type + "]";
		}

	}

	/**
	 * 发现中影评顶部的数据
	 * 
	 * @author Administrator
	 *
	 */
	public class Top_Review {
		public String imageUrl;
		public String movieName;
		public String posterUrl;
		public int reviewID;
		public String title;

		@Override
		public String toString() {
			return "Top_Review [imageUrl=" + imageUrl + ", movieName="
					+ movieName + ", posterUrl=" + posterUrl + ", reviewID="
					+ reviewID + ", title=" + title + "]";
		}

	}

	/**
	 * 发现中排行榜顶部的数据
	 * 
	 * @author Administrator
	 *
	 */
	public class TopList {
		public int id;
		public String imageUrl;
		public String title;
		public int type;

		@Override
		public String toString() {
			return "TopList [id=" + id + ", imageUrl=" + imageUrl + ", title="
					+ title + ", type=" + type + "]";
		}

	}

	/**
	 * 发现中预告片顶部的数据
	 * 
	 * @author Administrator
	 *
	 */
	public class Top_Trailer {
		public String hightUrl;
		public String imageUrl;
		public int movieId;
		public String mp4Url;
		public String title;
		public int trailerID;

		@Override
		public String toString() {
			return "Top_Trailer [hightUrl=" + hightUrl + ", imageUrl="
					+ imageUrl + ", movieId=" + movieId + ", mp4Url=" + mp4Url
					+ ", title=" + title + ", trailerID=" + trailerID + "]";
		}

	}

	/**
	 * 暂时不知道是什么
	 * 
	 * @author Administrator
	 *
	 */
	public class Top_ViewingGuide {
		public int id;
		public String imageUrl;

		@Override
		public String toString() {
			return "Top_ViewingGuide [id=" + id + ", imageUrl=" + imageUrl
					+ "]";
		}

	}

	@Override
	public String toString() {
		return "DisCoverTopBean [news=" + news + ", review=" + review
				+ ", topList=" + topList + ", trailer=" + trailer
				+ ", viewingGuide=" + viewingGuide + "]";
	}

}
