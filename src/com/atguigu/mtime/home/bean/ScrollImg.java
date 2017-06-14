package com.atguigu.mtime.home.bean;

import java.util.List;

public class ScrollImg {
	public List<Good> goods;

	public class Good {
		public String image;
		public String url;

		@Override
		public String toString() {
			return "Good [image=" + image + ", url=" + url + "]";
		}

	}

	@Override
	public String toString() {
		return "ScrollImg [goods=" + goods + "]";
	}

}
