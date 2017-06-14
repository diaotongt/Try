package com.atguigu.mtime.discover.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 发现3个排行榜的数据
 * 
 * @author Mr lu
 *
 */
public class Discover_TOP_Bean implements Serializable{
	public List<TopList1> topList;

	public class TopList1 {
		public String pageSubAreaId;
		public ArrayList<SubTopList> subTopList;
		public String title;
		public String titleSmall;
	}

	public class SubTopList implements Serializable {
		public String pageSubAreaId;
		public String title;
		public String titleSmall;
	}
}
