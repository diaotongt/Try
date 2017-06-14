package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 发现中排行榜ListView数据的封装类
 * 
 * @author Administrator
 *
 */
public class Discover_Ranklist_Bean {
	public int pageCount;
	public List<TopLists> topLists;
	public int totalCount;

	/**
	 * ListView中每个Item的数据
	 * 
	 * @author Administrator
	 *
	 */
	public class TopLists {
		public int id;
		public String summary;
		public String topListNameCn;
		public String topListNameEn;
		public int type;

		@Override
		public String toString() {
			return "TopLists [id=" + id + ", summary=" + summary
					+ ", topListNameCn=" + topListNameCn + ", topListNameEn="
					+ topListNameEn + ", type=" + type + "]";
		}

	}

	@Override
	public String toString() {
		return "Discover_Ranklist_Bean [pageCount=" + pageCount + ", topLists="
				+ topLists + ", totalCount=" + totalCount + "]";
	}

}
