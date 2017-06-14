package com.atguigu.mtime.discover.bean;

import java.util.List;

/**
 * 排行榜中item的封装类
 * 
 * @author lupeng
 *
 */
public class Ranklist_Item_Bean {
	public List<Movies> movies;
	public NextTopList nextTopList;
	public int pageCount;
	public PreviousTopList previousTopList;
	public TopList topList;
	public int totalCount;
	public List<Persons> persons;

	public class Movies {
		public String actor;
		public String actor2;
		public int decade;
		public String director;
		public int id;
		public String movieType;
		public String name;
		public String nameEn;
		public String posterUrl;
		public int rankNum;
		public double rating;
		public String releaseDate;
		public String releaseLocation;
		public String remark;

		@Override
		public String toString() {
			return "Movies [actor=" + actor + ", actor2=" + actor2
					+ ", decade=" + decade + ", director=" + director + ", id="
					+ id + ", movieType=" + movieType + ", name=" + name
					+ ", nameEn=" + nameEn + ", posterUrl=" + posterUrl
					+ ", rankNum=" + rankNum + ", rating=" + rating
					+ ", releaseDate=" + releaseDate + ", releaseLocation="
					+ releaseLocation + ", remark=" + remark + "]";
		}

	}

	public class NextTopList {
		public int toplistId;
		public int toplistItemType;
		public int toplistType;

		@Override
		public String toString() {
			return "NextTopList [toplistId=" + toplistId + ", toplistItemType="
					+ toplistItemType + ", toplistType=" + toplistType + "]";
		}

	}

	public class PreviousTopList {
		public int toplistId;
		public int toplistItemType;
		public int toplistType;

		@Override
		public String toString() {
			return "PreviousTopList [toplistId=" + toplistId
					+ ", toplistItemType=" + toplistItemType + ", toplistType="
					+ toplistType + "]";
		}

	}

	public class TopList {
		public int id;
		public String name;
		public String summary;

		@Override
		public String toString() {
			return "TopList [id=" + id + ", name=" + name + ", summary="
					+ summary + "]";
		}

	}

	public class Persons {
		public String birthDay;
		public String birthLocation;
		public int birthYear;
		public String constellation;
		public int id;
		public String nameCn;
		public String nameEn;
		public String posterUrl;
		public int rankNum;
		public double rating;
		public String sex;
		public String summary;

		@Override
		public String toString() {
			return "Persons [birthDay=" + birthDay + ", birthLocation="
					+ birthLocation + ", birthYear=" + birthYear
					+ ", constellation=" + constellation + ", id=" + id
					+ ", nameCn=" + nameCn + ", nameEn=" + nameEn
					+ ", posterUrl=" + posterUrl + ", rankNum=" + rankNum
					+ ", rating=" + rating + ", sex=" + sex + ", summary="
					+ summary + "]";
		}

	}

	@Override
	public String toString() {
		return "Ranklist_Item_Bean [movies=" + movies + ", nextTopList="
				+ nextTopList + ", pageCount=" + pageCount
				+ ", previousTopList=" + previousTopList + ", topList="
				+ topList + ", totalCount=" + totalCount + ", persons="
				+ persons + "]";
	}

}
