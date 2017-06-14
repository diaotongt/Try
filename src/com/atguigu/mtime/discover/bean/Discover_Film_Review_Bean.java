package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 发现影评ListView数据的封装类
 * 
 * @author Administrator
 *
 */
public class Discover_Film_Review_Bean {
	public int id;
	public String nickname;
	public String rating;
	public RelatedObj relatedObj;
	public String summary;
	public String title;
	public String userImage;

	public class RelatedObj {
		public int id;
		public String image;
		public String rating;
		public String title;
		public int type;

		@Override
		public String toString() {
			return "RelatedObj [id=" + id + ", image=" + image + ", rating="
					+ rating + ", title=" + title + ", type=" + type + "]";
		}

	}

	@Override
	public String toString() {
		return "Discover_Film_Review_Bean [id=" + id + ", nickname=" + nickname
				+ ", rating=" + rating + ", relatedObj=" + relatedObj
				+ ", summary=" + summary + ", title=" + title + ", userImage="
				+ userImage + "]";
	}

}
